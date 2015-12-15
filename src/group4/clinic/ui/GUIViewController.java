/**
 * 
 */
package group4.clinic.ui;

import group4.clinic.business.Clinic;
import group4.clinic.business.Priority;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dw317.clinic.business.interfaces.Patient;
import dw317.clinic.business.interfaces.Visit;
import dw317.clinic.data.NonExistingVisitException;
import dw317.lib.medication.DINMedication;
import dw317.lib.medication.Medication;
import dw317.lib.medication.NDCMedication;

/**
 * @author Tiffany
 *
 */
public class GUIViewController extends JFrame implements Observer {

	// Variables
	private Clinic model;
	private Medication medication = null;

	// Panels
	private JPanel pnlContent;
	private JPanel pnlDequeue;
	private JPanel pnlCreateNew;

	// Tabbed panel
	private JTabbedPane tabbedPnl;

	// Buttons
	private JButton btnNextToTriage;
	private JButton btnPrioritizeTriage;
	private JButton btnNextToExamine;
	private JButton btnPrioritySelect;
	private JButton btnExit;

	// RadioButtons
	private JRadioButton rbtnReanimation;
	private JRadioButton rbtnVeryUrgent;
	private JRadioButton rbtnUrgent;
	private JRadioButton rbtnLessUrgent;
	private JRadioButton rbtnNotUrgent;

	// TextFields for new patient
	private JTextField txtFldPriority = null;
	private JTextField txtFldComplaint = null;
	private JTextField txtFldMedNumber = null;
	private JTextField txtFldMedScheme = null;
	private JTextField txtFldMedName = null;
	private JTextField txtFldCondition = null;
	private JTextField txtFldPhoneNumber = null;
	private JTextField txtFldRamq = null;
	private JTextField txtFldLastName = null;
	private JTextField txtFldFirstName = null;
	
	//Labels
	JLabel lblCreateFail;
	
	//TextArea
	private JTextArea displayResult;

	public GUIViewController(Observable model) {

		this.model = (Clinic) model;

		initiateContentPanel();
		initiateDequeuePanel();
		initiateCreateNewPanel();

		model.addObserver(this);
		update(model, null);
		this.setVisible(true);
	}

	/**
	 * Sets the elements in the content panel
	 */
	public void initiateContentPanel() {
		setResizable(false);
		setTitle("Dawson Medical Clinic");
		setBounds(120, 120, 450, 450);

		// Initiate panels
		pnlContent = new JPanel();
		pnlContent.setLayout(null);
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlContent);

		pnlDequeue = new JPanel();
		pnlDequeue.setLayout(null);
		pnlDequeue.setBorder(new EmptyBorder(5, 5, 5, 5));

		pnlCreateNew = new JPanel();
		pnlCreateNew.setLayout(null);
		pnlCreateNew.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Initiate tabs
		tabbedPnl = new JTabbedPane();
		tabbedPnl.setBounds(0, 0, 450, 380); // Space to have exit button
		tabbedPnl.addTab("Add New Patient", pnlCreateNew);
		tabbedPnl.addTab("Dequeue Visits", pnlDequeue);

		// Initiate exit button
		btnExit = new JButton("Exit");
		btnExit.setBounds(325, 385, 60, 20);
		btnExit.addActionListener(new btnExitListener());

		// Add elements to main panel
		pnlContent.add(tabbedPnl);
		pnlContent.add(btnExit);
	}

	/**
	 * Sets the elements in the dequeue panel
	 */
	public void initiateDequeuePanel() {
		// Initiate Buttons
		btnNextToTriage = new JButton("Next to Triage");
		btnNextToTriage.setBounds(0, 12, 130, 40);
		btnNextToTriage.addActionListener(new btnNextToTriageListener());

		btnPrioritizeTriage = new JButton("Prioritize Triage");
		btnPrioritizeTriage.setBounds(0, 50, 130, 40);
		btnPrioritizeTriage
				.addActionListener(new btnPrioritizeTriageListener());

		btnNextToExamine = new JButton("Next to Examine");
		btnNextToExamine.setBounds(0, 90, 130, 40);
		btnNextToExamine.addActionListener(new btnNextToExamineListener());

		btnPrioritySelect = new JButton("Select");
		btnPrioritySelect.setBounds(170, 180, 70, 30);
		btnPrioritySelect.setVisible(false);
		btnPrioritySelect.addActionListener(new btnPrioritySelectListener());

		// RadioButtons
		rbtnReanimation = new JRadioButton("Reanimation");
		rbtnReanimation.setSelected(true);
		rbtnReanimation.setVisible(false);
		rbtnReanimation.setBounds(160, 20, 120, 25);

		rbtnVeryUrgent = new JRadioButton("Very Urgent");
		rbtnVeryUrgent.setVisible(false);
		rbtnVeryUrgent.setBounds(160, 50, 120, 25);

		rbtnUrgent = new JRadioButton("Urgent");
		rbtnUrgent.setVisible(false);
		rbtnUrgent.setBounds(160, 80, 120, 25);

		rbtnLessUrgent = new JRadioButton("Less Urgent");
		rbtnLessUrgent.setVisible(false);
		rbtnLessUrgent.setBounds(160, 110, 120, 25);

		rbtnNotUrgent = new JRadioButton("Not Urgent");
		rbtnNotUrgent.setVisible(false);
		rbtnNotUrgent.setBounds(160, 140, 120, 25);

		// Create Display window
		displayResult = new JTextArea("Results will appear here");
		displayResult.setBackground(Color.WHITE);
		displayResult.setBounds(20, 220, 400, 120);

		// Add radio buttons to group
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rbtnReanimation);
		buttonGroup.add(rbtnVeryUrgent);
		buttonGroup.add(rbtnLessUrgent);
		buttonGroup.add(rbtnUrgent);
		buttonGroup.add(rbtnNotUrgent);

		// Add elements to panel
		pnlDequeue.add(btnNextToTriage);
		pnlDequeue.add(btnPrioritizeTriage);
		pnlDequeue.add(btnNextToExamine);
		pnlDequeue.add(btnPrioritySelect);
		pnlDequeue.add(rbtnReanimation);
		pnlDequeue.add(rbtnVeryUrgent);
		pnlDequeue.add(rbtnUrgent);
		pnlDequeue.add(rbtnLessUrgent);
		pnlDequeue.add(rbtnNotUrgent);
		pnlDequeue.add(displayResult);
	}

	/**
	 * Sets the elements in the create new panel
	 */
	public void initiateCreateNewPanel() {
		
		// Create labels and add to panel
		
		lblCreateFail = new JLabel("Creation failed");
		lblCreateFail.setForeground(Color.RED);
		lblCreateFail.setBounds(140, 310, 120, 30);
		lblCreateFail.setVisible(false);
		pnlCreateNew.add(lblCreateFail);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(40, 10, 120, 25);
		pnlCreateNew.add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(40, 40, 120, 25);
		pnlCreateNew.add(lblLastName);

		JLabel lblRamq = new JLabel("RAMQ");
		lblRamq.setBounds(40, 70, 120, 25);
		pnlCreateNew.add(lblRamq);

		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(40, 100, 120, 25);
		pnlCreateNew.add(lblPhoneNumber);

		JLabel lblCondition = new JLabel("Condition");
		lblCondition.setBounds(40, 130, 120, 25);
		pnlCreateNew.add(lblCondition);

		JLabel lblMedicationName = new JLabel("Medication Name");
		lblMedicationName.setBounds(40, 160, 120, 25);
		pnlCreateNew.add(lblMedicationName);

		JLabel lblMedicationScheme = new JLabel("Medication Scheme");
		lblMedicationScheme.setBounds(40, 190, 120, 25);
		pnlCreateNew.add(lblMedicationScheme);

		JLabel lblMedicationNumber = new JLabel("Medication Number");
		lblMedicationNumber.setBounds(40, 220, 120, 25);
		pnlCreateNew.add(lblMedicationNumber);

		JLabel lblComplaint = new JLabel("Complaint");
		lblComplaint.setBounds(40, 250, 120, 25);
		pnlCreateNew.add(lblComplaint);

		JLabel lblPriority = new JLabel("Priority");
		lblPriority.setBounds(40, 280, 120, 25);
		pnlCreateNew.add(lblPriority);

		// Create text boxes and add too
		txtFldFirstName = new JTextField();
		txtFldFirstName.setBounds(180, 10, 220, 25);
		pnlCreateNew.add(txtFldFirstName);

		txtFldLastName = new JTextField();
		txtFldLastName.setBounds(180, 40, 220, 25);
		pnlCreateNew.add(txtFldLastName);

		txtFldRamq = new JTextField();
		txtFldRamq.setBounds(180, 70, 220, 25);
		pnlCreateNew.add(txtFldRamq);

		txtFldPhoneNumber = new JTextField();
		txtFldPhoneNumber.setBounds(180, 100, 220, 25);
		pnlCreateNew.add(txtFldPhoneNumber);

		txtFldCondition = new JTextField();
		txtFldCondition.setBounds(180, 130, 220, 25);
		pnlCreateNew.add(txtFldCondition);

		txtFldMedName = new JTextField();
		txtFldMedName.setBounds(180, 160, 220, 25);
		pnlCreateNew.add(txtFldMedName);

		txtFldMedScheme = new JTextField();
		txtFldMedScheme.setBounds(180, 190, 220, 25);
		pnlCreateNew.add(txtFldMedScheme);

		txtFldMedNumber = new JTextField();
		txtFldMedNumber.setBounds(180, 220, 220, 25);
		pnlCreateNew.add(txtFldMedNumber);

		txtFldComplaint = new JTextField();
		txtFldComplaint.setBounds(180, 250, 220, 25);
		pnlCreateNew.add(txtFldComplaint);

		txtFldPriority = new JTextField();
		txtFldPriority.setBounds(180, 280, 220, 25);
		pnlCreateNew.add(txtFldPriority);

		// Create button
		JButton btnCreate = new JButton("Create");
		btnCreate.setBounds(240, 310, 80, 30);
		pnlCreateNew.add(btnCreate);
		btnCreate.addActionListener(new btnCreateNewListener());
	}

	/**
	 * Inner class action listener. Exits when button is clicked
	 *
	 */
	private class btnExitListener implements ActionListener {

		/**
		 * Action listener for btnExit.
		 * 
		 * @param ActionEvent e
		 * 		Event 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				model.closeClinic();
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			} finally {
				System.exit(0);
			}
		}

	}

	/**
	 * Inner class action listener for the next to triage
	 * button.
	 *
	 */
	private class btnNextToTriageListener implements ActionListener {

		/**
		 * Action listener for Next to Triage button
		 * 
		 * @param ActionEvent e
		 * 		Event 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				model.nextForTriage();
			} catch (Exception exception) {
				displayResult.setText(exception.getMessage());
			}
		}

	}

	/**
	 * Inner class action listener for the prioritize
	 * triage button.
	 *
	 */
	private class btnPrioritizeTriageListener implements ActionListener {

		/**
		 * Action listener for Prioritize Triage button.
		 * 
		 * @param ActionEvent e
		 * 		Event 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			rbtnReanimation.setSelected(true);
			rbtnReanimation.setVisible(true);
			rbtnVeryUrgent.setVisible(true);
			rbtnUrgent.setVisible(true);
			rbtnLessUrgent.setVisible(true);
			rbtnNotUrgent.setVisible(true);
			btnPrioritySelect.setVisible(true);
		}
	}

	/**
	 * Inner class action listener for the next to examine
	 * button.
	 *
	 */
	private class btnNextToExamineListener implements ActionListener {

		/**
		 * Action listener for btnExit.
		 * 
		 * @param ActionEvent e
		 * 		Event 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				displayResult.setText("Here");
				model.nextForExamination();
			} catch (Exception exception) {
				displayResult.setText(exception.getMessage());
			}
		}
	}

	/**
	 * Inner class action listener for changing priorities.
	 *
	 */
	private class btnPrioritySelectListener implements ActionListener {

		/**
		 * Action listener for priority selection button.
		 * 
		 * @param ActionEvent e
		 * 		Event 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Priority priority = null;
			if (rbtnReanimation.isSelected())
				priority = Priority.REANIMATION;
			else if (rbtnVeryUrgent.isSelected())
				priority = Priority.VERYURGENT;
			else if (rbtnUrgent.isSelected())
				priority = Priority.URGENT;
			else if (rbtnLessUrgent.isSelected())
				priority = Priority.LESSURGENT;
			else if (rbtnNotUrgent.isSelected())
				priority = Priority.NOTURGENT;

			try {
				model.changeTriageVisitPriority(priority);
			} catch (NonExistingVisitException neve) {
				displayResult.setText(neve.getMessage());
			} catch (Exception exeption) {
				displayResult.setText(exeption.getMessage());
			}   

			rbtnReanimation.setVisible(false);
			rbtnVeryUrgent.setVisible(false);
			rbtnUrgent.setVisible(false);
			rbtnLessUrgent.setVisible(false);
			rbtnNotUrgent.setVisible(false);
			btnPrioritySelect.setVisible(false);
		}

	}

	private class btnCreateNewListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String firstName = txtFldFirstName.getText();
				String ramq = txtFldRamq.getText();
				String telephone = txtFldPhoneNumber.getText();
				String lastName = txtFldLastName.getText();
				String conditions = txtFldCondition.getText();
				
				if (txtFldMedScheme.getText().equalsIgnoreCase("DIN"))
					medication = new DINMedication(txtFldMedNumber.getText(), txtFldMedName.getText());
				else if (txtFldMedScheme.getText().equalsIgnoreCase("NDC"))
					medication = new NDCMedication(txtFldMedNumber.getText(), txtFldMedName.getText());
				else if (txtFldMedScheme.getText() == null || txtFldMedScheme.getText().length() < 1)
					medication = null;
				else
					throw new IllegalArgumentException("Invalid medication scheme");
				
				model.registerNewPatient(firstName, lastName, ramq, telephone, medication, conditions);
				displayResult.setText("Patient created");
			} catch (Exception exception) {
				lblCreateFail.setVisible(true);
				displayResult.setText("Error in creation. Overriden. " + exception.getMessage());
			}
			
		}
		
	}
	/**
	 * Observable update method. 
	 * 
	 * @param Observable obs
	 * @param Object arg
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable obs, Object arg) {

		if (arg instanceof Optional<?>)
		{
			displayResult.setText("Next Visit: " + ((Optional<Visit>) arg)
		                          .get().getPatient().getName().getFullName());
		} else if (arg instanceof Patient) 
		{
			displayResult.setText("Patient: " + ((Patient)arg).getName().toString()
					              + "\nRAMQ: " + ((Patient) arg).getRamq().toString());
		} else if (arg instanceof Priority)
		{
			displayResult.setText("Priority updated to: " + (Priority)arg );
		} else
		{
			displayResult.setText("Results will be displayed here");
		}

	}
}
