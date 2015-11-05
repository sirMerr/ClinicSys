/**
 * 
 */
package group4.clinic.business;

/**
 * Priority enum class.
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public enum Priority {
	NOTASSIGNED (0),
	REANIMATION (1),
	VERYURGENT (2),
	URGENT (3),
	LESSURGENT (4),
	NOTURGENT (5);

	private int code;
	private Priority(int code) {
		this.code = code;
	}
	/*
	 * Gets code
	 * 
	 * @return int
	 */
	public int getCode() {
		return this.code;
	}
}
