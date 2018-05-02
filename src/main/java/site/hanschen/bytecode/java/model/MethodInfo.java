package site.hanschen.bytecode.java.model;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class MethodInfo {

    /**
     * 方法访问标记
     */
    public short accessFlags;
    /**
     * 指向方法名称
     */
    public int nameIndex;
    /**
     * 指向方法签名
     */
    public int descriptionIndex;
    /**
     * 方法属性数量
     */
    public int attributesCount;
    /**
     * Code、Exceptions、Synthetic、Signature、Deprecated、RuntimeVisibleAnnotations
     * RuntimeInvisibleAnnotations、RuntimeVisibleParameterAnnotations、RuntimeInvisibleParameterAnnotations
     * AnnotationDefault
     */
    public AttributeInfo[] attributeInfos;

    public static MethodInfo readFrom(ByteBuffer buffer) {
        MethodInfo fieldInfo = new MethodInfo();
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
