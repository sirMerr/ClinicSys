package dw317.clinic.business.interfaces;

import java.io.Serializable;

import dw317.lib.medication.Medication;
/**
 * 
 * Patient/Visit Factory class that instantiates Patient and Visit objects
 * as well as gets the scheme.
 * 
 * @author Tiffany Le-Nguyen, Drew Azevedo, Hugo Pham, Sevan Topalian
 * 
 */
public interface PatientVisitFactory extends Serializable {
	/**
	 * Instantiates a patient object
	 * @param firstName
	 * @param lastName
	 * @param ramq
	 * @return Patient instance object
	 */
	Patient getPatientInstance(String firstName, String lastName, String ramq);
	/**
	 * Gets patient's medication scheme
	 * @return Medication.Scheme
	 */
	Medication.Scheme getScheme();
	/**
	 * Instantiates a visit object
	 * @param aPatient
	 * @return Visit instance object
	 */
	Visit getVisitInstance(Patient aPatient);
}
