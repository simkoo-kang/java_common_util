/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format06 extends ADateFormat {

	/**
	 * 
	 */
	public Format06() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{2}[0-9]{2}[0-9]{2}", DateFmt.HHmmss_NONE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "14:07:34";
		System.out.println(new Format06().format(date));
		date = "140734";
		System.out.println(new Format06().format(date));
	}

}
