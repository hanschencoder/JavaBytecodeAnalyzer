package site.hanschen.bytecode.java.model.constant;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * 存储的是源文件中出现的 double 型数据的值
 *
 * @author chenhang
 */
public class DoubleInfo extends Constant {

    public double value;

    public DoubleInfo(short tag, double value) {
        super(tag);
        this.value = value;
    }

    @Override
    public String getTag() {
        return "Double";
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    @Override
    public String getComment(Constant[] constantPool) {
        return null;
    }

    public static class Parser implements ConstantParser<DoubleInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_Double;
        }

        @Override
        public DoubleInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            long bytes = buffer.getLong();
            int s = ((bytes >> 63) == 0) ? 1 : -1;
            int e = (int) ((bytes >> 52) & 0x7ffL);
            long m = (e == 0) ? (bytes & 0xfffffffffffffL) << 1 : (bytes & 0xfffffffffffffL) | 0x10000000000000L;

            double value = s * m * Math.pow(2, e - 1075);
            return new DoubleInfo(tag, value);
        }
    }
}
