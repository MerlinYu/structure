package com.structure.utils;

import android.util.Log;

import com.structure.BuildConfig;

/**
 * Created by yuchao.
 */
public class DebugLog {

    private static String methodName;
    private static String className;
    private static int number;

    private DebugLog() {

    }

    public static boolean isDebugLog() {
        return BuildConfig.DEBUG;
    }


    public static void d(String message) {
        if (isDebugLog()) {
            Log.d(className,createLog(message));
        }
    }

    public static void e(String message) {
        if (isDebugLog()) {
            Log.e(className, createLog(message));
        }
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        getErrorInfo(new Throwable().getStackTrace());
        buffer.append("[");
        buffer.append(methodName);
        buffer.append("]");
        buffer.append(" line: " + number + " " + log);
        return buffer.toString();
    }

    private static void getErrorInfo(StackTraceElement[] stackTrace) {
        className = stackTrace[1].getClassName();
        methodName = stackTrace[1].getMethodName();
        number = stackTrace[1].getLineNumber();
    }


}
