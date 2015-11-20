/**
 * 
 */
package group4.clinic.data;

import group4.clinic.business.ClinicPatient;
import group4.clinic.business.ClinicVisit;
import group4.clinic.business.Priority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import dw317.clinic.DefaultPatientVisitFactory;
import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.PatientVisitFactory;
import dw317.clinic.business.interfaces.Visit;
import dw317.clinic.data.NonExistingVisitException;
import dw317.clinic.data.interfaces.VisitDAO;

/**
 * An object of type VisitQueueDB will represent the visit database as a list of
 * queues of visits.
 * 
 * @author @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 */
public class VisitQueueDB implements VisitDAO {

	private List<Queue<Visit>> database;
	private final ListPersistenceObject listPersistenceObject;
	private final PatientVisitFactory factory;

	public VisitQueueDB(ListPersistenceObject listPersistenceObject) {
		this.listPersistenceObject = listPersistenceObject;
		database = listPersistenceObject.getVisitDatabase();
		factory = DefaultPatientVisitFactory.DEFAULT;
	}

	public VisitQueueDB(ListPersistenceObject listPersistenceObject,
			PatientVisitFactory factory) {
		this.listPersistenceObject = listPersistenceObject;
		database = listPersistenceObject.getVisitDatabase();
		this.factory = factory;
	}

	/**
	 * Adds a visit object to the database. Add a reference to a copy of the
	 * object referenced by the aVisit and not the actual object being
	 * referenced by the parameter since we are using internal
	 * 
	 * queues as the database.
	 *
	 * @param aVisit
	 *            The visit to add to the database.
	 */
	@Override
	public void add(Visit aVisit) {
		Patient aPatient = aVisit.getPatient();
		Patient copyPatient = new ClinicPatient(aPatient.getName().getFirstName(), 
				aPatient.getName().getLastName(), aPatient.getRamq().toString());

		copyPatient.setExistingConditions(Optional.of(aPatient.getExistingCondition()));
		if(aPatient.getMedication().isPresent())
			copyPatient.setMedication(aPatient.getMedication());
		copyPatient.setTelephoneNumber(Optional.of(aPatient.getTelephoneNumber()));
		
		Visit copyVisit = new ClinicVisit(copyPatient);
		
		copyVisit.setRegistrationDateAndTime(Optional.of(aVisit.getRegistrationDateAndTime()));
		if(aVisit.getTriageDateAndTime().isPresent())
			copyVisit.setTriageDateAndTime(aVisit.getTriageDateAndTime());
		copyVisit.setPriority(aVisit.getPriority());
		copyVisit.setComplaint(Optional.of(aVisit.getComplaint()));
				
		database.get(copyVisit.getPriority().getCode()).add(copyVisit);
	}

	/**
	 * In order to make the database transactions persistent, the disconnect
	 * method must be implemented. This method must save the database to disk
	 * and assign null to the database field.
	 * 
	 * @throws IOException
	 *             Problems saving or disconnecting from database.
	 */
	@Override
	public void disconnect() throws IOException {
		listPersistenceObject.saveVisitDatabase(database);
		database = null;
	}

	/**
	 * Returns the next visit for a given priority.
	 *
	 * @param priority
	 *            The priority for the visit.
	 *
	 * @return The next visit for a given priority or null.
	 */
	@Override
	public Optional<Visit> getNextVisit(Priority priority) {
		for (Queue<Visit> visit : database) {
			if (!visit.isEmpty()) {
				if (visit.peek().getPriority().getCode() == priority.getCode()) {
					return Optional.ofNullable(visit.peek());
				}
			}
		}
		return null;
	}

	/**
	 * Removes the next visit for a given priority
	 *
	 * @param priority
	 *            The priority for the visit.
	 *
	 */
	@Override
	public void remove(Priority priority) {
		for (Queue<Visit> visit : database) {
			if (!visit.isEmpty())
				if (visit.peek().getPriority().getCode() == priority.getCode()) {
					System.out.println("Removed: " + visit.remove());
					break;
				}
		}

	}

	/**
	 * Returns the count of visits for a given priority
	 *
	 * @param priority
	 *            The priority for the visits.
	 * @return the count of visits for a given priority
	 *
	 */
	@Override
	public int size(Priority priority) {
		int size = 0;
		for (Queue<Visit> visit : database) {
			if(!visit.isEmpty())
				size++;
		}
		return size;
	}

	/**
	 * Overriden toString() method. Returns in a String format
	 * the database of Visit Queues. Also includes the number
	 * of Visits in each Priority queue. 
	 * 
	 * @return String representation of VisitQueueDB
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		int cntr = 0;
		for (Queue<Visit> visitQueue : database) {
			sb.append("Number of priority " + cntr + " visits in Database: "
					+ visitQueue.size() +"\n");
			for (Visit visit : visitQueue) {
				sb.append("\t" + visit.toString() + "\n");
			}
			cntr++;
		}
		return sb.toString();
	}

	/**
	 * The next visit at the head of the queue with the specified priority is
	 * removed from the queue and the database.
	 * It is then added to the corresponding queue with its new
	 * priority.
	 *
	 * @param oldPriority
	 *            The next visit with this priority will be updated.
	 * @param newPriority
	 *            The new priority to assign.
	 * @throws NonExistingVisitException
	 *             No visit with the old priority found.
	 */
	@Override
	public void update(Priority oldPriority, Priority newPriority)
			throws NonExistingVisitException {
		if (size(oldPriority) < 0)
			throw new NonExistingVisitException("No visit of priority " 
					+ oldPriority +" to update!");
		//else
		ClinicVisit newVisit = (ClinicVisit) database.get(oldPriority.getCode()).poll();
		newVisit.setPriority(newPriority);
		database.get(newPriority.getCode()).offer(newVisit);
	}

}