package group4.clinic.business;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Optional;

import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.Visit;

/**
 * This class implements the Visit interface and creates a ClinicVist object.
 * 
 * It holds five fields:
 * 
 * newPatient The Patient regDateTime The registration date and time
 * triageDateTime The time of triage complaint The complaint by the patient
 * priority Priority(urgency) of the visit
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public class ClinicVisit implements Visit {
	private static final long serialVersionUID = 42031768871L;
	private final Patient newPatient;
	private LocalDateTime regDateTime;
	private LocalDateTime triageDateTime;
	private String complaint = "";
	private Priority priority;

	/**
	 * 
	 * @param newPatient
	 */
	public ClinicVisit(Patient newPatient) {

		// Set patient
		this.newPatient = newPatient;

		// Set priority
		priority = Priority.NOTASSIGNED;

		// Set registration date & time

		regDateTime = LocalDateTime.now();

	}

	/**
	 * Objects are checked if they are the same, then compared 
	 * with the registered time between the Visit patients.
	 * 
	 * @param Visit visit patient to be compared
	 * @return positive if greater, negative if smaller, or 
	 * 		0 if the same Visit object or registered time
	 * 		is equal.
	 */
	@Override
	public int compareTo(Visit visit) {
		if(this == visit)
			return 0;
		
		int compared = this.getRegistrationDateAndTime().
				compareTo(visit.getRegistrationDateAndTime());
		
		if(compared < 0)
			return -1;
		else if(compared > 0)
			return 1;
		else
			return 0;
	}

	/**
	 * The equals method compares two Visit type objects for equality and
	 * returns true if they are equal. Two Visit Objects are considered equal if
	 * they are they have the same registration times and have their patients
	 * are equal
	 * 
	 * @param Object object
	 * 
	 * @return true if both Visit objects have the same registration times and
	 * their patients are equal
	 */
	@Override
	public final boolean equals(Object obj) {

		Visit visit = (Visit) obj; // casting
		if (!(obj instanceof Visit)) {
			return false;
		} // maybe remove
		if (visit.getRegistrationDateAndTime() != getRegistrationDateAndTime()) {
			return false;
		}
		if (!(visit.getPatient().getRamq().equals(getPatient().getRamq()))) {
			return false;
		}

		return true;
	}

	/**
	 * Return the complaint string.
	 * 
	 * @return the complaint
	 */
	@Override
	public String getComplaint() {
		return complaint;
	}

	/**
	 * Returns a deep copy of the Patient object.
	 * 
	 * @return copy of Patient
	 */
	@Override
	public Patient getPatient() {
		// Creating deep copy
		Patient copyPatient = new ClinicPatient(newPatient.getName()
				.getFirstName(), newPatient.getName().getLastName(), newPatient
				.getRamq().toString());

		return copyPatient;
	}

	/**
	 * Returns the priority of the visit.
	 * 
	 * @return The priority enum value
	 */
	@Override
	public Priority getPriority() {
		return priority;
	}

	/**
	 * Returns the registration date and time of the visit.
	 * 
	 * @return The date and time of visit
	 */
	@Override
	public LocalDateTime getRegistrationDateAndTime() {
		return regDateTime;
	}

	/**
	 * Gets the optional value of the date and time of the triage.
	 * 
	 * @return Optional value of date and time
	 */
	@Override
	public Optional<LocalDateTime> getTriageDateAndTime() {
		return Optional.ofNullable(triageDateTime);
	}

	/**
	 * Returns hashcode (regDateTime and triageDateTime)
	 * 
	 * @return The hash code
	 */
	@Override
	public int hashCode() {
		return regDateTime.hashCode() + triageDateTime.hashCode();
	}

	/**
	 * Sets the registration date and time by taking in a year, month, day,
	 * hour, and minute. //sec?
	 * 
	 * @param year
	 *            The year
	 * @param month
	 *            The numerical value of the month
	 * @param day
	 *            The numerical value of the day
	 * @param hour
	 *            The hour
	 * @param min
	 *            The minute //unsure
	 */
	@Override
	public void setRegistrationDateAndTime(int year, int month, int day,
			int hour, int min) {
		regDateTime = LocalDateTime.of(year, month, day, hour, min);

	}

	/**
	 * Sets the registration date and time of the visit. This value can be
	 * optional.
	 * 
	 * @param datetime
	 *            The date and time of the registration
	 * 
	 * @throws DateTimeException
	 *             if date is incorrect
	 */
	public void setRegistrationDateAndTime(Optional<LocalDateTime> datetime) {
		regDateTime = datetime.get();

	}

	/**
	 * Sets the triage date and time by taking in a year, month, day, hour, and
	 * minute. //sec?
	 * 
	 * @param year
	 *            The year
	 * @param month
	 *            The numerical value of the month
	 * @param day
	 *            The numerical value of the day
	 * @param hour
	 *            The hour
	 * @param min
	 *            The minute //unsure
	 * 
	 * @throws DateTimeException
	 *             if date is incorrect if triage date is before registration
	 *             date
	 */
	@Override
	public void setTriageDateAndTime(int year, int month, int day, int hour,
			int min) {

		triageDateTime = LocalDateTime.of(year, month, day, hour, min);

		try {
			if (triageDateTime.isBefore(regDateTime))
				throw new DateTimeException(
						"Triage date must be after registration date. Invalid value: "
								+ triageDateTime);
		} catch (DateTimeException dte) {
			System.out.println(dte.getMessage());
		}
	}

	/**
	 * Sets the triage date and time. This value can be optional.
	 * 
	 * @param Optional
	 *            <LocalDateTime> datetime The date and time of triage
	 * 
	 * @throws DateTimeException
	 *             if date is incorrect
	 */
	@Override
	public void setTriageDateAndTime(Optional<LocalDateTime> datetime) {
		if (datetime.isPresent()) {
			triageDateTime = datetime.get();
		} else {
			triageDateTime = null;
		}

	}

	/**
	 * Sets the priority of the visit.
	 * 
	 * @param aPriority
	 *            The priority to be assigned
	 */
	@Override
	public void setPriority(Priority aPriority) {
		priority = aPriority;

	}

	/**
	 * Sets the complaint by the patient. This value can be optional.
	 * 
	 * @param complaint
	 *            Optional complaint of patient
	 */
	@Override
	public void setComplaint(Optional<String> complaint) {
		if (complaint.isPresent()) {
			this.complaint = complaint.get().toString();
		} else {
			this.complaint = "";
		}
	}

	/**
	 * The toString method returns the Visit Object in String form. It will be
	 * in the following format:
	 * 
	 * ramq*registrationYear*registrationMonth*registrationDay*registrationHr*
	 * registrationMin*triageYear*triageMonth*triageDay*triageHr*triageMin*
	 * priorityCode*complaint
	 * 
	 * @return String representation of ClinicVisit
	 */
	@Override
	public String toString() {

		LocalDateTime registration = getRegistrationDateAndTime(), triage;

		String triageInfo = "****", priority = "";

		if (getTriageDateAndTime().isPresent()) {
			triage = getTriageDateAndTime().get();
			triageInfo = triage.getYear() + "*" + triage.getMonthValue() + "*"
					+ triage.getDayOfMonth() + "*" + triage.getHour() + "*"
					+ triage.getMinute();
		}
		if (!(getPriority() == Priority.NOTASSIGNED)) {
			priority = getPriority().getCode() + "";
		}

		return getPatient().getRamq() + "*" + registration.getYear() + "*"
				+ registration.getMonthValue() + "*"
				+ registration.getDayOfMonth() + "*" + registration.getHour()
				+ "*" + registration.getMinute() + "*" + triageInfo + "*"
				+ priority + "*" + getComplaint();
	}

}