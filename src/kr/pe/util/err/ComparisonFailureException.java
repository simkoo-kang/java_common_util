/**
 * ComparisonFailure.java
 */
package kr.pe.util.err;

/**
 * PC 2026. 4. 8. 오후 9:21:38
 */
public class ComparisonFailureException extends AssertionFailedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2475317212309284112L;

	private static final int MAX_CONTEXT_LENGTH = 20;

	private String fExpected;
	private String fActual;

	/**
	 * Constructs a comparison failure.
	 *
	 * @param expected the expected string value
	 * @param actual   the actual string value
	 * @param message  the identifying message or null
	 */
	public ComparisonFailureException(String expected, String actual, String message) {
		super(message);
		this.fExpected = expected;
		this.fActual = actual;
	}

	/**
	 * Returns "..." in place of common prefix and "..." in place of common suffix
	 * between expected and actual.
	 *
	 * @see Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return new ComparisonCompactor(MAX_CONTEXT_LENGTH, this.fExpected, this.fActual).compact(super.getMessage());
	}

	/**
	 * Gets the actual string value
	 *
	 * @return the actual string value
	 */
	public String getActual() {
		return this.fActual;
	}

	/**
	 * Gets the expected string value
	 *
	 * @return the expected string value
	 */
	public String getExpected() {
		return this.fExpected;
	}
}
