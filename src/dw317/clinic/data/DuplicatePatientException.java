package dw317.clinic.data;

/**
 * Signals that the provided RAMQ exists already.
 * 
 * @author@author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 */
public class DuplicatePatientException extends Exception {
	private static final long serialVersionUID = 42031768871L;

	/**
	 * 
	 */
	public DuplicatePatientException() {
		super("The provided RAMQ already exists.");
	}

	/**
	 * 
	 * @param message
	 */
	public DuplicatePatientException(String message) {
		super(message);
	}
}
