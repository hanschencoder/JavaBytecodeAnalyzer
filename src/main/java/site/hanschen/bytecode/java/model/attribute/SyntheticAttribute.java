package site.hanschen.bytecode.java.model.attribute;

import site.hanschen.bytecode.java.model.constant.Constant;

import java.nio.ByteBuffer;

/**
 * 表示这个字段， 方法或类不是有用户代码生成的，即不存在与源文件中，是由编译器自动添加的
 * 例如， 编译器会为内部类增加一个字段， 该字段是对外部类对象的引用； 如果一个不定义构造方法， 那么编译器会自动添加一个无参数的构造方法<init>，
 * 如果定义了静态字段或静态代码块， 还会根据具体情况， 增加静态初始化方法<clinit>
 *
 * @author chenhang
 */
public class SyntheticAttribute extends AttributeInfo {

    public SyntheticAttribute(int attributeNameIndex, long attributeLength, Constant[] constantPool) {
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

    public static class Parser implements AttributeParser<SyntheticAttribute> {

        @Override
        public boolean match(String attributeName) {
            return "Synthetic".equals(attributeName);
        }

        @Override
        public SyntheticAttribute create(ByteBuffer buffer, Constant[] constantPool) {
            int attributeNameIndex = buffer.getShort() & 0xffff;
            long attributeLength = buffer.getInt() & 0xffffffffL;

            return new SyntheticAttribute(attributeNameIndex, attributeLength, constantPool);
        }
    }
}
