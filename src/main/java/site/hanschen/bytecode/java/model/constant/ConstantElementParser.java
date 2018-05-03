package site.hanschen.bytecode.java.model.constant;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public interface ConstantElementParser<T extends ConstantElement> {

    boolean match(short tag);

    T create(ByteBuffer buffer);
}
