/**
 * 
 */
package kr.pe.util.date.fmt;

import kr.pe.util.date.DateFmt;

/**
 * 
 */
public class Format04 extends ADateFormat {

	/**
	 * 
	 */
	public Format04() {
		super();
	}

	@Override
	protected void init() {
		add("[0-9]{2}[0-9]{2}", DateFmt.HHmm_NONE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "1407";
		System.out.println(new Format04().format(date));
		date = "140734";
		System.out.println(new Format04().format(date));
	}

}
