/**
 * StrIter.java
 */
package kr.pe.util.iter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.pe.util.Mat;

/**
 * <pre>
 * patternStr에 매칭되는 문자열을 리스트에 담고
 * 하나 씩 꺼내는 작업을 한다.
 * 
 * PC  2026. 9. 7. 오전 8:04:50
 * </pre>
 */
public class StrIter implements Iter {

	private String patternStr;
	private Pattern pattern;

	private List<String> finds;

	private static String strPattern = "[^\\d\\s]+";

	/**
	 * <pre></pre>
	 */
	public StrIter() {
		this(strPattern);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param patternStr
	 */
	public StrIter(String patternStr) {
		super();
		this.patternStr = patternStr;
		this.pattern = Pattern.compile(this.patternStr);
		this.finds = new ArrayList<>();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param line
	 * @return
	 */
	public static Iter getStrIter(String line) {
		Iter it = getStrIter();
		it.find(line);
		return it;
	}

	/**
	 * <pre>
	 * not space
	 * </pre>
	 * 
	 * @return
	 */
	public static StrIter getStrIter() {
		// not space
		return new StrIter("[^\\s]+");
	}

	@Override
	public String matchStringAll() {
		if (this.finds.size() < 1) {
			return "";
		}
		if (this.finds.size() < 2) {
			return this.finds.get(0);
		}
		StringBuilder sb = new StringBuilder();
		String cma = "";
		for (String str : this.finds) {
			sb.append(cma).append(str);
			cma = " ";
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 7. 오전 8:09:00
	 * </pre>
	 *
	 * @param line
	 * @return
	 */
	public boolean find(String line) {
		Matcher m = this.pattern.matcher(line);
		while (m.find()) {
			this.finds.add(m.group());
		}
		return 0 < this.finds.size();
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 7. 오전 8:12:28
	 * </pre>
	 *
	 * @return
	 */
	public boolean hasMore() {
		return 0 < this.finds.size();
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 7. 오전 8:14:36
	 * </pre>
	 *
	 * @return
	 */
	public String next() {
		if (hasMore()) {
			return this.finds.remove(0);
		}
		return null;
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 7. 오전 8:30:26
	 * </pre>
	 *
	 * @return
	 */
	public int size() {
		return this.finds.size();
	}

	@Override
	public int nextInt() {
		String s = this.next();
		return Mat.parseInt(s);
	}
}
