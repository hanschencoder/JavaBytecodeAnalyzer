package site.hanschen.bytecode.java.model.attribute;

import site.hanschen.bytecode.java.model.constant.Constant;

import java.nio.ByteBuffer;

/**
 * 必须是静态字段才可以有 ConstantValue 属性，这个静态字段可以是 final 的， 也可以不是 final 的
 * 静态变量初始化的方式有两种， 一种就是现在要讲得 ConstantValue 属性， 另一种就是静态初始化方法 <clinit>
 * 不同的编译器和虚拟机可以有不同的实现方式，但是如果虚拟机决定使用ConstantValue属性为静态变量赋值， 那么为这个变量的赋值动作， 必须位于执行 <clinit> 方法之前
 *
 * @author chenhang
 */
public class ConstantValueAttribute extends AttributeInfo {

    /**
     * 指向常量池中的常量
     */
    public int constantValueIndex;

    public ConstantValueAttribute(int attributeNameIndex, long attributeLength, Constant[] constantPool) {
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

    public static class Parser implements AttributeParser<ConstantValueAttribute> {

        @Override
        public boolean match(String attributeName) {
            return "ConstantValue".equals(attributeName);
        }

        @Override
        public ConstantValueAttribute create(ByteBuffer buffer, Constant[] constantPool) {
            int attributeNameIndex = buffer.getShort() & 0xffff;
            long attributeLength = buffer.getInt() & 0xffffffffL;
            int constantValueIndex = buffer.getShort() & 0xffff;

            ConstantValueAttribute attribute = new ConstantValueAttribute(attributeNameIndex, attributeLength, constantPool);
            attribute.constantValueIndex = constantValueIndex;
            return attribute;
        }
    }
}
