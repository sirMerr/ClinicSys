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
				("testfiles/testPatients", "testfiles/testvisits.txt");
		try
		{
			test.convertSequentialFilesToSerialized("testfiles/testpatients.txt", "testfiles/testvisits.txt");
		}
		catch(IOException ioe)
		{
			ioe.getMessage();
		}

	}
}