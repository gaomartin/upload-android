package com.pplive.media.upload.util;

import android.util.Log;

/**
 * 日志工具类
 * 
 */
public class LogUtils {
	/**
	 * 日志级别
	 */
	private static boolean ENABLE_LOG = true;

	/**
	 * 异常栈位移
	 */
	private static final int EXCEPTION_STACK_INDEX = 2;

	/**
	 * 开启日志
	 */
	public static void enableLog() {
		ENABLE_LOG = true;
	}

	/**
	 * 关闭日志
	 */
	public static void disableLog() {
		ENABLE_LOG = false;
	}

	/**
	 * 是否开启日志
	 */
	public static boolean isEnableLog() {
		return ENABLE_LOG;
	}

	/**
	 * verbose级别的日志
	 * 
	 * @param msg
	 *            打印内容
	 * @see [类、类#方法、类#成员]
	 */
	public static void verbose(String msg) {
		if (ENABLE_LOG) {
			Log.v(getTag(), msg);
		}
	}

	/**
	 * debug级别的日志
	 * 
	 * @param msg
	 *            打印内容
	 * @see [类、类#方法、类#成员]
	 */
	public static void debug(String msg) {
		if (ENABLE_LOG) {
			Log.d(getTag(), msg);
		}
	}

	/**
	 * info级别的日志
	 * 
	 * @param msg
	 *            打印内容
	 * @see [类、类#方法、类#成员]
	 */
	public static void info(String msg) {
		if (ENABLE_LOG) {
			Log.i(getTag(), msg);
		}
	}

	/**
	 * warn级别的日志
	 * 
	 * @param msg
	 *            打印内容
	 * @see [类、类#方法、类#成员]
	 */
	public static void warn(String msg) {
		if (ENABLE_LOG) {
			Log.w(getTag(), msg);
		}
	}

	/**
	 * error级别的日志
	 * 
	 * @param msg
	 *            打印内容
	 * @see [类、类#方法、类#成员]
	 */
	public static void error(String msg) {
		if (ENABLE_LOG) {
			String tag = getTag();
			Log.e(tag, msg);
		}
		ApplogManager.getInstance().invokeMethod(ApplogManager.ACTION_LOGCAT,
				msg);
	}

	public static void error(String msg, Throwable tr) {
		if (ENABLE_LOG) {
			String tag = getTag();
			Log.e(tag, msg, tr);
		}
		ApplogManager.getInstance().invokeMethod(ApplogManager.ACTION_LOGCAT,
				msg + (", throws: " + tr));
	}

	/**
	 * 获取日志的标签 格式：类名_方法名_行号 （需要权限：android.permission.GET_TASKS）
	 * 
	 * @return tag
	 * @see [类、类#方法、类#成员]
	 */
	public static String getTag() {
		try {
			Exception exception = new Exception();
			if (exception.getStackTrace() == null
					|| exception.getStackTrace().length <= EXCEPTION_STACK_INDEX) {
				return "***";
			}
			StackTraceElement element = exception.getStackTrace()[EXCEPTION_STACK_INDEX];
			String className = element.getClassName();
			int index = className.lastIndexOf(".");
			if (index > 0) {
				className = className.substring(index + 1);
			}
			return className + "_" + element.getMethodName() + "_"
					+ element.getLineNumber();
		} catch (Throwable e) {
			e.printStackTrace();
			return "***";
		}
	}
}
