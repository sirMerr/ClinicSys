/**
 * 
 */
package group4.clinic.business;

import dw317.lib.Name;

/**
 * Test the Ramq class and its methods.
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 */
public class RamqTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Name name1 = new Name("Tiffany", "Newman");
		//Name name3 = new Name("Billy", "Nguyen");
		//Name name4 = new Name("John", "Smith"); //invalid name for ramq4
		
		Ramq ramq[] = new Ramq[10];
		
		ramq[0] = new Ramq("NEWT80551201");//valid ramq
		//ramq[1] = new Ramq("RIOP66860620");//invalid month value (86)
		//ramq[2] = new Ramq("NGUB90200845", name3);//invalid month value (20)
		//ramq[3] = new Ramq("JONZ91010630", name4);//invalid name
		ramq[4] = new Ramq("DENF94540404");//valid ramq
		ramq[5] = new Ramq("WEBH99522805");//valid ramq
		ramq[6] = new Ramq("NEWT80551201", name1);
		ramq[7] = null;
		ramq[8] = null;
		try{
			for(Ramq ramqID : ramq) {
				if (ramqID == null)
					continue;
				//test Ramq methods
				System.out.println("Ramq toString\t"+ ramqID.toString()); 
				System.out.println("Ramq getBirthdate\t"+ ramqID.getBirthdate());
				System.out.println("Ramq getGender\t"+ ramqID.getGender() + "\n");
				
			}
			//test equals and hashCode method
			System.out.println("Ramq equals test1: NEWT80551201 & NEWT80551201 " + ramq[0].equals(ramq[0]));
			System.out.println("Ramq hashCode test1: NEWT80551201 & NEWT80551201 " + (ramq[0].hashCode() == ramq[0].hashCode()) + "\n");
			
			System.out.println("Ramq equals test2: NEWT80551201 & NEWT80551201 with Name (Tiffany Newman) " + ramq[0].equals(ramq[6]));
			System.out.println("Ramq hashCode test2: NEWT80551201 & NEWT80551201 with Name (Tiffany Newman) " + (ramq[0].hashCode() == ramq[6].hashCode()) + "\n");
			
			System.out.println("Ramq equals test3: NEWT80551201 & DENF94540404 " + ramq[0].equals(ramq[4]));
			System.out.println("Ramq hashCode test3: NEWT80551201 & DENF94540404 " + (ramq[0].hashCode() == ramq[4].hashCode()) + "\n");
			
			System.out.println("Ramq equals test4: DENF94540404 & NEWT80551201 " + ramq[4].equals(ramq[0]));
			System.out.println("Ramq hashCode test4: DENF94540404 & NEWT80551201 " + (ramq[4].hashCode() == ramq[0].hashCode()) + "\n");
			try{
			System.out.println("Ramq equals test5: null to null ");
			System.out.println(ramq[7].equals(ramq[8]));
			} catch (NullPointerException e) {
				System.out.println(e+" -> Working well");
			}
			
		}
		catch (IllegalArgumentException iae) {
			System.out.println(iae.getMessage());
		}

	}

}
