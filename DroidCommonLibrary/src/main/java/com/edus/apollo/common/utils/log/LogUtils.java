package com.edus.apollo.common.utils.log;

import android.util.Log;

/**
 * Created by PandaPan on 2016/12/25.
 */

public class LogUtils {
    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    private static final boolean logVisible = true;

    private static final int visibleMinLevel = DEBUG;

    private static boolean isLogable(int level){
        return logVisible && level >= visibleMinLevel;
    }

    public static void v(String tag, String msg) {
        if(isLogable(VERBOSE)){
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr){
        if(isLogable(VERBOSE)){
            Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if(isLogable(DEBUG)){
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr){
        if(isLogable(DEBUG)){
            Log.d(tag, msg, tr);
        }
    }


    public static void i(String tag, String msg) {
        if(isLogable(INFO)){
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr){
        if(isLogable(INFO)){
            Log.i(tag, msg, tr);
        }
    }
    public static void w(String tag, String msg) {
        if(isLogable(WARN)){
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr){
        if(isLogable(WARN)){
            Log.w(tag, msg, tr);
        }
    }
    public static void e(String tag, String msg) {
        if(isLogable(ERROR)){
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr){
        if(isLogable(ERROR)){
            Log.e(tag, msg, tr);
        }
    }


    public static void logStackTrack(String tag){
        if(isLogable(DEBUG)){
            for (StackTraceElement i : Thread.currentThread().getStackTrace()) {
                Log.d(tag, i.toString());
            }
        }

    }
}
