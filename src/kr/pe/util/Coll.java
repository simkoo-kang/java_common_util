/**
 * Coll.java
 */
package kr.pe.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 19.
 */
public final class Coll {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("Nepal", "Kathmandu");
		map.put("United States", "Washington");
		map.put("India", "New Delhi");
		map.put("England", "London");
		map.put("Australia", "Canberra");

		Map<String, String> sortKey = sortMapByKey(map);
		String str = mapToString(sortKey, "\t");
		System.out.println(str);

		System.out.println();
		Map<String, String> sortValue = sortMapByValue(map);
		str = mapToString(sortValue);
		System.out.println(str);
	}

	/**
	 * <pre>
	 * map 에 k 값을 체크하여 +1 해서 반환
	 * </pre>
	 * 
	 * @param <K>
	 * @param map
	 * @param k
	 * @return
	 */
	public static <K> Map<K, Integer> plus(Map<K, Integer> map, K k) {
		Integer count = map.get(k);
		if (count == null) {
			count = 0;
		}
		count++;
		map.put(k, count);
		return map;
	}

	/**
	 * <pre>
	 * 리스트에서 [any] 문자열을 포함한 문자열이 있는 지 체크
	 * 단순히 같은 문자열을 포함하는 지 체크는 str.contains(any) 사용
	 * </pre>
	 * 
	 * @param list
	 * @param any
	 * @return
	 */
	public static boolean anyMatch(List<String> list, String any) {
		return list.stream().anyMatch(str -> str.contains(any));
	}

	/**
	 * <pre>
	 * 리스트에서 [any] 문자열을 포함한 문자열이 있는 지 체크
	 * 단순히 같은 문자열을 포함하는 지 체크는 str.contains(any) 사용
	 * </pre>
	 * 
	 * @param list
	 * @param any
	 * @return
	 */
	public static boolean anyMatchIgnoreCase(List<String> list, String any) {
		return list.stream().anyMatch(str -> str.toLowerCase().contains(any.toLowerCase()));
	}

	/**
	 * <pre>
	 * sub list from index
	 * 
	 * if index < 0 - from size - index, 즉 뒤에서부터 index 개
	 * 
	 * PC 2026. 9. 2. 오후 1:39:59
	 * </pre>
	 *
	 * @param <E>
	 * @param list
	 * @param fromIndex
	 * @return
	 */
	public static <E> List<E> getSubList(List<E> list, int fromIndex) {
		int startIndex = (fromIndex < 0) ? list.size() + fromIndex : fromIndex;
		return getSubList(list, startIndex, list.size());
	}

	/**
	 * <pre>
	 * sub list
	 * 
	 * PC 2026. 9. 2. 오후 1:37:52
	 * </pre>
	 *
	 * @param <E>
	 * @param list
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	public static <E> List<E> getSubList(List<E> list, int fromIndex, int toIndex) {
		List<E> sublist = new ArrayList<>();
		int maxIndex = Math.min(toIndex, list.size());
		int startIndex = Math.max(fromIndex, 0);
		for (int i = startIndex; i < maxIndex; i++) {
			sublist.add(list.get(i));
		}
		return sublist;
	}

	/**
	 * <pre>
	 * 맵에 값이 있으면 value 를 더하고, 없으면 추가한다.
	 * </pre>
	 *
	 * @param map
	 * @param value
	 */
	public static Map<Integer, Integer> addValue(Map<Integer, Integer> map, int value) {
		Integer count = map.get(value);
		if (count == null) {
			count = 0;
		}
		count++;
		map.put(value, count);
		return map;
	}

	/**
	 * 리스트에서 excludeArray에 포함된 숫자는 제외하고 반환.
	 * 
	 * @param list
	 * @param excludeArray
	 * @return
	 */
	public static List<Integer> exclude(List<Integer> list, int[] excludeArray) {
		// 1. 제외할 배열을 HashSet으로 변환(검색 속도를 극대화)
		Set<Integer> exSet = new HashSet<>(excludeArray.length);
		for (int n : excludeArray) {
			exSet.add(n);
		}

		// 2. 결과를 담을 리스트 (내부 배열 재할당 오버헤드 방지용 크기 지정)
		List<Integer> result = new ArrayList<>(list.size());

		// 3. HashSet에 포함되지 않은 값만 리스트에 수집
		for (int n : list) {
			if (!exSet.contains(n)) {
				result.add(n);
			}
		}
		return result;
	}

	/**
	 * <pre>
	 * 1. [가장 추천] LinkedHashSet 사용 (순서 유지 + 고속 처리)
	 *  중복을 허용하지 않는 HashSet 계열을 사용하되, LinkedHashSet을 쓰면 데이터가 처음 들어온 순서가 그대로 유지됩니다.
	 *  대용량 데이터에서도 O(N)의 속도로 가장 빠르고 효율적입니다.
	 * </pre>
	 * 
	 * @param <E>
	 * @param dupList
	 * @return
	 */
	public static <E> List<E> removeDuplicates(List<E> dupList) {
		return new ArrayList<>(new LinkedHashSet<>(dupList));
	}

	/**
	 * <pre>
	 * 2. Stream API 사용 (가독성 위주)
	 *  Java 8 이상을 사용 중이고 코드의 간결함이 중요하다면 distinct() 메서드를 활용할 수 있습니다.
	 *  내부적으로 LinkedHashSet과 유사하게 동작하므로 순서가 유지됩니다.
	 * </pre>
	 * 
	 * @param <E>
	 * @param dupList
	 * @return
	 */
	public static <E> List<E> removeDuplicates2(List<E> dupList) {
		return dupList.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * <pre>
	 * 3. TreeSet 사용 (중복 제거 + 오름차순 정렬까지 동시에)
	 *  중복을 제거하는 것과 동시에 데이터를 크기 순서대로 정렬하고 싶다면 TreeSet을 사용하면 됩니다. (순서는 무시되고 오름차순 정렬됨)
	 * </pre>
	 * 
	 * @param <E>
	 * @param dupList
	 * @return
	 */
	public static <E> List<E> removeDuplicates3(List<E> dupList) {
		return new ArrayList<>(new TreeSet<>(dupList));
	}

	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	public static <K, V> String mapToString(Map<K, V> map) {
		return mapToString(map, Str.TAB, Str.CRLF);
	}

	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param innerSign
	 * @return
	 */
	public static <K, V> String mapToString(Map<K, V> map, String innerSign) {
		return mapToString(map, innerSign, Str.CRLF);
	}

	/**
	 * <pre>
	 * mapToString(map, "^", " * ") -> 2^10 * 3^2
	 * mapToString(map, "^", "\r\n") -> 2^10
	 *                                  3^2
	 * mapToString(map, "-", "\t") -> 2-10 [tab] 3-2
	 * </pre>
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param innerSign
	 * @param lineSep
	 * @return
	 */
	public static <K, V> String mapToString(Map<K, V> map, String innerSign, String lineSep) {
		return map.entrySet().stream().map(e -> e.getKey() + innerSign + e.getValue())
				.collect(Collectors.joining(lineSep));
	}

	/**
	 * mapToString(map, "^", " * ", "{ ", " }") -> { 2^10 * 3^2 }
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param innerSign
	 * @param lineSep
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static <K, V> String mapToString(Map<K, V> map, String innerSign, String lineSep, String prefix,
			String suffix) {
		return map.entrySet().stream().map(e -> e.getKey() + innerSign + e.getValue())
				.collect(Collectors.joining(lineSep, prefix, suffix));
	}

	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param order
	 * @return
	 */
	public static <K extends Comparable<K>, V> LinkedHashMap<K, V> sortMapByKey(Map<K, V> map, boolean order) {
		if (order) {
			return sortMapByKey(map);
		}
		return sortMapByKeyReverseOrder(map);
	}

	/**
	 * @param <K> extends Comparable<K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unused")
	public static <K extends Comparable<K>, V> LinkedHashMap<K, V> sortMapByKey(Map<K, V> map) {
//		List<Map.Entry<K, V>> entries = new LinkedList<>(map.entrySet());
//		Collections.sort(entries, (o1,o2)->o1.getKey().compareTo(o2.getKey()));

//		List<Map.Entry<K, V>> entries = map.entrySet().stream().sorted(Map.Entry.comparingByKey())
//				.collect(Collectors.toList());
//		LinkedHashMap<K, V> result = new LinkedHashMap<>();
//		for (Map.Entry<K, V> entry : entries) {
//			result.put(entry.getKey(), entry.getValue());
//		}
//		return result;
		return map.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
	}

	/**
	 * sort reverse
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unused")
	public static <K extends Comparable<K>, V> LinkedHashMap<K, V> sortMapByKeyReverseOrder(Map<K, V> map) {
		return map.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
	}

	/**
	 * @param <K>
	 * @param <V> extends Comparable<V>
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unused")
	public static <K, V extends Comparable<V>> LinkedHashMap<K, V> sortMapByValue(Map<K, V> map, boolean order) {
		if (order) {
			return sortMapByValue(map);
		}
		return sortMapByValueReverseOrder(map);
	}

	/**
	 * @param <K>
	 * @param <V> extends Comparable<V>
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unused")
	public static <K, V extends Comparable<V>> LinkedHashMap<K, V> sortMapByValue(Map<K, V> map) {
		return map.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
	}

	/**
	 * @param <K>
	 * @param <V> extends Comparable<V>
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unused")
	public static <K, V extends Comparable<V>> LinkedHashMap<K, V> sortMapByValueReverseOrder(Map<K, V> map) {
		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
	}

	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	public static <K extends Comparable<K>, V> List<K> sortByKey(Map<K, V> map) {
		List<K> list = new ArrayList<K>(map.keySet());
		Collections.sort(list);
		return list;
	}

	/**
	 * @param map
	 * @return
	 */
	public static <K, V extends Comparable<V>> List<K> sortByValueKeyList(Map<K, V> map) {
		List<K> list = new ArrayList<>(map.keySet());
		list.sort(new Comparator<K>() {
			@Override
			public int compare(K o1, K o2) {
				return map.get(o1).compareTo(map.get(o2));
			}
		});
		return list;
	}

	/**
	 * @param map
	 * @param n
	 */
	public static <K> void countUpInMap(Map<K, Integer> map, K n) {
		Integer count = map.get(n);
		if (count == null) {
			count = 0;
		}
		count++;
		map.put(n, count);
	}
}
