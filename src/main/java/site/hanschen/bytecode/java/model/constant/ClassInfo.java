package site.hanschen.bytecode.java.model.constant;

import site.hanschen.bytecode.java.ClassFile;

import java.nio.ByteBuffer;

/**
 * 类信息的描述
 *
 * @author chenhang
 */
public class ClassInfo extends ConstantElement {

    /**
     * 2 byte
     */
    public int nameIndex;

    public ClassInfo(short tag, int nameIndex) {
        super(tag);
        this.nameIndex = nameIndex;
    }

    @Override
    public String getTag() {
        return "Class";
    }

    @Override
    public String getValue() {
        return "#" + nameIndex;
    }

    @Override
    public String getComment(ConstantElement[] constantPool) {
        return constantPool[nameIndex].getValue();
    }

    public static class Parser implements ConstantElementParser<ClassInfo> {

        @Override
        public boolean match(short tag) {
            return tag == ClassFile.CONSTANT_Class;
        }

        @Override
        public ClassInfo create(ByteBuffer buffer) {
            short tag = buffer.get();
            int nameIndex = buffer.getShort() & 0xffff;
            return new ClassInfo(tag, nameIndex);
        }
    }
}
