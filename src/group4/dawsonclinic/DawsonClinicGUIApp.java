/**
 * 
 */
package group4.dawsonclinic;

import group4.clinic.business.Clinic;
import group4.clinic.data.ObjectSerializedList;
import group4.clinic.data.PatientListDB;
import group4.clinic.data.VisitQueueDB;
import group4.clinic.ui.GUIViewController;
import group4.dawsonclinic.DawsonClinicFactory;
import dw317.clinic.ClinicFactory;
import dw317.clinic.data.interfaces.PatientDAO;
import dw317.clinic.data.interfaces.VisitDAO;

/**
 * @author Tiffany
 *
 */
public class DawsonClinicGUIApp {
	public static void main(String[] args) {
		
		ClinicFactory factory = DawsonClinicFactory.DAWSON_CLINIC;
		PatientDAO patientDb = new PatientListDB(new ObjectSerializedList(
				"testfiles/testPatientsSerialized.ser",
				"testfiles/testVisitsSerialized.ser"),
				factory.getPatientVisitFactory());
		
		VisitDAO visitDb = new VisitQueueDB(new ObjectSerializedList(
				"testfiles/testPatientsSerialized.ser",
				"testfiles/testVisitsSerialized.ser"),
				factory.getPatientVisitFactory());
		
		Clinic model = new Clinic(patientDb, visitDb, factory);
		GUIViewController app = new GUIViewController(model);
	}
}
