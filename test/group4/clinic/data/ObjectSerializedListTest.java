package group4.clinic.data;

import java.io.IOException;

import group4.clinic.data.ObjectSerializedList;

/**
 * ObjectSerializedList testing
 * 
 * @author Tiffany Le-Nguyen, Hugo Pham, Sevan Topalian, Drew Azevedo
 * @since JDK 1.8
 *
 */
public class ObjectSerializedListTest {
	
public static void main(String[] args){
		ObjectSerializedList test = new ObjectSerializedList
				("testfiles/patientsSerialized.ser", 
						"testfiles/visitsSerialized.ser");
		try
		{
			test.convertSequentialFilesToSerialized("testfiles/patients.txt",
					"testfiles/visits.txt");
		}
		catch(IOException ioe)
		{
			ioe.getMessage();
		}
		
		System.out.println("ObjectSerializedList getPatientDatabase() test: \n" 
				+ test.getPatientDatabase());
		System.out.println("ObjectSerializedList getVisitDatabase() test: \n"
				+ test.getVisitDatabase());
		
		//converting database files to serialized
		ObjectSerializedList serializedDB = new ObjectSerializedList
				("datafiles/database/patients.ser",
						"datafiles/database/visits.ser");
		try
		{
			serializedDB.convertSequentialFilesToSerialized
			("datafiles/database/patients.txt", 
					"datafiles/database/visits.txt");
		}
		catch(IOException ioe)
		{
			ioe.getMessage();
		}
		
	}
}
