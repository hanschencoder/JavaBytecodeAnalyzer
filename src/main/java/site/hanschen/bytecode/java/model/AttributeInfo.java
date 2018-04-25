package site.hanschen.bytecode.java.model;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class AttributeInfo {

    public int attributeNameIndex;
    public long attributeLength;
    public byte[] info;

    public static AttributeInfo readFrom(ByteBuffer buffer) {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.attributeNameIndex = buffer.getShort() & 0xffff;
        attributeInfo.attributeLength = buffer.getInt() & 0xffffffffL;
        // FIXME: byte length required int type.
        attributeInfo.info = new byte[(int) attributeInfo.attributeLength];
        buffer.get(attributeInfo.info);
        return attributeInfo;
    }
}
