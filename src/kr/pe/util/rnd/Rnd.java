/**
 * Rnd.java
 */
package kr.pe.util.rnd;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import kr.pe.util.Arr;
import kr.pe.util.rnd.picker.RandomStringsGenerator;

/**
 * PC 2026. 4. 8. 오후 1:32:55
 */
public class Rnd {

	private static Random random = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int length = 11, upperChars = 1, numberChars = 3, specialChars = 1;
		int count = 1000000;
		RandomStringsGenerator.StringGeneratorInfo info = new RandomStringsGenerator.StringGeneratorInfo();

		info.length = length;
		info.upperChars = upperChars;
		info.numberChars = numberChars;
		info.specialChars = specialChars;

		StringBuilder sb = new StringBuilder();

		String randomString = "";
		long start = System.currentTimeMillis(), end;

		for (int i = 0; i < count; i++) {
			randomString = Rnd.getRandomPassword(length, upperChars, numberChars, specialChars);
		}
		end = System.currentTimeMillis();

		// Cctsgpv#029 11 311 millis
		sb.append(randomString + " " + randomString.length() + " " + (end - start)).append("\n");

		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			randomString = RandomStringsGenerator.getRandomPasswordString(info);
		}
		end = System.currentTimeMillis();

		// Pfsyyrl$025 11 22,223 millis
		sb.append(randomString + " " + randomString.length() + " " + (end - start)).append("\n");

		System.out.println(sb.toString());
	}

	/**
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		return RandomStringGenerator.getRandomString(length);
	}

	/**
	 * @param length
	 * @param specialChars
	 * @return
	 */
	public static String getRandomString(int length, boolean specialChars) {
		return RandomStringGenerator.getRandomString(length, specialChars);
	}

	/**
	 * @param length
	 * @param specialChars
	 * @param avoidChars
	 * @return
	 */
	public static String getRandomString(int length, boolean specialChars, String avoidChars) {
		return RandomStringGenerator.getRandomString(length, specialChars, avoidChars);
	}

	/**
	 * @param length
	 * @param numberChars
	 * @param specialChars
	 * @return
	 */
	public static String getRandomPassword(int length, int numberChars, int specialChars) {
		return RandomStringGenerator.getRandomPassword(length, numberChars, specialChars);
	}

	/**
	 * @param length
	 * @param numberChars
	 * @param specialChars
	 * @param avoidChars
	 * @return
	 */
	public static String getRandomPassword(int length, int numberChars, int specialChars, String avoidChars) {
		return RandomStringGenerator.getRandomPassword(length, numberChars, specialChars, avoidChars);
	}

	/**
	 * @param length
	 * @param upperChars
	 * @param numberChars
	 * @param specialChars
	 * @return
	 */
	public static String getRandomPassword(int length, int upperChars, int numberChars, int specialChars) {
		return RandomStringGenerator.getRandomPassword(length, upperChars, numberChars, specialChars);
	}

	/**
	 * <pre>
	 * bound > -1
	 * 
	 * PC 2026. 8. 26. 오후 8:48:06
	 * </pre>
	 *
	 * @param bound
	 * @return
	 */
	public static String getRandomNmAsString(final int bound) {
		if (bound < 0) {
			throw new RuntimeException("Detected invalid quantifier: " + "{" + bound + "}");
		}
		if (random == null) {
			random = getRandom();
		}
		return Integer.toString(random.nextInt(bound + 1), 10);
	}

	/**
	 * <pre>
	 * end > start > -1
	 * 
	 * PC 2026. 8. 26. 오후 8:48:06
	 * </pre>
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getRandomNmAsString(final int start, final int end) {
		int bound = end - start;
		if (bound < 0) {
			throw new RuntimeException("Detected invalid quantifier: " + "{" + start + "," + end + "}");
		}
		if (random == null) {
			random = getRandom();
		}
		return Integer.toString(random.nextInt(bound + 1) + start, 10);
	}

	/**
	 * <pre>
	 * 
	 * 2. 암호학적 의사 난수 발생기 (java.security.SecureRandom)
	 *   표준 Random 은 시드 값과 출력된 결과 몇 개만 알면 다음 숫자를 수학적으로 예측할 수 있는 취약점이 있습니다.
	 *   보안이나 복권 번호 추첨처럼 예측이 불가능해야 하는 경우에는 SecureRandom을 사용해야 합니다.
	 * 
	 * random default seed is current time
	 * secure random
	 * 
	 * PC 2026. 8. 26. 오후 7:51:32
	 * </pre>
	 *
	 * @return
	 */
	public static Random getRandom() {
		if (random != null) {
			return random;
		}
		return getRandom(new Date().getTime());
	}

	/**
	 * @param millis
	 * @return
	 */
	public static Random getRandom(long millis) {
		if (random != null) {
			return random;
		}
		return new SecureRandom(Arr.longToByteArrayWithBitShift(millis));
	}

	/**
	 * @return
	 */
	public static Random getRandomLocalThread() {
		if (random != null) {
			return random;
		}
		return random = ThreadLocalRandom.current();
	}

	/**
	 * count 만큼 nextLong(bound)한다.
	 * 
	 * @param rand
	 * @param bound
	 * @param count
	 */
	public static void sleep(Random rand, int bound, long count) {
		for (long i = 0; i < count; i++) {
			rand.nextLong(bound);
		}
	}
}
