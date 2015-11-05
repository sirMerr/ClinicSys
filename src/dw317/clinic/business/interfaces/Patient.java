/**
 * Patient interface
 */
package dw317.clinic.business.interfaces;

import group4.clinic.business.Ramq;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

import dw317.lib.Gender;
import dw317.lib.Name;
import dw317.lib.medication.Medication;

/**
 * Patient interface used for creating a interface object that contains a name,
 * ramq, gender, birthday, telephone number, medication and existing conditions
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public interface Patient extends Comparable<Patient>, Serializable {
	/**
	 * Gets patient's name
	 * @return Name
	 */
	Name getName(); // must return deep copy
	/**
	 * Gets patient's RAMQ number
	 * @return Ramq ramq number
	 */
	Ramq getRamq();
	/**
	 * Gets patient's gender
	 * @return Gender gender
	 */
	Gender getGender();
	/**
	 * Gets patient's birthday
	 * @return LocalDate birthday
	 */
	LocalDate getBirthday();
	/**
	 * Gets patient's telephone number
	 * @return String telephone number
	 */
	String getTelephoneNumber();
	/**
	 * Gets patient's pre-existing condition
	 * @return String condition
	 */
	String getExistingCondition();
	/**
	 * Gets patient's medication if he has one
	 * @return Optional <Medication> medication, can be null
	 */
	Optional<Medication> getMedication();
	/**
	 * Sets patient's number (possibly null)
	 * @param telephoneNumber
	 */
	public void setTelephoneNumber(Optional<String> telephoneNumber);
	/**
	 * Set patient's medication (possibly null)
	 * @param medication
	 */
	public void setMedication(Optional<Medication> medication);
	/**
	 * Set patient's existing conditions (possibly null
	 * @param ailment
	 */
	public void setExistingConditions(Optional<String> ailment);

}
