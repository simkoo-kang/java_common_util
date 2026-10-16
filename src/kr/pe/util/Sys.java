/**
 * Sys.java
 */
package kr.pe.util;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PC 2025. 4. 27.
 */
public class Sys {
	private static Logger logger = LoggerFactory.getLogger(Sys.class);

	/**
	 * <pre>
	 * 호출하는 클래스의 이름과 메서드 이름
	 * ClassName.MethodName
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 8. 27. 오전 11:44:09
	 * </pre>
	 * 
	 * @param index
	 * @return
	 */
	public static String getClassAndMethodNameOfCaller(int index) {
		return getClassAndMethodNameOfCaller(index, 1);
	}

	/**
	 * <pre>
	 * 호출하는 클래스의 이름과 메서드 이름
	 * ClassName.MethodName
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 9. 8. 오후 2:28:28
	 * </pre>
	 *
	 * @param index
	 * @param more
	 * @return
	 */
	public static String getClassAndMethodNameOfCaller(int index, int more) {
		// : Thread.getStackTrace()
		// : LogUtil.getCallerInfo() (현재 메서드)
		// : 이 메서드를 실제로 호출한 곳
		return getClassNameOfCaller(index, more) + "." + getMethodNameOfCaller(index, more);
	}

	/**
	 * <pre>
	 * 호출하는 클래스의 이름과 메서드 이름
	 * ClassName.MethodName
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 8. 27. 오전 11:44:09
	 * </pre>
	 *
	 * @return
	 */
	public static String getClassAndMethodNameOfCaller() {
		return getClassAndMethodNameOfCaller(2, 1);
	}

	/**
	 * <pre>
	 * 호출하는 클래스의 이름
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 8. 27. 오전 11:39:03
	 * </pre>
	 *
	 * @return
	 */
	public static String getClassNameOfCaller() {
		return getClassNameOfCaller(2, 1);
	}

	/**
	 * <pre>
	 * 호출하는 클래스의 이름
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 9. 8. 오후 1:27:41
	 * </pre>
	 *
	 * @param index
	 * @return
	 */
	public static String getClassNameOfCaller(int index) {
		return getClassNameOfCaller(index, 1);
	}

	/**
	 * <pre>
	 * 호출하는 클래스의 이름
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 9. 8. 오후 2:27:19
	 * </pre>
	 *
	 * @param index
	 * @param more
	 * @return
	 */
	public static String getClassNameOfCaller(int index, int more) {
		// : Thread.getStackTrace()
		// : LogUtil.getCallerInfo() (현재 메서드)
		// : 이 메서드를 실제로 호출한 곳
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		int idx = Math.min(index + more, stackTrace.length - 1);
		if (idx < 1) {
			return "";
		}
		StackTraceElement caller = stackTrace[idx];
		return caller.getClassName();
	}

	/**
	 * <pre>
	 * 호출하는 클래스의 매서드 이름
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 8. 27. 오전 11:42:28
	 * </pre>
	 *
	 * @return
	 */
	public static String getMethodNameOfCaller() {
		return getMethodNameOfCaller(2, 1);
	}

	/**
	 * <pre>
	 * 호출하는 클래스의 매서드 이름
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 8. 27. 오전 11:42:28
	 * </pre>
	 *
	 * @param index
	 * @return
	 */
	public static String getMethodNameOfCaller(int index) {
		return getMethodNameOfCaller(index, 1);
	}

	/**
	 * <pre>
	 * 호출하는 클래스의 매서드 이름
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 9. 8. 오후 2:24:25
	 * </pre>
	 *
	 * @param index
	 * @param more
	 * @return
	 */
	public static String getMethodNameOfCaller(int index, int more) {
		// : Thread.getStackTrace()
		// : LogUtil.getCallerInfo() (현재 메서드)
		// : 이 메서드를 실제로 호출한 곳
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		int idx = Math.min(index + more, stackTrace.length - 1);
		if (idx < 1) {
			return "";
		}
		StackTraceElement caller = stackTrace[idx];
		return caller.getMethodName() + "()";
	}

	public static String getMethodName() {
		// : Thread.getStackTrace()
		// : LogUtil.getCallerInfo() (현재 메서드)
		// : 이 메서드를 실제로 호출한 곳
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement caller = stackTrace[2];
		return caller.getMethodName() + "()";
	}

	/**
	 * <pre>
	 * 호출하는 클래스의 매서드 이름
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 8. 27. 오전 11:42:28
	 * </pre>
	 * 
	 * @return
	 */
	public static int getLineNumberOfCaller() {
		return getLineNumberOfCaller(2, 1);
	}

	/**
	 * <pre>
	 * 호출하는 클래스의 매서드 이름
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 8. 27. 오전 11:42:28
	 * </pre>
	 * 
	 * @param index
	 * @return
	 */
	public static int getLineNumberOfCaller(int index) {
		return getLineNumberOfCaller(index, 1);
	}

	/**
	 * <pre>
	 * 호출하는 클래스의 매서드 이름
	 * 
	 * Thread.currentThread().getStackTrace()를 이용해 호출한 곳의 클래스명과 메서드명을 정확하게 가져오려면,
	 *  스택 트레이스의 인덱스 위치(배열 순서)를 올바르게 조정해야 합니다.
	 *  [1] 인덱스는 메서드 안에서 바로 호출할 때의 위치이며,
	 *  이를 별도의 유틸리티 함수(공통 함수)로 분리하면 스택에 함수가 하나 더 쌓이므로
	 *  인덱스를 [2]로 변경해야 호출한 곳의 정보를 가져올 수 있습니다.
	 * 
	 * PC 2026. 9. 8. 오후 2:25:59
	 * </pre>
	 *
	 * @param index
	 * @param more
	 * @return
	 */
	public static int getLineNumberOfCaller(int index, int more) {
		// : Thread.getStackTrace()
		// : LogUtil.getCallerInfo() (현재 메서드)
		// : 이 메서드를 실제로 호출한 곳
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		int idx = Math.min(index + more, stackTrace.length - 1);
		if (idx < 1) {
			return -1;
		}
//		logger.debug("getLineNumberOfCaller(index={}, more={}) idx={}", index, more, idx);
		StackTraceElement caller = stackTrace[idx];
		return caller.getLineNumber();
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 9:34:36
	 * </pre>
	 *
	 * @param className
	 * @return
	 */
	public static boolean isInterface(String className) {
		try {
			if (Str.isEmpty(className)) {
				return false;
			}

			// 문자열로 Class 객체를 동적으로 로드
			Class<?> clazz = Class.forName(className);
			return clazz.isInterface();
		} catch (ClassNotFoundException e) {
			System.out.println(className + " 해당 이름을 가진 클래스를 찾을 수 없습니다.");
		}
		return false;
	}

	/**
	 * Get the string from the system clipboard. 시스템 클립보드에서 문자열을 가져온다.
	 * 
	 * @return
	 */
	public static String getStringFromClipboard() {
		try {
			return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Save the string to the system clipboard. 시스템 클립보드에 문자열을 저장한다.
	 * 
	 * @param text
	 */
	public static void setStringToClipboard(String text) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
	}

	/**
	 * Verify that the clipboard has a stored string. 클립보드에 저장된 문자열이 있는 지 확인한다.
	 * 
	 * @return
	 */
	public static boolean existsTextAtClipboard() {
		return Str.isEmpty(getStringFromClipboard());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		setStringToClipboard("System.getenv()");
//		if (existsTextAtClipboard()) {
//			System.out.println(getStringFromClipboard());
//		} else {
//			System.out.println("clipboard is empty");
//		}
//		Map<String, String> envMap = System.getenv();
//		for (Iterator<String> it = envMap.keySet().iterator(); it.hasNext();) {
//			String key = it.next();
//			System.out.println(key + " : " + envMap.get(key));
//		}
//		Properties prop = System.getProperties();
//		for (Iterator<?> it = prop.keySet().iterator(); it.hasNext();) {
//			Object key = it.next();
//			System.out.println(key.toString() + " : " + prop.getProperty(key.toString()));
//		}
		String clsname = Thread.currentThread().getStackTrace()[1].getClassName();
		String mthname = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug("{} {}", clsname, mthname);
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		int idx = 0;
		for (StackTraceElement e : ste) {
			logger.debug("{} = {}", idx++, e.toString());
		}
	}
}
