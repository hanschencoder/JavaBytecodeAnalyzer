package site.hanschen.bytecode.java;

/**
 * <a href="https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html">The class File Format</a>
 *
 * @author chenhang
 */
public class ClassFile {

    public static final int MAGIC_CLASS_FILE = 0xCAFEBABE;

    /**
     * Constant Type
     */
    public static final int CONSTANT_Class = 7;
    public static final int CONSTANT_Fieldref = 9;
    public static final int CONSTANT_Methodref = 10;
    public static final int CONSTANT_InterfaceMethodref = 11;
    public static final int CONSTANT_String = 8;
    public static final int CONSTANT_Integer = 3;
    public static final int CONSTANT_Float = 4;
    public static final int CONSTANT_Long = 5;
    public static final int CONSTANT_Double = 6;
    public static final int CONSTANT_NameAndType = 12;
    public static final int CONSTANT_Utf8 = 1;
    public static final int CONSTANT_MethodHandle = 15;
    public static final int CONSTANT_MethodType = 16;
    public static final int CONSTANT_InvokeDynamic = 18;

    /**
     * Class access and property modifiers
     */
    public enum ClassAccess {

        ACC_PUBLIC(0x0001),
        ACC_FINAL(0x0010),
        ACC_SUPER(0x0020),
        ACC_INTERFACE(0x0200),
        ACC_ABSTRACT(0x0400),
        ACC_SYNTHETIC(0x1000),
        ACC_ANNOTATION(0x2000),
        ACC_ENUM(0x4000);

        private int flag;

        ClassAccess(int flag) {
            this.flag = flag;
        }
    }

    /**
     * Field access and property flags
     */
    public enum FieldAccess {

        ACC_PUBLIC(0x0001),
        ACC_PRIVATE(0x0002),
        ACC_PROTECTED(0x0004),
        ACC_STATIC(0x0008),
        ACC_FINAL(0x0010),
        ACC_VOLATILE(0x0040),
        ACC_TRANSIENT(0x0080),
        ACC_SYNTHETIC(0x1000),
        ACC_ENUM(0x4000);

        private int flag;

        FieldAccess(int flag) {
            this.flag = flag;
        }
    }

    /**
     * Method access and property flags
     */
    public enum MethodAccess {

        ACC_PUBLIC(0x0001),
        ACC_PRIVATE(0x0002),
        ACC_PROTECTED(0x0004),
        ACC_STATIC(0x0008),
        ACC_FINAL(0x0010),
        ACC_SYNCHRONIZED(0x0020),
        ACC_BRIDGE(0x0040),
        ACC_VARARGS(0x0080),
        ACC_NATIVE(0x0100),
        ACC_ABSTRACT(0x0400),
        ACC_STRICT(0x0800),
        ACC_SYNTHETIC(0x1000);

        private int flag;

        MethodAccess(int flag) {
            this.flag = flag;
        }
    }
}
