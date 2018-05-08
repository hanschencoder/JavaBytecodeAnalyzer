package site.hanschen.bytecode.java.model.attribute;

import site.hanschen.bytecode.java.model.constant.Constant;

import java.nio.ByteBuffer;

/**
 * @author chenhang
 */
public interface AttributeParser<T extends AttributeInfo> {

    boolean match(String attributeName);

    T create(ByteBuffer buffer, Constant[] constantPool);

}
