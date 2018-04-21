package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class IntegerInfo extends ConstantElement {

    public int value;

    public IntegerInfo(short tag, int value) {
        super(tag);
        this.value = value;
    }

    public static class Parser extends ConstantElementParser<IntegerInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_Integer;
        }

        @Override
        public IntegerInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            int value = buffer.getInt();
            return new IntegerInfo(tag, value);
        }
    }
}
