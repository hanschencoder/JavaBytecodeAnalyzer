package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class Utf8Info extends ConstantElement {

    /**
     * 2 byte
     */
    public int length;
    /**
     * 2 byte
     */
    public byte[] bytes;

    public Utf8Info(short tag, int length, byte[] bytes) {
        super(tag);
        this.length = length;
        this.bytes = bytes;
    }

    public static class Parser extends ConstantElementParser<Utf8Info> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_Utf8;
        }

        @Override
        public Utf8Info create(ByteBuffer buffer) {
            short tag = buffer.get();
            int length = buffer.getShort() & 0xffff;
            byte[] bytes = new byte[length];
            buffer.get(bytes);
            return new Utf8Info(tag, length, bytes);
        }
    }

    @Override
    public String toString() {
        return "Utf8Info{" + "length=" + length + ", string=" + new String(bytes) + '}';
    }
}
