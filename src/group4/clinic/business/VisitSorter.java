package group4.clinic.business;

import java.util.Comparator;

import dw317.clinic.business.interfaces.Visit;

/**
 * VisitSorter implements the Comparator<Visit> interface
 * to enable sorting of Visits based on priority code
 * and registration time. If two Visits have the same priority code
 * they will be ordered by the date. 
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public class VisitSorter implements Comparator<Visit> {
	
	public VisitSorter(){}
	
	/**
	 * Compares two Visits object and sorts them
	 * on the priority code and date.
	 *  
	 * @param v1 	The first Visit object
	 * @param v2	The second Visit object 
	 */
	@Override
	public int compare(Visit v1, Visit v2) {
		if (v1.equals(v2))
			return 0;
		if (v1.getPriority() != v2.getPriority())
			return v1.getPriority().compareTo(v2.getPriority());
		//if same priority, order by date
		return v1.compareTo(v2);
	}
}

