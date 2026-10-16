/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format09 extends ADateFormat {

	/**
	 * 
	 */
	public Format09() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{3}", DateFmt.HHmmssSSS_NONE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "202504107";
		System.out.println(new Format09().format(date));
	}

}
