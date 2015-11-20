/**
 * 
 */
package dw317.clinic.business.interfaces;

import java.io.Serializable;
import java.util.Optional;

/**
 * The Strategy Design Pattern enable’s the algorithm to be selected at
 * run-time, thus facilitating the interchanging of policies. The concrete
 * strategies will program to the following PriorityPolicy interface.
 * 
 * @author Tiffany
 *
 */
public interface PriorityPolicy extends Serializable {
	/**
	 * Returns the next visit of the next patient that must be examined.
	 *
	 * @return The visit of the next patient that must be examined.
	 */
	Optional<Visit> getNextVisit();
}
