/**
 * 
 */
package kr.pe.util;

import java.lang.reflect.Method;

/**
 * 
 */
public final class Obj {

	/**
	 * static final String[] EMPTY_STRING_ARRAY = new String[0];
	 */
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	/**
	 * <pre>
	 * There is a delay of a certain amount of time.
	 * 일정 시간 지체한다.
	 * </pre>
	 * 
	 * @param millis
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * If there is a close function, run it.
	 * close 함수가 있으면 실행한다.
	 * </pre>
	 * 
	 * @param o
	 * @return
	 */
	public static Object invokeCloseIfExists(Object o) {
		if (o == null) {
			return null;
		}
		try {
			Method m = o.getClass().getMethod("close");

			// 2. 메서드가 존재하면 실행 (접근 제어자가 public이 아닐 경우를 대비해 설정)
			m.setAccessible(true);
			m.invoke(o);
			System.out.println("[" + o.getClass().getSimpleName() + "] close() 실행 완료");
		} catch (NoSuchMethodException e) {

			// close() 메서드가 없는 경우 아무것도 하지 않음
			System.out.println("[" + o.getClass().getSimpleName() + "] close() 메서드가 존재하지 않습니다.");
		} catch (Exception e) {
			// 실행 중 기타 예외 발생 처리
			System.err.println("[" + o.getClass().getSimpleName() + "] close() 실행 중 오류 발생: " + e.getMessage());
		}
		return o;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Obj.sleep(1000);
	}

}
