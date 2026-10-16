/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format11 extends ADateFormat {

	/**
	 * 
	 */
	public Format11() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", DateFmt.ddHHmmss_NORM);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "14:07:34.123";
		System.out.println(new Format11().format(date));
		date = "202504171934";
		System.out.println(new Format11().format(date));
	}

}
