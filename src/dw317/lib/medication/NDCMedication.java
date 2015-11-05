/**
 * Defines a NDCMedication type.
 */
package dw317.lib.medication;

/**
 * This class creates a NDCMedication object holding two field values: 
 * 
 * number	The medication number 
 * name 	The medication name
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public final class NDCMedication extends AbstractMedication {
	private static final long serialVersionUID = 42031768871L;

	public NDCMedication(String number, String name) {
		super(Scheme.NDC, validateNumber(number), name);
	}

	/**
	 * This methods validate the medication number according to the NDC scheme.
	 * 
	 * @param number 		The medication number to be validated
	 * @return The validated medication number
	 * @throws IllegalArgumentException
	 *             if the number doesn't exist, doesn't have the correct length
	 *             or contains an invalid character
	 */
	private static String validateNumber(String number) {
		int lengthFormat = 12;
		int hyphenCount = 0;
		if (number == null)
			throw new IllegalArgumentException("The number input is null");

		number = number.trim();
		for (int i = 0; i< number.length(); i++)
		{
			if (number.charAt(i) == '-')
			{
				hyphenCount++;
			}
			if (!Character.isDigit(number.charAt(i)) && number.charAt(i) != '-')
			{
				throw new IllegalArgumentException(number + " has non-numeric characters");
			}
		}
		if (hyphenCount != 2)
		{
			throw new IllegalArgumentException(number + " is not a valid NDC number");
		}
			
		if (lengthFormat != number.length())
			throw new IllegalArgumentException(number
					+ " does not have the proper length");
		return number;
	}
}
