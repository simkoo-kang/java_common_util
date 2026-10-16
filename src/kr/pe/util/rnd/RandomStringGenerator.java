/**
 * RandomStringGenerator.java
 */
package kr.pe.util.rnd;

import java.util.Random;

/**
 * PC 2026. 4. 9. 오후 11:35:00
 */
public class RandomStringGenerator {

	private static final String ALPHA_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHA_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String ALPHA = ALPHA_UPPER + ALPHA_LOWER;
	private static final String NUMBER = "0123456789";
	private static final String SPECIAL = "!@#$%^&*()_";

	private static final String STRING = NUMBER + ALPHA;
//	private static final String SPECIAL_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzàèéìòù,;.:-_ç@°#([{}])+*/|!£$%&/()=?^~¡¢€£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
	private static final String SPECIAL_STRING = STRING + SPECIAL;
//	private static Random random = Rnd.getRandomLocalThread();
	private static Random random = Rnd.getRandom();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int length = 11, upperChars = 1, numberChars = 3, specialChars = 1;
		String randomString = getRandomPassword(length, upperChars, numberChars, specialChars);
		System.out.println(randomString + " " + randomString.length());

		upperChars = 0;
		randomString = getRandomPassword(length, upperChars, numberChars, specialChars);
		System.out.println(randomString + " " + randomString.length());

		numberChars = 4;
		specialChars = 0;
		randomString = getRandomPassword(length, upperChars, numberChars, specialChars);
		System.out.println(randomString + " " + randomString.length());
	}

	/**
	 * Generate a string with random chars, numbers and, eventually, special chars
	 * (e.g. punctuation chars). This random generator is based on {@link Random}.
	 * 
	 * <p>
	 * The invocation of this method is equivalent to
	 * {@code getRandomString(length, false)}.
	 * </p>
	 * 
	 * @param length specify the length of the result string
	 * @return a {@link String} with random alphanumeric content
	 * 
	 * @see #getRandomString(int, boolean)
	 */
	public static String getRandomString(int length) {
		return getRandomString(length, false);
	}

	/**
	 * Generate a string with random chars, numbers and, eventually, special chars
	 * (e.g. punctuation chars). This random generator is based on {@link Random}.
	 * 
	 * @param length       specify the length of the result string
	 * @param specialChars if true returns a string containing special chars, false
	 *                     otherwise
	 * @return a {@link String} with random alphanumeric content
	 */
	public static String getRandomString(int length, boolean specialChars) {
		StringBuilder sb = new StringBuilder(length);
		String string = (specialChars) ? SPECIAL_STRING : STRING;
		int size = string.length();

		for (int i = 0; i < length; i++) {
			sb.append(string.charAt(random.nextInt(size)));
		}
		return sb.toString();
	}

	/**
	 * Generate a string with random chars, numbers and, eventually, special chars
	 * (e.g. punctuation chars). It is possible to avoid some chars in the result
	 * string. This random generator is based on {@link Random}.
	 * 
	 * @param length       specify the length of the result string
	 * @param specialChars if true returns a string containing special chars, false
	 *                     otherwise
	 * @param avoidChars   string of chars that will not be used in the result
	 *                     string
	 * @return a {@link String} with random alphanumeric content
	 */
	public static String getRandomString(int length, boolean specialChars, String avoidChars) {
		StringBuilder sb = new StringBuilder(length);
		String string = (specialChars) ? SPECIAL_STRING : STRING;
		int size = string.length();

		for (int i = 0; i < length; i++) {
			char ch = string.charAt(random.nextInt(size));
			if (avoidChars.contains(String.valueOf(ch))) {
				i--;
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * @param length
	 * @param numberChars
	 * @param specialChars
	 * @return
	 */
	public static String getRandomPassword(int length, int numberChars, int specialChars) {
		StringBuilder sb = new StringBuilder(length);
		int stringChars = length - numberChars - specialChars;

		int stringSize = ALPHA.length();
		int size = stringChars;
		int len = 0;

		while (len++ < size) {
			sb.append(ALPHA.charAt(random.nextInt(stringSize)));
		}
		if (0 < specialChars) {
			int specialSize = SPECIAL.length();
			size += specialChars + 1;
			while (len++ < size) {
				sb.append(SPECIAL.charAt(random.nextInt(specialSize)));
			}
		}
		if (0 < numberChars) {
			int numberSize = NUMBER.length();
			size += numberChars + 1;
			while (len++ < size) {
				sb.append(NUMBER.charAt(random.nextInt(numberSize)));
			}
		}
		return sb.toString();
	}

	/**
	 * @param length
	 * @param numberChars
	 * @param specialChars
	 * @param avoidChars
	 * @return
	 */
	public static String getRandomPassword(int length, int numberChars, int specialChars, String avoidChars) {
		if (avoidChars == null || "".equals(avoidChars)) {
			return getRandomPassword(length, numberChars, specialChars);
		}

		StringBuilder sb = new StringBuilder(length);
		int stringChars = length - numberChars - specialChars;

		int stringSize = ALPHA.length();
		int size = stringChars;
		int len = 0;

		while (len++ < size) {
			char ch = ALPHA.charAt(random.nextInt(stringSize));
			if (avoidChars.contains(String.valueOf(ch))) {
				len--;
			} else {
				sb.append(ch);
			}
		}
		if (0 < specialChars) {
			int specialSize = SPECIAL.length();
			size += specialChars + 1;
			while (len++ < size) {
				char ch = SPECIAL.charAt(random.nextInt(specialSize));
				if (avoidChars.contains(String.valueOf(ch))) {
					len--;
				} else {
					sb.append(ch);
				}
			}
		}
		if (0 < numberChars) {
			int numberSize = NUMBER.length();
			size += numberChars + 1;
			while (len++ < size) {
				char ch = NUMBER.charAt(random.nextInt(numberSize));
				if (avoidChars.contains(String.valueOf(ch))) {
					len--;
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @param length
	 * @param upperChars
	 * @param numberChars
	 * @param specialChars
	 * @return
	 */
	public static String getRandomPassword(int length, int upperChars, int numberChars, int specialChars) {
		if (upperChars < 1) {
			return getRandomPassword(length, numberChars, specialChars);
		}

		StringBuilder sb = new StringBuilder(length);
		int ln = 0, size = 0;
		size = ALPHA_UPPER.length();
		for (ln = 0; ln < upperChars; ln++) {
			sb.append(ALPHA_UPPER.charAt(random.nextInt(size)));
		}

		size = ALPHA_LOWER.length();
		int lowerChars = length - upperChars - numberChars - specialChars;
		for (ln = 0; ln < lowerChars; ln++) {
			sb.append(ALPHA_LOWER.charAt(random.nextInt(size)));
		}

		if (0 < specialChars) {
			size = SPECIAL.length();
			for (ln = 0; ln < specialChars; ln++) {
				sb.append(SPECIAL.charAt(random.nextInt(size)));
			}
		}

		if (0 < numberChars) {
			size = NUMBER.length();
			for (ln = 0; ln < numberChars; ln++) {
				sb.append(NUMBER.charAt(random.nextInt(size)));
			}
		}
		return sb.toString();
	}
}
