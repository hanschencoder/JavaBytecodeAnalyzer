package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class StringInfo extends ConstantElement {

    /**
     * 2 byte
     */
    public int stringIndex;

    public StringInfo(short tag, int stringIndex) {
        super(tag);
        this.stringIndex = stringIndex;
    }

    public static class Parser extends ConstantElementParser<StringInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_String;
        }

        @Override
        public StringInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            int stringIndex = buffer.getShort() & 0xffff;
            return new StringInfo(tag, stringIndex);
        }
    }
}
