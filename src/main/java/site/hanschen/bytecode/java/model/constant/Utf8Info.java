package site.hanschen.bytecode.java.model.constant;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * 存储源文件中的各种字符串
 *
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

    public String value;

    public Utf8Info(short tag, int length, byte[] bytes) {
        super(tag);
        this.length = length;
        this.bytes = bytes;
        this.value = new String(bytes);
    }

    @Override
    public String getTag() {
        return "Utf8";
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getComment(ConstantElement[] constantPool) {
        return null;
    }

    public static class Parser implements ConstantElementParser<Utf8Info> {

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
        return "Utf8Info{" + "length=" + length + ", value=" + value + '}';
    }
}
