/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format05 extends ADateFormat {

	/**
	 * 
	 */
	public Format05() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{2}:[0-9]{2}", DateFmt.HHmm_NORM);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "14:07";
		System.out.println(new Format05().format(date));
		date = "140734";
		System.out.println(new Format05().format(date));
	}

}
