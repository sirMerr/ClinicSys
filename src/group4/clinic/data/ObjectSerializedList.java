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
	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getPatientDatabase() {
		@SuppressWarnings("rawtypes")
		List<Patient> patientList = new ArrayList();
		try {
			patientList = (List<Patient>) Utilities.deserializeObject(patientFilename);
		} catch (ClassNotFoundException | IOException e)	 {
			return new ArrayList<Patient>();
		}
		return patientList;
	}

	/**
	 * Returns a list of visit queues
	 * 
	 * @return List<Queue<Visit>>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Queue<Visit>> getVisitDatabase() {		
		List<Queue<Visit>> db = new ArrayList<Queue<Visit>>(
		Priority.values().length);
		try {
			db = (List<Queue<Visit>>) Utilities.deserializeObject(visitFilename);
		} catch (ClassNotFoundException | IOException e)	 {
			return new ArrayList<Queue<Visit>>();
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
	 * Save the patient database to serialized file.
	 * 
	 * @param List<Patient> patients
	 */
	@Override
	public void savePatientDatabase(List<Patient> patients) throws IOException {
		Utilities.serializeObject(patients, patientFilename);
	}
	
	/**
	 * Saves the visit database to serialized file.
	 * 
	 * @param List<Queue<Visit>> visits
	 */
	@Override
	public void saveVisitDatabase(List<Queue<Visit>> visits) throws IOException {
		Utilities.serializeObject(visits, visitFilename);
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
