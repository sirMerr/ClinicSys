/**
 * 
 */
package group4.dawsonclinic;

import java.io.File;
import java.io.IOException;

import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.Visit;
import dw317.clinic.data.interfaces.VisitDAO;
import group4.clinic.business.ClinicPatient;
import group4.clinic.business.ClinicVisit;
import group4.clinic.business.Priority;
import group4.clinic.data.ListPersistenceObject;
import group4.clinic.data.SequentialTextFileList;
import group4.clinic.data.VisitQueueDB;
import group4.util.ListUtilities;

/**
 * 
 * Test application for DawsonClinicPriorityTest.
 * Setup is to be commented out after first use.
 * 
 * @author Tiffany Le-Nguyen, Hugo Pham, Sevan Topalian, Andrew Azevedo
 * @since JDK.1.8
 *
 */
public class DawsonClinicPriorityPolicyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		setup(); 
		ListPersistenceObject listObject = new SequentialTextFileList(
				"testfiles/testPatients.txt", "testfiles/testVisits.txt");
		VisitDAO visitDAO = new VisitQueueDB(listObject);
//		DawsonClinicPriorityPolicy policyTest = new DawsonClinicPriorityPolicy(
//				visitDAO);
		
		/*Having an reanimation patient
		Patient patient = new ClinicPatient("Jon", "Snow", "SNOJ85032312");
		Visit aVisit = new ClinicVisit(patient);
		aVisit.setPriority(Priority.REANIMATION);
		visitDAO.add(aVisit);
		*/
		/*Having an urgent patient
		Patient patient = new ClinicPatient("Bobby", "Jones", "JONB45021803");
		Visit aVisit = new ClinicVisit(patient);
		aVisit.setPriority(Priority.URGENT);
		visitDAO.add(aVisit);
		
//		*///Having an non-urgent patient
//		Patient patient = new ClinicPatient("Jany", "Jones", "JONJ45021803");
//		Visit aVisit = new ClinicVisit(patient);
//		aVisit.setPriority(Priority.NOTURGENT);
//		visitDAO.add(aVisit);
		//addPatient(new ClinicPatient("Jany", "Jones","JONJ45021803"), Priority.NOTURGENT,visitDAO);

		
//		Patient patient1 = new ClinicPatient("Bobby", "Jones", "JONB45021803");
//		Visit aVisit1 = new ClinicVisit(patient);
//		aVisit.setPriority(Priority.URGENT);
//		visitDAO.add(aVisit);
		DawsonClinicPriorityPolicy policyTest = new DawsonClinicPriorityPolicy(
				visitDAO);
		//System.out.println("Adding visit: " + aVisit.toString());
		System.out.println(visitDAO);
		for(int i=0; i < 10; i++)
		{
			System.out.println(policyTest.getNextVisit());
		}

		//teardown();
	}
	
	private static void addPatient(Patient patient, Priority priority, VisitDAO visitDAO) {
		Visit aVisit = new ClinicVisit(patient);
		aVisit.setPriority(priority);
		visitDAO.add(aVisit);
	}

	private static void setup() {
		String[] visits = new String[10];
		visits[0] = "SMIM85122501*2015*9*1*13*30*******";
		visits[1] = "RODM90571001*2015*9*1*14*45*******";
		visits[2] = "LISH87100101*2015*9*1*13*20*"
				+ "2015*12*1*13*45*2*Severe rash";
		visits[3] = "RAOV86112001*2015*9*1*13*50*" + "2015*12*1*14*10*5*Bored";
		File dir = new File("testfiles");
		try {
			if (!dir.exists()) {
				dir.mkdirs();

			}
			ListUtilities
			.saveListToTextFile(visits, "testfiles/testVisits.txt");

		} catch (IOException io) {
			System.out.println("Error creating file in setUp()");
		}


		String[] patients = new String[10];
		patients[0] = "LISH87100101*Shao*Li**"
				+ "DIN*02238645*292 tablets*Pain";
		patients[1]
				= "RAOV86112001*Vishal*Rao*5143634564*"
						+ "NDC*43479-501-51*Pimple punisher*Acne";
		patients[2] = "RODM90571001*Maria*Rodriguez*5145555511****";
		patients[3] = "SMIM85122501*Mike*Smith*5143634564*"
				+ "DIN*02239497*Absorbine Jr*Athlete’s foot";

		try {
			if (!dir.exists()) {
				dir.mkdirs();
			}
			ListUtilities.saveListToTextFile(patients,
					"testfiles/testPatients.txt");
		} catch (IOException io) {
			System.out.println("Error creating file in setUp()");
		}
	}


	/**
	 * Removes the test cases.
	 */
	private static void teardown() {
		File theFile = new File("testfiles/testVisits.txt");
		if (theFile.exists()) {
			theFile.delete();
		}
		File theFile2 = new File("testfiles/testPatients.txt");
		if (theFile2.exists()) {
			theFile2.delete();
		}
	}

}
