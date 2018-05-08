package site.hanschen.bytecode.java.model.attribute;

import site.hanschen.bytecode.java.model.constant.Constant;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class InnerClassesAttribute extends AttributeInfo {

    public int numberOfClasses;
    public Classes[] classes;

    public InnerClassesAttribute(int attributeNameIndex, long attributeLength, Constant[] constantPool) {
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

    public static class Classes {
        public int innerClassInfoIndex;
        public int outerClassInfoIndex;
        public int innerNameIndex;
        public int innerClassAccessFlags;
    }

    public static class Parser implements AttributeParser<InnerClassesAttribute> {

        @Override
        public boolean match(String attributeName) {
            return "InnerClasses".equals(attributeName);
        }

        @Override
        public InnerClassesAttribute create(ByteBuffer buffer, Constant[] constantPool) {
            int attributeNameIndex = buffer.getShort() & 0xffff;
            long attributeLength = buffer.getInt() & 0xffffffffL;
            int numberOfClasses = buffer.getShort() & 0xffff;

            InnerClassesAttribute attribute = new InnerClassesAttribute(attributeNameIndex, attributeLength, constantPool);
            attribute.numberOfClasses = numberOfClasses;
            if (numberOfClasses > 0) {
                Classes[] classes = new Classes[numberOfClasses];
                for (int i = 0; i < numberOfClasses; i++) {
                    Classes clz = new Classes();
                    clz.innerClassInfoIndex = buffer.getShort() & 0xffff;
                    clz.outerClassInfoIndex = buffer.getShort() & 0xffff;
                    clz.innerNameIndex = buffer.getShort() & 0xffff;
                    clz.innerClassAccessFlags = buffer.getShort() & 0xffff;
                    classes[i] = clz;
                }
                attribute.classes = classes;
            }
            return attribute;
        }
    }
}
