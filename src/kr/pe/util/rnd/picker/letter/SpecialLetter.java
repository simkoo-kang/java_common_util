/**
 * SpecialLetter.java
 */
package kr.pe.util.rnd.picker.letter;

/**
 * PC 2026. 4. 29. 오후 8:27:12
 */
public enum SpecialLetter implements Letter {

	/**
	 * EXCLAMATION("!") 느낌표, 감탄 부호
	 */
	EXCLAMATION("!"),
	/**
	 * AT("@")
	 */
	AT("@"),
	/**
	 * DOLLER("$")
	 */
	DOLLER("$"),
	/**
	 * PERCENT("%")
	 */
	PERCENT("%"),
	/**
	 * SHARP("#")
	 */
	SHARP("#"),
	/**
	 * CAP("^")
	 */
	CAP("^"),
	/**
	 * AND("&")
	 */
	AND("&"),
	/**
	 * ASTERISK("*")
	 */
	ASTERISK("*"),
	/**
	 * LPAREN("(")
	 */
	LPAREN("("),
	/**
	 * RPAREN(")")
	 */
	RPAREN(")"),
	/**
	 * MINUS("-")
	 */
//	MINUS("-"),
	/**
	 * UNDERBAR("_")
	 */
	UNDERBAR("_"),
	/**
	 * DOT(".")
	 */
	DOT("."),
	/**
	 * QUESTION("?")
	 */
	QUESTION("?");

	private final String letter;

	/**
	 * @param letter
	 */
	private SpecialLetter(String letter) {
		this.letter = letter;
	}

	@Override
	public String getLetter() {
		return this.letter;
	}

}
