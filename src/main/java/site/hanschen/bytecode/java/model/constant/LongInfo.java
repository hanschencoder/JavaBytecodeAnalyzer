package site.hanschen.bytecode.java.model.constant;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * 存储的是源文件中出现的 long 型数据的值
 *
 * @author chenhang
 */
public class LongInfo extends ConstantElement {

    public long value;

    public LongInfo(short tag, long value) {
        super(tag);
        this.value = value;
    }

    @Override
    public String getTag() {
        return "Long";
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    @Override
    public String getComment(ConstantElement[] constantPool) {
        return null;
    }

    public static class Parser extends ConstantElementParser<LongInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_Long;
        }

        @Override
        public LongInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            long value = buffer.getLong();
            return new LongInfo(tag, value);
        }
    }
}
