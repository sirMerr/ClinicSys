/**
 * Visit interface
 */
package dw317.clinic.business.interfaces;

import group4.clinic.business.Priority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Visit interface used for creating a interface object that contains a Patient,
 *  registration date time, triage date time, priority and complaints.
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public interface Visit extends Comparable<Visit>, Serializable {
	/**
	 * Method that gets the patient object and returns a deep copy
	 * 
	 * @return Patient
	 */
	Patient getPatient(); // must return deep copy
	
	/**
	 * Method that gets the registration date and time and
	 * returns it
	 * 
	 * @return LocalDateTime
	 */
	LocalDateTime getRegistrationDateAndTime();

	/**
	 * Method that gets the triage date and time where it might be null.
	 * 
	 * @return Optional <LocalDateTime>
	 */
	Optional<LocalDateTime> getTriageDateAndTime();
	
	/**
	 * Method that gets a complaint from a patient (pain,etc.)
	 * 
	 * @return String 
	 */
	String getComplaint();
	
	/**
	 * Method that gets the priority level of a patient
	 * 
	 * @return Priority enum
	 */
	Priority getPriority();
	
	/**
	 * Method that sets the registration date and time using ints
	 * 
	 * @param int year
	 * @param int month
	 * @param int day
	 * @param int hour
	 * @param int sec
	 */
	public void setRegistrationDateAndTime(int year, int month, int day,
			int hour, int sec);

	/**
	 * Method that sets the registration date and time, where the given
	 * parameter can be null
	 * @param Optional<LocalDateTime> datetime
	 */
	public void setRegistrationDateAndTime(Optional<LocalDateTime> datetime);
	
	/**
	 * Method that sets the triage date using ints
	 * @param int year
	 * @param int month
	 * @param int day
	 * @param int hour
	 * @param int sec
	 */
	public void setTriageDateAndTime(int year, int month, int day, int hour,
			int sec);
	/**
	 * Method that sets the triage and date time using Optional,
	 * the parameter can be null
	 * 
	 * @param Optional<LocalDateTime> datetime
	 */
	public void setTriageDateAndTime(Optional<LocalDateTime> datetime);

	/**
	 * Method that sets the priority level using the Priority enum
	 * 
	 * @param Priority aPriority
	 */
	public void setPriority(Priority aPriority);

	/**
	 * Method that sets an optional complaint
	 * @param Optional <String> complaint
	 */
	public void setComplaint(Optional<String> complaint);
}
