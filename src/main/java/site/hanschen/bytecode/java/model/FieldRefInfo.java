package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class FieldRefInfo extends ConstantElement {

    /**
     * 2 byte
     */
    public int classIndex;
    /**
     * 2 byte
     */
    public int nameAndTypeIndex;

    public FieldRefInfo(short tag, int classIndex, int nameAndTypeIndex) {
        super(tag);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public static class Parser extends ConstantElementParser<FieldRefInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_Fieldref;
        }

        @Override
        public FieldRefInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            int classIndex = buffer.getShort() & 0xffff;
            int nameAndTypeIndex = buffer.getShort() & 0xffff;
            return new FieldRefInfo(tag, classIndex, nameAndTypeIndex);
        }
    }
}
