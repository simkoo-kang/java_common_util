/**
 * ListIterator.java
 */
package kr.pe.util.iter;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 17.
 */
public class ListIterator<T> implements Iterator<T> {

	private final List<T> list;
	private int cursor = 0;

	/**
	 * <pre></pre>
	 * 
	 * @param list
	 */
	public ListIterator(List<T> list) {
		super();
		this.list = list;
	}

	@Override
	public boolean hasNext() {
		// 커서가 리스트의 크기보다 작으면 다음 요소가 존재하는 것임
		return this.cursor < this.list.size();
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException("더 이상 가져올 요소가 없습니다.");
		}
		// 현재 위치의 요소를 추출하고 커서를 이동
		return this.list.get(this.cursor++);
	}

}
