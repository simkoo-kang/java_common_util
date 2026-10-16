/**
 * XorShiftRandom.java
 */
package kr.pe.util.rnd;

import java.util.Random;

/**
 * <pre>
 * 3. 특정 알고리즘 직접 구현 (XorShift 예시)
 *   자바 내장 라이브러리를 쓰지 않고,
 *   비트 연산(XOR, Shift)만을 이용해 빠르고 균등한 난수를 만드는
 *   XorShift 알고리즘을 자바로 직접 구현한 형태입니다.
 * 
 * PC  2026. 8. 26. 오후 7:44:15
 * </pre>
 */
public class XorShiftRandom extends Random {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3194128788630310047L;

	private long seed;

	/**
	 * default seed = System.nanoTime()
	 */
	public XorShiftRandom() {
		super();
		// 0이 아닌 초기 시드 값 설정
		this.seed = System.nanoTime();
	}

	/**
	 * @param seed
	 */
	public XorShiftRandom(long seed) {
		super(seed);
		this.seed = seed;
	}

	/**
	 * XorShift 64비트 알고리즘
	 * 
	 * @param max
	 * @return
	 */
	public int nextInt(int max) {
		// XorShift 64비트 알고리즘 수식
		this.seed ^= (this.seed << 13);
		this.seed ^= (this.seed >>> 7);
		this.seed ^= (this.seed << 17);

		// 양수로 변환 후 범위 내 지정
		int val = (int) (this.seed & Long.MAX_VALUE) % max;
		return val;
	}
}
