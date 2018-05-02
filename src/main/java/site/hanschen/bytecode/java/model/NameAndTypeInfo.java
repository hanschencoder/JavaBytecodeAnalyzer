package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * 源文件中对一个字段或方法的符号引用的描述
 *
 * @author chenhang
 */
public class NameAndTypeInfo extends ConstantElement {

    /**
     * 2 byte， 被引用的方法或者字段名称
     */
    public int nameIndex;
    /**
     * 2 byte，被引用的方法的签名或字段类型
     */
    public int descriptionIndex;

    public NameAndTypeInfo(short tag, int nameIndex, int descriptionIndex) {
        super(tag);
        this.nameIndex = nameIndex;
        this.descriptionIndex = descriptionIndex;
    }

    public static class Parser extends ConstantElementParser<NameAndTypeInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_NameAndType;
        }

        @Override
        public NameAndTypeInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            int nameIndex = buffer.getShort() & 0xffff;
            int descriptionIndex = buffer.getShort() & 0xffff;
            return new NameAndTypeInfo(tag, nameIndex, descriptionIndex);
        }
    }
}
