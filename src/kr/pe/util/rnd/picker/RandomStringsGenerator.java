/**
 * RandomStringGenerator.java
 */
package kr.pe.util.rnd.picker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.util.rnd.Rnd;

/**
 * PC 2026. 4. 8. 오후 7:26:06
 */
public class RandomStringsGenerator {
	private static Logger log = LoggerFactory.getLogger(RandomStringsGenerator.class);

	private static final int DEFAULT_NUM_OF_UPPER_LIMIT = 10;

	private int numOfUpperLimit;
	private final Map<String, RandomLetterPicker> userDefinedPickers;
	private final Random random;
	private final RandomLetterPickers pickers;

	public static class StringGeneratorInfo {
		public int length;
		public int upperChars;
		public int numberChars;
		public int specialChars;

		/**
		 * @return this.length - this.upperChars - this.numberChars - this.specialChars
		 */
		public int getLowerChars() {
			return this.length - this.upperChars - this.numberChars - this.specialChars;
		}
	}

	/**
	 * Instantiate generator with a default number of upper limit for regex
	 * quantifiers (for example {@code *}, {@code +} and etc; default value is 10)
	 * and a default instance of Random.
	 */
	public RandomStringsGenerator() {
		this(Rnd.getRandom(), DEFAULT_NUM_OF_UPPER_LIMIT);
	}

	/**
	 * Instantiate generator with a number of upper limit for regex quantifiers (for
	 * example {@code *} , {@code +} and etc) and a default instance of Random.
	 * 
	 * @param numOfUpperLimit Number of upper limit for quantifiers
	 */
	public RandomStringsGenerator(int numOfUpperLimit) {
		this(Rnd.getRandom(), numOfUpperLimit);
	}

	/**
	 * Instantiate generator with a default number of upper limit for regex
	 * quantifiers (for example {@code *}, {@code +} and etc; default value is 10)
	 * and an instance of Random.
	 *
	 * @param random Instance of Random
	 */
	public RandomStringsGenerator(Random random) {
		this(random, DEFAULT_NUM_OF_UPPER_LIMIT);
	}

	/**
	 * Instantiate generator with a number of upper limit for regex quantifiers (for
	 * example {@code *} , {@code +} and etc) and an instance of Random.
	 *
	 * @param random          Instance of Random
	 * @param numOfUpperLimit Number of upper limit for quantifiers
	 */
	public RandomStringsGenerator(Random random, int numOfUpperLimit) {
		this.numOfUpperLimit = numOfUpperLimit;
		this.random = random;
		this.userDefinedPickers = new HashMap<>();
		this.pickers = new RandomLetterPickers(this.random);
	}

	public static void main(String[] args) {
		StringGeneratorInfo info = new StringGeneratorInfo();
		info.length = 11;
		info.upperChars = 1;
		info.numberChars = 3;
		info.specialChars = 1;

		String pwd = RandomStringsGenerator.getRandomPasswordString(info);
		log.debug("{} {}", pwd.length(), pwd);
	}

	/**
	 * @param info
	 * @return
	 */
	public static String getRandomPasswordString(StringGeneratorInfo info) {
		StringBuilder sb = new StringBuilder();

		RandomLetterPickers pickers = new RandomLetterPickers(Rnd.getRandom());
		RandomLetterPicker picker;
		int lowerChars = info.getLowerChars();
		int chars;
		if (0 < info.upperChars) {
			picker = pickers.getUpperCase();
			for (chars = 0; chars < info.upperChars; chars++) {
				sb.append(picker.pickRandomLetter());
			}
		}
		picker = pickers.getLowerCase();
		for (chars = 0; chars < lowerChars; chars++) {
			sb.append(picker.pickRandomLetter());
		}
		if (0 < info.specialChars) {
			picker = pickers.getSpecial();
			for (chars = 0; chars < info.specialChars; chars++) {
				sb.append(picker.pickRandomLetter());
			}
		}
		if (0 < info.numberChars) {
			picker = pickers.getDigit();
			for (chars = 0; chars < info.numberChars; chars++) {
				sb.append(picker.pickRandomLetter());
			}
		}
		return sb.toString();
	}

	/**
	 * Generate random string from pattern.
	 * 
	 * <p>
	 * You can use following characters as pattern.
	 * <ul>
	 * <li>{@code c} : Any Latin lower-case character</li>
	 * <li>{@code C} : Any Latin upper-case character</li>
	 * <li>{@code n} : Any digit {@code [0-9]}</li>
	 * <li>{@code !} : A symbol character
	 * {@code [~`!@$%^&*()-_+= []|\:;"'.<>?/#,]}</li>
	 * <li>{@code .} : Any of the above</li>
	 * <li>{@code s} : A "salt" character {@code [A-Za-z0-9./]}</li>
	 * <li>{@code b} : An ASCIII character which has code from 0 to 255</li>
	 * </ul>
	 * 
	 * <p>
	 * e.g.
	 * 
	 * <pre>
	 * <code>
	 * RandomStringGenerator generator = new RandomStringGenerator();
	 * 
	 * // generates random string (e.g. "aB4@X.Ç")
	 * String randomString = generator.generateFromPattern(&quot;cCn!.sb&quot;);
	 * </code>
	 * </pre>
	 * 
	 * @param pattern Pattern string
	 * @return Random string which is generated according to pattern
	 */
	public String generateFromPattern(final String pattern) {
		String genstr = Arrays.stream(pattern.split("")).map(patternCharacter -> {
			RandomLetterPicker picker;
			switch (patternCharacter) {
			case "c":
				picker = this.pickers.getLowerCase();
				break;
			case "C":
				picker = this.pickers.getUpperCase();
				break;
			case "n":
				picker = this.pickers.getDigit();
				break;
			case "!":
			case "?":
				picker = this.pickers.getSymbol();
				break;
			case ".":
				picker = this.pickers.getAny();
				break;
			case "s":
				picker = this.pickers.getSalt();
				break;
			case "b":
				picker = this.pickers.getBinary();
				break;
			default:
				throw new RuntimeException("Detected invalid pattern character: " + patternCharacter);
			}
			return picker.pickRandomLetter();
		}).collect(Collectors.joining());

		String clsname = Thread.currentThread().getStackTrace()[1].getClassName();
		String mthname = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.debug("{} {} {}", clsname, mthname, genstr);
		return genstr;
	}

	/**
	 * Generate random string from regular expression.
	 * 
	 * <p>
	 * You can use following meta characters as regex.
	 * <ul>
	 * <li>{@code \w} : Alphanumeric + "_" {@code [A-Za-z0-9_]}</li>
	 * <li>{@code \d} : Digits {@code [0-9]}</li>
	 * <li>{@code \W} : Printable characters other than those in \w</li>
	 * <li>{@code \D} : Printable characters other than those in \d</li>
	 * <li>{@code \s} : Whitespace characters {@code [ \t]}</li>
	 * <li>{@code \S} : Printable characters</li>
	 * <li>{@code .} : Printable characters</li>
	 * <li>{@code []} : Character classes (Example of usage {@code [a-zA-Z]})</li>
	 * <li><code>{}</code>: Repetition</li>
	 * <li>{@code *} : Same as {0,}</li>
	 * <li>{@code ?} : Same as {0,1}</li>
	 * <li>{@code +} : Same as {1,}</li>
	 * </ul>
	 * 
	 * <p>
	 * e.g.
	 * 
	 * <pre>
	 * <code>
	 * RandomStringGenerator generator = new RandomStringGenerator();
	 * 
	 * // generates random string (e.g. "a5B123 18X")
	 * String randomString = generator.generateByRegex("\\w+\\d*\\s[0-9]{0,3}X");
	 * </code>
	 * </pre>
	 * 
	 * @param regex Pattern based on regular expression
	 * @return Random String
	 */
	public String generateByRegex(final String regex) {
		String expanded = new RegexNormalizer(this.numOfUpperLimit, this.random).normalizeQuantifiers(regex);

		final String[] regexCharacters = expanded.split("");
		final int length = regexCharacters.length;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			String character = regexCharacters[i];
			RandomLetterPicker picker = null;
			String candidateCharacter = null;
			switch (character) {
			case "\\":
				try {
					character = regexCharacters[++i];
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new RuntimeException("Detected invalid escape character");
				}

				switch (character) {
				case "w":
					picker = this.pickers.getWord();
					break;
				case "d":
					picker = this.pickers.getDigit();
					break;
				case "W":
					picker = this.pickers.getNotWord();
					break;
				case "D":
					picker = this.pickers.getNotDigit();
					break;
				case "s":
					picker = this.pickers.getSpace();
					break;
				case "S":
					picker = this.pickers.getAny();
					break;
				default:
					candidateCharacter = character;
				}
				break;
			case "[":
				try {
					UserDefinedLetterPickerScanner.ScannedUserDefinedPicker scannedUserDefinedPicker = UserDefinedLetterPickerScanner
							.scan(regexCharacters, i);
					String key = scannedUserDefinedPicker.getKey();
					i = scannedUserDefinedPicker.getCursor();

					if (this.userDefinedPickers.get(key) == null) {
						RandomLetterPicker userDefinedPicker = RandomLetterPicker
								.constructByCharacterRange(scannedUserDefinedPicker.getBounds());
						this.userDefinedPickers.put(key, userDefinedPicker);
					}

					picker = this.userDefinedPickers.get(key);
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new RuntimeException("Occurs parsing error");
				}
				break;
			case ".":
				picker = this.pickers.getAny();
				break;
			default:
				candidateCharacter = character;
			}

			int repetitionNum = 1;
			if (i + 1 < length) {
				String nextCharacter = regexCharacters[i + 1];
				if (nextCharacter.equals("{")) {
					int j = i + 1;
					StringBuilder sbForQuantifier = new StringBuilder();
					try {
						while (!(nextCharacter = regexCharacters[++j]).equals("}")) {
							sbForQuantifier.append(nextCharacter);
						}
						try {
							repetitionNum = Integer.parseInt(sbForQuantifier.toString(), 10);
							i = j;
						} catch (RuntimeException e) {
							// do nothing
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						// do nothing
					}
				}
			}

			if (picker != null) {
				for (int j = 0; j < repetitionNum; j++) {
					sb.append(picker.pickRandomLetter());
				}
			} else if (candidateCharacter != null) {
				for (int j = 0; j < repetitionNum; j++) {
					sb.append(candidateCharacter);
				}
			} else {
				throw new RuntimeException("Occurs parsing error");
			}
		}

		String genstr = sb.toString();
		String clsname = Thread.currentThread().getStackTrace()[1].getClassName();
		String mthname = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.debug("{} {} {}", clsname, mthname, genstr);
		return genstr;
	}

	/**
	 * Get number of upper limit for regex quantifiers, for example {@code *},
	 * {@code +} and etc.
	 * 
	 * @return Number of upper limit for quantifiers
	 */
	public int getNumOfUpperLimit() {
		return this.numOfUpperLimit;
	}

	/**
	 * Set number of upper limit for regex quantifiers, for example {@code *},
	 * {@code +} and etc.
	 * 
	 * @param numOfUpperLimit Number of upper limit for quantifiers
	 */
	public void setNumOfUpperLimit(int numOfUpperLimit) {
		this.numOfUpperLimit = numOfUpperLimit;
	}
}
