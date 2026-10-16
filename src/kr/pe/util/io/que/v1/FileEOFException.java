/**
 * 
 */
package kr.pe.util.io.que.v1;

/**
 * 
 */
public class FileEOFException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8101741350835079465L;

	/**
	 * 
	 */
	public FileEOFException() {
		super();
	}

	/**
	 * @param message
	 */
	public FileEOFException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public FileEOFException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FileEOFException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

}
