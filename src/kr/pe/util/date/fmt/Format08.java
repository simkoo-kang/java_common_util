/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format08 extends ADateFormat {

	/**
	 * 
	 */
	public Format08() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{4}[0-9]{2}[0-9]{2}", DateFmt.yyyyMMdd_NONE);
		add("[0-9]{2}:[0-9]{2}:[0-9]{2}", DateFmt.HHmmss_NORM);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "14:07:34";
		System.out.println(new Format08().format(date));
		date = "20250417";
		System.out.println(new Format08().format(date));
	}

}
