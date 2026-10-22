/**
 * LogUtil.java
 */
package kr.pe.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 1:18:37
 * </pre>
 */
public class LogUtil {

	private static final Map<Object, Logger> savedLoggerMap = new HashMap<>();

	/**
	 * <pre></pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Logger logger = LogUtil.getLogger(LogUtil.class);
		try {
			throw new Exception("test");
		} catch (Exception e) {
			logger.debug("exception {}", e.getMessage(), e);
		}
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 10:00:23
	 * </pre>
	 *
	 * @param callerObject
	 * @param message
	 */
	public static void debugAll(Object callerObject, String message) {
		if (callerObject == null) {
			debugAll(null, "{}", message);
		} else {
			debugAll(callerObject.getClass(), "{}", message);
		}
	}

	/**
	 * <pre>
	 * 첫 번째 파라미터로 호출한 객체(Object callerObject)를 받습니다.
	 * 
	 * PC 2026. 9. 8. 오후 9:53:26
	 * </pre>
	 *
	 * @param callerObject
	 * @param fmt
	 * @param args
	 */
	public static void debugAll(Object callerObject, String fmt, Object... args) {
		if (Str.isEmpty(fmt)) {
			return;
		}
		if (callerObject == null) {
			debugAll(null, fmt, args);
		} else {
			debugAll(callerObject.getClass(), fmt, args);
		}
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 10:00:23
	 * </pre>
	 *
	 * @param callerObject
	 * @param message
	 */
	public static void debugAll(Class<?> clazz, String message) {
		debugAll(clazz, "{}", message);
	}

	/**
	 * <pre>
	 * 첫 번째 파라미터로 호출한 객체(Object callerObject)를 받습니다.
	 * 
	 * PC 2026. 9. 8. 오후 9:53:26
	 * </pre>
	 *
	 * @param clazz
	 * @param fmt
	 * @param args
	 */
	public static void debugAll(Class<?> clazz, String fmt, Object... args) {
		if (Str.isEmpty(fmt)) {
			return;
		}
		Logger allUseLogger;
		if (clazz == null) {
			String name = getCallerName(LogUtil.class);
			if (savedLoggerMap.containsKey(name)) {
				allUseLogger = savedLoggerMap.get(name);
			} else {
				allUseLogger = getLogger(name);
				savedLoggerMap.put(name, allUseLogger);
			}
		} else {
			if (savedLoggerMap.containsKey(clazz)) {
				allUseLogger = savedLoggerMap.get(clazz);
			} else {
				allUseLogger = getLogger(clazz);
				savedLoggerMap.put(clazz, allUseLogger);
			}
		}
		allUseLogger.debug(fmt, args);
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 10. 오후 8:34:01
	 * </pre>
	 *
	 * @param class
	 * @return
	 */
	private static String getCallerName(Class<?> clazz) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		String className = clazz.getName();
		StackTraceElement prev = null;
		for (StackTraceElement e : stackTrace) {
			if (className.equals(e.getClassName())) {
				break;
			}
			prev = e;
		}
		if (prev == null) {
			return clazz.getName();
		}
		return prev.getClassName();
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 1:20:13
	 * </pre>
	 *
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 1:21:30
	 * </pre>
	 *
	 * @param o
	 * @return
	 */
	public static Logger getLogger(Object o) {
		return LoggerFactory.getLogger(o.getClass());
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 1:23:03
	 * </pre>
	 *
	 * @param name
	 * @return
	 */
	public static Logger getLogger(String name) {
		return LoggerFactory.getLogger(name);
	}
}
