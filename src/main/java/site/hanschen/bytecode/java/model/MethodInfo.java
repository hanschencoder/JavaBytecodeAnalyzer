package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.model.attribute.AttributeInfo;
import site.hanschen.bytecode.java.model.constant.ConstantElement;
import site.hanschen.bytecode.java.utils.Logger;

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
    public AttributeInfo[] attributeInfo;

    public static MethodInfo readFrom(ByteBuffer buffer, ConstantElement[] constantPool) {
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.accessFlags = buffer.getShort();
        methodInfo.nameIndex = buffer.getShort() & 0xffff;
        methodInfo.descriptionIndex = buffer.getShort() & 0xffff;
        methodInfo.attributesCount = buffer.getShort() & 0xffff;
        if (methodInfo.attributesCount > 0) {
            methodInfo.attributeInfo = new AttributeInfo[methodInfo.attributesCount];
            for (int i = 0; i < methodInfo.attributesCount; i++) {
                AttributeInfo attribute = AttributeInfo.readFrom(buffer, constantPool);
                methodInfo.attributeInfo[i] = attribute;
                Logger.d(String.format("attribute:%s, value:%s", attribute.getAttributeName(), attribute.getComment()));
            }
        }
        return methodInfo;
    }
}
