/**
 * 
 */
package group4.dawsonclinic;

import group4.clinic.business.Priority;

import java.io.IOException;
import java.util.Optional;

import dw317.clinic.business.interfaces.PriorityPolicy;
import dw317.clinic.business.interfaces.Visit;
import dw317.clinic.data.interfaces.VisitDAO;

/**
 * Code a DawsonClinicPriorityPolicy class that implements the PriorityPolicy
 * interface. The DawsonClinicPriorityPolicy class gets a VisitDAO object in its
 * constructor and it keeps a reference to this object as an instance variable.
 *
 * Priority 1 (REANIMATION) visit is always dequeued first. This means that you
 * will getNextVisit, remove, and then return the visit. If there are no
 * Priority 1 visits, the remaining queues are dequeued in the following cycle,
 * with the following constraint: a patient with a lower priority is only
 * dequeued if there is not a higher priority code patient who arrive before
 * them (i.e., visit is compared based on registration time) You will probably
 * also need an instance variable to remember where you are in the dequeue
 * cycle: 1. Priority 2 (VERYURGENT) visit dequeued 2. Priority 3 (URGENT) visit
 * dequeued 3. Priority 2 visit dequeued 4. Priority 4 (LESSURGENT) visit
 * dequeued 5. Priority 3 visit dequeued 6. Priority 2 visit dequeued 7.
 * Priority 5 (NOTURGENT) visit dequeued 8. Priority 2 visit dequeued 9.
 * Priority 3 visit dequeued 10. Priority 4 visit dequeued
 *
 * @author Tiffany Le-Nguyen, Hugo Pham, Sevan Topalian, Andrew Azevedo
 * @since JDK.1.8
 *
 */
public class DawsonClinicPriorityPolicy implements PriorityPolicy {
	private static final long serialVersionUID = 42031768871L;
	private VisitDAO visitDAO;
	private int position = 1;

	/**
	 * Public constructor
	 * @param visitDAO
	 */
	public DawsonClinicPriorityPolicy(VisitDAO visitDAO) {
		this.visitDAO = visitDAO;
	}

	/**
	 * Gets the next visit following a priority algorithm, removes it from the
	 * database and saves the database.
	 * 
	 * @return Optional <Visit> if none found, return null
	 * @throws IOExecption
	 *             if disconnect is unable to save/disconnect
	 */
	@Override
	public Optional<Visit> getNextVisit() {

		Optional<Visit> visit = Optional.empty();
		Priority priority = Priority.VERYURGENT;

		// If visitDB is null, return empty Optional object
		if (visitDAO == null) {
			System.out.println("The queue is empty");
			return visit;
		}

		// If Prioriy 1 exists, return them and remove them.
		try {
			if (visitDAO.getNextVisit(Priority.REANIMATION) != null) {
				System.out.println("There is a priority 1 patient to attend");
				visit = visitDAO.getNextVisit(Priority.REANIMATION);
				visitDAO.remove(Priority.REANIMATION);
				try {
					visitDAO.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return visit;
			}
		} catch (NullPointerException npe)
		{
			System.out.println("The rest are untriaged");
			return null;
		}

		// Algorithm for the priority dequeuing
		// If it reaches above 10,the visit should have an unassigned priority
		while (position <= 10) {
			System.out.println("We are at position: " + position
					+ " of the algorithm");
			switch (position) {

			case 1:
			case 3:
			case 6:
			case 8:
				priority = Priority.VERYURGENT; // Priority 2
				break;

			case 2:
			case 5:
			case 9:
				priority = Priority.URGENT; // Priority 3
				break;
			case 4:
			case 10:
				priority = Priority.LESSURGENT; // Priority 4
				break;
			case 7:
				priority = Priority.NOTURGENT; // Priority 5
				break;
			}
			position++;
			if (visitDAO.getNextVisit(priority) != null) {
				System.out.println("Found the next patient to be seen: ");
				visit = visitDAO.getNextVisit(priority);
				visitDAO.remove(priority);
				try {
					visitDAO.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return visit;
			}

		}
		if (position > 10) {
			position = 1;

		}
		System.out.println("Patients left have not been triaged");
		return Optional.empty();

	}
}
