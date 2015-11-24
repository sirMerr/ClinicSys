package group4.clinic.data;

import group4.clinic.business.Priority;
import group4.util.ListUtilities;
import group4.util.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.Serializable;

import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.Visit;
/**
 * ObjectSerializedList allows the clinic system to retrieve/save 
 * the database from/to object serialized files.
 * 
 * @author Tiffany Le-Nguyen, Hugo Pham, Sevan Topalian, Andrew Azevedo
 * @since JDK.1.8
 *
 */
public class ObjectSerializedList implements Serializable, ListPersistenceObject {
	private static final long serialVersionUID = 42031768871L;
	private String patientFilename, visitFilename;
	
	/**
	 * Public constructor
	 * 
	 * @param patientFilename
	 * @param visitFilename
	 */
	public ObjectSerializedList(String patientFilename, String visitFilename)
	{
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
	public void convertSequentialFilesToSerialized(String
			sequentialPatients, String sequentialVisits)
			throws IOException {
			SequentialTextFileList textFile = new
			SequentialTextFileList(sequentialPatients, sequentialVisits);
			//patients
			List<Patient> patients = textFile.getPatientDatabase();
			Utilities.serializeObject(patients, patientFilename);
			List<Queue<Visit>> visits = textFile.getVisitDatabase();
			Utilities.serializeObject(visits, visitFilename);
			}
	
	/**
	 * Returns a patient database
	 * 
	 * @return List<Patient>
	 */
	@Override
	public List<Patient> getPatientDatabase() {
		Patient[] patients;
		try {
			patients = ClinicFileLoader
					.getPatientListFromSequentialFile(patientFilename);
		} catch (IOException e) {
			return new ArrayList<Patient>();
		}
		// Create the adapter object that will be used as an argument to
		// instantiate an ArrayList instance.
		List<Patient> listAdapter = java.util.Arrays.asList(patients);
		// return a reference to an ArrayList instance.
		return new ArrayList<Patient>(listAdapter);
	}

	/**
	 * Returns a list of visit queues
	 * 
	 * @return List<Queue<Visit>>
	 */
	@Override
	public List<Queue<Visit>> getVisitDatabase() {
		Visit[] visits;
		Patient[] patients;
		List<Queue<Visit>> db = new ArrayList<Queue<Visit>>(
				Priority.values().length);
		// get Patients
		try {
			patients = ClinicFileLoader
					.getPatientListFromSequentialFile(patientFilename);
		} catch (IOException e) {
			return new ArrayList<Queue<Visit>>(0); // return empty arraylist
		}
		// get Visits
		try {
			visits = ClinicFileLoader.getVisitListFromSequentialFile(
					visitFilename, patients);
		} catch (IOException e) {
			return new ArrayList<Queue<Visit>>(0);
		}
		// separate visit array (already sorted) into queues by Priority
		Visit[][] priority = separateSortedVisitArray(visits);
		// Create the adapter object that will be used as an argument to
		// instantiate a LinkedList instance.
		for (int i = 0; i < Priority.values().length; i++) {
			List<Visit> listAdapter = java.util.Arrays.asList(priority[i]);
			// insert a reference to an LinkedList instance into array of queues
			db.add(i, new LinkedList<Visit>(listAdapter));
		}
		return db;
	}
	
	/**
	 * Separates a sorted visit array 
	 * 
	 * @param sorted
	 * @return Visit [] []
	 */
	private Visit[][] separateSortedVisitArray(Visit[] sorted) {
		int length = sorted.length;
		Visit[][] subsets = new Visit[Priority.values().length][length];
		int subsetCtr = 0;
		int allCtr = 0;
		int p = 0;
		// iterate through priorities
		while (allCtr < length && p < Priority.values().length) {
			while (allCtr < length
					&& sorted[allCtr].getPriority() == Priority.values()[p]) {
				subsets[p][subsetCtr] = sorted[allCtr];
				allCtr++;
				subsetCtr++;
			}
			// resize
			if (subsetCtr < length) {
				Visit[] resized = new Visit[subsetCtr];
				for (int i = 0; i < subsetCtr; i++) {
					resized[i] = subsets[p][i];
				}
				subsets[p] = resized;
			}
			// next priority
			p++;
			subsetCtr = 0;
		}
		return subsets;
	}

	/**
	 * Save the patient database.
	 * 
	 * @param List<Patient> patients
	 */
	@Override
	public void savePatientDatabase(List<Patient> patients) throws IOException {
		Patient[] patientArray = patients.toArray(new Patient[patients.size()]);
		ListUtilities.saveListToTextFile(patientArray, patientFilename); //patient file name
	}

	@Override
	public void saveVisitDatabase(List<Queue<Visit>> visits) throws IOException {
		// merge the queues into an array
		Visit[] visitArray = merge(visits);
		// use the existing saveListToTextFile utility method
		ListUtilities.saveListToTextFile(visitArray, visitFilename); //file name

	}
	/**
	 * Merges a list of queues into a single array
	 * 
	 * @param visits
	 *            List of queues of Visits
	 * @return a single array
	 */
	private Visit[] merge(List<Queue<Visit>> visits) {
		int numQueues = visits.size();
		List<Visit> all = new ArrayList<Visit>();
		for (int i = 0; i < numQueues; i++) {
			// iterate through the queues, converting to array
			Visit[] visitArray = visits.get(i).toArray(
					new Visit[visits.get(i).size()]);
			Collections.addAll(all, visitArray);
		}
		return all.toArray(new Visit[all.size()]);
	}
}