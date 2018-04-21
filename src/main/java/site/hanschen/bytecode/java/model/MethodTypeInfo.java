package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class MethodTypeInfo extends ConstantElement {

    /**
     * 2 byte
     */
    public int descriptorIndex;

    public MethodTypeInfo(short tag, int descriptorIndex) {
        super(tag);
        this.descriptorIndex = descriptorIndex;
    }

    public static class Parser extends ConstantElementParser<MethodTypeInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_MethodType;
        }

        @Override
        public MethodTypeInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            int descriptorIndex = buffer.getShort() & 0xffff;
            return new MethodTypeInfo(tag, descriptorIndex);
        }
    }
}
