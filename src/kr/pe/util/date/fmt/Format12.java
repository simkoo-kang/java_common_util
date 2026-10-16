/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format12 extends ADateFormat {

	/**
	 * 
	 */
	public Format12() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]+", DateFmt.HHmmssSSS_NORM);
		add("[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}", DateFmt.yyyyMMddHHmm_NONE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "14:07:34.123";
		System.out.println(new Format12().format(date));
		date = "202504171934";
		System.out.println(new Format12().format(date));
	}

}
