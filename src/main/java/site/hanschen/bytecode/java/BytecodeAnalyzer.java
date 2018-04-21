package site.hanschen.bytecode.java;

import site.hanschen.bytecode.java.utils.Logger;
import site.hanschen.bytecode.java.utils.Utils;

import java.io.IOException;

/**
 * @author chenhang
 */
public class BytecodeAnalyzer {

    public static void main(String[] args) {
        Logger.d("analyze start...");
        try {
            ClassFileReader reader = new ClassFileReader(Utils.getStreamFromResource("BytecodeAnalyzer.class"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
