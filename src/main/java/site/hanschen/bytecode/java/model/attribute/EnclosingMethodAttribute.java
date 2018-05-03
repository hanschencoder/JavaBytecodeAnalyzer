package site.hanschen.bytecode.java.model.attribute;

import site.hanschen.bytecode.java.model.constant.ConstantElement;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class EnclosingMethodAttribute extends AttributeInfo {

    public int classIndex;
    public int methodIndex;

    public EnclosingMethodAttribute(int attributeNameIndex, long attributeLength, ConstantElement[] constantPool) {
        super(attributeNameIndex, attributeLength, constantPool);
    }

    @Override
    public String getValue() {
        return "#" + classIndex;
    }

    @Override
    public String getComment() {
        return constantPool[classIndex].getValue();
    }

    public static class Parser implements AttributeParser<EnclosingMethodAttribute> {

        @Override
        public boolean match(String attributeName) {
            return "EnclosingMethod".equals(attributeName);
        }

        @Override
        public EnclosingMethodAttribute create(ByteBuffer buffer, ConstantElement[] constantPool) {
            int attributeNameIndex = buffer.getShort() & 0xffff;
            long attributeLength = buffer.getInt() & 0xffffffffL;
            int classIndex = buffer.getShort() & 0xffff;
            int methodIndex = buffer.getShort() & 0xffff;

            EnclosingMethodAttribute attribute = new EnclosingMethodAttribute(attributeNameIndex, attributeLength, constantPool);
            attribute.classIndex = classIndex;
            attribute.methodIndex = methodIndex;
            return attribute;
        }
    }
}
