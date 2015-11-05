/**
 * 
 */
package group4.clinic.data;

import group4.clinic.business.ClinicPatient;
import group4.clinic.business.Ramq;
import group4.util.ListUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import dw317.clinic.DefaultPatientVisitFactory;
import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.PatientVisitFactory;
import dw317.clinic.data.DuplicatePatientException;
import dw317.clinic.data.NonExistingPatientException;
import dw317.clinic.data.interfaces.PatientDAO;
import dw317.lib.medication.Medication;

/**
 * An object of type PatientListDB will represent the patient database as an
 * internal list of patients.
 * 
 * The listPersistenceObject field, once set by the constructor is to be used to
 * assign a reference to the database. The factory field must be assigned the
 * value referenced by a PatientVisitFactory or to the value referenced by the
 * factory parameter specified in the two parameter constructor’s parameter
 * list. Recall that final fields must be assigned a value prior to the
 * termination of the constructor. Although the database field is not final, you
 * must not provide a setter method. The reason that it is not final is because
 * we want to be able to assign it the null value after the list has been
 * persisted to disk in the disconnect method.
 * 
 * @author @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 */
public class PatientListDB implements PatientDAO {

	private List<Patient> database;

	private final ListPersistenceObject listPersistenceObject;

	private final PatientVisitFactory factory;

	public PatientListDB(ListPersistenceObject listPersistenceObject) {
		this.listPersistenceObject = listPersistenceObject;
		this.factory = DefaultPatientVisitFactory.DEFAULT;
		this.database = listPersistenceObject.getPatientDatabase();
	}

	public PatientListDB(ListPersistenceObject listPersistenceObject,
			PatientVisitFactory factory) {
		this.listPersistenceObject = listPersistenceObject;
		this.factory = factory;
		this.database = listPersistenceObject.getPatientDatabase();
	}

	/**
	 * Adds a patient object to the database. Add a reference to a copy of the
	 * object referenced by the aPatient and not the actual object being
	 * referenced by the parameter since we are using an internal list as the
	 * database. The method must throw a DuplicatePatientException if the
	 * specified ramq is already in the database. Note that the patient must be
	 * added in ramq order to keep the database in sorted order.
	 *
	 * @param aPatient
	 *            The patient to add to the database.
	 * @throws DuplicatePatientException
	 *             ; The requested ramq already exists.
	 */
	@Override
	public void add(Patient aPatient) throws DuplicatePatientException {

		int insertPosition = Collections.binarySearch(database, aPatient);

		if (insertPosition >= 0)
			throw new DuplicatePatientException("The ramq + \""
					+ aPatient.getRamq() + "\" already exists.");
		
		Patient copyPatient = new ClinicPatient(aPatient.getName().getFirstName(), 
				aPatient.getName().getLastName(), aPatient.getRamq().toString());

		copyPatient.setExistingConditions(Optional.of(aPatient.getExistingCondition()));
		if(aPatient.getMedication().isPresent())
			copyPatient.setMedication(aPatient.getMedication());
		copyPatient.setTelephoneNumber(Optional.of(aPatient.getTelephoneNumber()));

		database.add((-(insertPosition) - 1), copyPatient);
	}

	/**
	 * Saves the list of patients and disconnects from the database.
	 *
	 * @throws IOException
	 *             Problems saving or disconnecting from database.
	 */
	@Override
	public void disconnect() throws IOException {
		listPersistenceObject.savePatientDatabase(database);
		database = null;
	}

	/**
	 * Returns the patient with the specified ramq id.
	 *
	 * @param ramq
	 *            The ramq id of the requested patient.
	 *
	 * @return The patient with the specified ramq id.
	 *
	 * @throws NonExistingPatientException
	 *             If there is no patient with the specified ramq id.
	 */
	@Override
	public Patient getPatient(Ramq ramq) throws NonExistingPatientException {
		int index = binarySearch(database, ramq);
		if (index < 0)
			throw new NonExistingPatientException(
					"\t-The patient with the following ramq \"" + ramq
							+ "\" does not exist.");
		else {
			Patient copy = new ClinicPatient(database.get(index).getName()
					.getFirstName(), database.get(index).getName()
					.getLastName(), database.get(index).getRamq().toString());
			copy.setExistingConditions(Optional.of(database.get(index)
					.getExistingCondition()));
			copy.setMedication(database.get(index).getMedication());
			copy.setTelephoneNumber(Optional.of(database.get(index)
					.getTelephoneNumber()));

			return copy;
		}

	}

	/**
	 * Determines if a patient with the specified ramq id exists in the database
	 *
	 * @param ramq
	 *            The ramq id of the requested patient.
	 *
	 * @return true if the specified ramq exists; false if it does not
	 *
	 */
	@Override
	public boolean exists(Ramq ramq) {
		int index = binarySearch(database, ramq);
		if (index >= 0)
			return true;
		else
			return false;
	}

	private int binarySearch(List<Patient> database2, Ramq ramq) {
		int low = 0;
		int high = database2.size() - 1;
		int mid;
		int value;

		mid = (low + high) / 2;

		while (low <= high) {
			value = database2.get(mid).getRamq().compareTo(ramq);

			if (value == 0)
				return mid;

			if (value > 0)
				high = mid - 1;
			else
				low = mid + 1;

			mid = (low + high) / 2;
		}

		// If not found
		return -(low + 1);
	}

	/**
	 * Returns a list of the patients taking a given medication. The list will
	 * contain all patients associated with the given medication.
	 *
	 * @param medication
	 *            The medication that will be used to find the associated
	 *            patients.
	 *
	 * @return A list of patients who are prescribed the given medication. A
	 *         list of size zero will be returned if no patients are prescribed
	 *         the medication that is equal to the parameter value.
	 *
	 */
	@Override
	public List<Patient> getPatientsPrescribed(Medication medication) {

		List<Patient> patientsList = new ArrayList<Patient>();
		for (Patient patient : database) {
			if (patient.getMedication().isPresent())
				if (medication.getNumber().equals(
						patient.getMedication().get().getNumber())) {
					Patient copy = new ClinicPatient(patient.getName()
							.getFirstName(), patient.getName().getLastName(),
							patient.getRamq().toString());
					copy.setExistingConditions(Optional.of(patient
							.getExistingCondition()));
					copy.setMedication(patient.getMedication());
					copy.setTelephoneNumber(Optional.of(patient
							.getTelephoneNumber()));

					patientsList.add(copy);
				}
		}
		return patientsList;
	}

	/**
	 * Overriden toString() method. Returns in String format the database of
	 * Patients. Also includes the number of patients in the database.
	 * 
	 * @return String representation of PatientListDB
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Number of patients in database: " + database.size());

		for (Patient patient : database) {
			sb.append("\n" + patient.toString());
		}

		return sb.toString();
	}

	/**
	 * Modifies a patient’s details. Only the telephone number, medication and
	 * the conditions can be changed for an existing patient.
	 *
	 * @param modifiedPatient
	 *            The patient to be update.
	 * @throws NonExistingPatientException
	 *             The patient is not in database.
	 */
	@Override
	public void update(Patient modifiedPatient)
			throws NonExistingPatientException {
		int index = Collections.binarySearch(database, modifiedPatient);

		if (index < 0) {
			throw new NonExistingPatientException();
		}
		database.get(index).setTelephoneNumber(
				Optional.ofNullable(modifiedPatient.getTelephoneNumber()));
		database.get(index).setMedication(modifiedPatient.getMedication());
		database.get(index).setExistingConditions(
				Optional.ofNullable(modifiedPatient.getExistingCondition()));
	}
}
