package site.hanschen.bytecode.java.model;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class FieldInfo {

    public short accessFlags;
    public int nameIndex;
    public int descriptionIndex;
    public int attributesCount;
    public AttributeInfo[] attributeInfos;

    public static FieldInfo readFrom(ByteBuffer buffer) {
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.accessFlags = buffer.getShort();
        fieldInfo.nameIndex = buffer.getShort() & 0xffff;
        fieldInfo.descriptionIndex = buffer.getShort() & 0xffff;
        fieldInfo.attributesCount = buffer.getShort() & 0xffff;
        if (fieldInfo.attributesCount > 0) {
            fieldInfo.attributeInfos = new AttributeInfo[fieldInfo.attributesCount];
            for (int i = 0; i < fieldInfo.attributesCount; i++) {
                fieldInfo.attributeInfos[i] = AttributeInfo.readFrom(buffer);
            }
        }
        return fieldInfo;
    }
}
