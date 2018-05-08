package site.hanschen.bytecode.java.model.constant;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public interface ConstantParser<T extends Constant> {

    boolean match(short tag);

    T create(ByteBuffer buffer);
}
