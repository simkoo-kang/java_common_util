/**
 * Assert.java
 */
package kr.pe.util.err;

import java.util.Collection;
import java.util.Map;

import kr.pe.util.Str;

/**
 * PC 2026. 4. 8. 오후 7:52:47
 */
public class Assert {

	/**
	 * Protect constructor since it is a static only class
	 */
	protected Assert() {
	}

	/**
	 * Collection is either null or has no elements
	 * 
	 * @param <T>
	 * @param coll
	 */
	public static <T> void isEmpty(Collection<T> coll) {
		isEmpty(coll, "Collection must be either null or have no elements");
	}

	/**
	 * @param <T>
	 * @param coll
	 * @param message
	 */
	public static <T> void isEmpty(Collection<T> coll, String message) {
		if (!Str.isEmpty(coll)) {
			fail(message);
		}
	}

	/**
	 * the map mustn't be either null or empty
	 * 
	 * @param <T>
	 * @param coll
	 */
	public static <T> void isNotEmpty(Collection<T> coll) {
		isNotEmpty(coll, "the map mustn't be either null or empty");
	}

	/**
	 * @param <T>
	 * @param coll
	 * @param message
	 */
	public static <T> void isNotEmpty(Collection<T> coll, String message) {
		if (Str.isEmpty(coll)) {
			fail(message);
		}
	}

	/**
	 * the map must be either null or empty
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 */
	public static <K, V> void isEmpty(Map<K, V> map) {
		isEmpty(map, "the map must be either null or empty");
	}

	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param message
	 */
	public static <K, V> void isEmpty(Map<K, V> map, String message) {
		if (!Str.isEmpty(map)) {
			fail(message);
		}
	}

	/**
	 * the map mustn't be neither null and empty
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 */
	public static <K, V> void isNotEmpty(Map<K, V> map) {
		isNotEmpty(map, "the map mustn't be neither null and empty");
	}

	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param message
	 */
	public static <K, V> void isNotEmpty(Map<K, V> map, String message) {
		if (Str.isEmpty(map)) {
			fail(message);
		}
	}

	/**
	 * outer must contain inner
	 * 
	 * @param outer
	 * @param inner
	 */
	public static void isContains(String outer, String inner) {
		isContains(outer, inner, outer + " must contain " + inner);
	}

	/**
	 * @param outer
	 * @param inner
	 * @param message
	 */
	public static void isContains(String outer, String inner, String message) {
		if (!outer.contains(inner)) {
			fail(message);
		}
	}

	/**
	 * outer mustn't contain inner
	 * 
	 * @param outer
	 * @param inner
	 */
	public static void isNotContains(String outer, String inner) {
		isNotContains(outer, inner, outer + " mustn't contain " + inner);
	}

	/**
	 * @param outer
	 * @param inner
	 * @param message
	 */
	public static void isNotContains(String outer, String inner, String message) {
		if (outer.contains(inner)) {
			fail(message);
		}
	}

	/**
	 * key must not be null and must not the empty
	 * 
	 * @param key
	 */
	public static void hasLength(String key) {
		hasLength(key, key + " must not be null and must not the empty");
	}

	/**
	 * @param key
	 * @param message
	 */
	public static void hasLength(String key, String message) {
		if (Str.isEmpty(key)) {
			fail(message);
		}
	}

	/**
	 * @param <T>
	 * @param child
	 * @param parent
	 * @throws AssertionFailedException
	 */
	public static <T> void isInstanceOf(Class<T> child, Class<T> parent) throws AssertionFailedException {
		isTrue(child.isInstance(parent), child.getName() + " is not instanceOf " + parent.getName());
	}

	/**
	 * @param <T>
	 * @param child
	 * @param parent
	 * @param message
	 * @throws AssertionFailedException
	 */
	public static <T> void isInstanceOf(Class<T> child, Class<T> parent, String message)
			throws AssertionFailedException {
		if (message == null) {
			isTrue(child.isInstance(parent));
		} else {
			isTrue(child.isInstance(parent), message);
		}
	}

	/**
	 * Asserts that a condition is true. If it isn't it throws an
	 * AssertionFailedError with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isTrue(boolean condition, String message) throws AssertionFailedException {
		if (!condition) {
			fail(message);
		}
	}

	/**
	 * Asserts that a condition is true. If it isn't it throws an
	 * AssertionFailedError.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isTrue(boolean condition) throws AssertionFailedException {
		isTrue(condition, null);
	}

	/**
	 * Asserts that a condition is false. If it isn't it throws an
	 * AssertionFailedError with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isFalse(boolean condition, String message) throws AssertionFailedException {
		isTrue(!condition, message);
	}

	/**
	 * Asserts that a condition is false. If it isn't it throws an
	 * AssertionFailedError.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isFalse(boolean condition) throws AssertionFailedException {
		isFalse(condition, null);
	}

	/**
	 * Fails a test with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void fail(String message) throws AssertionFailedException {
		if (message == null) {
			throw new AssertionFailedException();
		}
		throw new AssertionFailedException(message);
	}

	/**
	 * Fails a test with no message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void fail() throws AssertionFailedException {
		fail(null);
	}

	/**
	 * Asserts that two objects are equal. If they are not an AssertionFailedError
	 * is thrown with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(Object expected, Object actual, String message) throws AssertionFailedException {
		if (expected == null && actual == null) {
			return;
		}
		if (expected != null && expected.equals(actual)) {
			return;
		}
		failNotEquals(expected, actual, message);
	}

	/**
	 * Asserts that two objects are equal. If they are not an AssertionFailedError
	 * is thrown.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(Object expected, Object actual) throws AssertionFailedException {
		isEquals(expected, actual, null);
	}

	/**
	 * Asserts that two Strings are equal.
	 * 
	 * @throws ComparisonFailureException
	 */
	public static void isEquals(String expected, String actual, String message) throws ComparisonFailureException {
		if (expected == null && actual == null) {
			return;
		}
		if (expected != null && expected.equals(actual)) {
			return;
		}
		String cleanMessage = message == null ? "" : message;
		throw new ComparisonFailureException(expected, actual, cleanMessage);
	}

	/**
	 * Asserts that two Strings are equal.
	 * 
	 * @throws ComparisonFailureException
	 */
	public static void isEquals(String expected, String actual) throws ComparisonFailureException {
		isEquals(expected, actual, null);
	}

	/**
	 * Asserts that two doubles are equal concerning a delta. If they are not an
	 * AssertionFailedError is thrown with the given message. If the expected value
	 * is infinity then the delta value is ignored.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(double expected, double actual, double delta, String message)
			throws AssertionFailedException {
		if (Double.compare(expected, actual) == 0) {
			return;
		}
		if (!(Math.abs(expected - actual) <= delta)) {
			failNotEquals(Double.valueOf(expected), Double.valueOf(actual), message);
		}
	}

	/**
	 * Asserts that two doubles are equal concerning a delta. If the expected value
	 * is infinity then the delta value is ignored.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(double expected, double actual, double delta) throws AssertionFailedException {
		isEquals(expected, actual, delta, null);
	}

	/**
	 * Asserts that two floats are equal concerning a positive delta. If they are
	 * not an AssertionFailedError is thrown with the given message. If the expected
	 * value is infinity then the delta value is ignored.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(float expected, float actual, float delta, String message)
			throws AssertionFailedException {
		if (Float.compare(expected, actual) == 0) {
			return;
		}
		if (!(Math.abs(expected - actual) <= delta)) {
			failNotEquals(Float.valueOf(expected), Float.valueOf(actual), message);
		}
	}

	/**
	 * Asserts that two floats are equal concerning a delta. If the expected value
	 * is infinity then the delta value is ignored.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(float expected, float actual, float delta) throws AssertionFailedException {
		isEquals(expected, actual, delta, null);
	}

	/**
	 * Asserts that two longs are equal. If they are not an AssertionFailedError is
	 * thrown with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(long expected, long actual, String message) throws AssertionFailedException {
		isEquals(Long.valueOf(expected), Long.valueOf(actual), message);
	}

	/**
	 * Asserts that two longs are equal.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(long expected, long actual) throws AssertionFailedException {
		isEquals(expected, actual, null);
	}

	/**
	 * Asserts that two booleans are equal. If they are not an AssertionFailedError
	 * is thrown with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(boolean expected, boolean actual, String message) throws AssertionFailedException {
		isEquals(Boolean.valueOf(expected), Boolean.valueOf(actual), message);
	}

	/**
	 * Asserts that two booleans are equal.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(boolean expected, boolean actual) throws AssertionFailedException {
		isEquals(expected, actual, null);
	}

	/**
	 * Asserts that two bytes are equal. If they are not an AssertionFailedError is
	 * thrown with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(byte expected, byte actual, String message) throws AssertionFailedException {
		isEquals(Byte.valueOf(expected), Byte.valueOf(actual), message);
	}

	/**
	 * Asserts that two bytes are equal.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(byte expected, byte actual) throws AssertionFailedException {
		isEquals(expected, actual, null);
	}

	/**
	 * Asserts that two chars are equal. If they are not an AssertionFailedError is
	 * thrown with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(char expected, char actual, String message) throws AssertionFailedException {
		isEquals(Character.valueOf(expected), Character.valueOf(actual), message);
	}

	/**
	 * Asserts that two chars are equal.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(char expected, char actual) throws AssertionFailedException {
		isEquals(expected, actual, null);
	}

	/**
	 * Asserts that two shorts are equal. If they are not an AssertionFailedError is
	 * thrown with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(short expected, short actual, String message) throws AssertionFailedException {
		isEquals(Short.valueOf(expected), Short.valueOf(actual), message);
	}

	/**
	 * Asserts that two shorts are equal.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(short expected, short actual) throws AssertionFailedException {
		isEquals(expected, actual, null);
	}

	/**
	 * Asserts that two ints are equal. If they are not an AssertionFailedError is
	 * thrown with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(int expected, int actual, String message) throws AssertionFailedException {
		isEquals(Integer.valueOf(expected), Integer.valueOf(actual), message);
	}

	/**
	 * Asserts that two ints are equal.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isEquals(int expected, int actual) throws AssertionFailedException {
		isEquals(expected, actual, null);
	}

	/**
	 * Asserts that an object isn't null.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isNotNull(Object object) throws AssertionFailedException {
		isNotNull(object, null);
	}

	/**
	 * Asserts that an object isn't null. If it is an AssertionFailedError is thrown
	 * with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isNotNull(Object object, String message) throws AssertionFailedException {
		isTrue(object != null, message);
	}

	/**
	 * Asserts that an object is null. If it isn't an {@link AssertionError} is
	 * thrown. Message contains: Expected: <null> but was: object
	 *
	 * @param object Object to check or <code>null</code>
	 * @throws AssertionFailedException
	 */
	public static void isNull(Object object) throws AssertionFailedException {
		if (object != null) {
			isNull(object, "Expected: <null> but was: " + object.toString());
		}
	}

	/**
	 * Asserts that an object is null. If it is not an AssertionFailedError is
	 * thrown with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isNull(Object object, String message) throws AssertionFailedException {
		isTrue(object == null, message);
	}

	/**
	 * Asserts that two objects refer to the same object. If they are not an
	 * AssertionFailedError is thrown with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isSame(Object expected, Object actual, String message) throws AssertionFailedException {
		if (expected == actual) {
			return;
		}
		failNotSame(expected, actual, message);
	}

	/**
	 * Asserts that two objects refer to the same object. If they are not the same
	 * an AssertionFailedError is thrown.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isSame(Object expected, Object actual) throws AssertionFailedException {
		isSame(expected, actual, null);
	}

	/**
	 * Asserts that two objects do not refer to the same object. If they do refer to
	 * the same object an AssertionFailedError is thrown with the given message.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isNotSame(Object expected, Object actual, String message) throws AssertionFailedException {
		if (expected == actual) {
			failSame(message);
		}
	}

	/**
	 * Asserts that two objects do not refer to the same object. If they do refer to
	 * the same object an AssertionFailedError is thrown.
	 * 
	 * @throws AssertionFailedException
	 */
	public static void isNotSame(Object expected, Object actual) throws AssertionFailedException {
		isNotSame(expected, actual, null);
	}

	public static void failSame(String message) throws AssertionFailedException {
		String formatted = (message != null) ? message + " " : "";
		fail(formatted + "expected not same");
	}

	public static void failNotSame(Object expected, Object actual, String message) throws AssertionFailedException {
		String formatted = (message != null) ? message + " " : "";
		fail(formatted + "expected same:<" + expected + "> was not:<" + actual + ">");
	}

	public static void failNotEquals(Object expected, Object actual, String message) throws AssertionFailedException {
		fail(format(expected, actual, message));
	}

	public static String format(Object expected, Object actual, String message) {
		String formatted = "";
		if (message != null && message.length() > 0) {
			formatted = message + " ";
		}
		return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
	}
}
