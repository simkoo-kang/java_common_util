/**
 * TestRandomStringGenerator.java
 */
package kr.pe.util.rnd.picker;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.util.err.Assert;

/**
 * PC 2026. 4. 8. 오후 7:48:27
 */
public class TestRandomStringGenerator {

	private static Logger log = LoggerFactory.getLogger(TestRandomStringGenerator.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean istest = false;
		if (istest) {
			test();
		}

//		RandomStringGenerator generator = new RandomStringGenerator(ThreadLocalRandom.current());
//		String randomString = generator.generateByRegex("\\w+\\d*\\W\\D{0,3}a\\{0,3}.\\s\\S[0-9][a-zA-Z]X");
		RandomStringsGenerator generator = new RandomStringsGenerator(10);
		String randomString = generator.generateByRegex("a{10,}");
//		String randomString = generator.generateFromPattern(randomString);
		log.debug("main randomstring  = ", randomString);

		shouldGenerateRandomStringFromPattern();
	}

	/**
	* 
	*/
	private static void test() {
		shouldGenerateRandomStringFromPattern();

		log.debug("");
		shouldOccurExceptionWhenItIsWithInvalidPatternChar();

		log.debug("");
		shouldGenerateRandomStringFromRegex();

		log.debug("");
		shouldIgnoreInvalidRange();

		log.debug("");
		notSpecifiedDestinationAtBrace();

		log.debug("");
		try {
			invalidNotSpecifiedDestionationAtBrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("");
		try {
			shouldOccurExceptionWhenExistsInvalidRange();
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("");
		try {
			shouldOccurExceptionWhenExistsInvalidEscapeCharacter();
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("");
		try {
			shouldOccurExceptionWhenUsingInvalidRangeChar();
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("");
		try {
			shouldOccurExceptionWhenUsingInvalidRangeOrder();
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("");
		try {
			shouldOccurExceptionWhenRangeNotationIsNotClosed();
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("");
		try {
			shouldSetAndGetNumOfUpperLimitSuccessfully();
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("");
		try {
			shouldSetNumOfUpperLimitSuccessfullyByConstructor();
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("");
		try {
			forThreadLocalRandom();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static void forThreadLocalRandom() {
		RandomStringsGenerator generator = new RandomStringsGenerator(ThreadLocalRandom.current());
		String randomString = generator.generateByRegex("\\w+\\d*\\W\\D{0,3}a\\{0,3}.\\s\\S[0-9][a-zA-Z]X");
		log.debug("forThreadLocalRandom randomstring  = ", randomString);
		Pattern patternToProve = Pattern.compile("^[a-zA-Z0-9_]+[0-9]*[~`!@$%^&*()\\-+={}\\[\\]|\\\\:;\"'.<>?/#,]"
				+ "[a-zA-Z0-9~`!@$%^&*()\\-_+={}\\[\\]|\\\\:;\"'.<>?/#,]{0,3}" + "a\\{0,3}.[ \t].[0-9][a-zA-Z]X$");
		Assert.isTrue(patternToProve.matcher(randomString).find());
	}

	/**
	 * 
	 */
	private static void shouldSetNumOfUpperLimitSuccessfullyByConstructor() {
		RandomStringsGenerator generator = new RandomStringsGenerator(0);
		Assert.isEquals(0, generator.getNumOfUpperLimit());
		String genstr = generator.generateByRegex("X.*X");
		Assert.isEquals("XX", genstr);
		log.debug("shouldSetAndGetNumOfUpperLimitSuccessfully generater string  = ", genstr);
	}

	/**
	 * 
	 */
	private static void shouldSetAndGetNumOfUpperLimitSuccessfully() {
		RandomStringsGenerator generator = new RandomStringsGenerator();
		generator.setNumOfUpperLimit(0);
		Assert.isEquals(0, generator.getNumOfUpperLimit());
		String genstr = generator.generateByRegex("X.*X");
		Assert.isEquals("XX", genstr);
		log.debug("shouldSetAndGetNumOfUpperLimitSuccessfully generater string  = ", genstr);
	}

	/**
	 * 
	 */
	private static void shouldOccurExceptionWhenRangeNotationIsNotClosed() {
		String genstr = new RandomStringsGenerator().generateByRegex("[a-");
		log.debug("shouldOccurExceptionWhenExistsInvalidRange generater string  = ", genstr);
	}

	/**
	 * 
	 */
	private static void shouldOccurExceptionWhenUsingInvalidRangeOrder() {
		String genstr = new RandomStringsGenerator().generateByRegex("[z-a]");
		log.debug("shouldOccurExceptionWhenExistsInvalidRange generater string  = ", genstr);
	}

	/**
	 * 
	 */
	private static void shouldOccurExceptionWhenUsingInvalidRangeChar() {
		String genstr = new RandomStringsGenerator().generateByRegex("[$-%]");
		log.debug("shouldOccurExceptionWhenExistsInvalidRange generater string  = ", genstr);
	}

	/**
	 * 
	 */
	private static void shouldOccurExceptionWhenExistsInvalidEscapeCharacter() {
		String genstr = new RandomStringsGenerator().generateByRegex("foo\\");
		log.debug("shouldOccurExceptionWhenExistsInvalidRange generater string  = ", genstr);
	}

	/**
	 * 
	 */
	private static void shouldOccurExceptionWhenExistsInvalidRange() {
		String genstr = new RandomStringsGenerator().generateByRegex("a{5,0}");
		log.debug("shouldOccurExceptionWhenExistsInvalidRange generater string  = ", genstr);
	}

	/**
	 * 
	 */
	private static void invalidNotSpecifiedDestionationAtBrace() {
		RandomStringsGenerator generator = new RandomStringsGenerator(10);
		String genstr = generator.generateByRegex("a{100,}");
		log.debug("invalidNotSpecifiedDestionationAtBrace generater string  = ", genstr);
	}

	/**
	 * 
	 */
	private static void notSpecifiedDestinationAtBrace() {
		String genstr;
		{
			RandomStringsGenerator generator = new RandomStringsGenerator(10);
			genstr = generator.generateByRegex("a{10,}");
			log.debug("notSpecifiedDestinationAtBrace generater string  = ", genstr);
			Assert.isEquals("aaaaaaaaaa", genstr);
		}
		{
			RandomStringsGenerator generator = new RandomStringsGenerator(10);
			genstr = generator.generateByRegex("a{0,}");
			log.debug("notSpecifiedDestinationAtBrace generater string  = ", genstr);
			Assert.isTrue(genstr.matches("a{0,10}"));
		}
	}

	/**
	 * 
	 */
	private static void shouldIgnoreInvalidRange() {
		RandomStringsGenerator generator = new RandomStringsGenerator();
		{
			String randomString = generator.generateByRegex("a{-1,10}b{foo}");
			log.debug("shouldIgnoreInvalidRange randomstring  = ", randomString);
			Assert.isTrue(randomString.equals("a{-1,10}b{foo}"));
		}
		{
			String randomString = generator.generateByRegex("a{");
			log.debug("shouldIgnoreInvalidRange randomstring  = ", randomString);
			Assert.isTrue(randomString.equals("a{"));
		}
		{
			String randomString = generator.generateByRegex("a{,1}");
			log.debug("shouldIgnoreInvalidRange randomstring  = ", randomString);
			Assert.isTrue(randomString.equals("a{,1}"));
		}
	}

	/**
	 * 
	 */
	private static void shouldGenerateRandomStringFromRegex() {
		RandomStringsGenerator generator = new RandomStringsGenerator();
		{
			String randomString = generator.generateByRegex("\\w+\\d*\\W\\D{0,3}a\\{0,3}.\\s\\S[0-9][a-zA-Z]X");
			log.debug("shouldGenerateRandomStringFromRegex randomstring  = ", randomString);
			Pattern patternToProve = Pattern.compile("^[a-zA-Z0-9_]+[0-9]*[~`!@$%^&*()\\-+={}\\[\\]|\\\\:;\"'.<>?/#,]"
					+ "[a-zA-Z0-9~`!@$%^&*()\\-_+={}\\[\\]|\\\\:;\"'.<>?/#,]{0,3}" + "a\\{0,3}.[ \t].[0-9][a-zA-Z]X$");
			Assert.isTrue(patternToProve.matcher(randomString).find());
		}
		{
			// regression test
			String randomString = generator.generateByRegex("\\w+\\d*\\W\\D{0,3}a\\{0,3}.\\s\\S[0-9][a-zA-Z]X");
			log.debug("shouldGenerateRandomStringFromRegex randomstring  = ", randomString);

			Pattern patternToProve = Pattern.compile("^[a-zA-Z0-9_]+[0-9]*[~`!@$%^&*()\\-+={}\\[\\]|\\\\:;\"'.<>?/#,]"
					+ "[a-zA-Z0-9~`!@$%^&*()\\-_+={}\\[\\]|\\\\:;\"'.<>?/#,]{0,3}" + "a\\{0,3}.[ \t].[0-9][a-zA-Z]X$");
			Assert.isTrue(patternToProve.matcher(randomString).find());
		}
	}

	/**
	 * 
	 */
	private static void shouldOccurExceptionWhenItIsWithInvalidPatternChar() {
		new RandomStringsGenerator().generateFromPattern("cCn?.sb");
	}

	/**
	 * 
	 */
	private static void shouldGenerateRandomStringFromPattern() {
		RandomStringsGenerator generator = new RandomStringsGenerator();
		String randomString = generator.generateFromPattern("cCn!.sb");
		log.debug(randomString);
		Pattern patternToProve = Pattern.compile("^[a-z][A-Z][0-9][~`!@$%^&*()\\-_+={}\\[\\]|\\\\:;\"'.<>?/#,]"
				+ "[a-zA-Z0-9~`!@$%^&*()\\-_+={}\\[\\]|\\\\:;\"'.<>?/#,]" + "[A-Za-z0-9./].$");
		Matcher matcher = patternToProve.matcher(randomString);

		Assert.isTrue(matcher.find());

		for (int i = 0; i <= matcher.groupCount(); i++) {
			log.debug("{} {}", i, matcher.group(i));
		}

		log.debug("OK!");
	}
}
