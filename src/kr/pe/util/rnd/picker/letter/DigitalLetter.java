/**
 * DigitalLetter.java
 */
package kr.pe.util.rnd.picker.letter;

/**
 * PC 2026. 4. 8. 오후 3:25:52
 */
public enum DigitalLetter implements Letter {

	ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9");

	private final String letter;

	private DigitalLetter(String letter) {
		this.letter = letter;
	}

	@Override
	public String getLetter() {
		return this.letter;
	}

}
