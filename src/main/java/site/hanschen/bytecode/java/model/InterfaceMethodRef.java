package site.hanschen.bytecode.java.model;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * 表示对一个接口方法的符号引用， 不能是对类中的方法的符号引用
 *
 * @author chenhang
 */
public class InterfaceMethodRef extends ConstantElement {

    /**
     * 2 byte，指向 CONSTANT_Class_info，被引用方法所在的接口
     */
    public int classIndex;
    /**
     * 2 byte，指向 CONSTANT_NameAndType_info，被引用方法的方法名称和方法签名
     */
    public int nameAndTypeIndex;

    public InterfaceMethodRef(short tag, int classIndex, int nameAndTypeIndex) {
        super(tag);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public static class Parser extends ConstantElementParser<InterfaceMethodRef> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_InterfaceMethodref;
        }

        @Override
        public InterfaceMethodRef create(ByteBuffer buffer) {
            short tag = buffer.get();
            int classIndex = buffer.getShort() & 0xffff;
            int nameAndTypeIndex = buffer.getShort() & 0xffff;
            return new InterfaceMethodRef(tag, classIndex, nameAndTypeIndex);
        }
    }
}
