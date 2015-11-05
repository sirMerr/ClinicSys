/**
 * Defines an ClinicPatient type/Patient type objects
 */
package group4.clinic.business;

import java.time.LocalDate;
import java.util.Optional;

import dw317.clinic.business.interfaces.Patient;
import dw317.lib.Gender;
import dw317.lib.Name;
import dw317.lib.medication.Medication;

/**
 * This class implements the Patient interface and creates a ClinicPatient
 * object.
 * 
 * It holds three fields:
 * 
 * firstName A first name lastName A last name ramqID A RAMQ ID
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public class ClinicPatient implements Patient {
	private static final long serialVersionUID = 42031768871L;
	private final Ramq ramq;
	private final Name patientName;
	private String existingCondition = "";
	private Medication medication;
	private String telephoneNumber = "";

	/**
	 * Initializes a new instance of the ClinicPatient class.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param ramq
	 */
	public ClinicPatient(String firstName, String lastName, String ramq) {
		validateName(firstName);
		validateName(lastName);

		this.patientName = new Name(firstName, lastName);
		this.ramq = new Ramq(ramq, patientName);

	}

	/**
	 * Patients are checked if they are the same, then are
	 * compared with their ramq field alphabetically 
	 * and numerically between eachother.
	 * 
	 * @param Patient patient to be compared
	 * @return 1 if the ramq of this patient is greater, 
	 * 		-1 if lesser, or 0 if the same.
	 */
	@Override
	public int compareTo(Patient patient) {
		if(this == patient)
			return 0;
		int compared = this.getRamq().compareTo(patient.getRamq());
		if (compared < 0)
			return -1;
		else if (compared > 0)
			return 1;
		else
			return 0;
	}

	/**
	 * The equals method compares two Patient objects for equality and returns
	 * true if they are equal. Two Patient Objects are considered equal if they
	 * are the same class and have the same ramq attributes.
	 * 
	 * @param Object
	 *            object
	 * @return true if both Patient objects have the same RAMQ ID
	 */
	@Override
	public final boolean equals(Object object) {
		// check if object is null or if both are of the same class
		if (object == null || !(object instanceof Patient))
			return false;

		Patient patient = (Patient) object;
		// ramq attributes
		if (this.ramq.equals(patient.getRamq())) {
			return true;
		}
		return false;

	}

	/**
	 * Returns hashcode (patient's name and ramq ID)
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		return patientName.hashCode() + ramq.hashCode();
	}

	/**
	 * The toString method returns the Patient Object in String form. It will be
	 * in the following format:
	 * ramq*firstname*lastname*phoneNumber*scheme*medicationNumber*
	 * medicationName*conditions
	 * 
	 * @return A string representation of a Patient Object
	 */
	@Override
	public String toString() {

		Medication med = null;
		String medInfo = "**";
		if (this.getMedication().isPresent()) {
			med = this.getMedication().get();
			medInfo = med.getScheme() + "*" + med.getNumber() + "*"
					+ med.getName();

		}
		return this.getRamq() + "*" + this.patientName.getFirstName() + "*"
				+ this.patientName.getLastName() + "*"
				+ this.getTelephoneNumber() + "*" + medInfo + "*"
				+ this.getExistingCondition();
	}

	/**
	 * Returns the birthday from the Ramq ramq
	 * 
	 * @return LocalDate
	 */
	@Override
	public LocalDate getBirthday() {
		return ramq.getBirthdate();
	}

	/**
	 * Returns the existing condition
	 * 
	 * @return String existingCondition;
	 */
	@Override
	public String getExistingCondition() {
		return existingCondition;
	}

	/**
	 * Returns patient's gender
	 * 
	 * @return Gender
	 */
	public Gender getGender() {
		return this.ramq.getGender();
	}

	/**
	 * Returns an optional Medication
	 * 
	 * @return Optional<Medication>;
	 */
	@Override
	public Optional<Medication> getMedication() {
		return Optional.ofNullable(this.medication);
	}

	/**
	 * Returns the patient's name.
	 * 
	 * @return A deep copy of patientName
	 */
	public Name getName() {
		Name name = new Name();
		name.setFirstName(patientName.getFirstName());
		name.setLastName(patientName.getLastName());
		return name;
	}

	/**
	 * Returns patient's ramq id
	 * 
	 * @return Ramq ramq
	 */
	public Ramq getRamq() {
		return ramq;
	}

	/**
	 * Returns the telephone number
	 * 
	 * @return telephoneNumber;
	 */
	@Override
	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * Sets the existing conditions. It checks if the Optional<String> ailment
	 * is present. If it is, it sets existingCondition as a String. If not, it
	 * sets existingCondition as an empty String.
	 * 
	 * @param Optional
	 *            <String> ailment
	 */
	@Override
	public void setExistingConditions(Optional<String> ailment) {
		if (ailment.isPresent()) {
			existingCondition = ailment.get().toString();
		} else {
			existingCondition = "";
		}
	}

	/**
	 * Sets the Medication object. It checks if the Optional<Medication>
	 * medication is present. If it isn't, it sets the global variable
	 * medication as a null.
	 * 
	 * @param Optional
	 *            <String> medication
	 */
	@Override
	public void setMedication(Optional<Medication> medication) {
		if (medication.isPresent()) {
			this.medication = medication.get();
		} else {
			this.medication = null;
		}

	}

	/**
	 * Sets the telephone number. It checks if the Optional<String>
	 * telephoneNumber is present. If it is, it sets it as a String. If not, it
	 * sets it as an empty String.
	 * 
	 * @param Optional
	 *            <String> telephoneNumber
	 */
	@Override
	public void setTelephoneNumber(Optional<String> telephoneNumber) {
		if (telephoneNumber.isPresent()) {
			this.telephoneNumber = telephoneNumber.get().toString();
		} else {
			this.telephoneNumber = "";
		}
	}

	/**
	 * Validates if the patient name is valid. Patient name is valid if not null
	 * + at least 2 characters + only alphabet characters
	 * 
	 * @param firstName
	 * @param lastName
	 * 
	 * @throws IllegalArgumentException
	 */
	private static void validateName(String patientName) {
		try {
			if (patientName == null)
				throw new IllegalArgumentException(
						"Name Error 1 - Name must exist. Invalid value ");
			else if (patientName.length() < 2)
				throw new IllegalArgumentException(
						"Name Error 2 - Name must at least than 2 characters. Invalid value: "
								+ patientName);
			else if (!patientName.matches("[a-zA-Z]+"))
				throw new IllegalArgumentException(
						"Name Error 3 - Name contains non-alphabetical characters. Invalid value: "
								+ patientName);
		} catch (IllegalArgumentException iae) {
			iae.getMessage();
		}
	}

}
