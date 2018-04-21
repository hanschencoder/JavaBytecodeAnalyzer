package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class InvokeDynamicInfo extends ConstantElement {

    /**
     * 2 byte
     */
    public int bootstrapMethodAttrIndex;
    /**
     * 2 byte
     */
    public int nameAndTypeIndex;

    public InvokeDynamicInfo(short tag, int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
        super(tag);
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public static class Parser extends ConstantElementParser<InvokeDynamicInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_InvokeDynamic;
        }

        @Override
        public InvokeDynamicInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            int bootstrapMethodAttrIndex = buffer.getShort() & 0xffff;
            int nameAndTypeIndex = buffer.getShort() & 0xffff;
            return new InvokeDynamicInfo(tag, bootstrapMethodAttrIndex, nameAndTypeIndex);
        }
    }
}
