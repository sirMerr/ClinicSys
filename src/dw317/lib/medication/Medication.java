/**
 * Medication interface
 */
package dw317.lib.medication;

import java.io.Serializable;

/**
 * Medication interface used for creating a medication object that contains a
 * number, a scheme and a name.
 * 
 * @author Andrew Azevedo, Tiffany Le-Nguyen, Hugo Pham & Sévan Topalian
 * @since JDK 1.8
 *
 */
public interface Medication extends Serializable {
	String getNumber();

	Scheme getScheme();

	String getName();

	// Medication factory method based on Scheme
	public static Medication getInstance(Scheme scheme, String number,
			String name) {
		Medication medication = null;
		switch (scheme) {
		case DIN:
			medication = new DINMedication(number, name);
			break;
		case NDC:
			medication = new NDCMedication(number, name);
		}
		return medication;
	}

	public enum Scheme {
		DIN("XXXXXXXX"), NDC("XXXX-XXXX-XX");

		private String format;

		private Scheme(String format) {
			this.format = format;
		}

		public String getName() {
			return format;
		}
	}

}
