/**
 * RandomLetterPickers.java
 */
package kr.pe.util.rnd.picker;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import kr.pe.util.rnd.picker.letter.DigitalLetter;
import kr.pe.util.rnd.picker.letter.LowerCaseLetter;
import kr.pe.util.rnd.picker.letter.SpecialLetter;
import kr.pe.util.rnd.picker.letter.SymbolLetter;
import kr.pe.util.rnd.picker.letter.UpperCaseLetter;

/**
 * PC 2026. 4. 8. 오후 4:11:44
 */
public class RandomLetterPickers {

	private final RandomLetterPicker upperCase;
	private final RandomLetterPicker lowerCase;
	private final RandomLetterPicker digit;
	private final RandomLetterPicker special;
	private final RandomLetterPicker symbol;
	private final RandomLetterPicker any;
	private final RandomLetterPicker salt;
	private final RandomLetterPicker binary;
	private final RandomLetterPicker word;
	private final RandomLetterPicker notWord;
	private final RandomLetterPicker notDigit;
	private final RandomLetterPicker space;

	/**
	 * @param random
	 */
	public RandomLetterPickers(Random random) {

		this.upperCase = RandomLetterPicker.builder().setRandom(random).addAllByEnum(UpperCaseLetter.class).build();

		this.lowerCase = RandomLetterPicker.builder().setRandom(random).addAllByEnum(LowerCaseLetter.class).build();

		this.digit = RandomLetterPicker.builder().setRandom(random).addAllByEnum(DigitalLetter.class).build();

		this.special = RandomLetterPicker.builder().setRandom(random).addAllByEnum(SpecialLetter.class).build();

		this.symbol = RandomLetterPicker.builder().setRandom(random).addAllByEnum(SymbolLetter.class).build();

		this.any = RandomLetterPicker.builder().setRandom(random).addAllByEnum(UpperCaseLetter.class)
				.addAllByEnum(LowerCaseLetter.class).addAllByEnum(DigitalLetter.class).addAllByEnum(SymbolLetter.class)
				.build();

		this.salt = RandomLetterPicker.builder().setRandom(random).addAllByEnum(UpperCaseLetter.class)
				.addAllByEnum(LowerCaseLetter.class).addAllByEnum(DigitalLetter.class).add(".").add("/").build();

		this.binary = RandomLetterPicker.builder().setRandom(random).addAll(
				IntStream.range(0, 255).mapToObj(i -> Character.toString((char) i)).collect(Collectors.toList()))
				.build();

		this.word = RandomLetterPicker.builder().setRandom(random).addAllByEnum(UpperCaseLetter.class)
				.addAllByEnum(LowerCaseLetter.class).addAllByEnum(DigitalLetter.class).add("_").build();

		this.notWord = RandomLetterPicker.builder().setRandom(random).addAllByEnum(SymbolLetter.class).remove("_")
				.build();

		this.notDigit = RandomLetterPicker.builder().setRandom(random).addAllByEnum(UpperCaseLetter.class)
				.addAllByEnum(LowerCaseLetter.class).addAllByEnum(SymbolLetter.class).build();

		this.space = RandomLetterPicker.builder().setRandom(random).add(" ").add("\t").build();
	}

	/**
	 * @return the upperCase
	 */
	public RandomLetterPicker getUpperCase() {
		return this.upperCase;
	}

	/**
	 * @return the lowerCase
	 */
	public RandomLetterPicker getLowerCase() {
		return this.lowerCase;
	}

	/**
	 * @return the digit
	 */
	public RandomLetterPicker getDigit() {
		return this.digit;
	}

	/**
	 * @return
	 */
	public RandomLetterPicker getSpecial() {
		return this.special;
	}

	/**
	 * @return the symbol
	 */
	public RandomLetterPicker getSymbol() {
		return this.symbol;
	}

	/**
	 * @return the any
	 */
	public RandomLetterPicker getAny() {
		return this.any;
	}

	/**
	 * @return the salt
	 */
	public RandomLetterPicker getSalt() {
		return this.salt;
	}

	/**
	 * @return the binary
	 */
	public RandomLetterPicker getBinary() {
		return this.binary;
	}

	/**
	 * @return the word
	 */
	public RandomLetterPicker getWord() {
		return this.word;
	}

	/**
	 * @return the notWord
	 */
	public RandomLetterPicker getNotWord() {
		return this.notWord;
	}

	/**
	 * @return the notDigit
	 */
	public RandomLetterPicker getNotDigit() {
		return this.notDigit;
	}

	/**
	 * @return the space
	 */
	public RandomLetterPicker getSpace() {
		return this.space;
	}
}
