package group4.clinic.data;

import group4.clinic.business.VisitSorter;
import group4.util.ListUtilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.Visit;

/**
 * This class will load, sort, and merge the 13 original
 * patient files. They must first be sorted and placed
 * in a subdirectory called "datafiles\sorted". Then merged
 * and stored in a subdirectory called "datafiles\database".
 * Finally it wil do the same process with all visit files
 * 
 * @author Hugo Pham, Sevan Topalian, Drew Azevedo, Tiffany Le-Nguyen
 * @since JDK 1.8
 */
public class SortMergeApp {

	public static void main(String[] args) {
		//Create the new directories
		(new File("datafiles\\sorted")).mkdir();
		(new File ("datafiles\\duplicates")).mkdir();
		(new File("datafiles\\database")).mkdir();
		
		//Load, Sort, Save, and Merge patient files
		try {
			loadSortSaveMergePatientFiles(13);
			System.out.println("Tasks on Patients files complete");
			loadSortSaveMergeVisitFiles(13);
			System.out.println("Tasks on Visits files complete");
		} catch (FileNotFoundException e) {
			System.out.println("Error file not found - " + e.getMessage());
		}catch (IOException e) {
			System.out.println("Error loading file - " + e.getMessage());
		}
	}
	
	/**
	 * Will load patient files from a folder, sort each one
	 * and place it in datafiles\sorted as sortedPatientsX.txt
	 * and finally merge them all together and be placed in 
	 * datafiles\database as patients.txt
	 * 
	 * Precondition: The user wants to read 13 files
	 * 
	 * @param numberOfFiles number of files in the folder that
	 * 						need to be merged. Assumes that file
	 * 						name format is patientsX.txt
	 * 
	 * @throws IOException 	if a file does not load or destination
	 * 						does not exist
	 */
	public static void loadSortSaveMergePatientFiles(int numberOfFiles) throws IOException {
		//Pull up first file to be part of final merge array
		Patient[] mergedPatientArray = new Patient[0];
						
		// Pull up each of the 13 patient files onto a sorted array.
		for (int i = 1;i<=numberOfFiles;i++) {
			System.out.println("Test: Accessing patient file " + i);
			Patient[] newPatientArray = ClinicFileLoader.getPatientListFromSequentialFile("datafiles\\patients"+i+".txt");
			ListUtilities.sort(newPatientArray);
					
			//Save the newly sorted array to a file sortedPatientX.txt
			ListUtilities.saveListToTextFile(newPatientArray, "datafiles\\sorted\\sortedPatients"+i+".txt");
					
			//Merge the new array to the final one
			try {
				mergedPatientArray = (Patient[]) ListUtilities.merge(newPatientArray, mergedPatientArray, "datafiles\\duplicates\\duplicatesPatient.txt");
			} catch (IOException e){
				System.out.println("Error merging file \"datafiles\\patients"+i+".txt\" - " + e.getMessage());
			}
			//Save the fully merged array to a file
			ListUtilities.saveListToTextFile(mergedPatientArray, "datafiles\\database\\patients.txt");
		}
		
		//Save the final merged array to a file in location datafiles\database
		ListUtilities.saveListToTextFile(mergedPatientArray, "datafiles\\patients.txt");
	}
	
	/**
	 * Will load patient files from a folder, sort each one
	 * and place it in datafiles\sorted as sortedPatientsX.txt
	 * and finally merge them all together and be placed in 
	 * datafiles\database as patients.txt
	 * 
	 * @param numberOfFiles number of files in the folder that
	 * 						need to be merged. Assumes that file
	 * 						name format is patientsX.txt
	 * 
	 * @throws IOException 	if a file does not load or destination
	 * 						does not exist
	 */
	public static void loadSortSaveMergeVisitFiles(int numberOfFiles) throws IOException {
		Patient[] checkPatientArray = new Patient[0];
		Visit[] mergedVisitArray = new Visit[0];
		VisitSorter vSort = new VisitSorter();
						
		// Pull up each of the 13 visit files onto a sorted array.
		for (int i=1;i<=numberOfFiles;i++) {
			System.out.println("Test: Accessing visit file " + i);
			checkPatientArray = ClinicFileLoader.getPatientListFromSequentialFile("datafiles\\patients"+i+".txt");
			Visit[] newVisitArray = ClinicFileLoader.getVisitListFromSequentialFile("datafiles\\visits"+i+".txt", checkPatientArray);
			//sort based on natural order (registered date)
			ListUtilities.sort(newVisitArray);
					
			//Save the newly sorted array to a file sortedPatientX.txt
			ListUtilities.saveListToTextFile(newVisitArray, "datafiles\\sorted\\sortedVisits"+i+".txt");
					
			//Merge the new array to the final one
			try {
				mergedVisitArray = (Visit[]) ListUtilities.merge(newVisitArray, mergedVisitArray, "datafiles\\duplicates\\duplicatesVisit.txt");
			} catch (IOException e){
				System.out.println("Error merging file \"datafiles\\visits"+i+".txt\" - " + e.getMessage());
			}
			//Save the fully merged array to a file
			ListUtilities.saveListToTextFile(mergedVisitArray, "datafiles\\database\\visits.txt");
		}
		//Sort and save the final merged array to a file in location datafiles\database
		//Sort based on priority code
		ListUtilities.sort(mergedVisitArray, vSort);
		ListUtilities.saveListToTextFile(mergedVisitArray, "datafiles\\database\\visits.txt");
		
	}
}
