/**
 * Str.java
 */
package kr.pe.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.util.date.CDate;
import kr.pe.util.date.DateFmt;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 9.
 */
public final class Str {

	private static Logger logger = LoggerFactory.getLogger(Str.class);

	/**
	 * static final String[] EMPTY_STRING_ARRAY = new String[0];
	 */
	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	public static final String TAB = "\t";
	public static final String CR = "\r";
	public static final String LF = "\n";
	public static final String CRLF = "\r\n";

	public static final long MILLIS_SECONT = 1000;
	public static final long MILLIS_MINUTE = 60 * MILLIS_SECONT;
	public static final long MILLIS_YOUR = 60 * MILLIS_MINUTE;
	public static final long MILLIS_DAY = 24 * MILLIS_YOUR;

	/**
	 * <pre></pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logger.debug("Str");
	}

	/**
	 * <pre></pre>
	 * 
	 * @param fmt "{}"
	 * @param obj
	 * @return
	 */
	public static String format(String fmt, Object... obj) {
		String fstr = fmt;
		for (Object o : obj) {
			fstr = fstr.replaceFirst(Pattern.quote("{}"), String.valueOf(o));
		}
		return fstr;
	}

	/**
	 * 0.##, #,###.##, 00000, 0.000%
	 * 
	 * @param d
	 * @param fmt
	 * @return
	 */
	public static String numberFormat(double d, String fmt) {
		NumberFormat nf = new DecimalFormat(fmt);
		return nf.format(d);
	}

	/**
	 * @param d
	 * @return [,]
	 */
	public static String numberFormat(long d) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		return nf.format(d);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param <E>
	 * @param list
	 * @param separator
	 * @return
	 */
	public static <E> String join(List<E> list, String separator) {
		StringBuilder sb = new StringBuilder();
		String spc = "";
		for (E e : list) {
			sb.append(spc).append(e.toString());
			spc = separator;
		}
		return sb.toString();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String join(int[] arr, String separator) {
		StringBuilder sb = new StringBuilder();
		String spc = "";
		for (int i : arr) {
			sb.append(spc).append(String.valueOf(i));
			spc = separator;
		}
		return sb.toString();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String join(long[] arr, String separator) {
		StringBuilder sb = new StringBuilder();
		String spc = "";
		for (long l : arr) {
			sb.append(spc).append(String.valueOf(l));
			spc = separator;
		}
		return sb.toString();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String join(String[] arr, String separator) {
		StringBuilder sb = new StringBuilder();
		String spc = "";
		for (String s : arr) {
			sb.append(spc).append(s);
			spc = separator;
		}
		return sb.toString();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String join(double[] arr, String separator) {
		StringBuilder sb = new StringBuilder();
		String spc = "";
		for (double d : arr) {
			sb.append(spc).append(String.valueOf(d));
			spc = separator;
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * char 를 count 만큼 반복된 문자열 만들기.
	 * </pre>
	 *
	 * @param lastChar
	 * @param count
	 * @return
	 */
	public static String fill(char lastChar, int count) {
		return String.valueOf(lastChar).repeat(count);
	}

	/**
	 * <pre>
	 * Converted the snake case string to the camel case string.
	 * snake case 문자열을 camel case 문자열로 변환.
	 * ex) good_morning09_everyone -> goodMorning09Everyone
	 * </pre>
	 * 
	 * @param snakeString
	 * @return
	 */
	public static String snakeToCamel(String snakeString) {
		Pattern compiler = Pattern.compile("([a-z0-9])_([a-z])");
		Matcher matcher = compiler.matcher(snakeString);
		return matcher.replaceAll(matchResult -> {
			return String.format("%s%s", matchResult.group(1).toLowerCase(), matchResult.group(2).toUpperCase());
		});
	}

	/**
	 * <pre>
	 * Converted the camel case string to the snake case string.
	 * camel case 문자열을 snake case 문자열로 변환.
	 * ex) goodMorning09Everyone -> good_morning09_everyone
	 * </pre>
	 * 
	 * @param snakeString
	 * @return
	 */
	public static String camelToSnake(String camelString) {
		Pattern compiler = Pattern.compile("([a-z0-9])([A-Z])");
		Matcher matcher = compiler.matcher(camelString);
		return matcher.replaceAll(matchResult -> {
			return String.format("%s_%s", matchResult.group(1), matchResult.group(2).toLowerCase());
		});
	}

	/**
	 * @param string
	 * @param defaultValue
	 * @return the string
	 */
	public static String nvl(String string, String defaultValue) {
		return (string == null) ? defaultValue : string;
	}

	/**
	 * @param string
	 * @param defaultValue
	 * @return the string
	 */
	public static String evl(String string, String defaultValue) {
		return (isEmpty(string)) ? defaultValue : string;
	}

	/**
	 * null or 빈 문자열이면 true
	 * 
	 * @param str
	 * @return the boolean
	 */
	public static boolean isEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	/**
	 * @param <E>
	 * @param coll
	 * @return
	 */
	public static <E> boolean isEmpty(Collection<E> coll) {
		if (coll == null || coll.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	public static <K, V> boolean isEmpty(Map<K, V> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * @param sarr
	 * @return
	 */
	public static boolean isEmpty(String[] sarr) {
		if (sarr == null || sarr.length < 1) {
			return true;
		}
		return false;
	}

	/**
	 * @param iarr
	 * @return
	 */
	public static boolean isEmpty(int[] iarr) {
		if (iarr == null || iarr.length < 1) {
			return true;
		}
		return false;
	}

	/**
	 * @param iarr
	 * @return
	 */
	public static boolean isEmpty(Integer[] iarr) {
		if (iarr == null || iarr.length < 1) {
			return true;
		}
		return false;
	}

	/**
	 * @param larr
	 * @return
	 */
	public static boolean isEmpty(long[] larr) {
		if (larr == null || larr.length < 1) {
			return true;
		}
		return false;
	}

	/**
	 * @param larr
	 * @return
	 */
	public static boolean isEmpty(Long[] larr) {
		if (larr == null || larr.length < 1) {
			return true;
		}
		return false;
	}

	/**
	 * @param darr
	 * @return
	 */
	public static boolean isEmpty(double[] darr) {
		if (darr == null || darr.length < 1) {
			return true;
		}
		return false;
	}

	/**
	 * @param darr
	 * @return
	 */
	public static boolean isEmpty(Double[] darr) {
		if (darr == null || darr.length < 1) {
			return true;
		}
		return false;
	}

	/**
	 * white space [ \f\n\r\t\v]
	 * 
	 * @param string
	 * @return the boolean
	 */
	public static boolean isWhitespace(String string) {
		boolean resultBoolean = true;
		if (string != null) {
			for (int i = 0; i < string.length(); i++) {
				if (!Character.isWhitespace(string.charAt(i))) {
					resultBoolean = false;
					break;
				}
			}
		}
		return resultBoolean;
	}

	/**
	 * Remove special white space from both ends of this string.
	 * <p>
	 * All characters that have codes less than or equal to
	 * <code>'&#92;u0020'</code> (the space character) are considered to be white
	 * space.
	 * <p>
	 * java.lang.String의 trim()과 차이점은 일반적인 white space만 짜르는 것이 아니라 위에서와 같은 특수한
	 * blank도 짤라 준다.<br>
	 * 이 소스는 IBM HOST와 데이타를 주고 받을 때 유용하게 사용했었다. 일반적으로 많이 쓰이지는 않을 것이다.
	 * 
	 * @param string
	 * @return the string
	 */
	public static String trim(String string) {
		try {
			char[] val = string.toCharArray();
			int count = val.length;
			int len = count;
			int st = 0;

			while ((st < len) && ((val[st] <= ' ') || (val[st] == '　'))) {
				st++;
			}
			while ((st < len) && ((val[len - 1] <= ' ') || (val[len - 1] == '　'))) {
				len--;
			}

			return ((st > 0) || (len < count)) ? string.substring(st, len) : string;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 입력한 문자열의 길이가 [size]보다 작으면 오른쪽에 길이가 [size]가 되게 공백[ ]을 추가하여 반환한다.
	 * 
	 * @param str
	 * @param size
	 * @return the string
	 */
	public static String rpad(String str, int size) {
		return rpad(str, size, " ");
	}

	/**
	 * @param l
	 * @param size
	 * @return
	 */
	public static String rpad(Integer l, int size) {
		return rpad(String.valueOf(l), size, " ");
	}

	/**
	 * @param l
	 * @param size
	 * @return
	 */
	public static String rpad(Long l, int size) {
		return rpad(String.valueOf(l), size, " ");
	}

	/**
	 * @param l
	 * @param size
	 * @param pad
	 * @return
	 */
	public static String rpad(Integer l, int size, String pad) {
		return rpad(String.valueOf(l), size, pad);
	}

	/**
	 * @param l
	 * @param size
	 * @param pad
	 * @return
	 */
	public static String rpad(Long l, int size, String pad) {
		return rpad(String.valueOf(l), size, pad);
	}

	/**
	 * 입력한 문자열의 길이가 [size]보다 작으면 오른쪽에 길이가 [size]가 되게 특정 문자를 추가하여 반환한다.
	 * 
	 * @param str
	 * @param size
	 * @param paddedString
	 * @return the string
	 */
	public static String rpad(String str, int size, String paddedString) {
		try {
			int length = str.length();
			if (length >= size) {
				return str;
			}
			int chlen = paddedString.length();
			StringBuilder buf = new StringBuilder(size);
			buf.append(str);
			while (length < size) {
				buf.append(paddedString);
				length += chlen;
			}
			return buf.toString();
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * 입력한 문자열의 길이가 [size]보다 작으면 왼쪽에 길이가 [size]가 되게 공백[ ]을 추가한 후 반환한다.
	 * 
	 * @param str
	 * @param size
	 * @return the string
	 */
	public static String lpad(String str, int size) {
		return lpad(str, size, " ");
	}

	/**
	 * @param l
	 * @param size
	 * @return
	 */
	public static String lpad(Integer l, int size) {
		return lpad(String.valueOf(l), size, " ");
	}

	/**
	 * @param l
	 * @param size
	 * @return
	 */
	public static String lpad(Long l, int size) {
		return lpad(String.valueOf(l), size, " ");
	}

	/**
	 * @param l
	 * @param size
	 * @param pad
	 * @return
	 */
	public static String lpad(Integer l, int size, String pad) {
		return lpad(String.valueOf(l), size, pad);
	}

	/**
	 * @param l
	 * @param size
	 * @param pad
	 * @return
	 */
	public static String lpad(Long l, int size, String pad) {
		return lpad(String.valueOf(l), size, pad);
	}

	/**
	 * 입력한 문자열의 길이가 [size]보다 작으면 왼쪽에 길이가 [size]가 되게 특정 문자를 추가한 후 반환한다.
	 * 
	 * @param str
	 * @param size
	 * @param paddedString 추가할 string
	 * @return the string
	 */
	public static String lpad(String str, int size, String paddedString) {
		try {
			int length = str.length();
			if (length >= size) {
				return str;
			}
			int chlen = paddedString.length();
			StringBuilder buf = new StringBuilder(size);
			while (length < size) {
				buf.append(paddedString);
				length += chlen;
			}
			buf.append(str);
			return buf.toString();
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * <pre>
	 * 숫자를 문자열로 변환하여 길이가 [size]가 되게 왼쪽에 [0]을 추가한 후 반환한다.
	 * default size = 2
	 * </pre>
	 * 
	 * @param number
	 * @return
	 */
	public static String fill0(int number) {
		return fill0(number, 2);
	}

	/**
	 * 숫자를 문자열로 변환하여 길이가 [size]가 되게 왼쪽에 [0]을 추가한 후 반환한다.
	 * 
	 * @param number
	 * @param size
	 * @return the string
	 */
	public static String fill0(int number, int size) {
		return lpad(String.valueOf(number), size, "0");
	}

	/**
	 * <pre>
	 * 숫자를 문자열로 변환하여 길이가 [size]가 되게 왼쪽에 [0]을 추가한 후 반환한다.
	 * default size = 2
	 * </pre>
	 * 
	 * @param number
	 * @return
	 */
	public static String fill0(long number) {
		return fill0(number, 2);
	}

	/**
	 * 숫자를 문자열로 변환하여 길이가 [size]가 되게 왼쪽에 [0]을 추가한 후 반환한다.
	 * 
	 * @param number
	 * @param size
	 * @return the string
	 */
	public static String fill0(long number, int size) {
		return lpad(String.valueOf(number), size, "0");
	}

	/**
	 * @param millisecond
	 * @return millis => 일 시 분 초 밀리
	 */
	public static String timeToString(long millisecond) {
		StringBuilder sb = new StringBuilder();
		long millis = millisecond;
		if (MILLIS_DAY < millis) {
			sb.append(Str.fill0(millis / MILLIS_DAY, 2)).append("일 ");
			millis = millis % MILLIS_DAY;
		}
		if (MILLIS_YOUR < millis) {
			sb.append(Str.fill0(millis / MILLIS_YOUR, 2)).append("시 ");
			millis = millis % MILLIS_YOUR;
		}
		if (MILLIS_MINUTE < millis) {
			sb.append(Str.fill0(millis / MILLIS_MINUTE, 2)).append("분 ");
			millis = millis % MILLIS_MINUTE;
		}
		if (MILLIS_SECONT < millis) {
			sb.append(Str.fill0(millis / MILLIS_SECONT, 2)).append("초 ");
			millis = millis % MILLIS_SECONT;
		}
		sb.append(Str.fill0(millis, 3)).append("밀리초");
		return sb.toString();
	}

	/**
	 * @param millisecond
	 * @return 년-월-일 시:분:초.밀리
	 */
	public static String timeToString2(long millisecond) {
		CDate cd = new CDate(millisecond);
		return cd.getDateString(DateFmt.yyyyMMddHHmmssSSS_DASH);
	}

}
