/**
 * SimbolLetter.java
 */
package kr.pe.util.rnd.picker.letter;

/**
 * PC 2026. 4. 8. 오후 3:39:18
 */
public enum SymbolLetter implements Letter {

	/**
	 * TILDE("~") 반복 기호(~).
	 */
	TILDE("~"),
	/**
	 * BACKTICK("`")
	 */
	BACKTICK("`"),
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
	CAP("^"), AND("&"), ASTERISK("*"), LPAREN("("), RPAREN(")"), MINUS("-"), UNDERBAR("_"), PLUS("+"), EQUAL("="),
	LBRACE("{"), RBRACE("}"), LBRACKET("["), RBRACKET("]"), PIPE("|"), COLON(":"), SEMICOLON(";"), SINGLEQUOTE("'"),
	DOT("."), LANGLE("<"), RANGLE(">"), QUESTION("?"), SLASH("/"), COMMA(","), BACKSLASH("\\"), DOUBLEQUOTE("\"");

	private final String letter;

	/**
	 * @param letter
	 */
	private SymbolLetter(String letter) {
		this.letter = letter;
	}

	@Override
	public String getLetter() {
		return this.letter;
	}

}
