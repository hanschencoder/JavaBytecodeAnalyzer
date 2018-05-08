package site.hanschen.bytecode.java.model.attribute;

import site.hanschen.bytecode.java.model.constant.Constant;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class UnknownAttribute extends AttributeInfo {

    public byte[] data;

    public UnknownAttribute(int attributeNameIndex, long attributeLength, Constant[] constantPool) {
        super(attributeNameIndex, attributeLength, constantPool);
    }

    @Override
    public String getValue() {
        return "Unknown";
    }

    @Override
    public String getComment() {
        return null;
    }

    public static class Parser implements AttributeParser<UnknownAttribute> {

        @Override
        public boolean match(String attributeName) {
            // always return true.
            return true;
        }

        @Override
        public UnknownAttribute create(ByteBuffer buffer, Constant[] constantPool) {
            int attributeNameIndex = buffer.getShort() & 0xffff;
            long attributeLength = buffer.getInt() & 0xffffffffL;
            byte[] data = new byte[(int) attributeLength];
            buffer.get(data);

            UnknownAttribute attribute = new UnknownAttribute(attributeNameIndex, attributeLength, constantPool);
            attribute.data = data;
            return attribute;
        }
    }
}
