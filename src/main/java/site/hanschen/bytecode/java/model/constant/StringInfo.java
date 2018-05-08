package site.hanschen.bytecode.java.model.constant;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * 存储的是源文件中出现的 String 型数据的索引
 *
 * @author chenhang
 */
public class StringInfo extends Constant {

    /**
     * 2 byte
     */
    public int stringIndex;

    public StringInfo(short tag, int stringIndex) {
        super(tag);
        this.stringIndex = stringIndex;
    }

    @Override
    public String getTag() {
        return "String";
    }

    @Override
    public String getValue() {
        return "#" + stringIndex;
    }

    @Override
    public String getComment(Constant[] constantPool) {
        return constantPool[stringIndex].getValue();
    }

    public static class Parser implements ConstantParser<StringInfo> {

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
