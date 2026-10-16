/**
 * FileQueueClosedException.java
 */
package kr.pe.util.io.que.v2;

/**
 * 
 * admin 2025. 4. 18.
 */
public class FileQueueClosedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1343908540563273874L;

	/**
	 * 
	 */
	public FileQueueClosedException() {
		super();
	}

	/**
	 * @param message
	 */
	public FileQueueClosedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public FileQueueClosedException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FileQueueClosedException(String message, Throwable cause) {
		super(message, cause);
	}
}
