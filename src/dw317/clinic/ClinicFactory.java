package dw317.clinic;

import dw317.clinic.business.interfaces.PatientVisitFactory;
import dw317.clinic.business.interfaces.PriorityPolicy;
import dw317.clinic.data.interfaces.VisitDAO;
import java.io.Serializable;

/**
 * In order to decouple the high-level component (i.e. the Clinic façade,
 * described in Part IV below) from the low-level components, thereby respecting
 * the Dependency Inversion Principle (DIP), we will introduce another abstract
 * factory. This will allow us to inject the dependencies through the Clinic’s
 * constructor. This is the same approach that we used in the previous phases of
 * the project.
 * 
 * @author Tiffany
 *
 */
public interface ClinicFactory extends Serializable {
	PatientVisitFactory getPatientVisitFactory();

	PriorityPolicy getPriorityPolicyInstance(VisitDAO visistConnection);
}
