/**
 * 
 */
package kr.pe.util.date.fmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public abstract class ADateFormat implements DateFormat {

	protected List<String> list;
	protected Map<String, String> map;
	/**
	 * 
	 */
	public ADateFormat() {
		this.list = new ArrayList<String>();
		this.map = new HashMap<String, String>();
		init();
	}

	public void add(String fmt, String pat) {
		this.list.add(fmt);
		this.map.put(fmt, pat);
	}
	/**
	 * 
	 */
	protected abstract void init();

	@Override
	public String format(String date) {
		for(String fmt : this.list) {
			if(format(date, fmt)) return this.map.get(fmt);
		}
		return null;
	}

	/**
	 * @param date
	 * @param fmt
	 * @return
	 */
	private boolean format(String date, String fmt) {
		Pattern p = Pattern.compile(fmt);
		Matcher m = p.matcher(date);
		return m.matches();
	}
}
