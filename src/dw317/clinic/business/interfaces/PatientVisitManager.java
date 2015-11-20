package dw317.clinic.business.interfaces;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import dw317.clinic.data.DuplicatePatientException;
import dw317.clinic.data.NonExistingPatientException;
import dw317.lib.medication.Medication;

/**
 * To order to make it easier to interact with the various system components we
 * will make use of the Façade Design Pattern (a structural pattern). A façade
 * is an object that provides a unified interface to the set of component
 * interfaces in the system; hence, it prevents tight coupling between the
 * client and the system components. This way the client merely interacts with
 * one interface without knowing any of the other system components (i.e. the
 * DAO objects, the PriorityPolicy objects, etc.)
 * 
 * @author Tiffany Le-Nguyen, Hugo Pham, Sevan Topalian, Andrew Azevedo
 * @since JDK.1.7
 */
public interface PatientVisitManager extends Serializable {
	/**
	 * Saves all data and closes the clinic.
	 *
	 * @throws IOException
	 *             If there is a problem closing the clinic files.
	 */
	void closeClinic() throws IOException;

	/**
	 * Creates a visit for a patient.
	 *
	 * @param patient
	 *            The patient for whom the visit is created.
	 */
	void createVisit(Patient patient, String complaint);

	/**
	 * Finds and returns a patient record.
	 *
	 * @param ramq
	 *            The patient’s ramq id.
	 * @return Patient object with the ramq id
	 *
	 * @throws NonExistingPatientException
	 *             If the patient with the ramq does not exist.
	 */
	Patient findPatient(String ramq) throws NonExistingPatientException;

	/**
	 * Finds and returns a list of all patients prescribed the given medication.
	 *
	 * @param meds
	 *            The medication that will be used to find the associated
	 *            patients.
	 * @return A list of patients who are taking the given medication. A list of
	 *         size zero will be returned if no patients are taking a medication
	 *         that matches the parameter value.
	 *
	 */
	List<Patient> findPatientsPrescribed(Medication meds);

	/**
	 * Get next visit for triage.
	 * 
	 * @return Optional<Visit> object with Priority NOTASSIGNED
	 *
	 */
	Optional<Visit> nextForTriage();

	/**
	 * Get next visit for examination.
	 * 
	 * @return Optional<Visit> object with next visit to be examined.
	 *
	 */
	Optional<Visit> nextForExamination();

	/**
	 * Registers a new patient.
	 *
	 * @param firstName
	 *            The patient’s first name.
	 * @param lastName
	 *            The patient’s last name.
	 * @param ramq
	 *            The patient’s ramq id.
	 * @param telephone
	 *            The patient’s telephone.
	 * @param meds
	 *            The patient’s medication.
	 * @param conditions
	 *            The patient’s conditions.
	 *
	 * @throws DuplicatePatientException
	 *             If the patient with the ramq already exists.
	 */
	void registerNewPatient(String firstName, String lastName, String ramq,
			String telephone, Medication meds, String conditions)
			throws DuplicatePatientException;
}
