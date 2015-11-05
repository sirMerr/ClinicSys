/**
 * Defines an AbstractMedication type.
 */
package dw317.lib.medication;

/** 
 *  This class implement the Medication interface 
 *  This class is meant to be extended (abstract) by others more specific type of
 *  medication.
 *  
 *  It doesn't provide a zero parameter constructor to force the class that instantiated it
 *  to use the three parameter constructor
 *  It holds three fields:
 *  scheme		A medication scheme
 *  number		The medication number
 *  name		The medication name
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public abstract class AbstractMedication implements Medication {
	private static final long serialVersionUID = 42031768871L;
	private final Scheme scheme;
	private final String number;
	private final String name;
	
	/**
	 * Initializes a new instance of the AbstractMedication class.
	 * 
	 * @param Scheme scheme
	 * @param String number
	 * @param String name
	 */
	public AbstractMedication (Scheme scheme, String number, String name) {
		this.scheme = scheme;
		this.number = number;
		this.name = name;
	}
	
	/**
	 * The equals method compares two medication objects for equality
	 * and returns true if they are equal. Two Medication Objects are
	 * considered equal if they are the same class and have the same
	 * scheme and medication number.
	 * 
	 * @param Object object
	 * 
	 * @return boolean
	 */
	@Override
	public final boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || this.getClass() != object.getClass())
			return false;

		Medication medication = (Medication) object;

		// compare scheme, then number
		if (this.getScheme().equals(medication.getScheme())) {
			if (number.equals(medication.getNumber()))
				return true;
		}
		return false;
		
	}
	
	/**
	 * Returns the medication name.
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the medication number.
	 * 
	 * @return String number
	 */
	public String getNumber() {
		return number;
	}
	
	/**
	 * Returns the scheme.
	 * 
	 * @return Scheme scheme
	 */
	public Scheme getScheme() {
		return scheme;
	}
	
	/**
	 * Returns the hashcode.
	 * 
	 * @return int
	 */
	public final int hashCode() {
		return scheme.hashCode() + number.hashCode();
	}
	
	/**
	 * The toString method returns the Medication Object
	 * in String form. It will be in the following format:
	 * SCHEME*NUMBER*NAME
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return scheme.toString() + "*" + number + "*" + name;
	}

}
