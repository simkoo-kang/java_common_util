/**
 * CamelMap.java
 */
package kr.pe.util;

import java.util.HashMap;
import java.util.Map;

/**
 * PC 2025. 4. 30.
 */
public class CamelMap extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9054976898750404235L;

	/**
	 * 
	 */
	public CamelMap() {
		super();
	}

	/**
	 * @param initialCapacity
	 */
	public CamelMap(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * @param m
	 */
	public CamelMap(Map<? extends String, ? extends Object> m) {
		super(m);
	}

	@Override
	public Object put(String key, Object value) {
		return super.put(Str.snakeToCamel(key), value);
	}

	/**
	 * 2025. 4. 30. 오후 7:34:49 by PC
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		Object o = get(key);
		if (o == null) {
			return "";
		}
		if (o instanceof Number) {
			Number on = (Number) o;
			return String.valueOf(on);
		}
		return o.toString();
	}

	/**
	 * 2025. 4. 30. 오후 7:34:56 by PC
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		String s = getString(key);
		if (s == null || "".equals(s.trim())) {
			return 0;
		}
		return Integer.parseInt(s);
	}

	/**
	 * 2025. 4. 30. 오후 7:27:39 by PC
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "abcddee";
		System.out.println(Str.snakeToCamel(str));
	}

}
