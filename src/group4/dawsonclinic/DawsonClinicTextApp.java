package group4.dawsonclinic;  

import dw317.clinic.ClinicFactory; 
import dw317.clinic.data.interfaces.PatientDAO; 
import dw317.clinic.data.interfaces.VisitDAO; 
import group4.clinic.business.Clinic; 
import group4.clinic.data.PatientListDB; 
import group4.clinic.data.VisitQueueDB; 
import group4.clinic.data.ObjectSerializedList; 
import group4.clinic.ui.TextController; 
import group4.clinic.ui.TextView; 
import group4.dawsonclinic.DawsonClinicFactory; 

public class DawsonClinicTextApp {  
	public static void main(String[] args) 
	{   
		ClinicFactory factory = 
				DawsonClinicFactory.DAWSON_CLINIC;   
		PatientDAO patientDb = new PatientListDB
				(new ObjectSerializedList("datafiles/database/patients.ser" , 
						"datafiles/database/visits.ser"), factory.getPatientVisitFactory());
		VisitDAO visitDb = new VisitQueueDB
				(new ObjectSerializedList("datafiles/database/patients.ser" , 
						"datafiles/database/visits.ser"), factory.getPatientVisitFactory());
		
		Clinic model = new Clinic(patientDb, visitDb, factory);
		TextView view = new TextView(model);   
		TextController controller = new TextController(model);   
		controller.run();  
	} 
} 