/**
 * ArrIterator.java
 */
package kr.pe.util.iter;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 17.
 */
public class ArrIterator<T> implements Iterator<T> {

	private final T[] array;
	private int cursor = 0; // 현재 가리키고 있는 인덱스 위치

	/**
	 * <pre></pre>
	 * 
	 * @param array
	 */
	public ArrIterator(T[] array) {
		super();
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		// 커서가 배열의 길이보다 작으면 다음 요소가 존재하는 것임
		return this.cursor < this.array.length;
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException("더 이상 가져올 요소가 없습니다.");
		}
		// 현재 위치의 요소를 가져온 후, 커서를 1 증가시킴
		return this.array[this.cursor++];
	}
}
