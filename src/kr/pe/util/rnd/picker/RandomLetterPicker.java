/**
 * RandomLetterPicker.java
 */
package kr.pe.util.rnd.picker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import kr.pe.util.rnd.Rnd;
import kr.pe.util.rnd.picker.letter.Letter;

/**
 * PC 2026. 4. 8. 오후 3:55:52
 */
public class RandomLetterPicker {

	public static class Builder {
		private List<String> letters;
		private Random random;

		public Builder() {
			this.letters = new ArrayList<>();
			this.random = null;
		}

		public <E extends Enum<E> & Letter> Builder addAllByEnum(Class<E> enumClass) {
			this.letters.addAll(
					Arrays.stream(enumClass.getEnumConstants()).map(e -> e.getLetter()).collect(Collectors.toList()));
			return this;
		}

		public Builder addAll(List<String> list) {
			this.letters.addAll(list);
			return this;
		}

		public Builder add(String letter) {
			this.letters.add(letter);
			return this;
		}

		public Builder remove(String remove) {
			this.letters = this.letters.stream().filter(l -> !l.equals(remove)).collect(Collectors.toList());
			return this;
		}

		public RandomLetterPicker build() {
			return new RandomLetterPicker(this);
		}

		/**
		 * @return
		 */
		public List<String> getLetters() {
			return this.letters;
		}

		/**
		 * @return the random
		 */
		public Random getRandom() {
			return this.random;
		}

		/**
		 * @param random the random to set
		 */
		public Builder setRandom(Random random) {
			this.random = random;
			return this;
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	private final List<String> letters;
	private final int size;
	private final Random random;

	/**
	 * @param b
	 * 
	 */
	RandomLetterPicker(Builder b) {

		this.letters = Collections.unmodifiableList(b.getLetters());
		if (b.getRandom() == null) {
			this.random = Rnd.getRandom();
		} else {
			this.random = b.getRandom();
		}

		this.size = this.letters.size();
	}

	public String pickRandomLetter() {
		return this.letters.get(this.random.nextInt(this.size));
	}

	public static RandomLetterPicker constructByCharacterRange(final List<String> bounds) {
		Builder definedPickerBuilder = builder();
		int bufferSize = bounds.size();

		for (int i = 0; i < bufferSize; i += 2) {
			int beginCode = (int) bounds.get(i).charAt(0);
			int endCode = (int) bounds.get(i + 1).charAt(0);

			if (beginCode > endCode) {
				throw new RuntimeException(
						"Detected invalid character range: [" + (char) beginCode + "-" + (char) endCode + "]");
			}

			for (int code = beginCode; code <= endCode; code++) {
				definedPickerBuilder.add(String.valueOf((char) code));
			}
		}

		return definedPickerBuilder.build();
	}
}
