/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format19 extends ADateFormat {

	/**
	 * 
	 */
	public Format19() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", DateFmt.yyyyMMddHHmmss_DASH);
		add("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", DateFmt.yyyyMMddHHmmss_DOT);
		add("[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", DateFmt.yyyyMMddHHmmss_SLASH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "2025-04-17 14:07:34";
		System.out.println(new Format19().format(date));
		date = "2025.04.17 14:07:34";
		System.out.println(new Format19().format(date));
		date = "2025/04/17 14:07:34.123";
		System.out.println(new Format19().format(date));
	}

}
