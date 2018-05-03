package site.hanschen.bytecode.java.model.attribute;

import site.hanschen.bytecode.java.model.constant.ConstantElement;

import java.nio.ByteBuffer;

/**
 * 该属性出现在顶层的 class 文件中。它描述了该类是从哪个源文件中编译来的，注意，描述的是源文件，而不是类，一个源文件中可以存在多个类
 *
 * @author chenhang
 */
public class SourceFileAttribute extends AttributeInfo {

    public int sourceFileIndex;

    public SourceFileAttribute(int attributeNameIndex, long attributeLength, ConstantElement[] constantPool) {
        super(attributeNameIndex, attributeLength, constantPool);
    }

    @Override
    public String getValue() {
        return "#" + sourceFileIndex;
    }

    @Override
    public String getComment() {
        return constantPool[sourceFileIndex].getValue();
    }

    public static class Parser implements AttributeParser<SourceFileAttribute> {

        @Override
        public boolean match(String attributeName) {
            return "SourceFile".equals(attributeName);
        }

        @Override
        public SourceFileAttribute create(ByteBuffer buffer, ConstantElement[] constantPool) {
            int attributeNameIndex = buffer.getShort() & 0xffff;
            long attributeLength = buffer.getInt() & 0xffffffffL;
            int sourceFileIndex = buffer.getShort() & 0xffff;

            SourceFileAttribute attribute = new SourceFileAttribute(attributeNameIndex, attributeLength, constantPool);
            attribute.sourceFileIndex = sourceFileIndex;
            return attribute;
        }
    }
}
