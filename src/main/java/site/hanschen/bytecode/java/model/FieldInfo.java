package site.hanschen.bytecode.java.model;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class FieldInfo {

    /**
     * 字段访问标记
     */
    public short accessFlags;
    /**
     * 指向字段名称
     */
    public int nameIndex;
    /**
     * 指向字段描述符
     */
    public int descriptionIndex;
    /**
     * 字段属性数量
     */
    public int attributesCount;
    /**
     * ConstantValue、Synthetic、Signature、Deprecated、RuntimeVisibleAnnotations、RuntimeInvisibleAnnotations
     */
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
