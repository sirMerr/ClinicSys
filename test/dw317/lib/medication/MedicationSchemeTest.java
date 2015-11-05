package dw317.lib.medication;

public class MedicationSchemeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Medication.Scheme schemeDIN = Medication.Scheme.DIN;
		Medication.Scheme schemeNDC = Medication.Scheme.NDC;
		
		System.out.println(schemeDIN);
		System.out.println(schemeDIN.getName());
		System.out.println(schemeNDC);	
		System.out.println(schemeNDC.getName());
	}

}
