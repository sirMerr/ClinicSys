/**
 * 
 */
package group4.dawsonclinic;

import java.io.File;
import java.io.IOException;

import group4.clinic.business.ClinicVisit;
import group4.clinic.data.SequentialTextFileList;
import group4.clinic.data.VisitQueueDB;
import group4.util.ListUtilities;

/**
 * @author Tiffany
 *
 */
public class DawsonClinicPriorityPolicyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		setup();
		VisitQueueDB visitQueue = new VisitQueueDB(new SequentialTextFileList(
				"testfiles/testPatients.txt", "testfiles/testVisits.txt"));
		DawsonClinicPriorityPolicy policyTest = new DawsonClinicPriorityPolicy(
				visitQueue);
		System.out.println(policyTest.getNextVisit());
		

	//	teardown();
	}

	private static void setup() {
		String[] visits = new String[10];
		visits[0] = "SMIM85122501*2015*9*1*13*30*******";
		visits[1] = "RODM90571001*2015*9*1*14*45*******";
		visits[2] = "LISH87100101*2015*9*1*13*20*"
				+ "2015*12*1*13*45*2*Severe rash";
		visits[3] = "RAOV86112001*2015*9*1*13*50*" + "2015*12*1*14*10*1*Bored";
		visits[4] = "WAKN60022987*2014*12*31*23*52*2015*1*1*00*08*1*Heart stroke";
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
		patients[1] = "RAOV86112001*Vishal*Rao*5143634564*"
				+ "NDC*43479-501-51*Pimple punisher*Acne";
		patients[2] = "RODM90571001*Maria*Rodriguez*5145555511****";
		patients[3] = "SMIM85122501*Mike*Smith*5143634564*"
				+ "DIN*02239497*Absorbine Jr*Athlete’s foot";
		patients[4] = "WAKN60022987*Norio*Wakamoto*4389945870*NDC*0363-8001-01*Nicotine Transdermal System*Smoking Addiction";

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
	}

}
