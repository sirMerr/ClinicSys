/**
 * 
 */
package group4.clinic.business;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Used to test the ClinicVisit class.
 * Contains multiple test cases to check if a Visit
 * object is valid.
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 */
public class ClinicVisitTest {


	/**s
	 * @param args
	 */
	public static void main(String[] args) {
		
		ClinicPatient aPatient = new ClinicPatient("Billy","Nguyen","NGUB90100845");
		ClinicPatient bPatient = new ClinicPatient("Pearl","Rios","RIOP66560620");
		ClinicPatient cPatient = new ClinicPatient("BILLY","Nguyen","NGUB90100845" );
		try {
			ClinicVisit visitTest = new ClinicVisit(aPatient);
			ClinicVisit visitTest2 = new ClinicVisit(bPatient);
			ClinicVisit visitTest3 = visitTest;
			ClinicVisit visitTest4 = new ClinicVisit(cPatient);
			
			//Comparing/Equals test
			System.out.println("Comparing visitTest & visitTest2. Result should be negative: " + visitTest.compareTo(visitTest2));
			System.out.println("Comparing visitTest & visitTest3. Result should be 0: " + visitTest.compareTo(visitTest3));
			System.out.println("Comparing visitTest & visitTest3. Result should be negative: " + visitTest.compareTo(visitTest4));
			System.out.println("Comparing visitTest & visitTest3. Result should be positive: " + visitTest4.compareTo(visitTest));

			System.out.println("Comparing (equals) visitTest & visitTest2. Result should be false: " + visitTest.equals(visitTest2));
			System.out.println("Comparing (equals) visitTest & visitTest3. Result should be true: " + visitTest.equals(visitTest3));
			
			//Setting and getting test
			Priority priority1 = Priority.LESSURGENT;
			Priority priority2 = Priority.NOTASSIGNED;
			LocalDateTime triage1 = LocalDateTime.now() ;
			visitTest.setPriority(priority1);
			visitTest.setComplaint(Optional.ofNullable("Cold"));
			visitTest.setTriageDateAndTime(Optional.ofNullable(triage1));
			visitTest.setRegistrationDateAndTime(Optional.ofNullable(triage1));
			visitTest2.setPriority(priority2);
			visitTest3.setTriageDateAndTime(2015, 12, 2, 1, 1);
			visitTest3.setRegistrationDateAndTime(2014, 2, 28, 2, 2);
			
			//Invalid values for date
			//visitTest2.setTriageDateAndTime(2015, 13, 2, 1, 1);
			//visitTest2.setRegistrationDateAndTime(2014, 2, 30, 2, 2);
			
			System.out.println("\nvisit.toString() = " + visitTest.toString());
			System.out.println("visit2.toString() = " + visitTest2.toString());
			
			System.out.println("visit1.getComplaint() = " + visitTest.getComplaint());
			System.out.println("visit2.getComplaint() = " + visitTest2.getComplaint());
			
			System.out.println("visit1.getPriority() = " + visitTest.getPriority());
			System.out.println("visit2.getPriority() = " + visitTest2.getPriority());
			System.out.println("visit3.getPriority() = " + visitTest3.getPriority());
			
			System.out.println("visit1.geRegDate() = " + visitTest.getRegistrationDateAndTime());
			System.out.println("visit2.getRegDate() = " + visitTest2.getRegistrationDateAndTime());
			System.out.println("visit3.getRegDate() = " + visitTest3.getRegistrationDateAndTime());
			System.out.println("visit2.getRegistrationDateAndTime() = " + visitTest2.getRegistrationDateAndTime());
			
			//Invalid, not set yet
			//System.out.println("visit2.getTriageDateAndTime() = " + visitTest2.getTriageDateAndTime().get());
			visitTest2.setTriageDateAndTime(2016, 7, 28, 18, 58);
			System.out.println("visit2.getTriageDateAndTime() = " + visitTest2.getTriageDateAndTime().get());
			System.out.println("visit1.getTriageDate() = " + visitTest.getTriageDateAndTime());
			System.out.println("visit3.getTriageDate() = " + visitTest3.getTriageDateAndTime());
			
		}catch (IllegalArgumentException iae){
			System.out.println(iae.getMessage()); 
		}catch (DateTimeException dte) {
			System.out.println(dte.getMessage());
		}
	}

}
