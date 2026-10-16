/**
 * UserDefinedLetterPickerScanner.java
 */
package kr.pe.util.rnd.picker;

import java.util.ArrayList;
import java.util.List;

/**
 * PC 2026. 4. 8. 오후 7:43:46
 */
public class UserDefinedLetterPickerScanner {

	public static class ScannedUserDefinedPicker {
		private final int cursor;
		private final String key;
		private final List<String> bounds;

		public ScannedUserDefinedPicker(final int cursorForScanning, final String key, final List<String> bounds) {
			this.cursor = cursorForScanning;
			this.key = key;
			this.bounds = bounds;
		}

		/**
		 * @return the cursor
		 */
		public int getCursor() {
			return this.cursor;
		}

		/**
		 * @return the key
		 */
		public String getKey() {
			return this.key;
		}

		/**
		 * @return the bounds
		 */
		public List<String> getBounds() {
			return this.bounds;
		}
	}

	public static ScannedUserDefinedPicker scan(final String[] regexCharacters, final int index) {
		String key = "";
		String character;
		List<String> bounds = new ArrayList<>();

		int i = index;
		while (!(character = regexCharacters[++i]).equals("]")) {
			if (character.equals("-") && !bounds.isEmpty()) {
				String beginCharacter = bounds.get(bounds.size() - 1); // take from tail
				String endCharacter = regexCharacters[++i]; // take from after "-"

				key += beginCharacter + "-" + endCharacter;

				bounds.add(endCharacter);
			} else {
				if (String.valueOf(character).matches("\\W")) {
					throw new RuntimeException("'" + character + "'" + "will be treated literally inside []");
				}
				bounds.add(character);
			}
		}

		return new ScannedUserDefinedPicker(i, key, bounds);
	}
}
