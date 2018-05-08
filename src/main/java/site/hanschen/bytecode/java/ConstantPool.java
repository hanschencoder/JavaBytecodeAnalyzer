package site.hanschen.bytecode.java;

import site.hanschen.bytecode.java.model.constant.*;
import site.hanschen.bytecode.java.utils.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhang
 */
public class ConstantPool {

    private static List<ConstantParser> PARSERS = new ArrayList<>();

    static {
        PARSERS.add(new ClassInfo.Parser());
        PARSERS.add(new FieldRefInfo.Parser());
        PARSERS.add(new MethodRefInfo.Parser());
        PARSERS.add(new InterfaceMethodRef.Parser());
        PARSERS.add(new StringInfo.Parser());
        PARSERS.add(new IntegerInfo.Parser());
        PARSERS.add(new FloatInfo.Parser());
        PARSERS.add(new LongInfo.Parser());
        PARSERS.add(new DoubleInfo.Parser());
        PARSERS.add(new NameAndTypeInfo.Parser());
        PARSERS.add(new Utf8Info.Parser());
        PARSERS.add(new MethodHandleInfo.Parser());
        PARSERS.add(new MethodTypeInfo.Parser());
        PARSERS.add(new InvokeDynamicInfo.Parser());
    }

    private Constant[] pool;
    private int poolCount;

    public static ConstantPool readFrom(ByteBuffer buffer) {
        // constant_pool_count
        int constantPoolCount = buffer.getShort() & 0xffff;
        Logger.d("constantPoolCount: %d", constantPoolCount);

        // attention, The constant_pool table is indexed from 1 to constant_pool_count-1
        // constantPool actual size: constant_pool_count-1
        final Constant[] constantPool = new Constant[constantPoolCount];
        for (int i = 1; i < constantPoolCount; i++) {
            short tag = buffer.get();
            // reset position at tag data
            buffer.position(buffer.position() - 1);
            ConstantParser parser = null;
            for (ConstantParser p : PARSERS) {
                if (p.match(tag)) {
                    parser = p;
                    break;
                }
            }
            if (parser == null) {
                throw new IllegalStateException("unsupported tag: " + tag);
            }

            Constant constant = parser.create(buffer);
            constantPool[i] = constant;
        }

        return new ConstantPool(constantPool, constantPoolCount);
    }


    private ConstantPool(Constant[] pool, int poolCount) {
        this.pool = pool;
        this.poolCount = poolCount;
    }

    public Constant[] getConstantPool() {
        return pool;
    }

    public int getConstantPoolCount() {
        return poolCount;
    }

    @SuppressWarnings("unchecked")
    public <T extends Constant> T get(int index) {
        return (T) pool[index];
    }

    public interface Visitor<R, P> {
        R visitClass(ClassInfo info, P p);

        R visitDouble(DoubleInfo info, P p);

        R visitFieldref(FieldRefInfo info, P p);

        R visitFloat(FloatInfo info, P p);

        R visitInteger(IntegerInfo info, P p);

        R visitInterfaceMethodref(InterfaceMethodRef info, P p);

        R visitInvokeDynamic(InvokeDynamicInfo info, P p);

        R visitLong(LongInfo info, P p);

        R visitNameAndType(NameAndTypeInfo info, P p);

        R visitMethodref(MethodRefInfo info, P p);

        R visitMethodHandle(MethodHandleInfo info, P p);

        R visitMethodType(MethodTypeInfo info, P p);

        R visitString(StringInfo info, P p);

        R visitUtf8(Utf8Info info, P p);
    }
}
