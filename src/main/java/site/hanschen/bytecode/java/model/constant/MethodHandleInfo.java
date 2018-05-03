package site.hanschen.bytecode.java.model.constant;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public class MethodHandleInfo extends ConstantElement {

    /**
     * 1 byte
     */
    public short referenceKind;
    /**
     * 2 byte
     */
    public int referenceIndex;

    public MethodHandleInfo(short tag, short referenceKind, int referenceIndex) {
        super(tag);
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    @Override
    public String getTag() {
        return "MethodHandle";
    }

    @Override
    public String getValue() {
        return String.format("#%d:#%d", referenceKind, referenceIndex);
    }

    @Override
    public String getComment(ConstantElement[] constantPool) {
        // TODO: show comment here
        return null;
    }

    public static class Parser extends ConstantElementParser<MethodHandleInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_MethodHandle;
        }

        @Override
        public MethodHandleInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            short referenceKind = buffer.get();
            int referenceIndex = buffer.getShort() & 0xffff;
            return new MethodHandleInfo(tag, referenceKind, referenceIndex);
        }
    }
}
