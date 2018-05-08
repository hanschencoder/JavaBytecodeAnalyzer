package site.hanschen.bytecode.java.model.constant;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * 存储的是源文件中出现的 int 型数据的值
 *
 * @author chenhang
 */
public class IntegerInfo extends Constant {

    public int value;

    public IntegerInfo(short tag, int value) {
        super(tag);
        this.value = value;
    }

    @Override
    public String getTag() {
        return "Integer";
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    @Override
    public String getComment(Constant[] constantPool) {
        return null;
    }

    public static class Parser implements ConstantParser<IntegerInfo> {

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
