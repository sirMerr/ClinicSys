/**
 * 
 */
package dw317.clinic.data;

/**
 * Signals that the provided Patient object does not exist
 * 
 * @author @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 */
public class NonExistingPatientException extends Exception {
	private static final long serialVersionUID = 42031768871L;

	/**
	 * 
	 */
	public NonExistingPatientException() {
		super("The provided Patient object is does not exist");
	}
	/**
	 * 
	 * @param message
	 */
	public NonExistingPatientException(String message) {
		super(message);
	}

}
