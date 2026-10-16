/**
 * UpperCaseLetter.java
 */
package kr.pe.util.rnd.picker.letter;

/**
 * PC 2026. 4. 8. 오후 3:50:00
 */
public enum UpperCaseLetter implements Letter {

	A("A"), B("B"), C("C"), D("D"), E("E"), F("F"), G("G"), H("H"), I("I"), J("J"), K("K"), L("L"), M("M"), N("N"),
	O("O"), P("P"), Q("Q"), R("R"), S("S"), T("T"), U("U"), V("V"), W("W"), X("X"), Y("Y"), Z("Z");

	private final String letter;

	/**
	 * @param letter
	 */
	private UpperCaseLetter(String letter) {
		this.letter = letter;
	}

	@Override
	public String getLetter() {
		return this.letter;
	}

}
