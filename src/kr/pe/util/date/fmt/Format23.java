/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format23 extends ADateFormat {

	/**
	 * 
	 */
	public Format23() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]+", DateFmt.yyyyMMddHHmmssSSS_DASH);
		add("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]+", DateFmt.yyyyMMddHHmmssSSS_DOT);
		add("[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]+", DateFmt.yyyyMMddHHmmssSSS_SLASH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "2025-04-17 14:07:34.123";
		System.out.println(new Format23().format(date));
		date = "2025-04-17 14:07:34";
		System.out.println(new Format23().format(date));
	}
}
