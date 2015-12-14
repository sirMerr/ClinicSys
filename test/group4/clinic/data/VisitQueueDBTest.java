package group4.clinic.data;

import group4.clinic.business.ClinicPatient;
import group4.clinic.business.ClinicVisit;
import group4.clinic.business.Priority;
import group4.util.ListUtilities;

import java.io.File;
import java.io.IOException;

import dw317.clinic.DefaultPatientVisitFactory;
import dw317.clinic.data.NonExistingVisitException;

public class VisitQueueDBTest {

	/**
	 * Testing VisitQueueDB methods
	 * @param args
	 * @throws IOException
	 * 
	 * 
	 * @author @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
	 * @since JDK 1.8
	 */
	public static void main(String[] args) throws IOException {
		setup();
		VisitQueueDB visitQueue = new VisitQueueDB
				(new SequentialTextFileList("testfiles/testPatients.txt",
						"testfiles/testVisits.txt"));
		VisitQueueDB visitQueue2 = new VisitQueueDB
				(new SequentialTextFileList("testfiles/testPatients.txt",
						"testfiles/testVisits.txt"), 
						DefaultPatientVisitFactory.DEFAULT);
		
		System.out.println("One parameter constructor: \n"
				+ visitQueue.toString());
		System.out.println("\nTwo parameter constructor: \n" 
				+ visitQueue2.toString());
		
		ClinicVisit visit1 = new ClinicVisit(new ClinicPatient("Lady", "Zelda", "ZELL90521800"));
		visit1.setPriority(Priority.NOTURGENT);
		testAdd(visitQueue, new ClinicVisit(new ClinicPatient("Bob", "Hill", "HILB90121801")));
		testAdd(visitQueue, visit1);
		
		testGetNextVisit(visitQueue, Priority.NOTASSIGNED);
		testGetNextVisit(visitQueue, Priority.LESSURGENT);
		
		testRemove(visitQueue, Priority.NOTASSIGNED, 
				"SMIM85122501*2015*9*1*13*30*******");
		testRemove(visitQueue, Priority.NOTASSIGNED,
				"RODM90571001*2015*9*1*14*45*******");
		testRemove(visitQueue, Priority.REANIMATION,
				"Nothing to remove!");
		testRemove(visitQueue, Priority.NOTURGENT,
				"RAOV86112001*2015*9*1*13*50*" +
						"2015*12*1*14*10*5*Bored");
		
		testUpdate(visitQueue, Priority.NOTASSIGNED, Priority.LESSURGENT);
		testUpdate(visitQueue, Priority.NOTURGENT, Priority.NOTASSIGNED);
		
		System.out.println(visitQueue.toString());
		visitQueue.disconnect();
		teardown();
	}
	/**
	 * Sets up the patients.
	 */
	private static void setup()
	{
		String[] visits = new String[10];
		visits [0] = "SMIM85122501*2015*9*1*13*30*******";
		visits [1] = "RODM90571001*2015*9*1*14*45*******";
		visits [2] = "LISH87100101*2015*9*1*13*20*" +
				"2015*12*1*13*45*2*Severe rash";
		visits [3] = "RAOV86112001*2015*9*1*13*50*" +
				"2015*12*1*14*10*5*Bored";
		File dir = new File("testfiles");
		try{
			if (!dir.exists()){
				dir.mkdirs();
			}
			ListUtilities.saveListToTextFile(visits,
					"testfiles/testVisits.txt");
		}
		catch(IOException io){
			System.out.println
			("Error creating file in setUp()");
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
	/**
	 * Testing add method
	 * @param visitQueue
	 * @param aVisit
	 */
	private static void testAdd(VisitQueueDB visitQueue, ClinicVisit aVisit) {
		System.out.println("\nTesting VisitQueueDB add() method");
		System.out.println("Adding the following Visit: \"" 
				+ aVisit +"\"");
		visitQueue.add(aVisit);
		System.out.println("Printing Visit Queue: " +
				"\n" + visitQueue.toString());
	}
	/**
	 * Testing getNextVisit method
	 * 
	 * @param visitQueue
	 * @param priority
	 */
	private static void testGetNextVisit(VisitQueueDB visitQueue, Priority priority) {
		System.out.println("\nTesting VisitQueueDB getNextVisit() method");
		System.out.println("Getting Visit with the following priority: \"" 
				+ priority +"\"");
	
		System.out.print("Result: ");

		if(visitQueue.getNextVisit(priority) != null)
			System.out.print(visitQueue.getNextVisit(priority).get() + "\n");
		else
			System.out.println();
		
	}
	/**
	 * Test remove method
	 * 
	 * @param visitQueue
	 * @param priority
	 * @param expectedResult
	 */
	private static void testRemove(VisitQueueDB visitQueue, Priority priority, 
			String expectedResult) {
		System.out.println("\nTesting VisitQueueDB remove() method");
		System.out.println("Removing Visit with the following priority: \"" 
				+ priority +"\"");
		System.out.println("Expected Result - removal of: " + expectedResult);
		visitQueue.remove(priority);
		System.out.println("Printing Visit Queue: " +
		"\n" + visitQueue.toString());
	}
	
	/**
	 * Test update method
	 * 
	 * @param visitQueue
	 * @param oldPriority
	 * @param newPriority
	 */
	private static void testUpdate(VisitQueueDB visitQueue, Priority oldPriority, Priority newPriority) {
		System.out.println("\nTesting VisitQueueDB update() method");
		System.out.println("Updating the first Visit with the following priority: \"" 
				+ oldPriority +"\" to \"" + newPriority +"\"");
		System.out.println("Updating " + visitQueue.getNextVisit(oldPriority).get() + "\n");
		try {
			visitQueue.update(oldPriority, newPriority);
		} catch (NonExistingVisitException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

}
