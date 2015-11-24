/**
 * Defines a Ramq type object
 */
package group4.clinic.business;

import java.time.LocalDate;

import dw317.lib.Gender;
import dw317.lib.Name;
import java.io.Serializable;


/**
 * This class creates a Ramq objects that holds one field value:
 * 
 * ramq			A RAMQ ID
 * 
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 * 
 */
public class Ramq implements Serializable, Comparable<Ramq> {
	private String ramq;
	private static final long serialVersionUID = 42031768871L;
	/**
	 * Single parameter constructor of the Ramq class.
	 * Only uses a RAMQ ID String to be initialized.
	 * If the RAMQ ID is invalid, an IllegalArgumentException
	 *  will be thrown.
	 * 
	 * @param ramq		A RAMQ ID
	 */
	public Ramq(String ramq) {
		if (!validateRamqNumeric(ramq))
			throw new IllegalArgumentException(ramq + " is an invalid RAMQ ID.");
		this.ramq = ramq;
	}
	
	/**
	 * Two parameter constructor of the Ramq class.
	 * Uses a RAMQ ID String and a Name Object to be initialized. 
	 * The name is used to validate the RAMQ ID.
	 * If the RAMQ is invalid, an IllegalArgumentException will be thrown.
	 *   
	 * @param ramq		A RAMQ ID
	 * @param name		A Name Object
	 */
	public Ramq(String ramq, Name name) {
		if(!validateRamq(ramq, name))
			throw new IllegalArgumentException
			(ramq + " is an invalid RAMQ code. \n"
					+ "The name " + name.getFullName() 
					+ " does not correspond to this code.");
		this.ramq = ramq;	
	}
	
	/**
	 * Compares the ramq String between two instances of Ramq.
	 * 
	 * @param otherRamq			Another Ramq object
	 * @return a negative integer, zero, or a positive integer as the 
	 * specified String is greater than, equal to, or less than this 
	 * String, ignoring case considerations.
	 */
	@Override
	public int compareTo(Ramq otherRamq) {
		return ramq.compareToIgnoreCase(otherRamq.ramq);
	}
	
	/** 
	 * This overridden equals() method tests equality between two
	 * Ramq objects. They are considered equal if the ramq String
	 * is the same. 
	 * 
	 *  @param otherObj			The other Object to be compared
	 *  @return true if both Ramq Objects are the same Object or
	 *  both have an equal ramq String. 
	 */
	@Override
	public boolean equals(Object otherObj) {
		if ((otherObj == null) || (this.getClass() != otherObj.getClass())) 
			return false;
		
		//is this the same object
		if (otherObj == this) 
			return true;
		
		Ramq otherRamq = (Ramq)otherObj;
		
		return ramq.equalsIgnoreCase(otherRamq.ramq);		
	}
	
	/**
	 * Gets the birthdate in Year-Month-Day from a RAMQ ID.
	 * For the moment, a person cannot be older than 100 years old.
	 * 
	 * @return The birthdate

	 */
	public LocalDate getBirthdate() {
		//temp
		int year;
		int month;
		int dayOfMonth;
		LocalDate birthdate;
		year = Integer.parseInt(ramq.substring(4, 6));
		month = Integer.parseInt(ramq.substring(6, 8));
		dayOfMonth = Integer.parseInt(ramq.substring(8, 10));
		
		//cannot be older than 100 years old
		if(year < 15)
			year = 2000 + year;
		else
			year = 1900 + year;
		
		//if female
		if(month > 12)
			month = month - 50;
		
			birthdate = LocalDate.of(year, month, dayOfMonth);
		return birthdate;
	}
	
	/**
	 * Returns the gender of a person.
	 * 
	 * @return The gender of the person with this RAMQ ID
	 */
	public Gender getGender() {
		
		int ramqMonthValue = Integer.parseInt(ramq.substring(6, 8));
		if(ramqMonthValue > 12)
			return Gender.FEMALE;
		else
			return Gender.MALE;
		
	}
	
	/**
	 * Returns the hash code value of the ramq string.
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		return ramq.hashCode();
	}
	
	/**
	 * Overriden toString method returns
	 * the ramq String.
	 * 
	 * @return ramq		The RAMQ ID
	 */
	@Override
	public String toString() {
		return ramq;
	}

	/**
	 * Validates a RAMQ ID by comparing it with the name that is
	 * associated with it. The first three characters of the ramq string
	 * must be equal to the first three characters of the last name and
	 * the fourth character must be equal to first letter of the first 
	 * name.
	 * 
	 * @param ramq 		The ramq string
	 * @param name		The Name object associated with the
	 * RAMQ ID.
	 * 
	 * @return true if the name corresponds to the RAMQ ID
	 */
	private static boolean validateRamq(String ramq, Name name) {
		
		
		String lastName = name.getLastName().replaceAll(" ", "").replaceAll("'", "").replaceAll("-", "");
		String firstName = name.getFirstName().replaceAll(" ", "").replaceAll("'", "").replaceAll("-", "");
		
		if (lastName.length() < 2 || firstName.length() < 2)
		{
			throw new IllegalArgumentException(lastName + " " + firstName + " has a name of length smaller than 2" );
		}
		if (name.getLastName().length() > 2)
		{
			lastName = lastName.substring(0,3); //first 3 letters
			firstName = firstName.substring(0,1);
		} else {
			firstName = firstName.substring(0,2);
		}
		String ramqName = lastName + firstName;
		if (ramqName.compareToIgnoreCase(ramq.substring(0,4)) != 0)
			return false;
			
		return validateRamqNumeric(ramq);
	}
	
	/**
	 * Validates the ramq string by making sure its length is
	 * twelve characters, the first four characters are alphabetic
	 * and the rest are digits. Checks if the values for the month 
	 * and day of the birthdate are valid.
	 * 
	 * @param ramq 		The ramq string to be validated
	 * @return true if ramq string is valid
	 */
	private static boolean validateRamqNumeric(String ramq) {
		int length = 12;
		if (ramq.length() != length)
			return false;
		for (int cntr = 0; cntr < length; cntr++)
		{
			//checks if the first 4 characters are alphabetical
			if (cntr < 4 && !Character.isAlphabetic(ramq.charAt(cntr)) )
				return false; //character is not alphabetic
			
			//checks if the rest of the characters are digits
			if (cntr >= 4 && !Character.isDigit(ramq.charAt(cntr)) )
				return false; //character is not a digit	
		}
		//checks if the month value is correct
		int month = Integer.parseInt(ramq.substring(6, 8));
		
		if(month > 12)//check if female
			month = month - 50;
		
		if(!(month>= 1 && month <=12))
			return false;//invalid month of birth
		
		//checks if day value is correct
		int dayOfMonth = Integer.parseInt(ramq.substring(8, 10));
		if(dayOfMonth > 31)
			return false;//invalid day of birth
		
		return true;
	}
	
	
}
