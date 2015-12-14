/**
 * 
 */
package group4.clinic.data;

import java.io.IOException;

/**
 * Serializes the sequential files used as databases into 
 * serialized files. These files will be used by the main
 * Clinic application. 
 * The serialized files will be in:
 * datafiles/database/
 * 
 * @author Tiffany Le-Nguyen, Hugo Pham, Sevan Topalian, Drew Azevedo
 * @since JDK 1.8
 *
 */
public class SerializeDatabases {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ObjectSerializedList databasesSer = new ObjectSerializedList(
				"datafiles/database/patients.ser",
				"datafiles/database/visits.ser");
		try {
			System.out.println("Serializing the database:\n"
					+ "1. datafiles/database/patients.txt\n"
					+ "2. datafiles/database/visits.txt\n"
					+ "...");
			databasesSer.convertSequentialFilesToSerialized(
					"datafiles/database/patients.txt",
					"datafiles/database/visits.txt");
			System.out.println("Done!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
