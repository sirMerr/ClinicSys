/**
 * 
 */
package dw317.clinic.data;

/**
 * Signals the provided Visit object is non existing
 * 
 * @author @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 */
public class NonExistingVisitException extends Exception {

	private static final long serialVersionUID = 42031768871L;
	/**
	 * 
	 */
	public NonExistingVisitException() {
		super("The provided Visit object does not exist");
	}
	/**
	 * 
	 * @param message
	 */
	public NonExistingVisitException(String message) {
		super(message);
		}

}
