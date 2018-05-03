package site.hanschen.bytecode.java.model.attribute;

import site.hanschen.bytecode.java.model.constant.ConstantElement;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class CodeAttribute extends AttributeInfo {

    public int maxStack;
    public int maxLocals;
    public long codeLength;
    public byte[] code;
    public int exceptionTableLength;
    public ExceptionTable[] exceptionTable;
    public int attributesCount;
    public AttributeInfo[] attributeInfo;

    public CodeAttribute(int attributeNameIndex, long attributeLength, ConstantElement[] constantPool) {
        super(attributeNameIndex, attributeLength, constantPool);
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public String getComment() {
        return null;
    }

    public static class ExceptionTable {
        public int startPc;
        public int endPc;
        public int handlerPc;
        public int catchType;
    }

    public static class Parser implements AttributeParser<CodeAttribute> {

        @Override
        public boolean match(String attributeName) {
            return "Code".equals(attributeName);
        }

        @Override
        public CodeAttribute create(ByteBuffer buffer, ConstantElement[] constantPool) {
            int attributeNameIndex = buffer.getShort() & 0xffff;
            long attributeLength = buffer.getInt() & 0xffffffffL;
            int maxStack = buffer.getShort() & 0xffff;
            int maxLocals = buffer.getShort() & 0xffff;
            long codeLength = buffer.getInt() & 0xffffffffL;
            byte[] code = new byte[(int) codeLength];
            buffer.get(code);
            int exceptionTableLength = buffer.getShort() & 0xffff;
            ExceptionTable[] exceptionTable = null;
            if (exceptionTableLength > 0) {
                exceptionTable = new ExceptionTable[exceptionTableLength];
                for (int i = 0; i < exceptionTableLength; i++) {
                    ExceptionTable table = new ExceptionTable();
                    table.startPc = buffer.getShort() & 0xffff;
                    table.endPc = buffer.getShort() & 0xffff;
                    table.handlerPc = buffer.getShort() & 0xffff;
                    table.catchType = buffer.getShort() & 0xffff;
                    exceptionTable[i] = table;
                }
            }
            int attributesCount = buffer.getShort() & 0xffff;
            AttributeInfo[] attributeInfo = null;
            if (attributesCount > 0) {
                attributeInfo = new AttributeInfo[attributesCount];
                for (int i = 0; i < attributesCount; i++) {
                    AttributeInfo attribute = AttributeInfo.readFrom(buffer, constantPool);
                    attributeInfo[i] = attribute;
                }
            }

            CodeAttribute attribute = new CodeAttribute(attributeNameIndex, attributeLength, constantPool);
            attribute.maxStack = maxStack;
            attribute.maxLocals = maxLocals;
            attribute.codeLength = codeLength;
            attribute.code = code;
            attribute.exceptionTableLength = exceptionTableLength;
            attribute.exceptionTable = exceptionTable;
            attribute.attributesCount = attributesCount;
            attribute.attributeInfo = attributeInfo;
            return attribute;
        }
    }
}
