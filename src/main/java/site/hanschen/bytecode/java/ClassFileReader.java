package site.hanschen.bytecode.java;

import org.apache.commons.io.IOUtils;
import site.hanschen.bytecode.java.model.AttributeInfo;
import site.hanschen.bytecode.java.model.FieldInfo;
import site.hanschen.bytecode.java.model.MethodInfo;
import site.hanschen.bytecode.java.model.constant.*;
import site.hanschen.bytecode.java.utils.Logger;
import site.hanschen.bytecode.java.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhang
 */
public class ClassFileReader {

    private static List<ConstantElementParser> PARSERS = new ArrayList<>();

    static {
        PARSERS.add(new ClassInfo.Parser());
        PARSERS.add(new FieldRefInfo.Parser());
        PARSERS.add(new MethodRefInfo.Parser());
        PARSERS.add(new InterfaceMethodRef.Parser());
        PARSERS.add(new StringInfo.Parser());
        PARSERS.add(new IntegerInfo.Parser());
        PARSERS.add(new FloatInfo.Parser());
        PARSERS.add(new LongInfo.Parser());
        PARSERS.add(new DoubleInfo.Parser());
        PARSERS.add(new NameAndTypeInfo.Parser());
        PARSERS.add(new Utf8Info.Parser());
        PARSERS.add(new MethodHandleInfo.Parser());
        PARSERS.add(new MethodTypeInfo.Parser());
        PARSERS.add(new InvokeDynamicInfo.Parser());
    }

    public ClassFileReader(File file) throws IOException {
        this(new FileInputStream(file));
    }

    public ClassFileReader(InputStream input) throws IOException {
        this(IOUtils.toByteArray(input));
    }

    public ClassFileReader(byte[] data) {
        this(ByteBuffer.wrap(data));
    }

    public ClassFileReader(ByteBuffer buffer) {
        Logger.d(Utils.dumpHex(buffer.array()));
        buffer.position(0);
        buffer = buffer.asReadOnlyBuffer().order(ByteOrder.BIG_ENDIAN);

        // check magic number.
        int magic = buffer.getInt();
        if (ClassFile.MAGIC_CLASS_FILE != magic) {
            throw new IllegalArgumentException(String.format("invalid java class file, magic number: 0x%X", magic));
        }

        // show version.
        int minor = buffer.getShort() & 0xffff;
        int major = buffer.getShort() & 0xffff;
        Logger.d("bytecode version: %d.%d", major, minor);

        // constant_pool_count
        int constantPoolCount = buffer.getShort() & 0xffff;
        Logger.d("constantPoolCount: %d", constantPoolCount);

        // attention, The constant_pool table is indexed from 1 to constant_pool_count-1
        // constantPool actual size: constant_pool_count-1
        ConstantElement[] constantPool = new ConstantElement[constantPoolCount];
        for (int i = 1; i < constantPoolCount; i++) {
            short tag = buffer.get();
            // reset position at tag data
            buffer.position(buffer.position() - 1);
            ConstantElementParser parser = null;
            for (ConstantElementParser p : PARSERS) {
                if (p.match(tag)) {
                    parser = p;
                    break;
                }
            }
            if (parser == null) {
                throw new IllegalStateException("unsupported tag: " + tag);
            }

            ConstantElement element = parser.create(buffer);
            constantPool[i] = element;
        }
        for (int i = 1; i < constantPoolCount; i ++) {
            ConstantElement element = constantPool[i];
            String comment = element.getComment(constantPool);
            if (comment != null && comment.length() > 0) {
                Logger.d("#%02d  =  %-20s %-10s // %s", i, element.getTag(), element.getValue(), comment);
            } else {
                Logger.d("#%02d  =  %-20s %s", i, element.getTag(), element.getValue());
            }
        }

        // parse access flags
        short accessFlags = buffer.getShort();
        Logger.d("accessFlags: %d", accessFlags);

        // get class name
        int thisClassIndex = buffer.getShort() & 0xffff;
        ClassInfo classInfo = (ClassInfo) constantPool[thisClassIndex];
        Utf8Info classname = (Utf8Info) constantPool[classInfo.nameIndex];
        Logger.d("this class name: %s", classname.value);

        // parse super class name
        int superClass = buffer.getShort() & 0xffff;
        if (superClass == 0) {
            // this class file must represent the class Object, the only class or interface without a direct superclass.
        } else {
            classInfo = (ClassInfo) constantPool[superClass];
            classname = (Utf8Info) constantPool[classInfo.nameIndex];
            Logger.d("super class name: %s", classname.value);
        }

        // parse interfaces
        int interfaceCount = buffer.getShort() & 0xffff;
        Logger.d("interface count: %d", interfaceCount);
        if (interfaceCount > 0) {
            int interfaceIndex;
            for (int i = 0; i < interfaceCount; i++) {
                interfaceIndex = buffer.getShort() & 0xffff;
                classInfo = (ClassInfo) constantPool[interfaceIndex];
                classname = (Utf8Info) constantPool[classInfo.nameIndex];
                Logger.d("interface name: %s", classname.value);
            }
        }

        // parse fields count
        int fieldsCount = buffer.getShort() & 0xffff;
        Logger.d("fields count: %d", fieldsCount);
        if (fieldsCount > 0) {
            FieldInfo[] fieldInfo = new FieldInfo[fieldsCount];
            for (int i = 0; i < fieldsCount; i++) {
                fieldInfo[i] = FieldInfo.readFrom(buffer);
                Logger.d("field: %s", ((Utf8Info) constantPool[fieldInfo[i].nameIndex]).value);
            }
        }

        // parse method count
        int methodsCount = buffer.getShort() & 0xffff;
        Logger.d("methods count: %d", methodsCount);
        if (methodsCount > 0) {
            MethodInfo[] methodInfo = new MethodInfo[methodsCount];
            for (int i = 0; i < methodsCount; i++) {
                methodInfo[i] = MethodInfo.readFrom(buffer);
                Logger.d("method: %s", ((Utf8Info) constantPool[methodInfo[i].nameIndex]).value);
            }
        }

        // parse attributes count
        int attributesCount = buffer.getShort() & 0xffff;
        Logger.d("attributes count: %d", attributesCount);
        if (attributesCount > 0) {
            AttributeInfo[] attributeInfo = new AttributeInfo[attributesCount];
            for (int i = 0; i < attributesCount; i++) {
                attributeInfo[i] = AttributeInfo.readFrom(buffer);
            }
        }

        Logger.d("parse finish, remain byte: %d", buffer.remaining());
    }
}
