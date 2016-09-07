package com.lsh.lib.android.utils.log;

import android.util.Log;

/**
 * 日志输出
 * Author:liush
 * Date:2016/7/25
 */
public class CLog {
    static String TAG = "CLog--->";
    //是否屏蔽日志
    public static boolean isDebug = true;

    /**
     * 是否关闭日志
     *
     * @param isDebug
     */
    public static void setIsDebug(boolean isDebug) {
        CLog.isDebug = isDebug;
    }

    //------------------DEBUG-------------------------------
    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg, Throwable t) {
        if (isDebug) {
            Log.d(TAG, msg, t);
        }
    }

    public static void d(String tag, String msg, Throwable t) {
        if (isDebug) {
            Log.d(tag, msg, t);
        }
    }

    //--------------------ERROR---------------------------
    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg, Throwable t) {
        if (isDebug) {
            Log.e(TAG, msg, t);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (isDebug) {
            Log.e(tag, msg, t);
        }
    }

    //--------------------INFO---------------------------
    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg, Throwable t) {
        if (isDebug) {
            Log.i(TAG, msg, t);
        }
    }

    public static void i(String tag, String msg, Throwable t) {
        if (isDebug) {
            Log.i(tag, msg, t);
        }
    }

    //--------------------WARN--------------------------
    public static void w(String msg) {
        if (isDebug) {
            Log.w(TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg, Throwable t) {
        if (isDebug) {
            Log.w(TAG, msg, t);
        }
    }

    public static void w(String tag, String msg, Throwable t) {
        if (isDebug) {
            Log.w(tag, msg, t);
        }
    }
}
