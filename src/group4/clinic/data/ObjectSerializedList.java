package group4.clinic.data;

import group4.util.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.io.Serializable;

import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.Visit;

/**
 * ObjectSerializedList allows the clinic system to retrieve/save the database
 * from/to object serialized files.
 * 
 * @author Tiffany Le-Nguyen, Hugo Pham, Sevan Topalian, Andrew Azevedo
 * @since JDK.1.8
 *
 */
public class ObjectSerializedList implements Serializable,
		ListPersistenceObject {
	private static final long serialVersionUID = 42031768871L;
	private String patientFilename, visitFilename;

	/**
	 * Public constructor
	 * 
	 * @param patientFilename
	 * @param visitFilename
	 */
	public ObjectSerializedList(String patientFilename, String visitFilename) {
		this.patientFilename = patientFilename;
		this.visitFilename = visitFilename;
	}

	/**
	 * Converts sequential file to serialized.
	 * 
	 * @param sequentialPatients
	 * @param sequentialVisits
	 * @throws IOException
	 */
	public void convertSequentialFilesToSerialized(String sequentialPatients,
			String sequentialVisits) throws IOException {
		SequentialTextFileList textFile = new SequentialTextFileList(sequentialPatients, sequentialVisits);
		// patients
		List<Patient> patients = textFile.getPatientDatabase();
		Utilities.serializeObject(patients, patientFilename);
		List<Queue<Visit>> visits = textFile.getVisitDatabase();
		Utilities.serializeObject(visits, visitFilename);
	}

	/**
	 * Returns a patient database
	 * 
	 * @return List<Patient>
	 * 		the patient database
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getPatientDatabase() {
		List<Patient> patients;
		try {
			patients = (List<Patient>) Utilities.deserializeObject(patientFilename);
		} catch (IOException ioe) {
			System.out.println("Error, object is not serializable. " + ioe.getMessage());
			return new ArrayList<Patient>();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error, class not found. " + cnfe.getMessage());
			return new ArrayList<Patient>();
		}
		return patients;
	}

	/**
	 * Returns a list of visit queues
	 * 
	 * @return List<Queue<Visit>>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Queue<Visit>> getVisitDatabase() {
		List<Queue<Visit>> db;
		try {
			db = (List<Queue<Visit>>) Utilities
					.deserializeObject(visitFilename);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Error " + e.getMessage());
			return new ArrayList<Queue<Visit>>(0);
		}
		return db;
	}

	/**
	 * Save the patient database to serialized file.
	 * 
	 * @param List
	 *            <Patient> patients
	 */
	@Override
	public void savePatientDatabase(List<Patient> patients) throws IOException {
		Utilities.serializeObject(patients, patientFilename);
	}

	/**
	 * Saves the visit database to serialized file.
	 * 
	 * @param List
	 *            <Queue<Visit>> visits
	 */
	@Override
	public void saveVisitDatabase(List<Queue<Visit>> visits) throws IOException {
		Utilities.serializeObject(visits, visitFilename);
	}

}
