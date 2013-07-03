package com.mye.gmobile.common.log;

import android.util.Log;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-2-3
 */
public class LogUtil {

    /**
     * Priority constant for the println method; use LogUtil.v. <br />
     * Constant Value: 2 (0x00000002)
     */
    public static final int VERBOSE = Log.VERBOSE;

    /**
     * Priority constant for the println method; use LogUtil.d.<br />
     * Constant Value: 3 (0x00000003)
     */
    public static final int DEBUG = Log.DEBUG;

    /**
     * Priority constant for the println method; use LogUtil.i.<br />
     * Constant Value: 4 (0x00000004)
     */
    public static final int INFO = Log.INFO;

    /**
     * Priority constant for the println method; use LogUtil.w.<br />
     * Constant Value: 5 (0x00000005)
     */
    public static final int WARN = Log.WARN;

    /**
     * Priority constant for the println method; use LogUtil.e.<br />
     * Constant Value: 6 (0x00000006)
     */
    public static final int ERROR = Log.ERROR;

    /**
     * Priority constant for the println method.<br />
     * Constant Value: 7 (0x00000007)
     */
    public static final int ASSERT = Log.ASSERT;

    /**
     * control level.
     */
    private static int level = VERBOSE;

    /**
     * get the level.
     * 
     * @return the level
     */
    public static int getLevel() {
        return level;
    }

    /**
     * set the level.
     * 
     * @param level
     *            the level to set
     */
    public static void setLevel(int level) {
        LogUtil.level = level;
    }

    /**
     * Send a VERBOSE log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public static int verbose(String tag, String msg) {
        if (VERBOSE >= level) {
            return Log.v(tag, msg);
        }
        return 0;
    }

    /**
     * Send a VERBOSE log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     * @param tr
     *            An exception to log
     */
    public static int verbose(String tag, String msg, Throwable tr) {
        if (VERBOSE >= level) {
            return Log.v(tag, msg, tr);
        }
        return 0;
    }

    /**
     * Send a DEBUG log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public static int debug(String tag, String msg) {
        if (DEBUG >= level) {
            return Log.d(tag, msg);
        }
        return 0;
    }

    /**
     * Send a DEBUG log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     * @param tr
     *            An exception to log
     */
    public static int debug(String tag, String msg, Throwable tr) {
        if (DEBUG >= level) {
            return Log.d(tag, msg, tr);
        }
        return 0;
    }

    /**
     * Send a INFO log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public static int info(String tag, String msg) {
        if (INFO >= level) {
            return Log.i(tag, msg);
        }
        return 0;
    }

    /**
     * Send a INFO log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     * @param tr
     *            An exception to log
     */
    public static int info(String tag, String msg, Throwable tr) {
        if (INFO >= level) {
            return Log.i(tag, msg, tr);
        }
        return 0;
    }

    /**
     * Send an ERROR log message.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public static int error(String tag, String msg) {
        if (ERROR >= level) {
            return Log.e(tag, msg);
        }
        return 0;
    }

    /**
     * Send a ERROR log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     * @param tr
     *            An exception to log
     */
    public static int error(String tag, String msg, Throwable tr) {
        if (ERROR >= level) {
            return Log.e(tag, msg, tr);
        }
        return 0;
    }

    /**
     * Send a WARN log message.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public static int warn(String tag, String msg) {
        if (WARN >= level) {
            return Log.w(tag, msg);
        }
        return 0;
    }

    /**
     * Send a WARN log message. and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     * @param tr
     *            An exception to log
     */
    public static int warn(String tag, Throwable tr) {
        if (WARN >= level) {
            return Log.w(tag, tr);
        }
        return 0;
    }

    /**
     * Send a WARN log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     * @param tr
     *            An exception to log
     */
    public static int warn(String tag, String msg, Throwable tr) {
        if (WARN >= level) {
            return Log.w(tag, msg, tr);
        }
        return 0;
    }

}
