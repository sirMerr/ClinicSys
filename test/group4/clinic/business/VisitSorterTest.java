/**
 * 
 */
package group4.clinic.business;

/**
 * Tests the VisitSorter class in group4.clinic.business
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public class VisitSorterTest {

	
	public static void main(String[] args) {
		ClinicPatient aPatient = new ClinicPatient("Billy","Nguyen","NGUB90100845");
		ClinicPatient bPatient = new ClinicPatient("Pearl","Rios","RIOP66560620");
		
		try {
			ClinicVisit visitTest = new ClinicVisit(aPatient);
			ClinicVisit visitTest2 = new ClinicVisit(bPatient);
			
			//test1
			String testCase = "Test 1: compare LESSURGENT to URGENT\n" 
					+ "Expected result: > 0\n";
			visitTest.setPriority(Priority.LESSURGENT);
			visitTest2.setPriority(Priority.URGENT);			

			testCompare(testCase,visitTest,visitTest2);
			
			//test2
			testCase = "Test 2: compare URGENT to URGENT with same date.\n"
					+ "Expected result: 0\n";
			visitTest.setPriority(Priority.URGENT);
			visitTest2.setPriority(Priority.URGENT);
			visitTest.setRegistrationDateAndTime(2016, 12, 28, 14, 30);
			visitTest2.setRegistrationDateAndTime(2016, 12, 28, 14, 30);
			
			testCompare(testCase,visitTest,visitTest2);
			
			//test3
			testCase = "Test 3: compare VERYURGENT to NOTURGENT\n" 
					+ "Expected result: < 0\n";
			visitTest.setPriority(Priority.VERYURGENT);
			visitTest2.setPriority(Priority.NOTURGENT);
			
			testCompare(testCase,visitTest,visitTest2);
			
			//test4
			testCase = "Test 4: compare VERYURGENT to VERYURGENT with different dates\n" 
					+ "Expected result: > 0\n";
			visitTest.setPriority(Priority.VERYURGENT);
			visitTest2.setPriority(Priority.VERYURGENT);
			visitTest.setRegistrationDateAndTime(2020, 12, 28, 14, 30);
			visitTest2.setRegistrationDateAndTime(2015, 12, 28, 14, 30);
			
			testCompare(testCase,visitTest,visitTest2);
		}catch (IllegalArgumentException iae){
			System.out.println(iae.getMessage()); 
		}

	}
	
	public static void testCompare(String testCase,ClinicVisit v1, ClinicVisit v2) {
		System.out.print(testCase);
		
		VisitSorter visitSorter = new VisitSorter();
		int result = visitSorter.compare(v1, v2);
		
		System.out.println("Comparing: " + v1.toString() + " & "
				+ v2.toString() + "\nResult: "
				+ result + "\n");
		
	}

}
