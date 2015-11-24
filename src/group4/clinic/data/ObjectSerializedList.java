package group4.clinic.data;

import group4.util.ListUtilities;
import group4.util.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.io.Serializable;

import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.Visit;

public class ObjectSerializedList implements Serializable, ListPersistenceObject {

	private static final long serialVersionUID = 42031768871L;
	private String patientFilename, visitFilename;
	
	public ObjectSerializedList(String patientFilename, String visitFilename)
	{
		this.patientFilename = patientFilename;
		this.visitFilename = visitFilename;
	}
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
	
	@Override
	public List<Patient> getPatientDatabase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Queue<Visit>> getVisitDatabase() {
		// TODO Auto-generated method stub
		return null;
	}

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