/**
 * 
 */
package kr.pe.util.io.que.v1;

/**
 * 
 */
public class FileFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8770265999990071446L;

	/**
	 * 
	 */
	public FileFormatException() {
		super();
	}

	/**
	 * @param message
	 */
	public FileFormatException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public FileFormatException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FileFormatException(String message, Throwable cause) {
		super(message, cause);
	}
}
