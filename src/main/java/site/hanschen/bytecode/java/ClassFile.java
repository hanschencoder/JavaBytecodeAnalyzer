package site.hanschen.bytecode.java;



import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

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

        public int getFlag() {
            return flag;
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

    public static Map<Integer, Pair<String, String>> opCodeMap = new HashMap<>();

    static {
        opCodeMap.put(0x00, new Pair<>("nop", "None"));
        opCodeMap.put(0x01, new Pair<>("aconst_null", "将null推送至栈顶"));
        opCodeMap.put(0x02, new Pair<>("iconst_m1", "将int型-1推送至栈顶"));
        opCodeMap.put(0x03, new Pair<>("iconst_0", "将int型0推送至栈顶"));
        opCodeMap.put(0x04, new Pair<>("iconst_1", "将int型1推送至栈顶"));
        opCodeMap.put(0x05, new Pair<>("iconst_2", "将int型2推送至栈顶"));
        opCodeMap.put(0x06, new Pair<>("iconst_3", "将int型3推送至栈顶"));
        opCodeMap.put(0x07, new Pair<>("iconst_4", "将int型4推送至栈顶"));
        opCodeMap.put(0x08, new Pair<>("iconst_5", "将int型5推送至栈顶"));
        opCodeMap.put(0x09, new Pair<>("lconst_0", "将long型0推送至栈顶"));
        opCodeMap.put(0x0a, new Pair<>("lconst_1", "将long型1推送至栈顶"));
        opCodeMap.put(0x0b, new Pair<>("fconst_0", "将float型0推送至栈顶"));
        opCodeMap.put(0x0c, new Pair<>("fconst_1", "将float型1推送至栈顶"));
        opCodeMap.put(0x0d, new Pair<>("fconst_2", "将float型2推送至栈顶"));
        opCodeMap.put(0x0e, new Pair<>("dconst_0", "将double型0推送至栈顶"));
        opCodeMap.put(0x0f, new Pair<>("dconst_1", "将double型1推送至栈顶"));
        opCodeMap.put(0x10, new Pair<>("bipush", "将单字节的常量值(-128~127)推送至栈顶"));
        opCodeMap.put(0x11, new Pair<>("sipush", "将一个短整型常量(-32768~32767)推送至栈顶"));
        opCodeMap.put(0x12, new Pair<>("ldc", "将int,float或String型常量值从常量池中推送至栈顶"));
        opCodeMap.put(0x13, new Pair<>("ldc_w", "将int,float或String型常量值从常量池中推送至栈顶(宽索引)"));
        opCodeMap.put(0x14, new Pair<>("ldc2_w", "将long或double型常量值从常量池中推送至栈顶(宽索引)"));
        opCodeMap.put(0x15, new Pair<>("iload", "将指定的int型本地变量推送至栈顶"));
        opCodeMap.put(0x16, new Pair<>("lload", "将指定的long型本地变量推送至栈顶"));
        opCodeMap.put(0x17, new Pair<>("fload", "将指定的float型本地变量推送至栈顶"));
        opCodeMap.put(0x18, new Pair<>("dload", "将指定的double型本地变量推送至栈顶"));
        opCodeMap.put(0x19, new Pair<>("aload", "将指定的引用类型本地变量推送至栈顶"));
        opCodeMap.put(0x1a, new Pair<>("iload_0", "将第一个int型本地变量推送至栈顶"));
        opCodeMap.put(0x1b, new Pair<>("iload_1", "将第二个int型本地变量推送至栈顶"));
        opCodeMap.put(0x1c, new Pair<>("iload_2", "将第三个int型本地变量推送至栈顶"));
        opCodeMap.put(0x1d, new Pair<>("iload_3", "将第四个int型本地变量推送至栈顶"));
        opCodeMap.put(0x1e, new Pair<>("lload_0", "将第一个long型本地变量推送至栈顶"));
        opCodeMap.put(0x1f, new Pair<>("lload_1", "将第二个long型本地变量推送至栈顶"));
        opCodeMap.put(0x20, new Pair<>("lload_2", "将第三个long型本地变量推送至栈顶"));
        opCodeMap.put(0x21, new Pair<>("lload_3", "将第四个long型本地变量推送至栈顶"));
        opCodeMap.put(0x22, new Pair<>("fload_0", "将第一个float型本地变量推送至栈顶"));
        opCodeMap.put(0x23, new Pair<>("fload_1", "将第二个float型本地变量推送至栈顶"));
        opCodeMap.put(0x24, new Pair<>("fload_2", "将第三个float型本地变量推送至栈顶"));
        opCodeMap.put(0x25, new Pair<>("fload_3", "将第四个float型本地变量推送至栈顶"));
        opCodeMap.put(0x26, new Pair<>("dload_0", "将第一个double型本地变量推送至栈顶"));
        opCodeMap.put(0x27, new Pair<>("dload_1", "将第二个double型本地变量推送至栈顶"));
        opCodeMap.put(0x28, new Pair<>("dload_2", "将第三个double型本地变量推送至栈顶"));
        opCodeMap.put(0x29, new Pair<>("dload_3", "将第四个double型本地变量推送至栈顶"));
        opCodeMap.put(0x2a, new Pair<>("aload_0", "将第一个引用类型本地变量推送至栈顶"));
        opCodeMap.put(0x2b, new Pair<>("aload_1", "将第二个引用类型本地变量推送至栈顶"));
        opCodeMap.put(0x2c, new Pair<>("aload_2", "将第三个引用类型本地变量推送至栈顶"));
        opCodeMap.put(0x2d, new Pair<>("aload_3", "将第四个引用类型本地变量推送至栈顶"));
        opCodeMap.put(0x2e, new Pair<>("iaload", "将int型数组指定索引的值推送至栈顶"));
        opCodeMap.put(0x2f, new Pair<>("laload", "将long型数组指定索引的值推送至栈顶"));
        opCodeMap.put(0x30, new Pair<>("faload", "将float型数组指定索引的值推送至栈顶"));
        opCodeMap.put(0x31, new Pair<>("daload", "将double型数组指定索引的值推送至栈顶"));
        opCodeMap.put(0x32, new Pair<>("aaload", "将引用类型数组指定索引的值推送至栈顶"));
        opCodeMap.put(0x33, new Pair<>("baload", "将boolean或byte型数组指定索引的值推送至栈顶"));
        opCodeMap.put(0x34, new Pair<>("caload", "将char型数组指定索引的值推送至栈顶"));
        opCodeMap.put(0x35, new Pair<>("saload", "将short型数组指定索引的值推送至栈顶"));
        opCodeMap.put(0x36, new Pair<>("istore", "将栈顶int型数值存入指定本地变量"));
        opCodeMap.put(0x37, new Pair<>("lstore", "将栈顶long型数值存入指定本地变量"));
        opCodeMap.put(0x38, new Pair<>("fstore", "将栈顶float型数值存入指定本地变量"));
        opCodeMap.put(0x39, new Pair<>("dstore", "将栈顶double型数值存入指定本地变量"));
        opCodeMap.put(0x3a, new Pair<>("astore", "将栈顶引用类型数值存入指定本地变量"));
        opCodeMap.put(0x3b, new Pair<>("istore_0", "将栈顶int型数值存入第一个本地变量"));
        opCodeMap.put(0x3c, new Pair<>("istore_1", "将栈顶int型数值存入第二个本地变量"));
        opCodeMap.put(0x3d, new Pair<>("istore_2", "将栈顶int型数值存入第三个本地变量"));
        opCodeMap.put(0x3e, new Pair<>("istore_3", "将栈顶int型数值存入第四个本地变量"));
        opCodeMap.put(0x3f, new Pair<>("lstore_0", "将栈顶long型数值存入第一个本地变量"));
        opCodeMap.put(0x40, new Pair<>("lstore_1", "将栈顶long型数值存入第二个本地变量"));
        opCodeMap.put(0x41, new Pair<>("lstore_2", "将栈顶long型数值存入第三个本地变量"));
        opCodeMap.put(0x42, new Pair<>("lstore_3", "将栈顶long型数值存入第四个本地变量"));
        opCodeMap.put(0x43, new Pair<>("fstore_0", "将栈顶float型数值存入第一个本地变量"));
        opCodeMap.put(0x44, new Pair<>("fstore_1", "将栈顶float型数值存入第二个本地变量"));
        opCodeMap.put(0x45, new Pair<>("fstore_2", "将栈顶float型数值存入第三个本地变量"));
        opCodeMap.put(0x46, new Pair<>("fstore_3", "将栈顶float型数值存入第四个本地变量"));
        opCodeMap.put(0x47, new Pair<>("dstore_0", "将栈顶double型数值存入第一个本地变量"));
        opCodeMap.put(0x48, new Pair<>("dstore_1", "将栈顶double型数值存入第二个本地变量"));
        opCodeMap.put(0x49, new Pair<>("dstore_2", "将栈顶double型数值存入第三个本地变量"));
        opCodeMap.put(0x4a, new Pair<>("dstore_3", "将栈顶double型数值存入第四个本地变量"));
        opCodeMap.put(0x4b, new Pair<>("astore_0", "将栈顶引用型数值存入第一个本地变量"));
        opCodeMap.put(0x4c, new Pair<>("astore_1", "将栈顶引用型数值存入第二个本地变量"));
        opCodeMap.put(0x4d, new Pair<>("astore_2", "将栈顶引用型数值存入第三个本地变量"));
        opCodeMap.put(0x4e, new Pair<>("astore_3", "将栈顶引用型数值存入第四个本地变量"));
        opCodeMap.put(0x4f, new Pair<>("iastore", "将栈顶int型数值存入指定数组的指定索引位置"));
        opCodeMap.put(0x50, new Pair<>("lastore", "将栈顶long型数值存入指定数组的指定索引位置"));
        opCodeMap.put(0x51, new Pair<>("fastore", "将栈顶float型数值存入指定数组的指定索引位置"));
        opCodeMap.put(0x52, new Pair<>("dastore", "将栈顶double型数值存入指定数组的指定索引位置"));
        opCodeMap.put(0x53, new Pair<>("aastore", "将栈顶引用型数值存入指定数组的指定索引位置"));
        opCodeMap.put(0x54, new Pair<>("bastore", "将栈顶boolean或byte型数值存入指定数组的指定索引位置"));
        opCodeMap.put(0x55, new Pair<>("castore", "将栈顶char型数值存入指定数组的指定索引位置"));
        opCodeMap.put(0x56, new Pair<>("sastore", "将栈顶short型数值存入指定数组的指定索引位置"));
        opCodeMap.put(0x57, new Pair<>("pop", "将栈顶数值弹出(数值不能是long或double类型的)"));
        opCodeMap.put(0x58, new Pair<>("pop2", "将栈顶的一个(对于非long或double类型)或两个数值(对于非long或double的其他类型)弹出"));
        opCodeMap.put(0x59, new Pair<>("dup", "复制栈顶数值并将复制值压入栈顶"));
        opCodeMap.put(0x5a, new Pair<>("dup_x1", "复制栈顶数值并将两个复制值压入栈顶"));
        opCodeMap.put(0x5b, new Pair<>("dup_x2", "复制栈顶数值并将三个(或两个)复制值压入栈顶"));
        opCodeMap.put(0x5c, new Pair<>("dup2", "复制栈顶一个(对于long或double类型)或两个(对于非long或double的其他类型)数值并将复制值压入栈顶"));
        opCodeMap.put(0x5d, new Pair<>("dup2_x1", "dup_x1指令的双倍版本"));
        opCodeMap.put(0x5e, new Pair<>("dup2_x2", "dup_x2指令的双倍版本"));
        opCodeMap.put(0x5f, new Pair<>("swap", "将栈顶最顶端的两个数值互换(数值不能是long或double类型)"));
        opCodeMap.put(0x60, new Pair<>("iadd", "将栈顶两int型数值相加并将结果压入栈顶"));
        opCodeMap.put(0x61, new Pair<>("ladd", "将栈顶两long型数值相加并将结果压入栈顶"));
        opCodeMap.put(0x62, new Pair<>("fadd", "将栈顶两float型数值相加并将结果压入栈顶"));
        opCodeMap.put(0x63, new Pair<>("dadd", "将栈顶两double型数值相加并将结果压入栈顶"));
        opCodeMap.put(0x64, new Pair<>("isub", "将栈顶两int型数值相减并将结果压入栈顶"));
        opCodeMap.put(0x65, new Pair<>("lsub", "将栈顶两long型数值相减并将结果压入栈顶"));
        opCodeMap.put(0x66, new Pair<>("fsub", "将栈顶两float型数值相减并将结果压入栈顶"));
        opCodeMap.put(0x67, new Pair<>("dsub", "将栈顶两double型数值相减并将结果压入栈顶"));
        opCodeMap.put(0x68, new Pair<>("imul", "将栈顶两int型数值相乘并将结果压入栈顶"));
        opCodeMap.put(0x69, new Pair<>("lmul", "将栈顶两long型数值相乘并将结果压入栈顶"));
        opCodeMap.put(0x6a, new Pair<>("fmul", "将栈顶两float型数值相乘并将结果压入栈顶"));
        opCodeMap.put(0x6b, new Pair<>("dmul", "将栈顶两double型数值相乘并将结果压入栈顶"));
        opCodeMap.put(0x6c, new Pair<>("idiv", "将栈顶两int型数值相除并将结果压入栈顶"));
        opCodeMap.put(0x6d, new Pair<>("ldiv", "将栈顶两long型数值相除并将结果压入栈顶"));
        opCodeMap.put(0x6e, new Pair<>("fdiv", "将栈顶两float型数值相除并将结果压入栈顶"));
        opCodeMap.put(0x6f, new Pair<>("ddiv", "将栈顶两double型数值相除并将结果压入栈顶"));
        opCodeMap.put(0x70, new Pair<>("irem", "将栈顶两int型数值作取模运算并将结果压入栈顶"));
        opCodeMap.put(0x71, new Pair<>("lrem", "将栈顶两long型数值作取模运算并将结果压入栈顶"));
        opCodeMap.put(0x72, new Pair<>("frem", "将栈顶两float型数值作取模运算并将结果压入栈顶"));
        opCodeMap.put(0x73, new Pair<>("drem", "将栈顶两double型数值作取模运算并将结果压入栈顶"));
        opCodeMap.put(0x74, new Pair<>("ineg", "将栈顶int型数值取负并将结果压入栈顶"));
        opCodeMap.put(0x75, new Pair<>("lneg", "将栈顶long型数值取负并将结果压入栈顶"));
        opCodeMap.put(0x76, new Pair<>("fneg", "将栈顶float型数值取负并将结果压入栈顶"));
        opCodeMap.put(0x77, new Pair<>("dneg", "将栈顶double型数值取负并将结果压入栈顶"));
        opCodeMap.put(0x78, new Pair<>("ishl", "将int型数值左移指定位数并将结果压入栈顶"));
        opCodeMap.put(0x79, new Pair<>("lshl", "将long型数值左移指定位数并将结果压入栈顶"));
        opCodeMap.put(0x7a, new Pair<>("ishr", "将int型数值右(带符号)移指定位数并将结果压入栈顶"));
        opCodeMap.put(0x7b, new Pair<>("lshr", "将long型数值右(带符号)移指定位数并将结果压入栈顶"));
        opCodeMap.put(0x7c, new Pair<>("iushr", "将int型数值右(无符号)移指定位数并将结果压入栈顶"));
        opCodeMap.put(0x7d, new Pair<>("lushr", "将long型数值右(无符号)移指定位数并将结果压入栈顶"));
        opCodeMap.put(0x7e, new Pair<>("iand", "将栈顶两int型数值\"按位与\"并将结果压入栈顶"));
        opCodeMap.put(0x7f, new Pair<>("land", "将栈顶两long型数值\"按位与\"并将结果压入栈顶"));
        opCodeMap.put(0x80, new Pair<>("ior", "将栈顶两int型数值\"按位或\"并将结果压入栈顶"));
        opCodeMap.put(0x81, new Pair<>("lor", "将栈顶两long型数值\"按位或\"并将结果压入栈顶"));
        opCodeMap.put(0x82, new Pair<>("ixor", "将栈顶两int型数值\"按位异或\"并将结果压入栈顶"));
        opCodeMap.put(0x83, new Pair<>("lxor", "将栈顶两long型数值\"按位异或\"并将结果压入栈顶"));
        opCodeMap.put(0x84, new Pair<>("iinc", "将指定int型变量增加指定值(如i++, i--, i+=2等)"));
        opCodeMap.put(0x85, new Pair<>("i2l", "将栈顶int型数值强制转换为long型数值并将结果压入栈顶"));
        opCodeMap.put(0x86, new Pair<>("i2f", "将栈顶int型数值强制转换为float型数值并将结果压入栈顶"));
        opCodeMap.put(0x87, new Pair<>("i2d", "将栈顶int型数值强制转换为double型数值并将结果压入栈顶"));
        opCodeMap.put(0x88, new Pair<>("l2i", "将栈顶long型数值强制转换为int型数值并将结果压入栈顶"));
        opCodeMap.put(0x89, new Pair<>("l2f", "将栈顶long型数值强制转换为float型数值并将结果压入栈顶"));
        opCodeMap.put(0x8a, new Pair<>("l2d", "将栈顶long型数值强制转换为double型数值并将结果压入栈顶"));
        opCodeMap.put(0x8b, new Pair<>("f2i", "将栈顶float型数值强制转换为int型数值并将结果压入栈顶"));
        opCodeMap.put(0x8c, new Pair<>("f2l", "将栈顶float型数值强制转换为long型数值并将结果压入栈顶"));
        opCodeMap.put(0x8d, new Pair<>("f2d", "将栈顶float型数值强制转换为double型数值并将结果压入栈顶"));
        opCodeMap.put(0x8e, new Pair<>("d2i", "将栈顶double型数值强制转换为int型数值并将结果压入栈顶"));
        opCodeMap.put(0x8f, new Pair<>("d2l", "将栈顶double型数值强制转换为long型数值并将结果压入栈顶"));
        opCodeMap.put(0x90, new Pair<>("d2f", "将栈顶double型数值强制转换为float型数值并将结果压入栈顶"));
        opCodeMap.put(0x91, new Pair<>("i2b", "将栈顶int型数值强制转换为byte型数值并将结果压入栈顶"));
        opCodeMap.put(0x92, new Pair<>("i2c", "将栈顶int型数值强制转换为char型数值并将结果压入栈顶"));
        opCodeMap.put(0x93, new Pair<>("i2s", "将栈顶int型数值强制转换为short型数值并将结果压入栈顶"));
        opCodeMap.put(0x94, new Pair<>("lcmp", "比较栈顶两long型数值大小, 并将结果(1, 0或-1)压入栈顶"));
        opCodeMap.put(0x95, new Pair<>("fcmpl", "比较栈顶两float型数值大小, 并将结果(1, 0或-1)压入栈顶; 当其中一个数值为NaN时, 将-1压入栈顶"));
        opCodeMap.put(0x96, new Pair<>("fcmpg", "比较栈顶两float型数值大小, 并将结果(1, 0或-1)压入栈顶; 当其中一个数值为NaN时, 将1压入栈顶"));
        opCodeMap.put(0x97, new Pair<>("dcmpl", "比较栈顶两double型数值大小, 并将结果(1, 0或-1)压入栈顶; 当其中一个数值为NaN时, 将-1压入栈顶"));
        opCodeMap.put(0x98, new Pair<>("dcmpg", "比较栈顶两double型数值大小, 并将结果(1, 0或-1)压入栈顶; 当其中一个数值为NaN时, 将1压入栈顶"));
        opCodeMap.put(0x99, new Pair<>("ifeq", "当栈顶int型数值等于0时跳转"));
        opCodeMap.put(0x9a, new Pair<>("ifne", "当栈顶int型数值不等于0时跳转"));
        opCodeMap.put(0x9b, new Pair<>("iflt", "当栈顶int型数值小于0时跳转"));
        opCodeMap.put(0x9c, new Pair<>("ifge", "当栈顶int型数值大于等于0时跳转"));
        opCodeMap.put(0x9d, new Pair<>("ifgt", "当栈顶int型数值大于0时跳转"));
        opCodeMap.put(0x9e, new Pair<>("ifle", "当栈顶int型数值小于等于0时跳转"));
        opCodeMap.put(0x9f, new Pair<>("if_icmpeq", "比较栈顶两int型数值大小, 当结果等于0时跳转"));
        opCodeMap.put(0xa0, new Pair<>("if_icmpne", "比较栈顶两int型数值大小, 当结果不等于0时跳转"));
        opCodeMap.put(0xa1, new Pair<>("if_icmplt", "比较栈顶两int型数值大小, 当结果小于0时跳转"));
        opCodeMap.put(0xa2, new Pair<>("if_icmpge", "比较栈顶两int型数值大小, 当结果大于等于0时跳转"));
        opCodeMap.put(0xa3, new Pair<>("if_icmpgt", "比较栈顶两int型数值大小, 当结果大于0时跳转"));
        opCodeMap.put(0xa4, new Pair<>("if_icmple", "比较栈顶两int型数值大小, 当结果小于等于0时跳转"));
        opCodeMap.put(0xa5, new Pair<>("if_acmpeq", "比较栈顶两引用型数值, 当结果相等时跳转"));
        opCodeMap.put(0xa6, new Pair<>("if_acmpne", "比较栈顶两引用型数值, 当结果不相等时跳转"));
        opCodeMap.put(0xa7, new Pair<>("goto", "无条件跳转"));
        opCodeMap.put(0xa8, new Pair<>("jsr", "跳转至指定的16位offset位置, 并将jsr的下一条指令地址压入栈顶"));
        opCodeMap.put(0xa9, new Pair<>("ret", "返回至本地变量指定的index的指令位置(一般与jsr或jsr_w联合使用)"));
        opCodeMap.put(0xaa, new Pair<>("tableswitch", "用于switch条件跳转, case值连续(可变长度指令)"));
        opCodeMap.put(0xab, new Pair<>("lookupswitch", "用于switch条件跳转, case值不连续(可变长度指令)"));
        opCodeMap.put(0xac, new Pair<>("ireturn", "从当前方法返回int"));
        opCodeMap.put(0xad, new Pair<>("lreturn", "从当前方法返回long"));
        opCodeMap.put(0xae, new Pair<>("freturn", "从当前方法返回float"));
        opCodeMap.put(0xaf, new Pair<>("dreturn", "从当前方法返回double"));
        opCodeMap.put(0xb0, new Pair<>("areturn", "从当前方法返回对象引用"));
        opCodeMap.put(0xb1, new Pair<>("return", "从当前方法返回void"));
        opCodeMap.put(0xb2, new Pair<>("getstatic", "获取指定类的静态域, 并将其压入栈顶"));
        opCodeMap.put(0xb3, new Pair<>("putstatic", "为指定类的静态域赋值"));
        opCodeMap.put(0xb4, new Pair<>("getfield", "获取指定类的实例域, 并将其压入栈顶"));
        opCodeMap.put(0xb5, new Pair<>("putfield", "为指定类的实例域赋值"));
        opCodeMap.put(0xb6, new Pair<>("invokevirtual", "调用实例方法"));
        opCodeMap.put(0xb7, new Pair<>("invokespecial", "调用超类构建方法, 实例初始化方法, 私有方法"));
        opCodeMap.put(0xb8, new Pair<>("invokestatic", "调用静态方法"));
        opCodeMap.put(0xb9, new Pair<>("invokeinterface", "调用接口方法"));
        opCodeMap.put(0xba, new Pair<>("invokedynamic", "调用动态方法"));
        opCodeMap.put(0xbb, new Pair<>("new", "创建一个对象, 并将其引用引用值压入栈顶"));
        opCodeMap.put(0xbc, new Pair<>("newarray", "创建一个指定的原始类型(如int, float, char等)的数组, 并将其引用值压入栈顶"));
        opCodeMap.put(0xbd, new Pair<>("anewarray", "创建一个引用型(如类, 接口, 数组)的数组, 并将其引用值压入栈顶"));
        opCodeMap.put(0xbe, new Pair<>("arraylength", "获取数组的长度值并压入栈顶"));
        opCodeMap.put(0xbf, new Pair<>("athrow", "将栈顶的异常抛出"));
        opCodeMap.put(0xc0, new Pair<>("checkcast", "检验类型转换, 检验未通过将抛出 ClassCastException"));
        opCodeMap.put(0xc1, new Pair<>("instanceof", "检验对象是否是指定类的实际, 如果是将1压入栈顶, 否则将0压入栈顶"));
        opCodeMap.put(0xc2, new Pair<>("monitorenter", "获得对象的锁, 用于同步方法或同步块"));
        opCodeMap.put(0xc3, new Pair<>("monitorexit", "释放对象的锁, 用于同步方法或同步块"));
        opCodeMap.put(0xc4, new Pair<>("wide", "扩展本地变量的宽度"));
        opCodeMap.put(0xc5, new Pair<>("multianewarray", "创建指定类型和指定维度的多维数组(执行该指令时, 操作栈中必须包含各维度的长度值), 并将其引用压入栈顶"));
        opCodeMap.put(0xc6, new Pair<>("ifnull", "为null时跳转"));
        opCodeMap.put(0xc7, new Pair<>("ifnonnull", "不为null时跳转"));
        opCodeMap.put(0xc8, new Pair<>("goto_w", "无条件跳转(宽索引)"));
        opCodeMap.put(0xc9, new Pair<>("jsr_w", "跳转至指定的32位offset位置, 并将jsr_w的下一条指令地址压入栈顶"));
    }
}
