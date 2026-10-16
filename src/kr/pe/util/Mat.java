/**
 * Mat.java
 */
package kr.pe.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * PC 2026. 4. 24. 오후 10:02:58
 */
public class Mat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int dec = 2;
		double numd = 9998 / (double) 9999;
		System.out.println(numd);
		System.out.println(roundHalfUp(numd, dec));
		System.out.println(decimal(roundHalfUp(numd, dec), dec));
	}

	static void testRound() {
		int max = 10000000;
		double d = 1234.56789;
		long start = System.currentTimeMillis();
		for (int j = 0; j < 1; j++) {
			for (int i = 0; i < max; i++) {
				round2(d, -2);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(Str.timeToString(end - start));

		start = System.currentTimeMillis();
		for (int j = 0; j < 1; j++) {
			for (int i = 0; i < max; i++) {
				roundHalfUp(d, -2);
			}
		}
		end = System.currentTimeMillis();
		System.out.println(Str.timeToString(end - start));
	}

	static void test() {
		List<Integer> list = getPrime(30);
		System.out.println(Arr.toStringIntegerList(list, " "));
		list = getPrime2(30);
		System.out.println(Arr.toStringIntegerList(list, " "));

		System.out.println("GCD: 48, 60 = " + getGCD(48, 60));
		System.out.println("GCD: 60, 48 = " + getGCD(60, 48));

		int n = 20;
		System.out.println("Divisors " + n + " = " + Arr.toStringIntegerList(getDivisors(n), " "));
		System.out.println("Divisor  " + n + " = " + Arr.toStringIntegerList(getDivisor(n), " "));
		System.out.println("getPrimeFactor " + n + " = " + Arr.toStringIntegerList(getPrimeFactor(n), " "));

		n = 17408;
		System.out.println("getPrimeFactor " + n + " = " + Arr.toStringIntegerList(getPrimeFactor(n), " "));

		Map<Integer, Integer> pfmap = getPrimeFactor((long) 17408);
		System.out.println(Coll.mapToString(pfmap));

		pfmap = Coll.sortMapByKey(pfmap);
		String s = Coll.mapToString(pfmap, "^", " * ", "{ ", " }");
//		pfmap.entrySet().stream().map(entry -> entry.getKey() + "[" + entry.getValue() + "]")
//				.collect(Collectors.joining(" * ", "{ ", " }"));
		System.out.println(s);

		list.clear();
		list.add(5);
		list.add(3);
		list.add(1);
		list.add(2);
		list.add(4);
		System.out.println(Arr.toStringIntegerList(list, " "));

		list.sort(Integer::compareTo);
		System.out.println(Arr.toStringIntegerList(list, " "));

		long ln = 40;
		List<Long> longlist = getFibonacci(ln);
		System.out.println(longlist.size() + " " + Arr.toStringLongList(longlist, " "));

		System.out.println(getFibonacci(40));
	}

	/**
	 * <pre>
	 * nCr
	 * =(r!∗(n−r)!)
	 * =n−1Cr−1+n−1Cr
	 * 
	 * 오버플로우 방지:
	 *    n!을 직접 구하면 n=21만 되어도 long 범위를 넘어섭니다.
	 *    위 코드는 더하기 연산만 사용하므로 최종 결과가 long 범위 안에 있다면 중간에 절대 터지지 않습니다.
	 *    
	 * 성능 최적화:
	 *    r > n / 2 일 때 r = n - r 변환을 처리하여 (예: 50C45 -> 50C5)
	 *    불필요한 배열 크기와 연산을 줄입니다.
	 * </pre>
	 */
	public static long combinationValue(int n, int r) {
		// 예외 처리 및 최적화 (nCr == nCn-r)
		if (r < 0 || r > n) {
			return 0;
		}
		if (r == 0 || r == n) {
			return 1;
		}
		if (r > n / 2) {
			r = n - r;
		}

		// 메모이제이션 배열 (Pascal's Triangle)
		long[][] dp = new long[n + 1][r + 1];

		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= Math.min(i, r); j++) {
				if (j == 0 || j == i) {
					dp[i][j] = 1; // nC0 = 1, nCn = 1
				} else {
					// 점화식: nCr = n-1Cr-1 + n-1Cr
					dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
				}
			}
		}

		return dp[n][r];
	}

	/**
	 * 조합 수열을 반환한다.
	 * 
	 * @param arr
	 * @param r
	 * @return
	 */
	public static List<int[]> getCombinations(int[] arr, int r) {
		List<int[]> result = new ArrayList<>();
		backTrack(arr, new ArrayList<>(), 0, r, result);
		return result;
	}

	/**
	 * @param arr
	 * @param current
	 * @param startIdx
	 * @param r
	 * @param result
	 */
	private static void backTrack(int[] arr, List<Integer> current, int startIdx, int r, List<int[]> result) {
		// 원하는 개수만큼 뽑았으면 결과 리스트에 복사하여 추가
		if (current.size() == r) {
			result.add(Arr.listToIntArray(current));
			return;
		}

		for (int i = startIdx; i < arr.length; i++) {
			current.add(arr[i]); // 원소 선택
			backTrack(arr, current, i + 1, r, result); // 다음 원소 탐색
			current.remove(current.size() - 1); // 원소 되돌리기 (백트래킹)
		}
	}

	/**
	 * @param is
	 * @return default -1
	 */
	public static int parseInt(String is) {
		return parseInt(is, -1);
	}

	/**
	 * @param is
	 * @param defaultValue
	 * @return
	 */
	public static int parseInt(String is, int defaultValue) {
		try {
			return Integer.parseInt(is);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * @param is
	 * @return default -1
	 */
	public static long parseLong(String is) {
		return parseLong(is, -1);
	}

	/**
	 * @param is
	 * @param defaultValue
	 * @return
	 */
	public static long parseLong(String is, long defaultValue) {
		try {
			return Long.parseLong(is);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static int cmp(int a, int b) {
		return (a < b) ? -1 : (a > b) ? 1 : 0;
	}

	public static int cmp(long a, long b) {
		return (a < b) ? -1 : (a > b) ? 1 : 0;
	}

	public static int cmp(double a, double b) {
		return (a < b) ? -1 : (a > b) ? 1 : 0;
	}

	public static int cmp(String a, String b) {
		int cmp = a.compareTo(b);
		return (cmp < 0) ? -1 : (cmp > 0) ? 1 : 0;
	}

	/**
	 * @param n is a count, max 46 or change to long value
	 * @return
	 */
	public static List<Long> getFibonacci(long n) {
		n++;
		if (n < 2) {
			return List.of(n);
		}
		return Stream.iterate(new long[] { 0, 1 }, a -> new long[] { a[1], a[0] + a[1] }).limit(n).map(a -> a[0])
				.toList();
	}

	private static long[] memo = new long[100];

	public static long getFibonacci(int n) {
		if (n < 2) {
			return n;
		}
		if (memo[n] != 0) {
			return memo[n];
		}
		memo[n] = getFibonacci(n - 1) + getFibonacci(n - 2);
		return memo[n];
	}

	/**
	 * 소인수 분회
	 * 
	 * @param n
	 * @return
	 */
	public static List<Integer> getPrimeFactor(int n) {
		List<Integer> list = new ArrayList<>();
		int sqrt = (int) Math.sqrt(n);
		for (int i = 2; i <= sqrt; i++) {
			while (n % i == 0) {
				list.add(i);
				n /= i;
			}
		}
		if (n != 1) {
			list.add(n);
		}
		return list;
	}

	/**
	 * @param l
	 * @return
	 */
	public static Map<Integer, Integer> getPrimeFactor(long l) {
		Map<Integer, Integer> map = new HashMap<>();
		List<Integer> list = getPrimeFactor((int) l);
		for (Integer n : list) {
			Integer c = map.get(n);
			if (c == null) {
				c = 1;
			} else {
				c++;
			}
			map.put(n, c);
		}
		return map;
	}

	/**
	 * 약수 구하기 - 중복되는 포함
	 * 
	 * @param n
	 * @return
	 */
	public static List<Integer> getDivisors(int n) {
		List<Integer> list = new ArrayList<>();
		int sqrt = (int) Math.sqrt(n);
		for (int i = 1; i <= sqrt; i++) {
			if (n % i == 0) {
				list.add(i);
				list.add(n / i);
			}
		}
		Collections.sort(list);
		return list;
	}

	/**
	 * 약수 구하기 - 중복되는 것 제외
	 * 
	 * @param n
	 * @return
	 */
	public static List<Integer> getDivisor(int n) {
		List<Integer> list = new ArrayList<>();
		int sqrt = (int) Math.sqrt(n);
		for (int i = 1; i <= sqrt; i++) {
			if (n % i == 0) {
				list.add(i);
				if (i != n / i) {
					list.add(n / i);
				}
			}
		}
		Collections.sort(list);
		return list;
	}

	/**
	 * use recursive
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static int getGCD(int n1, int n2) {
		int num = n1 % n2;
		if (num == 0) {
			return n2;
		}
		return getGCD(n2, num);
	}

	/**
	 * use while loop
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int getGcd(int n1, int n2) {
		int a = n1, b = n2;
		while (b != 0) {
			int t = b;
			b = a % b;
			a = t;
		}
		return a;
	}

	/**
	 * @param n
	 * @return
	 */
	public static boolean isPrime(int n) {
		if (n < 2) {
			return false;
		}
		if (n == 2) {
			return true;
		}
		if (n % 2 == 0) {
			return false;
		}
		int sqrt = (int) Math.sqrt(n);
		for (int i = 3; i <= sqrt; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * n 보다 작은 소수 구하기
	 * 
	 * @param n
	 * @return
	 */
	public static List<Integer> getPrime(int n) {
		List<Integer> list = new ArrayList<>();
		for (int i = 2; i <= n; i++) {
			if (isPrime(i)) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * n 보다 작은 소수 구하기2
	 * 
	 * @param n
	 * @return
	 */
	public static List<Integer> getPrime2(int n) {
		boolean[] prime = new boolean[n + 1]; // 0 ~ n

		List<Integer> list = new ArrayList<>();
		if (n < 2) {
			return list;
		}
		prime[0] = prime[1] = true;
		int sqrt = (int) Math.sqrt(n);
		for (int i = 2; i <= sqrt; i++) {
			if (prime[i] == true) {
				continue;
			}
			for (int j = i + i; j < prime.length; j = j + i) {
				prime[j] = true;
			}
		}
		for (int i = 2; i < prime.length; i++) {
			if (prime[i] == false) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * @param d
	 * @return
	 */
	public static double roundHalfUp(double d) {
		return roundHalfUp(d, 1);
	}

	/**
	 * 속도가 빠름
	 * 
	 * @param d
	 * @param dec 소수점 자리 수
	 * @return
	 */
	public static double roundHalfUp(double d, int dec) {
		double dc = 1;
		double v = d;
		if (dec > 0) {
			while (0 < dec--) {
				dc *= 10;
			}
		} else if (dec < 0) {
			while (0 > dec++) {
				dc /= 10;
			}
		}
		v = Math.round(v * dc) / dc;
		return v;
	}

	/**
	 * <pre>
	 * ???????????????????????????????????
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * PC 2026. 8. 27. 오후 3:26:25
	 * </pre>
	 *
	 * @param d
	 * @param dec
	 * @param dec1
	 * @return
	 */
	public static double roundHalfUp(double d, int dec, int dec1) {
		return roundHalfUp(roundHalfUp(d, dec), dec1);
	}

	/**
	 * <pre>
	 * decimalHalfDown(d, dec)
	 * </pre>
	 *
	 * @param d
	 * @param dec
	 * @return
	 */
	public static double roundHalfDown(double d, int dec) {
		return decimalHalfDown(d, dec);
	}

	/**
	 * <pre>
	 * double scale = Math.pow(d, dec);
	 * return Math.round(d * scale) / scale;
	 * </pre>
	 * 
	 * @param d
	 * @param dec
	 * @return
	 */
	public static double round2(double d, int dec) {
		double scale = Math.pow(d, dec);
		return Math.round(d * scale) / scale;
	}

	/**
	 * <pre>
	 * 소수점 자리수 보정
	 * 
	 * 방법 2: String.format() 또는 printf 사용 (화면 표시/출력용)
	 *  더 이상의 계산은 필요 없고, 화면에 '1.89'라고 예쁘게 보여주기만 하면 된다면
	 *   문자열 포맷팅이 가장 빠르고 간편합니다.
	 * </pre>
	 *
	 * @param d
	 * @param dec
	 * @return
	 */
	public static double decimal(double d, int dec) {
		if (dec == 0) {
			return d;
		}
		if (dec < 0) {
			return decimalHalfUp(d, dec);
		}
		// 소수점 아래 dec 자리까지만 출력 (자동 반올림 포함)
		String result = String.format("%." + dec + "f", d);
		return Double.parseDouble(result);
	}

	/**
	 * <pre>
	 * 소수점 자리수 보정
	 * 
	 * 방법 1: BigDecimal 사용 (연산 및 계산용, 가장 확실)
	 *  돈 계산이나 정확한 수치 연산이 필요하다면 부동소수점 오차가 없는 BigDecimal을 사용해야 합니다.
	 * </pre>
	 *
	 * @param d
	 * @param dec
	 * @return
	 */
	public static double decimalHalfUp(double d, int dec) {
		// 💡 중요: double을 바로 넣지 말고 String.valueOf()를 통해 변환해야 오차가 사라집니다.
		BigDecimal bd = new BigDecimal(String.valueOf(d)).setScale(dec, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * <pre>
	 * 소수점 자리수 보정
	 * 
	 * 방법 1: BigDecimal 사용 (연산 및 계산용, 가장 확실)
	 *  돈 계산이나 정확한 수치 연산이 필요하다면 부동소수점 오차가 없는 BigDecimal을 사용해야 합니다.
	 * </pre>
	 *
	 * @param d
	 * @param dec1
	 * @param dec2
	 * @return
	 */
	public static double decimalHalfUp(double d, int dec1, int dec2) {
		double dv = decimalHalfUp(d, dec1);
		double scale = Math.pow(10, dec2);
		return decimalHalfUp(dv * scale, dec2);
	}

	/**
	 * <pre>
	 * 소수점 자리수 보정
	 * 
	 * 방법 1: BigDecimal 사용 (연산 및 계산용, 가장 확실)
	 *  돈 계산이나 정확한 수치 연산이 필요하다면 부동소수점 오차가 없는 BigDecimal을 사용해야 합니다.
	 * </pre>
	 *
	 * @param d
	 * @param dec
	 * @return
	 */
	public static double decimalHalfDown(double d, int dec) {
		// 💡 중요: double을 바로 넣지 말고 String.valueOf()를 통해 변환해야 오차가 사라집니다.
		BigDecimal bd = new BigDecimal(String.valueOf(d)).setScale(dec, RoundingMode.HALF_DOWN);
		return bd.doubleValue();
	}
}
