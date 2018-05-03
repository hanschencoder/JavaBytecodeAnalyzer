package site.hanschen.bytecode.java.model.constant;

/**
 * @author chenhang
 */
public abstract class ConstantElement {

    public short tag;

    public ConstantElement(short tag) {
        this.tag = tag;
    }

    /**
     * 获取 tag 类型
     */
    abstract public String getTag();

    /**
     * 解析常量池内容
     */
    abstract public String getValue();

    /**
     * 进一步解释常量池内容
     */
    abstract public String getComment(ConstantElement[] constantPool);
}
