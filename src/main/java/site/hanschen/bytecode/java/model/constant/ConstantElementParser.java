package site.hanschen.bytecode.java.model.constant;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public abstract class ConstantElementParser<T extends ConstantElement> {

    public abstract boolean match(short tag);

    public abstract T create(ByteBuffer buffer);
}
