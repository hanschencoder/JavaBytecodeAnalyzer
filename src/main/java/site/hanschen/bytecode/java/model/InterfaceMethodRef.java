package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class InterfaceMethodRef extends ConstantElement {

    /**
     * 2 byte
     */
    public int classIndex;
    /**
     * 2 byte
     */
    public int nameAndTypeIndex;

    public InterfaceMethodRef(short tag, int classIndex, int nameAndTypeIndex) {
        super(tag);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public static class Parser extends ConstantElementParser<InterfaceMethodRef> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_InterfaceMethodref;
        }

        @Override
        public InterfaceMethodRef create(ByteBuffer buffer) {
            short tag = buffer.get();
            int classIndex = buffer.getShort() & 0xffff;
            int nameAndTypeIndex = buffer.getShort() & 0xffff;
            return new InterfaceMethodRef(tag, classIndex, nameAndTypeIndex);
        }
    }
}
