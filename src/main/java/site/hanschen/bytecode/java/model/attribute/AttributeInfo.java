package site.hanschen.bytecode.java.model.attribute;

import site.hanschen.bytecode.java.model.constant.Constant;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhang
 */
public abstract class AttributeInfo {

    private static List<AttributeParser> PARSERS = new ArrayList<>();

    static {
        PARSERS.add(new SourceFileAttribute.Parser());
        PARSERS.add(new ConstantValueAttribute.Parser());
        PARSERS.add(new SyntheticAttribute.Parser());
        PARSERS.add(new DeprecatedAttribute.Parser());
        PARSERS.add(new EnclosingMethodAttribute.Parser());
        PARSERS.add(new InnerClassesAttribute.Parser());
        PARSERS.add(new SignatureAttribute.Parser());
        PARSERS.add(new CodeAttribute.Parser());
        PARSERS.add(new UnknownAttribute.Parser()); // must at last
    }

    /**
     * 指向属性名称，如 SourceFile、ConstantValue、Code 等
     */
    public int attributeNameIndex;
    /**
     * 属性数据长度
     */
    public long attributeLength;
    /**
     * 常量池
     */
    protected Constant[] constantPool;

    public AttributeInfo(int attributeNameIndex, long attributeLength, Constant[] constantPool) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
        this.constantPool = constantPool;
    }

    /**
     * 获取属性名称
     */
    public String getAttributeName() {
        return constantPool[attributeNameIndex].getValue();
    }

    /**
     * 解析属性内容
     */
    abstract public String getValue();

    /**
     * 进一步解释属性内容
     */
    abstract public String getComment();


    @Override
    public String toString() {
        return getAttributeName() + " " + getValue();
    }

    public static AttributeInfo readFrom(ByteBuffer buffer, Constant[] constantPool) {
        int attributeNameIndex = buffer.getShort() & 0xffff;
        // reset position at attributeNameIndex
        buffer.position(buffer.position() - 2);
        String attributeName = constantPool[attributeNameIndex].getValue();
        AttributeParser parser = null;
        for (AttributeParser p : PARSERS) {
            if (p.match(attributeName)) {
                parser = p;
                break;
            }
        }
        if (parser == null) {
            throw new IllegalStateException("unsupported attribute: " + attributeName);
        }

        return parser.create(buffer, constantPool);
    }
}
