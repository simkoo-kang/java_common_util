/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format16 extends ADateFormat {

	/**
	 * 
	 */
	public Format16() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2} [0-9]{2}:[0-9]{2}", DateFmt.yyyyMMddHHmm_DASH);
		add("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2} [0-9]{2}:[0-9]{2}", DateFmt.yyyyMMddHHmm_DOT);
		add("[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}", DateFmt.yyyyMMddHHmm_SLASH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "2025-04-17 14:07";
		System.out.println(new Format16().format(date));
		date = "2025.04.17 14:07";
		System.out.println(new Format16().format(date));
		date = "2025/04/17 14:07";
		System.out.println(new Format16().format(date));
	}

}
