/**
 * NDCMedicationTest class
 */
package dw317.lib.medication;

/**
 * Used to test the NDCMedication class.
 * Contains multiple test cases to check if a medication
 * object is valid. 
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public class NDCMedicationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			NDCMedication medicationTest = new NDCMedication("1234-5678-90", "NDC medication 1");
			NDCMedication medicationTest2 = new NDCMedication("0123-4567-89", "NDC medication 2");
			
			
			System.out.println("Comparing medicationTest & medicationTest 2. Result should be false: " + medicationTest.equals(medicationTest2));
			System.out.println("\nmedicationTest = " + medicationTest.toString());
			System.out.println("medicationTest2 = " + medicationTest2.toString());
			
			NDCMedication medicationTest3 = new NDCMedication("12-12-123456", "Not a NDC medication");
			System.out.println(medicationTest3.toString());
		}
		catch (IllegalArgumentException iae) {
			System.out.println("\nmedicationTest3 instantiation failed. " + iae.getMessage());
		}

	}

}
