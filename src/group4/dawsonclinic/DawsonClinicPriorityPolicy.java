/**
 * 
 */
package group4.dawsonclinic;

import group4.clinic.business.Priority;

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
 * @since JDK.1.7
 *
 */
public class DawsonClinicPriorityPolicy implements PriorityPolicy {

	private static final long serialVersionUID = 42031768871L;
	private VisitDAO visitDB;
	private int position = 1;

	public DawsonClinicPriorityPolicy(VisitDAO visitDB) {
		this.visitDB = visitDB;
	}

	@Override
	public Optional<Visit> getNextVisit() {

		Optional<Visit> visit = null;
		Priority priority = Priority.VERYURGENT;

		//If visitDB is null, return empty Optional object
		if (visitDB == null) {
			return visit;
		}
		// If Prioriy 1 exists, return them and remove them.
		while (visitDB.getNextVisit(Priority.REANIMATION) != null) {
			visit = visitDB.getNextVisit(Priority.REANIMATION);
			visitDB.remove(Priority.REANIMATION);
			return visit;
		}

		//Algorithm for the priority dequeuing 
		//If there is nothing and it reaches position 10, 
		//there's an issue.
		while (visitDB.getNextVisit(priority) == null && position <= 10) 
		{
			switch (position) {
			
			case 1: case 3: case 6:
			case 8:
				priority = Priority.VERYURGENT; //Priority 2
				break;

			case 2: case 5:
			case 9:
				priority = Priority.URGENT; //Priority 3
				break;
			case 4: case 10:
				priority = Priority.LESSURGENT; //Priority 4
				break;
			case 7:
				priority = Priority.NOTURGENT; //Priority 5
				break;
			}
			position++;
		}
		if (position > 10) {
			position = 0;
		}
		visit = visitDB.getNextVisit(priority);
		visitDB.remove(priority);
		
		return visit;
	}

}
