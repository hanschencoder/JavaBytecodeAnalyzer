package site.hanschen.bytecode.java.utils;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;


/**
 * @author chenhang
 */
public class Logger {


    private Logger() {
        //no instance
    }

    public static void d(String msg) {
        log(msg);
    }

    public static void d(String format, Object... args) {
        log(format, args);
    }

    public static void d(String msg, Throwable tr) {
        log(msg, tr);
    }

    private static void log(String format, Object... args) {
        if (format != null && args != null && args.length > 0) {
            log(String.format(format, args));
        } else {
            log(format);
        }
    }

    private static void log(String message, Throwable tr) {
        log(message + '\n' + throwableToString(tr));
    }

    private static void log(String message) {
        System.out.println(message);
    }

    public static String throwableToString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
