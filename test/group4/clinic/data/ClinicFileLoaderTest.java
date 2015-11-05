
/**
 * 
 */
package group4.clinic.data;

import group4.clinic.business.ClinicPatient;
import group4.clinic.business.ClinicVisit;

import java.io.IOException;

import dw317.clinic.business.interfaces.Patient;



/**
 * Test application for ClinicFileLoader
 * 
 * @author Hugo Pham, Sevan Topalian, Drew Azevedo, Tiffany Le-Nguyen
 * @since JDK 1.8
 *
 */
public class ClinicFileLoaderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Test getPatientListFromSequentialFiles method
		try {
			//valid
			
			ClinicPatient [] patientTest1 = (ClinicPatient[]) ClinicFileLoader.getPatientListFromSequentialFile("datafiles/patients4.txt");
			for (ClinicPatient elements: patientTest1)
			{
				System.out.println(elements.toString());
			}
			
			System.out.println("\n**************************************\n");
			
			//Test getVisitListFromSequentialFiles method ->>>>>> this gives null after the third visit
			ClinicVisit[] visitTest = (ClinicVisit[]) ClinicFileLoader.getVisitListFromSequentialFile("datafiles/visits1.txt", ClinicFileLoader.getPatientListFromSequentialFile("datafiles/patients1.txt"));
			for (ClinicVisit elements: visitTest)
			{
				System.out.println(elements.toString());
			}
			
			System.out.println("\n**************************************\n");
			
			//test invalid path
			//ClinicFileLoader.getPatientListFromSequentialFile("invalidpath");
			//ClinicFileLoader.getVisitListFromSequentialFile("invalidpath");
			
			//test with invalid cases, should skip the invalid cases. 
			ClinicPatient [] patientTest2 = (ClinicPatient[]) ClinicFileLoader.getPatientListFromSequentialFile("datafiles/invalidPatients.txt");
			for (ClinicPatient elements: patientTest2)
			{
				System.out.println(elements);
			}
			System.out.println("\n**************************************\n");
			
			//Should be null.
			ClinicVisit [] visitTest2 = (ClinicVisit[]) ClinicFileLoader.getVisitListFromSequentialFile("datafiles/invalidVisits.txt", patientTest1);
			for (ClinicVisit elements: visitTest2)
			{
				System.out.println(elements.toString());
			}
			
			System.out.println("Testing inexistance file.");
			Patient [] patientTest3 = ClinicFileLoader.getPatientListFromSequentialFile("datafiles/patient66.txt");
			for (Patient elements: patientTest3)
			{
				System.out.println(elements);
			}
			System.out.println("\n**************************************\n");
			
			System.out.println("Testing null fileinput.");
			Patient [] patientTest4 = ClinicFileLoader.getPatientListFromSequentialFile(null);
			for (Patient elements: patientTest4)
			{
				System.out.println(elements);
			}
			System.out.println("\n**************************************\n");
		}
		catch (IllegalArgumentException | NullPointerException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
