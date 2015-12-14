package group4.clinic.business;

import group4.clinic.business.Priority;
import group4.dawsonclinic.DawsonClinicFactory;
import group4.dawsonclinic.DawsonClinicPriorityPolicy;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

import dw317.clinic.ClinicFactory;
import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.PatientVisitManager;
import dw317.clinic.business.interfaces.Visit;
import dw317.clinic.data.DuplicatePatientException;
import dw317.clinic.data.NonExistingPatientException;
import dw317.clinic.data.NonExistingVisitException;
import dw317.clinic.data.interfaces.PatientDAO;
import dw317.clinic.data.interfaces.VisitDAO;
import dw317.lib.medication.Medication;

/*Clinic objects, in addition to allowing the client to
 * query the system about patients and to get the next
 * prioritized visit, will ensure that all business rules
 * are respected before allowing patients or visits to be
 * added or modified.
 */
public class Clinic extends Observable implements PatientVisitManager {

	private static final long serialVersionUID = 42031768871L;
	private final ClinicFactory factory;
	private final PatientDAO patientConnection;
	private final VisitDAO visitConnection;

	/**
	 * Constructor. Instantiates the private parameters for the object.
	 * 
	 * @param PatientDAO
	 *            patientConnection existing connections to Patient database.
	 * @param VisitDAO
	 *            visitConnection existing connection to Visit database.
	 * @param ClinicFactory
	 *            factory connection to existing factory type object.
	 *
	 */
	public Clinic(PatientDAO patientConnection, VisitDAO visitConnection,
			ClinicFactory factory) {

		if (patientConnection == null || visitConnection == null) {

			throw new IllegalArgumentException("Null argument");
		}

		if (factory == null)
			this.factory = DawsonClinicFactory.DAWSON_CLINIC;
		else
			this.factory = factory;

		this.patientConnection = patientConnection;
		this.visitConnection = visitConnection;
	}

	/**
	 * Closes connections to clinic
	 * 
	 * @throws IOException if there is a problem when saving databases
	 */
	@Override
	public void closeClinic() throws IOException {
		patientConnection.disconnect();
		visitConnection.disconnect();

	}
	
	/**
	 * Creates visit for a provided patient with a complaint and
	 * adds it to the database
	 * 
	 * @param Patient patient
	 * 		Existing Patient
	 * @param String complaint
	 * 		Ailment of the patient
	 */
	@Override
	public void createVisit(Patient patient, String complaint) {
		if (patient == null)
			throw new IllegalArgumentException("Patient cannot be null");
		
		Visit newVisit = new ClinicVisit(patient);
		newVisit.setComplaint(Optional.ofNullable(complaint));


		visitConnection.add(newVisit);
		
		setChanged();
		notifyObservers(newVisit);

	}

	@Override
	public Patient findPatient(String ramq) throws NonExistingPatientException {
		
		// convert ramq to type Ramq
		Ramq searchRamq = new Ramq(ramq);
		
		// return patient or throw a NonExistingPatientException is not found
		Patient patient = patientConnection.getPatient(searchRamq);

		setChanged();
		notifyObservers(patient);

		return patient;

	}

	@Override
	public List<Patient> findPatientsPrescribed(Medication meds) {
		
		if (meds == null)
			throw new IllegalArgumentException("Medication cannot be null");
			
		return patientConnection.getPatientsPrescribed(meds);
	}

	@Override
	public Optional<Visit> nextForTriage() {
		
		Optional<Visit> nextTriage = visitConnection
				.getNextVisit(Priority.NOTASSIGNED);

		setChanged();
		notifyObservers(nextTriage);

		return nextTriage;
	}

	@Override
	public Optional<Visit> nextForExamination() {
		
		DawsonClinicPriorityPolicy policy = new DawsonClinicPriorityPolicy(visitConnection);
		
		Optional<Visit> nextVisit = policy.getNextVisit();
		
		setChanged();
		notifyObservers(nextVisit);

		return nextVisit;
	}

	@Override
	public void registerNewPatient(String firstName, String lastName,
			String ramq, String telephone, Medication meds, String conditions)
			throws DuplicatePatientException {
		
		if (firstName.length() == 0 || lastName.length() == 0 || ramq.length() == 0)
		{
			throw new IllegalArgumentException("The first name, last name " +
												"or RAMQ is invalid");
		}
		// Create new patient

		Patient newPatient = new ClinicPatient(firstName, lastName, ramq);
		newPatient.setTelephoneNumber(Optional.ofNullable(telephone));
		newPatient.setMedication(Optional.ofNullable(meds));
		newPatient.setExistingConditions(Optional.ofNullable(conditions));

		patientConnection.add(newPatient);

		setChanged();
		notifyObservers(newPatient);
	}

	/**
	 * Updates the priority of the first visit in the triage queue to a new
	 * priority.
	 *
	 * @param newPriority
	 *            The new priority
	 *            
	 * @throws NonExistingVisitException
	 * 			if visit that is unassigned is not found
	 * 
	 * @throws IllegalArgumentException
	 * 			if priority is null or not assigned
	 */
	@Override
	public void changeTriageVisitPriority(Priority newPriority)
			throws NonExistingVisitException {
		if (newPriority == null)
			throw new IllegalArgumentException("Priority argument is null");

		if (newPriority.equals(Priority.NOTASSIGNED))
			throw new IllegalArgumentException("Priority is not assigned");
		
		visitConnection.update(Priority.NOTASSIGNED, newPriority);

		setChanged();
		notifyObservers(newPriority);
	}

}
