/**
 * 
 */
package group4.dawsonclinic;

import java.util.Optional;

import dw317.clinic.business.interfaces.PriorityPolicy;
import dw317.clinic.business.interfaces.Visit;

/**
 * Code a DawsonClinicPriorityPolicy class that implements the PriorityPolicy
 * interface. The DawsonClinicPriorityPolicy class gets a VisitDAO object in its
 * constructor and it keeps a reference to this object as an instance variable.
 *
 * Priority 1 (REANIMATION) visit is always dequeued first. This means that you will
 * getNextVisit, remove, and then return the visit. If there are no Priority 1 visits, 
 * the remaining queues are dequeued in the following cycle, with the following constraint:
 * a patient with a lower priority is only dequeued if there is not a higher priority code patient 
 * who arrive before them (i.e., visit is compared based on registration time)
 * You will probably also need an instance variable to remember where you are in the
 * dequeue cycle:
 *	1. Priority 2 (VERYURGENT) visit dequeued
 *	2. Priority 3 (URGENT) visit dequeued
 *	3. Priority 2 visit dequeued
 *	4. Priority 4 (LESSURGENT) visit dequeued
 *	5. Priority 3 visit dequeued
 * 	6. Priority 2 visit dequeued
 *	7. Priority 5 (NOTURGENT) visit dequeued
 *	8. Priority 2 visit dequeued
 *	9. Priority 3 visit dequeued
 *	10. Priority 4 visit dequeued
 *
 * @author Tiffany Le-Nguyen, Hugo Pham, Sevan Topalian, Andrew Azevedo
 * @since JDK.1.7
 *
 */
public class DawsonClinicPriorityPolicy implements PriorityPolicy {

	private static final long serialVersionUID = 42031768871L;

	@Override
	public Optional<Visit> getNextVisit() {
		// TODO Auto-generated method stub
		return null;
	}

}
