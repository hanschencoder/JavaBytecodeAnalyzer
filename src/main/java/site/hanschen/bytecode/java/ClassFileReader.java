package site.hanschen.bytecode.java;

import org.apache.commons.io.IOUtils;
import site.hanschen.bytecode.java.model.FieldInfo;
import site.hanschen.bytecode.java.model.MethodInfo;
import site.hanschen.bytecode.java.model.attribute.AttributeInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author chenhang
 */
public class ClassFileReader {

    private int minor;
    private int major;
    private ConstantPool constantPool;
    private short accessFlags;
    private int thisClassIndex;
    private int superClass;
    private int interfaceCount;
    private int[] interfaces;
    private int fieldsCount;
    private FieldInfo[] fieldInfo;
    private int methodsCount;
    private MethodInfo[] methodInfo;
    private int attributesCount;
    private AttributeInfo[] attributeInfo;

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
        buffer.position(0);
        buffer = buffer.asReadOnlyBuffer().order(ByteOrder.BIG_ENDIAN);

        // check magic number.
        int magic = buffer.getInt();
        if (ClassFile.MAGIC_CLASS_FILE != magic) {
            throw new IllegalArgumentException(String.format("invalid java class file, magic number: 0x%X", magic));
        }

        // parse version
        minor = buffer.getShort() & 0xffff;
        major = buffer.getShort() & 0xffff;

        // parse constant pool
        constantPool = ConstantPool.readFrom(buffer);

        // parse access flags
        accessFlags = buffer.getShort();

        // get class name
        thisClassIndex = buffer.getShort() & 0xffff;

        // parse super class name
        superClass = buffer.getShort() & 0xffff;

        // parse interfaces
        interfaceCount = buffer.getShort() & 0xffff;
        if (interfaceCount > 0) {
            interfaces = new int[interfaceCount];
            for (int i = 0; i < interfaceCount; i++) {
                interfaces[i] = buffer.getShort() & 0xffff;
            }
        }

        // parse fields count
        fieldsCount = buffer.getShort() & 0xffff;
        if (fieldsCount > 0) {
            fieldInfo = new FieldInfo[fieldsCount];
            for (int i = 0; i < fieldsCount; i++) {
                fieldInfo[i] = FieldInfo.readFrom(buffer, constantPool.getConstantPool());
            }
        }

        // parse method count
        methodsCount = buffer.getShort() & 0xffff;
        if (methodsCount > 0) {
            methodInfo = new MethodInfo[methodsCount];
            for (int i = 0; i < methodsCount; i++) {
                methodInfo[i] = MethodInfo.readFrom(buffer, constantPool.getConstantPool());
            }
        }

        // parse attributes count
        attributesCount = buffer.getShort() & 0xffff;
        if (attributesCount > 0) {
            attributeInfo = new AttributeInfo[attributesCount];
            for (int i = 0; i < attributesCount; i++) {
                AttributeInfo attribute = AttributeInfo.readFrom(buffer, constantPool.getConstantPool());
                attributeInfo[i] = attribute;
            }
        }
    }

    public int getMinor() {
        return minor;
    }

    public int getMajor() {
        return major;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public short getAccessFlags() {
        return accessFlags;
    }

    public int getThisClassIndex() {
        return thisClassIndex;
    }

    public int getSuperClass() {
        return superClass;
    }

    public int getInterfaceCount() {
        return interfaceCount;
    }

    public int[] getInterfaces() {
        return interfaces;
    }

    public int getFieldsCount() {
        return fieldsCount;
    }

    public FieldInfo[] getFieldInfo() {
        return fieldInfo;
    }

    public int getMethodsCount() {
        return methodsCount;
    }

    public MethodInfo[] getMethodInfo() {
        return methodInfo;
    }

    public int getAttributesCount() {
        return attributesCount;
    }

    public AttributeInfo[] getAttributeInfo() {
        return attributeInfo;
    }
}
