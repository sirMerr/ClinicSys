/**
 * 
 */
package group4.clinic.ui;

import group4.clinic.business.Priority;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.PatientVisitManager;
import dw317.clinic.business.interfaces.Visit;
import dw317.clinic.data.DuplicatePatientException;
import dw317.clinic.data.NonExistingPatientException;
import dw317.clinic.data.NonExistingVisitException;
import dw317.lib.Name;
import dw317.lib.medication.Medication;

/**
 * The TextController class contains a run() method
 * which is the main loop of the application. This method
 * will create and display a text menu, and get the user's input.
 * It will then procede to call different private methods based
 * on the user's choice. 
 * 
 * This class also contains the Command enum, which is used
 * to control what the application will do.
 * 
 * @author Tiffany Le-Nguyen, Hugo Pham, Sevan Topalian, Andrew Azevedo
 * @since JDK.1.8
 */
public class TextController { 
	private PatientVisitManager model;
	private static final String MENU_OPTIONS = "Dawson Clinic Menu\n"
			+ "Select a choice from the menu:\n"
			+ "\t1 - Display patient information.\n"
			+ "\t2 - Add a new patient.\n"
			+ "\t3 - Add a new visit.\n"
			+ "\t4 - Display the next visit for triage.\n"
			+ "\t5 - Change the priority of a visit.\n"
			+ "\t6 - Display the next visit for examination.\n"
			+ "\t7 - Exit application.\n" 
			+ "Please enter your choice(1-7) : ";

	public TextController (PatientVisitManager model){
		this.model = model;
	}
	
	/**
	 * Main loop of the application.
	 * Creates a menu where the user can choose one of the following:
	 * Display patient information;
	 * Add a new patient;
	 * Display the next visit for triage;
	 * Change the priority of a visit;
	 * Display the next visit for examination;
	 * Exit application.
	 * 
	 * The user will choose a number between 1 to 7 in order
	 * to navigate in the menu.
	 */
	public void run() {
		Scanner keyboard = new Scanner(System.in);
		Boolean loopAgain = true;
		int option;
		Command command = null;

		do {
			System.out.print(MENU_OPTIONS);
			option = acceptInt(keyboard);
			keyboard.nextLine();
			
			if(option > 0 && option <=7)
			{
				if (option == 7)
					loopAgain=false;
				command = Command.values()[option-1];
				userCommand(command);
			}
			else
				System.out
				.println("\t-Invalid menu options, please try again :)\n");
		} while (loopAgain);
	}

	private static int acceptInt(Scanner keyboard) {
		int option = -10;

		try {
			option = keyboard.nextInt();
		} catch (InputMismatchException ime) {
		}

		return option;

	}
	
	private enum Command {
		PATIENT_INFO,
		NEW_PATIENT, 
		NEW_VISIT, 
		NEXT_TO_TRIAGE,
		CHANGE_PRIORITY,
		NEXT_TO_EXAMINE,
		STOP
	}
	
	
	/**
	 * Private method for changing the priority of a visit.
	 * Displays a menu in order to choose the priority and
	 * handles the user's input.
	 */
	private void changePriority() {
		final String PRIORITY_MENU = "Select the new priority from the menu:\n"
				+"\t1 - REANIMATION\n"
				+"\t2 - VERYURGENT\n"
				+"\t3 - URGENT\n"
				+"\t4 - LESSURGENT\n"
				+"\t5 - NOTURGENT\n\n"
				+ "Enter your choice: ";
		Priority newPriority = null;
		boolean loopAgain = true;
		int option;
		
		Scanner keyboard = new Scanner(System.in);
		do{
			System.out.print(PRIORITY_MENU);
			option = acceptInt(keyboard);
			keyboard.nextLine();
			if(option < 6 && option > 0)
				newPriority = Priority.values()[option];
			else
				System.out
				.println("\t-Invalid menu options, please try again :)\n");

			if(newPriority != null)
			{
				System.out.println("Triage visit priority changed to: "
						+  newPriority);
				try {
					model.changeTriageVisitPriority(newPriority);
				} catch (NonExistingVisitException | NullPointerException e) {
					// TODO Auto-generated catch block
					System.out.println("No more visit for triage.");
				}
				loopAgain = false;
			}
			
		} while (loopAgain);
	}
	
	/**
	 * Private method to create a new Patient.
	 * Asks the user for a first name, last name
	 * RAMQ, telephone number and any existing conditions.
	 */
	private void createNewPatient() {
		String firstName = "";
		String lastName = "";
		String ramq = "";
		String telephone = "";
		String conditions = "";
		Medication medication = null;
		
		Scanner keyboard = new Scanner(System.in);
		try {
			System.out.print("Please enter the first name: ");
			firstName = keyboard.nextLine();
			System.out.print("Please enter the last name: ");
			lastName = keyboard.nextLine();
			System.out.print("Please enter the ramq: ");
			ramq = keyboard.nextLine();
			System.out.print("Please enter the phone number: ");
			telephone = keyboard.nextLine();
			System.out.print("Please enter any existing conditions, "
					+ "or press enter if none available: ");
			conditions = keyboard.nextLine();
			
			model.registerNewPatient(firstName, lastName, ramq, telephone,
					medication, conditions);
			Patient newPatient = model.findPatient(ramq);
			
			System.out.println("Patient information"
					+ "\nRAMQ: " + newPatient.getRamq()
					+ "\n" + newPatient.getName().getLastName() + ", "
					+ newPatient.getName().getFirstName() 
					+ "\nTelephone: " + newPatient.getTelephoneNumber());
		} catch (IllegalArgumentException | DuplicatePatientException | 
				NonExistingPatientException e) {
			System.out.println("Error creating the new patient!");
		}
	}
	
	/**
	 * Private method to create a new Visit.
	 * Asks the user for an existing RAMQ and
	 * the complain of the patient with this RAMQ.
	 */
	private void createNewVisit() {
		String ramq;
		String complaint;
		Patient patient;
		Scanner keyboard = new Scanner(System.in);
		
		System.out.print("Please enter the ramq: ");
		ramq = keyboard.nextLine();
		System.out.println("Please enter the primary complaint"
				+ " of the patient, or press enter if unknown: ");
		complaint = keyboard.nextLine();
		
		try {
			patient = model.findPatient(ramq);
			model.createVisit(patient, complaint);
			System.out.println("Patient information"
					+ "\nRAMQ: " + patient.getRamq()
					+ "\n" + patient.getName().getLastName() + ", "
					+ patient.getName().getFirstName() 
					+ "\nTelephone: " + patient.getTelephoneNumber()
					+ "\n\nNew visit for: "
					+ "\n" + patient.getName().getLastName() + ", "
					+ patient.getName().getFirstName());
		} catch (NonExistingPatientException e) {
			// TODO Auto-generated catch block
			System.out.println("The patient with the following RAMQ doesn't exist. "
					+ ramq);
		}
	}
	
	/**
	 * Returns the next Patient to be examined.
	 */
	private void getNextToExamine() {
		Visit aVisit;
		Name visitName;
		try{
			aVisit = model.nextForExamination().get();
			visitName = aVisit.getPatient().getName();
			System.out.println("Next visit:\n" 
				+ visitName.getLastName() + ", "
				+ visitName.getFirstName() + "\n");
		}
		catch (NoSuchElementException | NullPointerException e) {
			System.out.println("No visit available for examination.\n");
		}
	}
	
	/**
	 * Returns the next visit for triage.
	 */
	private void getNextToTriage() {
		Visit aVisit;
		Name visitName;
		try{
			aVisit = model.nextForTriage().get();
			visitName = aVisit.getPatient().getName();
			System.out.println("Next visit:\n" 
				+ visitName.getLastName() + ", "
				+ visitName.getFirstName() + "\n");
		}
		catch (NoSuchElementException | NullPointerException e) {
			System.out.println("No visit available for triage.\n");
		}
	}
	
	/**
	 * Private method for getting a Patient's information.
	 * Asks the user for an existing RAMQ.
	 * User can type "exit" in order to return to the menu.
	 */
	private void getPatientInfo() {
		boolean loopAgain = true;
		String ramq = null;
		Patient aPatient;
		
		Scanner keyboard = new Scanner(System.in);
		do {
			System.out.print("Please enter the ramq: ");
			String choice = keyboard.nextLine();
			ramq = choice.trim();
			if (ramq.compareToIgnoreCase("EXIT") == 0)
			{
				System.out.println();
				break;
			}
				
			try {
				aPatient = model.findPatient(ramq);
				System.out.println("Patient information\n"
						+ "RAMQ: " + aPatient.getRamq() + "\n"
						+ aPatient.getName().getLastName() + ", "
						+ aPatient.getName().getFirstName() + "\n"
						+ "Telephone: " + aPatient.getTelephoneNumber() + "\n");
				loopAgain = false;
			} catch (NonExistingPatientException | IllegalArgumentException e)
			{
				System.out.println("A patient with this RAMQ doesn't exist");
			}
		} while (loopAgain);
	}
	
	/**
	 * This method controls the what the user chose in the
	 * main menu. It interacts with the Command enum.
	 * @param command
	 */
	private void userCommand(Command command){
		if(command != null)
			switch(command) {
			case PATIENT_INFO:
				getPatientInfo();
				break;
			case NEW_PATIENT:
				createNewPatient();
				break;
			case NEW_VISIT:
				createNewVisit();
				break;
			case NEXT_TO_TRIAGE:
				getNextToTriage();
				break;
			case CHANGE_PRIORITY:
				changePriority();
				break;
			case NEXT_TO_EXAMINE:
				getNextToExamine();
				break;
			case STOP:
				try {
					model.closeClinic();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out
				.println("\tThank you for using Dawson Clinic.\n" 
						+ "\tHave a nice day!");
				break;
			}
	}
}
