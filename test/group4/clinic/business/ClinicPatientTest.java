/**
 * ClinicPatient testing
 */
package group4.clinic.business;

import java.util.Optional;

import dw317.lib.medication.DINMedication;
import dw317.lib.medication.Medication;
import dw317.lib.medication.NDCMedication;

/**
 * Used to test the ClinicPatient class.
 * Contains multiple test cases to check if a Patient
 * object is valid.
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public class ClinicPatientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ClinicPatient patientTest = new ClinicPatient("Billy","Nguyen","NGUB90100845" );
			ClinicPatient patientTest2 = new ClinicPatient("Pearl","Rios","RIOP66560620");
			ClinicPatient patientTest3 = new ClinicPatient("BILLY","Nguyen","NGUB90100845" );
			
			/*Invalid cases, invalid RAMQ and name
			1. Too little characters in name + wrong RAMQ
			ClinicPatient patientTest4 = new ClinicPatient("Tiffany","A","ATIF12300123");
			ClinicPatient patientTest4 = new ClinicPatient("A","Tiffany","TIFA12300123");
			
			2. Wrong name (has numbers)
			ClinicPatient patientTest5 = new ClinicPatient("Aira","22222","AIRA56666656");
			
			3. Wrong RAMQ and name
			ClinicPatient patientTest6= new ClinicPatient("Josh","Rios","RIO66560620");
			ClinicPatient patientTest7 = new ClinicPatient("Billy","Bguyen","NGUB90100845" );
			*/
			System.out.println("Comparing(equals) patientTest & patientTest2. Result should be false: " + patientTest.equals(patientTest2));
			System.out.println("Comparing (equals) patientTest & patientTest3. Result should be true: " + patientTest.equals(patientTest3));
			System.out.println("\nComparing patientTest & patientTest2. Result should be negative: " + patientTest.compareTo(patientTest2));
			System.out.println("Comparing patientTest & patientTest3. Result should be 0: " + patientTest.compareTo(patientTest3));
			System.out.println("Comparing patientTest & patientTest2. Result should be positive: " + patientTest2.compareTo(patientTest));

			System.out.println("\npatientTest.toString() = " + patientTest.toString()); //NGUB90100845*Billy*Nguyen*****
			System.out.println("patientTest2.toString() = " + patientTest2.toString()); //RIOP66560620*Pearl*Rios*****
			System.out.println("patientTest.getGender() = " + patientTest.getGender()); //male
			System.out.println("patientTest2.getGender() = " + patientTest2.getGender()); //female
			System.out.println("patientTest.getBirthday() = " + patientTest.getBirthday()); //1990-10-08
			System.out.println("patientTest2.getBirthday() = " + patientTest2.getBirthday()); //1966-06-06
			System.out.println("patientTest.hashCode() = " + patientTest.hashCode()); //-383079491
			System.out.println("patientTest2.hashCode() = " + patientTest2.hashCode()); //409536638
			
			//Testing out setting/getting conditions/medications/telephone numbers
			Medication medication1 = new DINMedication("12345678", "Oxycodone");
			Medication medication2 = new NDCMedication("123456789123","Vitamin D");
			patientTest2.setExistingConditions(Optional.ofNullable("Cold"));
			patientTest.setTelephoneNumber(Optional.ofNullable("5143434872"));
			patientTest.setMedication(Optional.ofNullable(medication1));
			patientTest2.setMedication(Optional.ofNullable(medication2));
			patientTest3.setExistingConditions(Optional.ofNullable(""));
			
			System.out.println("patientTest.getExistingConditions() = " + patientTest.getExistingCondition()); //none
			System.out.println("patientTest.getTelephoneNumber() = " + patientTest.getTelephoneNumber()); //exists
			System.out.println("patientTest.getExistingMedication() = " + patientTest.getMedication()); //oxycodone
			System.out.println("patientTest2.getExistingConditions() = " + patientTest2.getExistingCondition()); //cold
			System.out.println("patientTest2.getTelephoneNumber() = " + patientTest2.getTelephoneNumber()); //none
			System.out.println("patientTest2.getExistingMedication() = " + patientTest2.getMedication()); //vitamin d
			System.out.println("patientTest3.getExistingMedication() = " + patientTest3.getMedication()); //none
			System.out.println("patientTest2.getExistingConditions() = " + patientTest3.getExistingCondition()); //none

		}catch (IllegalArgumentException iae){
			System.out.println(iae.getMessage()); //patientTest3 is to throw an error
		}

	}

}
