package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.model.attribute.AttributeInfo;
import site.hanschen.bytecode.java.model.constant.Constant;
import site.hanschen.bytecode.java.utils.Logger;

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
    public AttributeInfo[] attributeInfo;

    public static FieldInfo readFrom(ByteBuffer buffer, Constant[] constantPool) {
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.accessFlags = buffer.getShort();
        fieldInfo.nameIndex = buffer.getShort() & 0xffff;
        fieldInfo.descriptionIndex = buffer.getShort() & 0xffff;
        fieldInfo.attributesCount = buffer.getShort() & 0xffff;
        if (fieldInfo.attributesCount > 0) {
            fieldInfo.attributeInfo = new AttributeInfo[fieldInfo.attributesCount];
            for (int i = 0; i < fieldInfo.attributesCount; i++) {
                AttributeInfo attribute = AttributeInfo.readFrom(buffer, constantPool);
                fieldInfo.attributeInfo[i] = attribute;
                Logger.d(String.format("attribute:%s, value:%s", attribute.getAttributeName(), attribute.getComment()));
            }
        }
        return fieldInfo;
    }
}
