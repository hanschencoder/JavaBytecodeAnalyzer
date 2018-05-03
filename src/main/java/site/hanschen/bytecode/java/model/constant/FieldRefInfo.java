package site.hanschen.bytecode.java.model.constant;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * 表示对一个字段的符号引用
 *
 * @author chenhang
 */
public class FieldRefInfo extends ConstantElement {

    /**
     * 2 byte，指向 CONSTANT_Class_info，被引用字段所在的类
     */
    public int classIndex;
    /**
     * 2 byte，指向 CONSTANT_NameAndType_info，被引用字段的名称和描述符
     */
    public int nameAndTypeIndex;

    public FieldRefInfo(short tag, int classIndex, int nameAndTypeIndex) {
        super(tag);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public String getTag() {
        return "FieldRef";
    }

    @Override
    public String getValue() {
        return String.format("#%d:#%d", classIndex, nameAndTypeIndex);
    }

    @Override
    public String getComment(ConstantElement[] constantPool) {
        return constantPool[classIndex].getComment(constantPool) + "." + constantPool[nameAndTypeIndex].getComment(constantPool);
    }

    public static class Parser implements ConstantElementParser<FieldRefInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_Fieldref;
        }

        @Override
        public FieldRefInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            int classIndex = buffer.getShort() & 0xffff;
            int nameAndTypeIndex = buffer.getShort() & 0xffff;
            return new FieldRefInfo(tag, classIndex, nameAndTypeIndex);
        }
    }
}
