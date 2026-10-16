/**
 * Iter.java
 */
package kr.pe.util.iter;

/**
 * <pre>
 * 
 * PC  2026. 9. 7. 오전 8:47:55
 * </pre>
 */
public interface Iter {

	public boolean find(String line);

	public boolean hasMore();

	public String next();

	public int size();

	public String matchStringAll();
}
