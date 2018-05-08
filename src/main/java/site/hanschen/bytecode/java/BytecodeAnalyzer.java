package site.hanschen.bytecode.java;

import site.hanschen.bytecode.java.model.FieldInfo;
import site.hanschen.bytecode.java.model.MethodInfo;
import site.hanschen.bytecode.java.model.attribute.AttributeInfo;
import site.hanschen.bytecode.java.model.constant.ClassInfo;
import site.hanschen.bytecode.java.model.constant.Constant;
import site.hanschen.bytecode.java.utils.Logger;
import site.hanschen.bytecode.java.utils.Utils;

import java.io.IOException;

/**
 * @author chenhang
 */
public class BytecodeAnalyzer {

    private String tag = "BytecodeAnalyzer";

    public static void main(String[] args) {
        Logger.d("analyze start...");
        try {
            ClassFileReader reader = new ClassFileReader(Utils.getStreamFromResource("BytecodeAnalyzer.class"));
            dump(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dump(ClassFileReader reader) {
        Logger.d("bytecode version: %d.%d", reader.getMajor(), reader.getMinor());

        ConstantPool constantPool = reader.getConstantPool();
        int poolCount = reader.getConstantPool().getConstantPoolCount();
        for (int i = 1; i < poolCount; i++) {
            Constant constant = constantPool.get(i);
            String comment = constant.getComment(constantPool.getConstantPool());
            int width = String.valueOf(poolCount).length() + 1;
            if (comment != null && comment.length() > 0) {
                Logger.d("%" + width + "s  =  %-20s %-10s // %s", ("#" + i), constant.getTag(), constant.getValue(), comment);
            } else {
                Logger.d("%" + width + "s  =  %-20s %s", ("#" + i), constant.getTag(), constant.getValue());
            }
        }

        Logger.d("accessFlags: %s", Utils.getClassAccessFlags(reader.getAccessFlags()));

        ClassInfo classInfo = constantPool.get(reader.getThisClassIndex());
        Logger.d("this.class: %s", classInfo.getComment(constantPool.getConstantPool()));

        //noinspection StatementWithEmptyBody
        if (reader.getSuperClass() == 0) {
            // this class file must represent the class Object, the only class or interface without a direct superclass.
        } else {
            classInfo = constantPool.get(reader.getSuperClass());
            Logger.d("super.class: %s", classInfo.getComment(constantPool.getConstantPool()));
        }

        Logger.d("interface count: %d", reader.getInterfaceCount());
        if (reader.getInterfaceCount() > 0) {
            for (int index : reader.getInterfaces()) {
                classInfo = constantPool.get(index);
                Logger.d("interface: %s", classInfo.getComment(constantPool.getConstantPool()));
            }
        }

        Logger.d("fields count: %d", reader.getFieldsCount());
        if (reader.getFieldsCount() > 0) {
            for (FieldInfo fieldInfo : reader.getFieldInfo()) {
                Logger.d("field: %s", constantPool.get(fieldInfo.nameIndex).getValue());
            }
        }

        Logger.d("methods count: %d", reader.getMethodsCount());
        if (reader.getMethodsCount() > 0) {
            for (MethodInfo methodInfo : reader.getMethodInfo()) {
                Logger.d("method: %s", constantPool.get(methodInfo.nameIndex).getValue());
            }
        }

        Logger.d("attributes count: %d", reader.getAttributesCount());
        if (reader.getAttributesCount() > 0) {
            for (AttributeInfo attribute : reader.getAttributeInfo()) {
                Logger.d(String.format("attribute:%s, value:%s", attribute.getAttributeName(), attribute.getComment()));
            }
        }
    }
}
