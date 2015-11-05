/**
 * Defines a DINMedication type.
 */
package dw317.lib.medication;

/**
 * This class creates a DINMedication object holding two field values: 
 * 
 * number 	The medication number 
 * name 	The medication name
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public final class DINMedication extends AbstractMedication {

	private static final long serialVersionUID = 42031768871L;

	public DINMedication(String number, String name) {
		super(Scheme.DIN, validateNumber(number), name);
	}

	/**
	 * This method makes sure that the medication number does not contain any
	 * letters.
	 * 
	 * @param number		A medication number
	 * @return true if the medication number contains only numerics
	 */
	private static boolean validateNoLetters(String number) {
		for (int counter = 0; counter < number.length(); counter++) {
			if (!Character.isDigit(number.charAt(counter)))
				return false;
		}

		return true;
	}

	/**
	 * This methods validate the medication number according to the NDC scheme.
	 * 
	 * @param number		The medication number to be validated
	 * @return The validated medication number.
	 * @throws IllegalArgumentException
	 *             if the number doesn't exist, doesn't have the correct length
	 *             or contains an invalid character
	 */
	private static String validateNumber(String number) {
		int lengthFormat = 8;
		if (number == null)
			throw new IllegalArgumentException("The number input is null");

		number = number.trim();

		if (lengthFormat != number.length())
			throw new IllegalArgumentException(number
					+ " is not a valid DIN number");

		if (validateNoLetters(number))
			return number;
		else
			throw new IllegalArgumentException(number
					+ " is not a valid number");
	}
	
}
