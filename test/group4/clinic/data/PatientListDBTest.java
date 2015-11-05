/**
 * 
 */
package group4.clinic.data;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import group4.clinic.business.ClinicPatient;
import group4.clinic.business.Ramq;
import group4.util.ListUtilities;
import dw317.clinic.DefaultPatientVisitFactory;
import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.data.DuplicatePatientException;
import dw317.clinic.data.NonExistingPatientException;
import dw317.lib.medication.DINMedication;
import dw317.lib.medication.Medication;
import dw317.lib.medication.NDCMedication;

/**
 * @author @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 */
public class PatientListDBTest {

	/**
	 * Tests the Patient List DB
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		setup();
		PatientListDB patientList = new PatientListDB
				(new SequentialTextFileList("testfiles/testPatients.txt",
						"testfiles/testVisits.txt"));
		PatientListDB patientList2 = new PatientListDB
				(new SequentialTextFileList("testfiles/testPatients.txt",
						"testfiles/testVisits.txt"), 
						DefaultPatientVisitFactory.DEFAULT);
		
		System.out.println("One parameter constructor: \n"
				+ patientList.toString());
		System.out.println("\nTwo parameter constructor: \n" 
				+ patientList2.toString());
		
		Ramq ramqTest1 = new Ramq("RODM90571001");
		testExists(patientList, ramqTest1,true);
		testGetPatient(patientList, ramqTest1,"RODM90571001*Maria*Rodriguez*5145555511****");
		
		Ramq ramqTest2 = new Ramq("LISH87100101");
		testExists(patientList, ramqTest2, true);
		testGetPatient(patientList, ramqTest2, "LISH87100101*Shao*Li**DIN*02238645*292 tablets*Pain");
		
		Ramq ramqTest3 = new Ramq("ABBA01010101");
		testExists(patientList, ramqTest3, false);
		testGetPatient(patientList, ramqTest3, "-The patient with the following ramq 'ABBA01010101' does not exist.");
		
		Ramq ramqTest4 = new Ramq("SMIM85122501");
		testExists(patientList, ramqTest4, true);
		testGetPatient(patientList, ramqTest4, "SMIM85122501*Mike*Smith*5143634564*DIN*02239497*Absorbine Jr*Athlete’s foot");
		
		testGetPatientsPrescribed(patientList, new DINMedication("02238645","292 tablets"), "02238645");
		testGetPatientsPrescribed(patientList, new NDCMedication("43479-501-51","Pimple punisher"), "43479-501-51");
		testGetPatientsPrescribed(patientList, new NDCMedication("50000-500-50","Pimple giver"), "43479-501-51");
		
		ClinicPatient patient1 = new ClinicPatient("Bob", "Hill", "HILB90121801");
		ClinicPatient patient2 = new ClinicPatient("Lady", "Zelda", "ZELL90521800");
		ClinicPatient patient3 = new ClinicPatient("Alex", "Al", "ALAL90121800");
		testAdd(patientList, patient1);
		testAdd(patientList, patient2);
		testAdd(patientList, patient3);
		
		ClinicPatient patient4 = new ClinicPatient("Lady", "Zelda", "ZELL90521800");
		patient4.setTelephoneNumber(Optional.of("5149999999"));
		patient4.setMedication(Optional.of(new DINMedication("12239125", "Random medication")));
		patient4.setExistingConditions(Optional.of("Stomach ache"));
		
		ClinicPatient patient5 = new ClinicPatient("Alex", "Al", "ALAL90121800");
		patient5.setTelephoneNumber(Optional.of("5149999999"));
		patient5.setMedication(Optional.of(new DINMedication("12239125", "Random medication")));
		patient5.setExistingConditions(Optional.of("Stomach ache"));
		
		testUpdate(patientList, patient4);
		testUpdate(patientList, patient5);
	
		System.out.println(patientList.toString());
		patientList.disconnect();
		teardown();
	}
	
	/**
	 * Prepares the setup with patients.
	 */
	private static void setup()

	{
		String[] patients = new String[10];
		patients [0] = "LISH87100101*Shao*Li**" +
				"DIN*02238645*292 tablets*Pain";
		patients [1] = "RAOV86112001*Vishal*Rao*5143634564*" +
				"NDC*43479-501-51*Pimple punisher*Acne";
		//...
		patients [2] = "RODM90571001*Maria*Rodriguez*5145555511****";
		patients [3] = "SMIM85122501*Mike*Smith*5143634564*"+
				"DIN*02239497*Absorbine Jr*Athlete’s foot";
		patients [4] = "TOSH87100104*Shawn*To**" +
				"DIN*02238645*SUPER TABLETS*Pain";
		
		File dir = new File("testfiles");

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

	private static void teardown() {

		File theFile = new File("testfiles/testPatients.txt");

		if (theFile.exists()) {
			theFile.delete();
		}
	}
	
	private static void testAdd(PatientListDB patientList, Patient aPatient){
		System.out.println("\nTesting PatientListDB add() method");
		System.out.println("Test adding of: \"" + aPatient);
		try {
			patientList.add(aPatient);
		} catch (DuplicatePatientException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		System.out.println("Test result (print list) -> " + patientList.toString());
	}
	
	private static void testExists(PatientListDB patientList, Ramq ramq, boolean expectedResult) {
		System.out.println("\nTesting PatientListDB exists() method");
		System.out.println("Test existence of: \"" + ramq +"\" \n"
				+ "Expected result -> " + expectedResult);
		System.out.println("Test result -> " + patientList.exists(ramq));
		
	}
	
	private static void testGetPatientsPrescribed(PatientListDB patientList, Medication med, String medNum) {
		System.out.println("\nTesting PatientListDB getPatientsPrescribed() method");
		System.out.println("Getting patients with the following medication: \"" 
				+ medNum +"\"");
		for(Patient patient : patientList.getPatientsPrescribed(med))	{
			System.out.println("\t" + patient);
		}
	}
	
	
	private static void testGetPatient(PatientListDB patientList, Ramq ramq, String expectedResult) {
		System.out.println("\nTesting PatientListDB getPatient() method");
		System.out.println("Getting: \"" + ramq +"\" \n"
				+ "Expected result -> " + expectedResult);
		try{
		System.out.println("Test result -> " + patientList.getPatient(ramq));
		}
		catch(Exception e) {
			System.out.println("Test result -> " + e.getMessage());
		}
	}
	
	private static void testUpdate(PatientListDB patientList, Patient modifiedPatient) {
		System.out.println("\nTesting PatientListDB update() method");
		try {
			System.out.println("Updating details of: \"" + patientList.getPatient(modifiedPatient.getRamq()) +"\" \n"
					+ "Expected result -> " + modifiedPatient);
		} catch (NonExistingPatientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			patientList.update(modifiedPatient);
			System.out.println("Test result -> " + patientList.getPatient(modifiedPatient.getRamq()));
		}
		catch(Exception e) {
			System.out.println("Test result -> " + e.getMessage());
		}
	}
}
