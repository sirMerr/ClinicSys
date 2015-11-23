package group4.clinic.business;

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
	
	private final ClinicFactory factory;
	private final PatientDAO patientConnection;
	private final VisitDAO visitConnection;

	public Clinic(ClinicFactory factory, PatientDAO patientConnection, VisitDAO visitConnection) {
		this.factory = factory;
		this.patientConnection = patientConnection;
		this.visitConnection = visitConnection;
	}

	@Override
	public void closeClinic() throws IOException {
		patientConnection.disconnect();
		visitConnection.disconnect();

	}

	@Override
	public void createVisit(Patient patient, String complaint) {
		ClinicVisit newVisit = new ClinicVisit(patient);
		newVisit.setComplaint(Optional.of(complaint));
		
		//Set into database
		visitConnection.add(newVisit);

	}

	@Override
	public Patient findPatient(String ramq) throws NonExistingPatientException {
		//convert ramq to type Ramq
		Ramq searchRamq = new Ramq(ramq);
		
		//return patient or throw a NonExistingPatientException is not found
		return patientConnection.getPatient(searchRamq);
																
	}
	
	@Override
	public List<Patient> findPatientsPrescribed(Medication meds) {
		//Pull list of patients with the specified medication
		List<Patient> prescribedList = patientConnection.getPatientsPrescribed(meds);
		return prescribedList;
	}

	@Override
	public Optional<Visit> nextForTriage() {
		return visitConnection.getNextVisit(Priority.NOTASSIGNED);
	}

	@Override
	public Optional<Visit> nextForExamination() {
		//find next visit in queue
		Optional<Visit> nextVisit = null;
		Priority priority = null;
		int priorityNum = 1;
		
		//Loop to find next patient in order of priority
		while (nextVisit == null || priorityNum == 6){
			switch (priorityNum) {
			case 1:
				priority = Priority.REANIMATION;
				break;

			case 2:
				priority = Priority.VERYURGENT;
				break;

			case 3:
				priority = Priority.URGENT;
				break;

			case 4:
				priority = Priority.LESSURGENT;
				break;

			case 5:
				priority = Priority.NOTURGENT;
				break;

			}
			nextVisit = visitConnection.getNextVisit(priority);
			
			priorityNum++;
		}		
		return nextVisit;
	}

	@Override
	public void registerNewPatient(String firstName, String lastName,
			String ramq, String telephone, Medication meds, String conditions)
			throws DuplicatePatientException {
		//Create new patient
		ClinicPatient newPatient = new ClinicPatient(firstName, lastName, ramq);
		newPatient.setTelephoneNumber(Optional.of(telephone));
		newPatient.setMedication(Optional.of(meds));
		newPatient.setExistingConditions(Optional.of(conditions));
		
		patientConnection.add(newPatient);
	}

}