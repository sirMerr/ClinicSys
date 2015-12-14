package group4.clinic.data;

import java.io.File;
import java.io.IOException;

import dw317.clinic.DefaultPatientVisitFactory;
import group4.clinic.data.PatientListDB;
import group4.util.ListUtilities;
import group4.clinic.data.ObjectSerializedList;

/**
 * ObjectSerializedList testing
 * 
 * @author Tiffany Le-Nguyen, Hugo Pham, Sevan Topalian, Drew Azevedo
 * @since JDK 1.8
 *
 */
public class ObjectSerializedListTest {

	public static void main(String[] args) {

		// Two params
		setup();
		testTwoParamConstructor("testfiles/testPatients.txt",
				"testfiles/testVisits.txt", DefaultPatientVisitFactory.DEFAULT);
		testTwoParamConstructor(null, null, DefaultPatientVisitFactory.DEFAULT);
		testTwoParamConstructor("testfiles/testPatients.txt",
				"testfiles/testVisits.txt", null);
		testTwoParamConstructor(null, null, null);
		teardown();

		//Test Visit Database 
		setup();
		testVisitDatabase(
				"testfiles/testPatients.txt", "testfiles/testVisits.txt",
				DefaultPatientVisitFactory.DEFAULT);
		testVisitDatabase(null, null,
				DefaultPatientVisitFactory.DEFAULT);
		testVisitDatabase("testfiles/testPatients.txt", "testfiles/testVisits.txt", null);
		testVisitDatabase(null, null, null);
		teardown();
		
		//Test patient database
		setup();
		testPatientDatabase("testfiles/testPatients.txt", "testfiles/testVisits.txt",
				DefaultPatientVisitFactory.DEFAULT);
		testPatientDatabase(null,null, DefaultPatientVisitFactory.DEFAULT);
		testPatientDatabase("testfiles/testPatients.txt", "testfiles/testVisits.txt", null);
		testPatientDatabase(null, null, null);
		teardown();

	}

	private static void testTwoParamConstructor(String patientFile,
			String visitFile, DefaultPatientVisitFactory factory) {
		try {
			ObjectSerializedList listObject = new ObjectSerializedList(
					"testfiles/testVisits.ser",
					"testfiles/testPatients.ser");
			listObject.convertSequentialFilesToSerialized(patientFile,
					visitFile);

			@SuppressWarnings("unused")
			PatientListDB patients = new PatientListDB(listObject, factory);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
	}


	private static void testPatientDatabase(String patientFile,
			String visitFile, DefaultPatientVisitFactory factory) {
		try {

			ObjectSerializedList listObject = new ObjectSerializedList(
					"testfiles/testVisits.ser",
					"testfiles/testPatients.ser");

			listObject.convertSequentialFilesToSerialized(patientFile,
					visitFile);

			@SuppressWarnings("unused")
			PatientListDB patients = new PatientListDB(listObject, factory);
			System.out.println(listObject.getPatientDatabase());
 
		} catch (Exception e) {
			System.out.println(e.getClass()+ " " + e.getMessage());
		}
		System.out.println();
	}


	private static void testVisitDatabase(
			String patientFile, String visitFile,
			DefaultPatientVisitFactory factory) {
		try {
			ObjectSerializedList listObject = new ObjectSerializedList(
					"testfiles/testVisits.ser",
					"testfiles/testPatients.ser");
			listObject.convertSequentialFilesToSerialized(patientFile,
					visitFile);
			System.out.println(listObject.getVisitDatabase());

		} catch (Exception e) {
			System.out.println(e.getClass()+ " " + e.getMessage());
		}
		System.out.println();
	}

	/**
	 * Set up patients and visits
	 */
	private static void setup() {
		String[] patients = new String[10];
		patients[0] = "LISH87100101*Shao*Li**"
				+ "DIN*02238645*292 tablets*Pain";
		patients[1] = "RAOV86112001*Vishal*Rao*5143634564*"
				+ "NDC*43479-501-51*Pimple punisher*Acne";
		patients[2] = "RODM90571001*Maria*Rodriguez*5145555511****";
		patients[3] = "SMIM85122501*Mike*Smith*5143634564*"
				+ "DIN*02239497*Absorbine Jr*Athlete’s foot";

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
			ListUtilities.saveListToTextFile(patients,
					"testfiles/testPatients.txt");
			ListUtilities
					.saveListToTextFile(visits, "testfiles/testVisits.txt");
		} catch (IOException io) {
			System.out.println("Error creating file");
		}
	}

	/**
	 * Delete existing files
	 */
	private static void teardown() {
		File file = new File("testfiles/testPatients.txt");
		
		if (file.exists()) {
			file.delete();
		}
		file = new File("testfiles/testVisits.txt");
		if (file.exists()) {
			file.delete();
		}
	}
}
