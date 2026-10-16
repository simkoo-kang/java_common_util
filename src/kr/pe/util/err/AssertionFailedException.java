/**
 * AssertionFailedError.java
 */
package kr.pe.util.err;

/**
 * PC 2026. 4. 8. 오후 9:10:04
 */
public class AssertionFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3877980362864564178L;

	/**
	 * 
	 */
	public AssertionFailedException() {
		super();
	}

	/**
	 * @param message
	 */
	public AssertionFailedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AssertionFailedException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AssertionFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
