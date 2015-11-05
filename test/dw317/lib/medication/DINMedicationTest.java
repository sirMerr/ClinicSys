/**
 * 
 */
package dw317.lib.medication;

/**
 * Used to test the DINMedication class.
 * Contains multiple test cases to check if a medication
 * object is valid.
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public class DINMedicationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DINMedication medicationTest = new DINMedication("02150328", "something tho");
			DINMedication medicationTest2 = new DINMedication("02150320", "something tho2");
			
			
			System.out.println("Comparing medicationTest & medicationTest 2. Result should be false: " + medicationTest.equals(medicationTest2));
			System.out.println("\nmedicationTest = " + medicationTest.toString());
			System.out.println("medicationTest2 = " + medicationTest2.toString());
			
			DINMedication medicationTest3 = new DINMedication("adasdasd", "notCool");
			System.out.println(medicationTest3.toString());
		}
		catch (IllegalArgumentException iae) {
			System.out.println("\nmedicationTest3 instantiation failed. " + iae.getMessage());
		}

	}

}
