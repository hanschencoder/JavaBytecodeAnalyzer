package site.hanschen.bytecode.java.model.attribute;

import site.hanschen.bytecode.java.model.constant.ConstantElement;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class SignatureAttribute extends AttributeInfo {

    public int signatureIndex;

    public SignatureAttribute(int attributeNameIndex, long attributeLength, ConstantElement[] constantPool) {
        super(attributeNameIndex, attributeLength, constantPool);
    }

    @Override
    public String getValue() {
        return "#" + signatureIndex;
    }

    @Override
    public String getComment() {
        return constantPool[signatureIndex].getValue();
    }

    public static class Parser implements AttributeParser<SignatureAttribute> {

        @Override
        public boolean match(String attributeName) {
            return "Signature".equals(attributeName);
        }

        @Override
        public SignatureAttribute create(ByteBuffer buffer, ConstantElement[] constantPool) {
            int attributeNameIndex = buffer.getShort() & 0xffff;
            long attributeLength = buffer.getInt() & 0xffffffffL;
            int signatureIndex = buffer.getShort() & 0xffff;

            SignatureAttribute attribute = new SignatureAttribute(attributeNameIndex, attributeLength, constantPool);
            attribute.signatureIndex = signatureIndex;
            return attribute;
        }
    }
}
