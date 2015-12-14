/**
 * 
 */
package group4.clinic.ui;

import group4.clinic.business.Priority;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.PatientVisitManager;
import dw317.clinic.business.interfaces.Visit;
import dw317.clinic.data.NonExistingPatientException;
import dw317.clinic.data.NonExistingVisitException;
import dw317.lib.Name;

/**
 * @author Hugo
 *
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
	
	public void run() {
		Scanner keyboard = new Scanner(System.in);
		Boolean loopAgain = true;
		int option;
		Command command = null;

		do {
			System.out.print(MENU_OPTIONS);
			option = acceptInt(keyboard);
			keyboard.nextLine();

			switch (option) {
			case 1:
				command = Command.PATIENT_INFO;
				break;
			case 2:
				command = Command.NEW_PATIENT;
				break;
			case 3:
				command = Command.NEW_VISIT;
				break;
			case 4:
				command = Command.NEXT_TO_TRIAGE;
				break;
			case 5:
				command = Command.CHANGE_PRIORITY;
				break;
			case 6:
				command = Command.NEXT_TO_EXAMINE;
				break;
			case 7:
				command = Command.STOP;
				loopAgain = false;
				break;
			default:
				System.out
				.println("\t-Invalid menu options, please try again :)\n");
				break;
			}
			userCommand(command);

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
	
	private void userCommand(Command command){
		if(command != null)
			switch(command) {
			case PATIENT_INFO:
				getPatientInfo();
				break;
			case NEW_PATIENT:
				break;
			case NEW_VISIT:
				break;
			case NEXT_TO_TRIAGE:
				getNextToTriage();
				break;
			case CHANGE_PRIORITY:
				changePriority();
				break;
			case NEXT_TO_EXAMINE:
				break;
			case STOP:
				System.out
				.println("\tThank you for using Dawson Clinic.\n" 
						+ "\tHave a nice day!");
				break;
			}
	}
	
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
			if(option <= 6)
				switch (option) {
				case 1:
					newPriority = Priority.values()[option];
					break;
				case 2:
					newPriority = Priority.values()[option];
					break;
				case 3:
					newPriority = Priority.values()[option];
					break;
				case 4:
					newPriority = Priority.values()[option];
					break;
				case 5:
					newPriority = Priority.values()[option];
					break;
				case 6:
					newPriority = Priority.values()[option];
					break;
				default:
					System.out
					.println("\t-Invalid menu options, please try again :)\n");
				}

			System.out.println(newPriority);
			
		} while (loopAgain);
	}
	
	private void createNewPatient() {
		Patient newPatient;
		
		
	}
	
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
		catch (NoSuchElementException e) {
			System.out.println("No visit available for triage.\n");
		}
	}
	
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
	
}
