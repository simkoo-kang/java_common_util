/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format14 extends ADateFormat {

	/**
	 * 
	 */
	public Format14() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}", DateFmt.yyyyMMddHHmmss_NONE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "20250417140734";
		System.out.println(new Format14().format(date));
		date = "2025-04-17 14:07:34.123";
		System.out.println(new Format14().format(date));
	}
}
