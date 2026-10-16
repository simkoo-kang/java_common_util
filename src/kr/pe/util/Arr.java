/**
 * 
 */
package kr.pe.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import kr.pe.util.rnd.Rnd;

/**
 * 
 */
public final class Arr {

	/**
	 * space
	 */
	private static String DEFAULT_SEPARATOR = " ";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] arr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 22, 23, 24, 25, 29, 30,
				31, 36, 37, 43 };
		int[] nums = new int[] { 2, 11, 25, 26, 37, 45 };

		long start = System.currentTimeMillis();
		for (int i = 0; i < 2000000; i++) {
			for (int j = 0; j < 6; j++) {
				Arr.contains(arr, nums[j]);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(Str.timeToString(end - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < 2000000; i++) {
			for (int j = 0; j < 6; j++) {
				Arr.contains1(arr, nums[j]);
			}
		}
		end = System.currentTimeMillis();
		System.out.println(Str.timeToString(end - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < 2000000; i++) {
			for (int j = 0; j < 6; j++) {
				Arr.contains2(arr, nums[j]);
			}
		}
		end = System.currentTimeMillis();
		System.out.println(Str.timeToString(end - start));

		List<Integer> list = Arr.intArrayAsList(arr);
		start = System.currentTimeMillis();
		for (int i = 0; i < 2000000; i++) {
			for (int j = 0; j < 6; j++) {
				list.contains(nums[j]);
			}
		}
		end = System.currentTimeMillis();
		System.out.println(Str.timeToString(end - start));

		shuffle(nums);
		System.out.println(Arrays.toString(nums));

		System.out.println(contains3(nums, 0));
	}

	/**
	 * <pre>
	 * 방법 1. 반복문(For문) 사용하기 (가장 추천, 직관적)
	 * 2차원 배열 복사
	 * 성능
	 * </pre>
	 * @param matrix
	 * @return
	 */
	public static String[][] copyMe(String[][] matrix){
		String[][] copy = new String[matrix.length][];
		for(int i=0; i<matrix.length; i++) {
			copy[i] = matrix[i].clone();
		}
		return copy;
	}
	/**
	 * <pre>
	 * 방법 2. Stream API 사용하기 (Java 8 이상, 한 줄 컷)
	 * 2차원 배열 복사  use Arrays.stream
	 * 가독성
	 * </pre>
	 * @param matrix
	 * @return
	 */
	public String[][] copyMeWithStream(String[][] matrix){
		return Arrays.stream(matrix).map(String[]::clone).toArray(String[][]::new);
	}
	/**
	 * <pre>
	 * C/C++ 소켓 통신 시 주의할 점
	 *  - 패킷 헤더 크기 맞추기:
	 *     C/C++ 구조체(Struct)와 통신할 때는 컴파일러의 패딩 규칙(Struct Alignment) 때문에 공백 바이트가 낄 수 있으므로,
	 *     자바에서 데이터를 보낼 때는 패딩 바이트를 직접 맞추어 주어야 합니다.
	 *  - 부호 없는 정수(Unsigned):
	 *     C/C++에서 unsigned int로 보낸 데이터가 자바의 int 범위를 넘어가면 음수로 바뀔 수 있습니다.
	 *     이 경우 자바에서는 long 변수에 받아 & 0xFFFFFFFFL 연산으로 보정해야 합니다.
	 * 
	 * 2. 비트 연산으로 리틀 엔디안 변환
	 *   성능 최적화가 필요할 때 쓰는 비트 연산 방식입니다.
	 *   빅 엔디안과 반대로 배열의 앞부분에 데이터의 하위 비트(낮은 자리)를 채워 넣습니다.
	 *   
	 * PC 2026. 8. 26. 오후 8:34:24
	 * </pre>
	 *
	 * @param value
	 * @return
	 */
	public static byte[] intToByteArrayWithBitShiftImplementLittleEndian(int value) {
		return new byte[] { (byte) (value), // 가장 낮은 자리 바이트를 0번 인덱스에
				(byte) (value >> 8), (byte) (value >> 16), (byte) (value >> 24) // 가장 높은 자리 바이트를 마지막에
		};
	}

	/**
	 * <pre>
	 * 2. 비트 연산으로 리틀 엔디안 변환
	 *   성능 최적화가 필요할 때 쓰는 비트 연산 방식입니다.
	 *   빅 엔디안과 반대로 배열의 앞부분에 데이터의 하위 비트(낮은 자리)를 채워 넣습니다.
	 * 
	 * PC 2026. 8. 26. 오후 8:44:02
	 * </pre>
	 *
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToIntWithBitShiftImplementLittleEndian(byte[] bytes) {
		return (bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8) | ((bytes[2] & 0xFF) << 16) | ((bytes[3] & 0xFF) << 24);
	}

	/**
	 * <pre>
	 * 1. ByteBuffer로 리틀 엔디안 변환 (추천)
	 * 
	 *   order(ByteOrder.LITTLE_ENDIAN) 메서드만 지정하면 자바가 내부적으로 순서를 알아서 정렬해 줍니다.
	 * 
	 * PC 2026. 8. 26. 오후 8:19:19
	 * </pre>
	 *
	 * @param iv
	 * @return
	 */
	public static byte[] intToByteArrayWithLittleEndian(int iv) {
		// 리틀 엔디안 설정
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(iv).array();
	}

	/**
	 * <pre>
	 * 1. ByteBuffer로 리틀 엔디안 변환 (추천)
	 * 
	 *   order(ByteOrder.LITTLE_ENDIAN) 메서드만 지정하면 자바가 내부적으로 순서를 알아서 정렬해 줍니다.
	 * 
	 * 
	 * PC 2026. 8. 26. 오후 8:21:40
	 * </pre>
	 *
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToIntWithLittleEndian(byte[] bytes) {
		// 읽을 때도 반드시 리틀 엔디안 설정
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	/**
	 * <pre>
	 * 2. 비트 연산 (Bit Shift) 사용 방법
	 *   객체를 생성하지 않아 메모리 효율과 속도가 가장 뛰어난 방식입니다.
	 * 
	 * PC 2026. 8. 26. 오후 8:12:43
	 * </pre>
	 *
	 * @param iv
	 * @return
	 */
	public static byte[] intToByteArrayWithBitShift(int iv) {
		return new byte[] { (byte) (iv >> 24), (byte) (iv >> 16), (byte) (iv >> 8), (byte) (iv) };
	}

	/**
	 * <pre>
	 * 2. 비트 연산 (Bit Shift) 사용 방법
	 *   객체를 생성하지 않아 메모리 효율과 속도가 가장 뛰어난 방식입니다.
	 * 
	 *   주의: 복원 시 & 0xFF 연산을 생략하면 자바의 부호 확장(Sign Extension) 때문에
	 *        음수 바이트 값이 int로 변환될 때 값이 오염됩니다.
	 * 
	 * PC 2026. 8. 26. 오후 8:14:47
	 * </pre>
	 *
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToIntWithBitShift(byte[] bytes) {
		return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
	}

	/**
	 * <pre>
	 * 1. ByteBuffer 사용 방법 (추천)
	 *   코드가 직관적이며 엔디안(바이트 순서) 처리가 안전합니다.
	 * 
	 *  --- 1. int -> byte[] 변환 (4바이트 크기) ---
	 *  
	 * PC 2026. 8. 26. 오후 8:08:13
	 * </pre>
	 *
	 * @param iv
	 * @return
	 */
	public static byte[] intToByteArrayWithByteBuffer(int iv) {
		return ByteBuffer.allocate(4).putInt(iv).array();
	}

	/**
	 * <pre>
	 * 1. ByteBuffer 사용 방법 (추천)
	 *   코드가 직관적이며 엔디안(바이트 순서) 처리가 안전합니다.
	 * 
	 *  --- 2. byte[] -> int 복원 ---
	 * 
	 * PC 2026. 8. 26. 오후 8:10:35
	 * </pre>
	 *
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToIntWithByteBuffer(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}

	/**
	 * <pre>
	 * int[] to long
	 * 
	 * 2. 비트 연산 (Bit Shift) 사용
	 *   객체 생성 없이 속도가 가장 빠른 방법입니다.
	 *   상위 32비트와 하위 32비트를 밀어내고 합치는 방식입니다.
	 * 
	 * PC 2026. 8. 26. 오후 7:58:13
	 * </pre>
	 *
	 * @param ints
	 * @return
	 */
	public static long intArrayToLongWithBitShift(int[] ints) {
		// 첫 번째 int를 상위 32비트로 밀고, 두 번째 int를 하위 32비트에 합성
		return ((long) ints[0] << 32) | (ints[1] & 0xFFFFFFFFL);
	}

	/**
	 * <pre>
	 * long to int[]
	 * 
	 * 2. 비트 연산 (Bit Shift) 사용
	 *   객체 생성 없이 속도가 가장 빠른 방법입니다.
	 *   상위 32비트와 하위 32비트를 밀어내고 합치는 방식입니다.
	 * 
	 * 
	 * PC 2026. 8. 26. 오후 8:03:02
	 * </pre>
	 *
	 * @param lv
	 * @return
	 */
	public static int[] longToIntArrayWithBitShift(long lv) {
		int[] result = new int[2];
		result[0] = (int) (lv >> 32); // 상위 32비트 추출
		result[1] = (int) lv; // 하위 32비트 추출
		return result;
	}

	/**
	 * <pre>
	 * byte[] to long
	 * 
	 * ByteBuffer.wrap() 메서드를 사용하면 바이트 배열을 쉽게 long으로 변환할 수 있습니다.
	 * 
	 * PC 2026. 8. 26. 오후 7:32:01
	 * </pre>
	 *
	 * @param bytes
	 * @return
	 */
	public static long byteArrayToLong(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getLong();
	}

	/**
	 * <pre>
	 * byte[] to long
	 * 
	 * 2. 바이트 순서 (Endian) 설정하기
	 *   자바의 기본 엔디안은 빅 엔디안(Big-Endian)입니다.
	 * 
	 * PC 2026. 8. 26. 오후 7:32:01
	 * </pre>
	 *
	 * @param bytes
	 * @return
	 */
	public static long byteArrayToLongWithGibEndian(byte[] bytes) {
		// 1. 빅 엔디안 변환 (Java 기본값)
		return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN) // 읽을 때도 동일한 엔디안 지정
				.getLong();
	}

	/**
	 * <pre>
	 * byte[] to long
	 * 
	 * 2. 바이트 순서 (Endian) 설정하기
	 *   네트워크 통신이나 C/C++ 시스템과 데이터를 주고받을 때는 리틀 엔디안(Little-Endian)
	 * 
	 * PC 2026. 8. 26. 오후 7:32:01
	 * </pre>
	 *
	 * @param bytes
	 * @return
	 */
	public static long byteArrayRoLongWithLittleEndian(byte[] bytes) {
		// 1. 빅 엔디안 변환 (Java 기본값)
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN) // 읽을 때도 동일한 엔디안 지정
				.getLong();
	}

	/**
	 * <pre>
	 * long value to byte array
	 * 
	 * 1. ByteBuffer 사용 방법 (추천)
	 *   코드가 간결하고 가독성이 좋으며, 자바 표준 라이브러리 기능이라 안전합니다.
	 * 
	 * PC 2026. 8. 26. 오후 7:25:20
	 * </pre>
	 *
	 * @param lv
	 * @return
	 */
	public static byte[] lontToByteArrayWithByteBuffer(long lv) {
		// 1. 8바이트 크기의 ByteBuffer 할당
		// 2. putLong()으로 long 값 저장
		// 3. array()로 byte[] 반환
		return ByteBuffer.allocate(8).putLong(lv).array();
	}

	/**
	 * <pre>
	 * long value to byte array
	 * 
	 * 2. 비트 연산(Bit Shift) 사용 방법
	 *   ByteBuffer를 사용하지 않고 직접 비트를 밀어내며(>>) 변환할 수도 있습니다.
	 *   성능이 미세하게 중요하거나 내부 동작을 직접 제어하고 싶을 때 씁니다.
	 * 
	 * PC 2026. 8. 26. 오후 7:25:20
	 * </pre>
	 *
	 * @param lv
	 * @return
	 */
	public static byte[] longToByteArrayWithBitShift(long lv) {
		byte[] result = new byte[8];
		for (int i = 7; i >= 0; i--) {
			result[i] = (byte) (lv & 0xFF);
			lv >>= 8;
		}
		return result;
	}

	/**
	 * 두 배열을 합친다. 빠르다. - 중복 제거 없슴. = use System.arraycopy
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int[] combine(int[] a, int[] b) {
		// 두 배열의 길이를 합친 새 배열 생성
		int[] combined = new int[a.length + b.length];

		// 배열 복사 (원본, 원본시작위치, 목적지, 목적지시작위치, 복사할길이)
		System.arraycopy(a, 0, combined, 0, a.length);
		System.arraycopy(b, 0, combined, a.length, b.length);
		Arrays.sort(combined);
		return combined;
	}

	/**
	 * 두 배열을 합친다 - 중복 제거 = use IntStream
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int[] combineDistinctByStream(int[] a, int[] b) {
		int[] combined = IntStream.concat(IntStream.of(a), IntStream.of(b)).distinct().toArray();
		Arrays.sort(combined);
		return combined;
	}

	/**
	 * 두 배열을 합친다 - 중복 제거 = use Set
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int[] combineDistinctBySet(int[] a, int[] b) {
		// 1. 중복을 막기 위한 Set 생성
		Set<Integer> set = new HashSet<>();

		// 2. 두 배열의 요소를 Set에 추가 (중복은 자동 제거됨)
		for (int num : a) {
			set.add(num);
		}
		for (int num : b) {
			set.add(num);
		}

		// 3. Set을 다시 int[] 배열로 변환
		int[] combined = new int[set.size()];
		int index = 0;
		for (int num : set) {
			combined[index++] = num;
		}
		Arrays.sort(combined);
		return combined;
	}

	/**
	 * <pre>
	 * 3 배열을 합친다 - 중복 제거 = use Set
	 * </pre>
	 *
	 * @param skippedPatternNumbers
	 * @param overWinNumbers
	 * @param lastPositionNumbers
	 * @return
	 */
	public static int[] combineDistinctBySet(int[] a, int[] b, int[] c) {
		// 1. 중복을 막기 위한 Set 생성
		Set<Integer> set = new HashSet<>();

		// 2. 3 배열의 요소를 Set에 추가 (중복은 자동 제거됨)
		for (int num : a) {
			set.add(num);
		}
		for (int num : b) {
			set.add(num);
		}
		for (int num : c) {
			set.add(num);
		}

		// 3. Set을 다시 int[] 배열로 변환
		int[] combined = new int[set.size()];
		int index = 0;
		for (int num : set) {
			combined[index++] = num;
		}
		Arrays.sort(combined);
		return combined;
	}

	/**
	 * 지정한 값과 같거나 큰 index 를 다른 배열로 반환
	 * 
	 * @param arr
	 * @param value
	 * @return
	 */
	public static int[] subIndexInArray(int[] arr, int value) {
		List<Integer> list = new ArrayList<>();
		int idx = -1;
		for (int n : arr) {
			idx++;
			if (n < value) {
				continue;
			}
			list.add(idx + 1);
		}
		return Arr.listToIntArray(list);
	}

	/**
	 * 지정한 값과 같은 index 를 다른 배열로 반환 - 가독성
	 * 
	 * @param arr
	 * @param value
	 * @return
	 */
	public static int[] subIndexInArray2(int[] arr, int value) {
		int[] filtered = Arrays.stream(arr).filter(n -> n >= value).toArray();
		for (int i = 0; i < filtered.length; i++) {
			filtered[i]++;
		}
		return filtered;
	}

	/**
	 * 평균
	 * 
	 * @param list
	 * @return
	 */
	public static double mean(List<Integer> list) {
		int size = list.size();
		if (size < 2) {
			return 0.0;
		}
		double sum = 0.0;
		for (int n : list) {
			sum += n;
		}
		return sum / size;
	}

	public static double mean(int[] nums) {
		int size = nums.length;
		if (size < 2) {
			return 0.0;
		}
		double sum = 0.0;
		for (int n : nums) {
			sum += n;
		}
		return sum / size;
	}

	public static double average(int[] nums) {
		return Arrays.stream(nums).average().orElse(0);
	}

	/**
	 * 표준편차
	 * 
	 * @param list
	 * @return
	 */
	public static double sd(List<Integer> list) {
		int size = list.size();
		double mean = mean(list);
		if (mean == 0.0) {
			return 0.0;
		}
		double sum = 0.0;
		for (int n : list) {
			double sm = n - mean;
			sum += sm * sm;
		}
		sum /= size;
		return Math.sqrt(sum);
	}

	public static double sd(int[] nums) {
		int size = nums.length;
		double mean = mean(nums);
		if (mean == 0.0) {
			return 0.0;
		}
		double sum = 0.0;
		for (int n : nums) {
			double sm = n - mean;
			sum += sm * sm;
		}
		sum /= size;
		return Math.sqrt(sum);
	}

	/**
	 * <pre>
	 * 2차 배열의 합
	 * 
	 * PC 2026. 8. 27. 오후 4:41:06
	 * </pre>
	 *
	 * @param arr
	 * @return
	 */
	public static int sum(int[][] arr) {
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				sum += arr[i][j];
			}
		}
		return sum;
	}

	/**
	 * <pre>
	 * </pre>
	 *
	 * @param list
	 * @return
	 */
	public static int sum(List<Integer> list) {
		int sum = 0;
		for (int n : list) {
			sum += n;
		}
		return sum;
	}

	/**
	 * <pre>
	 * </pre>
	 *
	 * @param list
	 * @param size
	 * @return
	 */
	public static int sum(List<Integer> list, int size) {
		int sum = 0;
		for (int i = 0; i < Math.min(size, list.size()); i++) {
			sum += list.get(i);
		}
		return sum;
	}

	/**
	 * <pre>
	 * </pre>
	 *
	 * @param nums
	 * @return
	 */
	public static int sum(int[] nums) {
		int sum = 0;

		for (int num : nums) {
			sum += num;
		}
		return sum;
	}

	/**
	 * <pre>
	 * </pre>
	 *
	 * @param nums
	 * @param size
	 * @return
	 */
	public static int sum(int[] nums, int size) {
		int sum = 0;

		// size 와 배열 길이 중 더 작은 값만큼만 반복
		for (int i = 0; i < Math.min(size, nums.length); i++) {
			sum += nums[i];
		}
		return sum;
	}

	/**
	 * @param nums
	 * @return
	 */
	public static int min(int[] nums) {
		int m = nums[0];
		for (int n : nums) {
			if (m > n) {
				m = n;
			}
		}
		return m;
	}

	/**
	 * @param nums
	 * @param size
	 * @return
	 */
	public static int min(int[] nums, int size) {
		int m = nums[0];
		for (int i = 0; i < size; i++) {
			if (m > nums[i]) {
				m = nums[i];
			}
		}
		return m;
	}

	/**
	 * @param nums
	 * @return
	 */
	public static int max(int[] nums) {
		int m = nums[0];
		for (int n : nums) {
			if (m < n) {
				m = n;
			}
		}
		return m;
	}

	/**
	 * @param nums
	 * @param size
	 * @return
	 */
	public static int max(int[] nums, int size) {
		int m = nums[0];
		for (int i = 0; i < size; i++) {
			if (m < nums[i]) {
				m = nums[i];
			}
		}
		return m;
	}

	/**
	 * <pre>
	 * 배열에서 값이 value 인 갯수
	 * 
	 * PC 2026. 8. 27. 오후 2:20:08
	 * </pre>
	 *
	 * @param nums
	 * @param value
	 * @return
	 */
	public static int count(int[] nums, int value) {
		int cnt = 0;
		for (int i = 0; i < nums.length; i++) {
			if (value == nums[i]) {
				cnt++;
			}
		}
		return cnt;
	}

	/**
	 * <pre>
	 * 0이 연속으로 최대 몇 개인지 확인
	 * 
	 * PC 2026. 8. 27. 오후 2:31:28
	 * </pre>
	 *
	 * @param nums
	 * @return
	 */
	public static int countSerialZero(int[] nums) {
		int cnt = 0, max = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == 0) {
				cnt++;
			} else {
				if (max < cnt) {
					max = cnt;
				}
				cnt = 0;
			}
		}
		return (max < cnt) ? cnt : max;
	}

	/**
	 * <pre>
	 * 0이 연속으로 최대 몇 개인지 확인
	 * 
	 * PC 2026. 8. 27. 오후 2:31:28
	 * </pre>
	 *
	 * @param nums
	 * @return
	 */
	public static int countSerialNonZero(int[] nums) {
		int cnt = 0, max = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == 0) {
				if (max < cnt) {
					max = cnt;
				}
				cnt = 0;
			} else {
				cnt++;
			}
		}
		return (max < cnt) ? cnt : max;
	}

	/**
	 * 
	 * @param <E>
	 * @param ea
	 * @return
	 */
	public static <E> Set<E> createSet(E[] ea) {
		Set<E> set = new HashSet<>();
		for (E e : ea) {
			set.add(e);
		}
		return set;
	}

	/**
	 * count zero in the array
	 * 
	 * @param cnts
	 * @return
	 */
	public static int containsZero(int[] cnts) {
		int cnt0 = 0;
		for (int i = 0; i < cnts.length; i++) {
			if (cnts[i] == 0) {
				cnt0++;
			}
		}
		return cnt0;
	}

	/**
	 * <pre>
	 * for loop  ->  성능이 극도로 중요하거나 역순/건너뛰며 탐색할 때
	 * 
	 * contains2보다 빠르다.
	 * </pre>
	 * 
	 * @param ns 역순 정렬
	 * @param n
	 * @return true or false if n is included in the ns array
	 */
	public static int containsGreatThan(int[] ns, int n) {
		int cnt = 0;
		for (int ni : ns) {
			if (ni < n) {
				return cnt;
			}
			cnt++;
		}
		return cnt;
	}

	/**
	 * <pre>
	 * prev 와 next 의 중복 숫자
	 * contains1보다 살짝 빠르다. 조금
	 * </pre>
	 * 
	 * @param prev
	 * @param next
	 * @return
	 */
	public static int contains(int[] prev, int[] next) {
		int cnt = 0;
		for (int i = 0; i < next.length; i++) {
			if (contains(prev, next[i])) {
				cnt++;
			}
		}
		return cnt;
	}

	/**
	 * <pre>
	 * for loop  ->  성능이 극도로 중요하거나 역순/건너뛰며 탐색할 때
	 * 
	 * contains2보다 빠르다.
	 * contains1보다 살짝 빠르다. 조금
	 * </pre>
	 * 
	 * @param ns
	 * @param n
	 * @return true or false if n is included in the ns array
	 */
	public static boolean contains(int[] ns, int n) {
		for (int ni : ns) {
			if (ni == n) {
				return true;
			}
			if (n < ni) {
				break;
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * for loop  ->  성능이 극도로 중요하거나 역순/건너뛰며 탐색할 때
	 * 
	 * contains2보다 빠르다.
	 * contains1보다 살짝 빠르다. 조금
	 * </pre>
	 * 
	 * @param ns
	 * @param n
	 * @param size
	 * @return
	 */
	public static boolean contains(int[] ns, int n, int size) {
		for (int i = 0; i < size; i++) {
			if (ns[i] == n) {
				return true;
			}
			if (n < ns[i]) {
				break;
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * for loop  ->  성능이 극도로 중요하거나 역순/건너뛰며 탐색할 때
	 * 
	 * arr 가 정렬이 되어 있을 경우 제일 빠르다.
	 * contains 보다 살짝 느리다. 조금
	 * 
	 * PC 2026. 9. 2. 오후 9:38:56
	 * </pre>
	 *
	 * @param nsSmallSize
	 * @param nsBigSize
	 * @return
	 */
	public static int contains1(int[] nsSmallSize, int[] nsBigSize) {
		int cnt = 0;
		for (int ns : nsSmallSize) {
			for (int nb : nsBigSize) {
				if (ns == nb) {
					cnt++;
				} else if (ns < nb) {
					break;
				}
			}
		}
		return cnt;
	}

	/**
	 * <pre>
	 * for loop  ->  성능이 극도로 중요하거나 역순/건너뛰며 탐색할 때
	 * 
	 * arr 가 정렬이 되어 있을 경우 제일 빠르다.
	 * contains 보다 살짝 느리다. 조금
	 * </pre>
	 * 
	 * @param ns
	 * @param n
	 * @return true or false if n is included in the ns array
	 */
	public static boolean contains1(int[] ns, int n) {
		for (int ni : ns) {
			if (ni == n) {
				return true;
			}
			if (n < ni) {
				return false;
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * for loop  ->  성능이 극도로 중요하거나 역순/건너뛰며 탐색할 때
	 * 
	 * contains2보다 빠르다.
	 * contains 보다 살짝 느리다. 조금
	 * </pre>
	 * 
	 * @param ns
	 * @param n
	 * @param size
	 * @return
	 */
	public static boolean contains1(int[] ns, int n, int size) {
		for (int i = 0; i < size; i++) {
			if (n < ns[i])
				return false;
			if (ns[i] == n)
				return true;
		}
		return false;
	}

	/**
	 * <pre>
	 * Arrays.stream()  ->  가독성이 중요하고 코드를 간결하게 유지할 때
	 * 
	 * contains 보다 느리다.
	 * </pre>
	 * 
	 * @param ns
	 * @param n
	 * @return
	 */
	public static boolean contains2(int[] ns, int n) {
		return IntStream.of(ns).anyMatch(x -> x == n);
	}

	/**
	 * <pre>
	 * Arrays.binarySearch() ->  배열이 정렬되어 있고 탐색할 구간이 매우 클 때
	 * 
	 * </pre>
	 * 
	 * @param ns
	 * @param n
	 * @return true or false if n is included in the ns array
	 */
	public static boolean contains3(int[] ns, int n) {
		int index = Arrays.binarySearch(ns, n);
		return (index < 0) ? false : true;
	}

	/**
	 * @param ia
	 * @return int array to Integer array
	 */
	public static Integer[] intArrayToIntegerArray(int[] ia) {
		return Arrays.stream(ia).boxed().toArray(Integer[]::new);
	}

	/**
	 * @param ia
	 * @return Integer array to int array
	 */
	public static int[] integerArrayToIntArray(Integer[] ia) {
		return Arrays.stream(ia).mapToInt(Integer::intValue).toArray();
	}

	/**
	 * @param ia
	 * @return integer array to list
	 */
	public static List<Integer> integerArrayAsList(Integer[] ia) {
//		return Arrays.asList(ia); //Unable to change list
		return new ArrayList<Integer>(Arrays.asList(ia)); // able to change list
	}

	/**
	 * @param ia
	 * @return int array to list
	 */
	public static List<Integer> intArrayAsList(int[] ia) {
		List<Integer> list = new ArrayList<>();
		for (int n : ia) {
			list.add(n);
		}
		return list;
//		return Arrays.stream(ia).boxed().collect(Collectors.toList());
	}

	/**
	 * @param list
	 * @return list to int array
	 */
	public static int[] listToIntArray(List<Integer> list) {
		int[] res = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
//		return list.stream().mapToInt(Integer::intValue).toArray();
	}

	/**
	 * @param set
	 * @return
	 */
	public static int[] toIntArray(Set<Integer> set) {
		int[] arr = set.stream().mapToInt(Integer::intValue).toArray();
		Arrays.sort(arr);
		return arr;
	}

	/**
	 * @param list
	 * @return list to integer array
	 */
	public static Integer[] listToIntegerArray(List<Integer> list) {
		Integer[] arr = list.toArray(Integer[]::new);
		Arrays.sort(arr);
		return arr;
	}

	/**
	 * @param la
	 * @return Long array to long array
	 */
	public static long[] longArrayUnboxed(Long[] la) {
		long[] arr = Arrays.stream(la).mapToLong(Long::longValue).toArray();
		Arrays.sort(arr);
		return arr;
	}

	/**
	 * @param la
	 * @return long array to Long array
	 */
	public static Long[] longArrayBoxed(long[] la) {
		return Arrays.stream(la).boxed().toArray(Long[]::new);
	}

	/**
	 * @param la
	 * @return long array to list
	 */
	public static List<Long> longArrayToList(long[] la) {
		return Arrays.stream(la).boxed().collect(Collectors.toList());
	}

	/**
	 * @param la
	 * @return Long array to list
	 */
	public static List<Long> longArrayToList(Long[] la) {
		return new ArrayList<Long>(Arrays.asList(la));
	}

	/**
	 * @param list
	 * @return list to long array
	 */
	public static long[] listToLongArray(List<Long> list) {
		long[] arr = list.stream().mapToLong(Long::longValue).toArray();
		Arrays.sort(arr);
		return arr;
	}

	/**
	 * @param list
	 * @return list to integer array
	 */
	public static Long[] listToObjectLongArray(List<Long> list) {
		return list.toArray(Long[]::new);
	}

	/**
	 * @param ia
	 * @return int array to long array
	 */
	public static long[] intArrayToLongArray(int[] ia) {
		long[] arr = Arrays.stream(ia).asLongStream().toArray();
		Arrays.sort(arr);
		return arr;
	}

	/**
	 * @param la
	 * @return long array to int array
	 */
	public static int[] longArrayToIntArray(long[] la) {
		int[] arr = Arrays.stream(la).mapToInt(i -> (int) i).toArray();
		Arrays.sort(arr);
		return arr;
	}

	/**
	 * @param sa
	 * @return string array to list
	 */
	public static List<String> stringArrayToList(String[] sa) {
		return new ArrayList<String>(Arrays.asList(sa));
	}

	/**
	 * @param list
	 * @return list to string array
	 */
	public static String[] listToStringArray(List<String> list) {
		return list.toArray(String[]::new);
	}

	/**
	 * <pre>
	 * 방법 1: 이중 For 루프 사용 (추천, 성능 최적)
	 *  가장 빠르고 메모리 효율적인 표준 방법입니다.
	 *   새 배열의 행과 열 인덱스를 원본과 반대로 매핑합니다.
	 * </pre>
	 *
	 * @param matrix
	 * @return
	 */
	public static int[][] transpose(int[][] matrix) {
		if (matrix == null || matrix.length == 0) {
			return new int[0][0];
		}

		int r = matrix.length;
		int c = matrix[0].length;

		// 💡 r x c 배열을 c x r 배열로 생성
		int[][] transposed = new int[c][r];

		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				transposed[j][i] = matrix[i][j]; // 💡 행과 열을 스왑
			}
		}
		return transposed;
	}

	/**
	 * <pre>
	 * 방법 2: Java Stream 사용 (함수형 스타일)
	 * 코드를 함수형 스타일로 작성하고 싶을 때 사용하는 방법입니다.
	 *  인덱스 스트림을 열(c) 기준으로 먼저 생성하여 변환합니다.
	 * </pre>
	 *
	 * @param matrix
	 * @return
	 */
	public static int[][] transposeWithStream(int[][] matrix) {
		if (matrix == null || matrix.length == 0)
			return new int[0][0];

		int r = matrix.length;
		int c = matrix[0].length;

		// 💡 열(c) 인덱스를 기준으로 스트림을 시작하여 변환
		return IntStream.range(0, c).mapToObj(j -> IntStream.range(0, r).map(i -> matrix[i][j]).toArray())
				.toArray(int[][]::new);
	}

	/**
	 * <pre>
	 * 값이 0이면 0, 아니면 1로 된 문자열, size = arr.length
	 * 
	 * use StringBuilder - 성능 최적화
	 * </pre>
	 *
	 * @param arr
	 * @return
	 */
	public static String toStringBin(int[] arr) {
		return toStringBin(arr, arr.length);
	}

	/**
	 * <pre>
	 * 값이 0이면 0, 아니면 1로 된 문자열
	 * 
	 * use StringBuilder - 성능 최적화
	 * </pre>
	 *
	 * @param arr
	 * @param size
	 * @return
	 */
	public static String toStringBin(int[] arr, int size) {
		if (Str.isEmpty(arr)) {
			return "";
		}
		StringBuilder sb = new StringBuilder(size);
		for (int i = 0; i < size; i++) {
			sb.append(arr[i] == 0 ? "0" : "1");
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * 값이 0이면 0, 아니면 1로 된 문자열, size = arr.length
	 * 
	 * use Arrays.stream - 간결함
	 * </pre>
	 *
	 * @param arr
	 * @return
	 */
	public static String toStringBinStream(int[] arr) {
		return toStringBinStream(arr, arr.length);
	}

	/**
	 * <pre>
	 * 값이 0이면 0, 아니면 1로 된 문자열
	 * 
	 * use Arrays.stream - 간결함
	 * </pre>
	 *
	 * @param arr
	 * @param size
	 * @return
	 */
	public static String toStringBinStream(int[] arr, int size) {
		if (Str.isEmpty(arr)) {
			return "";
		}
		return Arrays.stream(arr).limit(size).mapToObj(val -> val == 0 ? "0" : "1").collect(Collectors.joining());
	}

	/**
	 * <pre>
	 * 값이 0이면 0, 아니면 1로 된 문자열, size = arr.length
	 * 
	 * use Arrays.stream - 간결함 - 추천
	 * </pre>
	 *
	 * @param arr
	 * @return
	 */
	public static String toStringBinStream2(int[] arr) {
		return toStringBinStream2(arr, arr.length);
	}

	/**
	 * <pre>
	 * 값이 0이면 0, 아니면 1로 된 문자열
	 * 
	 * use Arrays.stream - 간결함 - 추천
	 * </pre>
	 *
	 * @param arr
	 * @param size
	 * @return
	 */
	public static String toStringBinStream2(int[] arr, int size) {
		if (Str.isEmpty(arr)) {
			return "";
		}
		return Arrays.stream(arr).mapToObj(val -> val == 0 ? "0" : "1").collect(Collector.of(
				() -> new StringBuilder(size), StringBuilder::append, StringBuilder::append, StringBuilder::toString));
	}

	/**
	 * <pre>
	 * 값이 0이면 0, 아니면 1로 된 문자열, size = arr.length
	 * 
	 * use Arrays.stream - 간결함 - 추천
	 * 
	 * 만약 결과를 List로 수집하는데, 이 리스트의 초기 용량(Capacity)을 원본 배열 크기로 맞추고 싶다면 ArrayList 생성자를 전달합니다.
	 * </pre>
	 *
	 * @param arr
	 * @return
	 */
	public static List<String> toStringBinStream3(int[] arr) {
		return toStringBinStream3(arr, arr.length);
	}

	/**
	 * <pre>
	 * 값이 0이면 0, 아니면 1로 된 문자열
	 * 
	 * use Arrays.stream - 간결함 - 추천
	 * 
	 * 만약 결과를 List로 수집하는데, 이 리스트의 초기 용량(Capacity)을 원본 배열 크기로 맞추고 싶다면 ArrayList 생성자를 전달합니다.
	 * </pre>
	 *
	 * @param arr
	 * @param size
	 * @return
	 */
	public static List<String> toStringBinStream3(int[] arr, int size) {
		if (Str.isEmpty(arr)) {
			return new ArrayList<>();
		}
		return Arrays.stream(arr).mapToObj(val -> val == 0 ? "0" : "1")
				.collect(Collectors.toCollection(() -> new ArrayList<>(size)));
	}

	/*
	 * List<String> list = Arrays.stream(arr) .mapToObj(val -> val > 0 ? "1" : "0")
	 * .collect(Collectors.toCollection(() -> new ArrayList<>(arr.length))); // 💡
	 * ArrayList 초기 용량을 arr.length로 지정
	 */
	/**
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String toStringStringArray(String[] arr, String separator) {
		return String.join(separator, arr);
	}

	/**
	 * separator = space " "
	 * 
	 * @param arr
	 * @return
	 */
	public static String toStringStringArray(String[] arr) {
		return toStringStringArray(arr, DEFAULT_SEPARATOR);
	}

	/**
	 * <pre>
	 * use Arrays.toString
	 * 배열 요소를 String 병합하기 전에 객체로 변환합니다.
	 * 
	 * 내장된 Arrays.toString(arr) 메서드는 사용자 지정 구분 기호를 허용하지 않습니다.
	 * 쉼표와 대괄호로 이루어진 하드코딩된 형식을 강제로 사용합니다.
	 * </pre>
	 * 
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String toStringIntArray1(int[] arr, String separator) {
		return Arrays.toString(arr).replace(",", "").replace("[", "").replace("]", "").replace(" ", separator);
	}

	/**
	 * <pre>
	 * use Arrays.toString
	 * 배열 요소를 String 병합하기 전에 객체로 변환합니다.
	 * 
	 * 내장된 Arrays.toString(arr) 메서드는 사용자 지정 구분 기호를 허용하지 않습니다.
	 * 쉼표와 대괄호로 이루어진 하드코딩된 형식을 강제로 사용합니다.
	 * 
	 * separator = space " "
	 * </pre>
	 * 
	 * @param arr
	 * @return
	 */
	public static String toStringIntArray1(int[] arr) {
		return toStringIntArray1(arr, DEFAULT_SEPARATOR);
	}

	/**
	 * <pre>
	 * use StringBuilter 빠르다.
	 * </pre>
	 *
	 * @param arr
	 * @return
	 */
	public static String toStringIntArray(int[] arr) {
		return toStringIntArray(arr, DEFAULT_SEPARATOR);
	}

	/**
	 * <pre>
	 * use StringBuilter 빠르다.
	 * </pre>
	 *
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String toStringIntArray(int[] arr, String separator) {
		if (Str.isEmpty(arr)) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		sb.append(arr[0]);

		for (int i = 1; i < arr.length; i++) {
			sb.append(separator).append(arr[i]);
		}

		return sb.toString();
	}

	/**
	 * <pre>
	 * use Stream - 권장
	 * </pre>
	 *
	 * @param arr
	 * @return
	 */
	public static String toStringIntArray2(int[] arr) {
		return toStringIntArray2(arr, DEFAULT_SEPARATOR);
	}

	/**
	 * <pre>
	 * use Stream - 권장
	 * </pre>
	 *
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String toStringIntArray2(int[] arr, String separator) {
		if (Str.isEmpty(arr)) {
			return "";
		}
		return Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(separator));
	}

	/**
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String toStringIntegerArray(Integer[] arr, String separator) {
		return Arrays.toString(arr).replace(",", "").replace("[", "").replace("]", "").replace(" ", separator);
	}

	/**
	 * separator = space " "
	 * 
	 * @param arr
	 * @return
	 */
	public static String toStringIntegerArray(Integer[] arr) {
		return toStringIntegerArray(arr, DEFAULT_SEPARATOR);
	}

	/**
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String toStringLongArray(long[] arr, String separator) {
		return Arrays.toString(arr).replace(",", "").replace("[", "").replace("]", "").replace(" ", separator);
	}

	/**
	 * separator = space " "
	 * 
	 * @param arr
	 * @return
	 */
	public static String toStringLongArray(long[] arr) {
		return toStringLongArray(arr, DEFAULT_SEPARATOR);
	}

	/**
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String toStringLongArray(Long[] arr, String separator) {
		return Arrays.toString(arr).replace(",", "").replace("[", "").replace("]", "").replace(" ", separator);
	}

	/**
	 * separator = space " "
	 * 
	 * @param arr
	 * @return
	 */
	public static String toStringLongArray(Long[] arr) {
		return toStringLongArray(arr, DEFAULT_SEPARATOR);
	}

	/**
	 * @param arr
	 * @return
	 */
	public static String toStringDoubleArray(double[] arr) {
		return toStringDoubleArray(arr, DEFAULT_SEPARATOR);
	}

	/**
	 * @param arr
	 * @param dEFAULT_SEPARATOR2
	 * @return
	 */
	public static String toStringDoubleArray(double[] arr, String separator) {
		return Arrays.toString(arr).replace(",", "").replace("[", "").replace("]", "").replace(" ", separator);
	}

	/**
	 * @param arr
	 * @return
	 */
	public static String toStringDoubleArray(Double[] arr) {
		return toStringDoubleArray(arr, DEFAULT_SEPARATOR);
	}

	/**
	 * @param arr
	 * @param dEFAULT_SEPARATOR2
	 * @return
	 */
	public static String toStringDoubleArray(Double[] arr, String separator) {
		return Arrays.toString(arr).replace(",", "").replace("[", "").replace("]", "").replace(" ", separator);
	}

	/**
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String toStringIntegerList(List<Integer> list, String separator) {
		return StringUtils.join(list, separator);
	}

	/**
	 * separator = space " "
	 * 
	 * @param list
	 * @return
	 */
	public static String toStringIntegerList(List<Integer> list) {
		return toStringIntegerList(list, DEFAULT_SEPARATOR);
	}

	/**
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String toStringLongList(List<Long> list, String separator) {
//		return Joiner.on(separator).join(list);
		return StringUtils.join(list, separator);
	}

	/**
	 * separator = space " "
	 * 
	 * @param list
	 * @return
	 */
	public static String toStringLongList(List<Long> list) {
		return toStringLongList(list, DEFAULT_SEPARATOR);
	}

	/**
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String toStringStringList(List<String> list, String separator) {
//		return Joiner.on(separator).join(list);
		return StringUtils.join(list, separator);
	}

	/**
	 * separator = space " "
	 * 
	 * @param list
	 * @return
	 */
	public static String toStringStringList(List<String> list) {
		return toStringStringList(list, DEFAULT_SEPARATOR);
	}

	public static void shuffle(int[] ns) {
		Random rand = Rnd.getRandom();
		for (int i = ns.length - 1; 0 < i; i--) {
			int j = rand.nextInt(i + 1);
			int temp = ns[i];
			ns[i] = ns[j];
			ns[j] = temp;
		}
	}
}
