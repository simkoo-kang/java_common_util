/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format17 extends ADateFormat {

	/**
	 * 
	 */
	public Format17() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]+", DateFmt.yyyyMMddHHmmssSSS_NONE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "20250417192814123";
		System.out.println(new Format17().format(date));
	}

}
