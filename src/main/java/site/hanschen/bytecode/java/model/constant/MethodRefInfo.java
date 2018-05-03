package site.hanschen.bytecode.java.model.constant;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * 表示对一个类中方法的符号引用
 *
 * @author chenhang
 */
public class MethodRefInfo extends ConstantElement {

    /**
     * 2 byte，指向 CONSTANT_Class_info，被引用方法所在的类
     */
    public int classIndex;
    /**
     * 2 byte，指向 CONSTANT_NameAndType_info，被引用方法的方法名称和方法签名
     */
    public int nameAndTypeIndex;

    public MethodRefInfo(short tag, int classIndex, int nameAndTypeIndex) {
        super(tag);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public String getTag() {
        return "MethodRef";
    }

    @Override
    public String getValue() {
        return String.format("#%d:#%d", classIndex, nameAndTypeIndex);
    }

    @Override
    public String getComment(ConstantElement[] constantPool) {
        return constantPool[classIndex].getComment(constantPool) + "." + constantPool[nameAndTypeIndex].getComment(constantPool);
    }

    public static class Parser implements ConstantElementParser<MethodRefInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_Methodref;
        }

        @Override
        public MethodRefInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            int classIndex = buffer.getShort() & 0xffff;
            int nameAndTypeIndex = buffer.getShort() & 0xffff;
            return new MethodRefInfo(tag, classIndex, nameAndTypeIndex);
        }
    }
}
