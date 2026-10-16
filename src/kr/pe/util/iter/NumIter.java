/**
 * NumIter.java
 */
package kr.pe.util.iter;

/**
 * <pre>
 * 
 * PC  2026. 9. 7. 오전 8:21:55
 * </pre>
 */
public class NumIter extends StrIter {

	private static String numPattern = "[\\d]+";

	/**
	 * <pre></pre>
	 */
	public NumIter() {
		super(numPattern);
	}

	public static void main(String[] args) {
		String line = "1등 28669084128원 16 1791817758원 6개번호 일치 2등 4778180706원 78 61258727원 5개번호 일치 + 보너스번호 일치 3등 4778182080원 3087 1547840원 5개번호 일치 4등 7760100000원 155202 50000원 4개번호 일치 5등 13097115000원 2619423 5000원 3개번호 일치 ";
		NumIter niter = new NumIter();
		if (niter.find(line)) {
			String ns = null;
			while ((ns = niter.next()) != null) {
				System.out.println(ns + " " + niter.size());
			}
		}
		line = "제 1240회 추첨 결과 2026.09.05 추첨 당첨번호 11 13 19 20 31 44 보너스번호 27 당첨번호 보너스번호";
		if (niter.find(line)) {
			String ns = null;
			while ((ns = niter.next()) != null) {
				System.out.println(ns + " " + niter.size());
			}
		}
//		while (niter.find(line) && index++ < 30) {
//			System.out.println(niter.next());
//		}
	}
}
