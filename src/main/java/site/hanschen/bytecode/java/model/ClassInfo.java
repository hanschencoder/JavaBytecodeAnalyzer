package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class ClassInfo extends ConstantElement {

    /**
     * 2 byte
     */
    public int nameIndex;

    public ClassInfo(short tag, int nameIndex) {
        super(tag);
        this.nameIndex = nameIndex;
    }

    public static class Parser extends ConstantElementParser<ClassInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_Class;
        }

        @Override
        public ClassInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            int nameIndex = buffer.getShort() & 0xffff;
            return new ClassInfo(tag, nameIndex);
        }
    }
}
