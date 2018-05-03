package site.hanschen.bytecode.java.model.attribute;

import site.hanschen.bytecode.java.model.constant.ConstantElement;

import java.nio.ByteBuffer;

/**
 * 表示这个字段， 方法或类已经过时
 * 这个属性用来支持源文件中的 @deprecated 注解。如果在源文件中为一个字段， 方法或类标注了 @deprecated 注解
 * 那么编译器就会在 class 文件中为这个字段， 方法或类生成一个 Deprecated 属性
 *
 * @author chenhang
 */
public class DeprecatedAttribute extends AttributeInfo {

    public DeprecatedAttribute(int attributeNameIndex, long attributeLength, ConstantElement[] constantPool) {
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

    public static class Parser implements AttributeParser<DeprecatedAttribute> {

        @Override
        public boolean match(String attributeName) {
            return "Deprecated".equals(attributeName);
        }

        @Override
        public DeprecatedAttribute create(ByteBuffer buffer, ConstantElement[] constantPool) {
            int attributeNameIndex = buffer.getShort() & 0xffff;
            long attributeLength = buffer.getInt() & 0xffffffffL;

            return new DeprecatedAttribute(attributeNameIndex, attributeLength, constantPool);
        }
    }
}
