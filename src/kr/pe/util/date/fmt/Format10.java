/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format10 extends ADateFormat {

	/**
	 * 
	 */
	public Format10() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}", DateFmt.yyyyMMdd_DASH);
		add("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2}", DateFmt.yyyyMMdd_DOT);
		add("[0-9]{4}/[0-9]{2}/[0-9]{2}", DateFmt.yyyyMMdd_SLASH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "2025-04-17";
		System.out.println(new Format10().format(date));
		date = "2025.04.17";
		System.out.println(new Format10().format(date));
		date = "2025/04/17";
		System.out.println(new Format10().format(date));
	}

}
