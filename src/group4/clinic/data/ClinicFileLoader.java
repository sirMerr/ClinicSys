package group4.clinic.data;

import group4.clinic.business.ClinicPatient;
import group4.clinic.business.ClinicVisit;
import group4.clinic.business.Priority;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.Visit;
import dw317.lib.medication.DINMedication;
import dw317.lib.medication.Medication;
import dw317.lib.medication.NDCMedication;

/**
 * Utility class (with a private no-parameter constructor to prevent
 * instantiation). This class will contain a series of static utility methods
 * that can be used to load files of various formats (i.e. text sequential and
 * binary files). During this phase, we will only be concerned with text
 * sequential files.
 * 
 * @author Hugo Pham, Sevan Topalian, Drew Azevedo, Tiffany Le-Nguyen
 * @since JDK 1.8
 *
 */
public class ClinicFileLoader {

	/**
	 * Private no-parameter constructor to prevent instantiation
	 */
	private ClinicFileLoader() {

	}

	/**
	 * Custom method that returns a full patient array from the sequential file
	 * or an empty patient array.
	 * 
	 * @param String
	 *            filename
	 * @return Patient [] must be an array whose size is equal to its capacity
	 *         (i.e full to capacity)
	 * @throws IOException
	 */
	public static Patient[] getPatientListFromSequentialFile(String filename)
			throws IOException {
		Patient[] patientList = new ClinicPatient[2]; // check the size later
		int cntrPatients = 0;
		Scanner inputFile = null;
		BufferedReader bufferedReader;

		String record;
		String[] fields = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), StandardCharsets.UTF_8));
			inputFile = new Scanner(bufferedReader);

			while (inputFile.hasNext()) {
				record = inputFile.nextLine();
				try {
					fields = record.split("\\*", -1); // -1 doesn't discard empty
														// tokens
					patientList[cntrPatients] = new ClinicPatient(fields[1],
							fields[2], fields[0]);
					patientList[cntrPatients].setTelephoneNumber(Optional
							.ofNullable(fields[3]));
	
					Medication medication = null; 
					if (fields[4].equalsIgnoreCase("DIN"))
						medication = new DINMedication(fields[5], fields[6]);
					if (fields[4].equalsIgnoreCase("NDC"))
						medication = new NDCMedication(fields[5], fields[6]);
	
					patientList[cntrPatients].setMedication(Optional
							.ofNullable(medication));
					patientList[cntrPatients].setExistingConditions(Optional
							.ofNullable(fields[7]));
				} catch (Exception e) {
					System.out.println("Erroneous Data: "+e.getMessage()); //Finds the errors within the patient files
					cntrPatients--;
				}

				cntrPatients++;

				// check capacity of patientList, grows it if needed
				if (cntrPatients >= patientList.length) {
					patientList = Arrays.copyOf(patientList,
							(patientList.length * 2));
				}

			}
			// Shrink array if necessary
			if (patientList.length > cntrPatients) {
				patientList = Arrays.copyOf(patientList, cntrPatients);
			}

		} catch (IOException e) {
			System.out.println("Could not open: " + filename);
			return new Patient[0];
		} finally {
			// Succeeded in opening file
			if (inputFile != null)
				inputFile.close();
		}
		return patientList;
	}

	/**
	 * This method gets the a ClinicVisit list from a sequential file and
	 * returns a fully loaded array or one of size zero
	 * 
	 * Precondition: Make sure the filename is not null
	 * 
	 * Postcondition: Fully loaded array, if file not found or no records or all
	 * records are incorrect, return an array of size zero
	 * 
	 * @param filename
	 * @param patientList
	 * @return Visit []
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public static Visit[] getVisitListFromSequentialFile(String filename,
			Patient[] patientList) throws IOException,
			IllegalArgumentException, NullPointerException {

		Visit[] visitList = new ClinicVisit[2]; // check the size later
		int cntrVisits = 0;
		Scanner inputFile = null;
		BufferedReader bufferedReader;

		String record;
		String[] fields = null;

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), StandardCharsets.UTF_8));
			inputFile = new Scanner(bufferedReader);

			while (inputFile.hasNext()) {
				record = inputFile.nextLine();
				fields = record.split("\\*", -1);
				try {
					for (int cntrPatients = 0; cntrPatients < patientList.length; cntrPatients++) {
						if (fields[0].equals(patientList[cntrPatients]
								.getRamq().toString())) {
							visitList[cntrVisits] = new ClinicVisit(
									patientList[cntrPatients]);
							visitList[cntrVisits].setRegistrationDateAndTime(
									Integer.parseInt(fields[1]),
									Integer.parseInt(fields[2]),
									Integer.parseInt(fields[3]),
									Integer.parseInt(fields[4]),
									Integer.parseInt(fields[5]));
							if (!fields[6].isEmpty())
								visitList[cntrVisits]
										.setTriageDateAndTime(Optional.ofNullable(LocalDateTime.of(
												Integer.parseInt(fields[6]),
												Integer.parseInt(fields[7]),
												Integer.parseInt(fields[8]),
												Integer.parseInt(fields[9]),
												Integer.parseInt(fields[10]))));
							// set the priority
							Priority priority = null;
							switch (fields[11]) {

							case "":
								priority = Priority.NOTASSIGNED;
								break;

							case "0":
								priority = Priority.NOTASSIGNED;
								break;

							case "1":
								priority = Priority.REANIMATION;
								break;

							case "2":
								priority = Priority.VERYURGENT;
								break;

							case "3":
								priority = Priority.URGENT;
								break;

							case "4":
								priority = Priority.LESSURGENT;
								break;

							case "5":
								priority = Priority.NOTURGENT;
								break;

							}
							visitList[cntrVisits].setPriority(priority);
							visitList[cntrVisits].setComplaint(Optional
									.ofNullable(fields[12]));
							cntrVisits++;
							continue;	
						}
						
						//throw new IllegalArgumentException("The following Visit patient could not be found in the patient list:\n" +
						//record + "\n");
					}

					
					// check capacity of visitList
					if (cntrVisits >= visitList.length) {
						visitList = Arrays.copyOf(visitList,
								(visitList.length * 2));
					}

				

				} catch (IllegalArgumentException iae) {
					//iae.printStackTrace();
					System.out.println(iae.getMessage());
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Error record: " + record);
				}

			}
			// Shrink array if necessary
			if (visitList.length > cntrVisits) {
				visitList = Arrays.copyOf(visitList, cntrVisits);
			}
			return visitList;
		} catch (IOException e) {
			System.out.println("Could not open: " + filename);
			return new Visit[0];
		} finally {
			// Succeeded in opening file
			if (inputFile != null) {
				inputFile.close();
			}

		}

	}
}
