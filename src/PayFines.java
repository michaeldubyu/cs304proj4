import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;


public class PayFines {

	public PayFines(){

		final Frame searchFrame = new Frame("Fine Lookup");
		final JTextField bid = new JTextField(20);

		searchFrame.setLayout(new GridLayout(2,2));

		Label bidLabel = new Label("Borrower ID* :");

		searchFrame.add(bidLabel);
		searchFrame.add(bid);
		// Add a button to enter fines paid
		// For each paid Date that matches a real date, alter the tuple
		// refresh list
		Button submit = new Button("Submit");
		searchFrame.add(new Label("(*)Required fields marked.")); //to pad the submit button to the right
		searchFrame.add(submit);
		searchFrame.setLocationRelativeTo(null);
		searchFrame.pack();
		searchFrame.setVisible(true);
		bid.requestFocus();	

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//when the submit button is clicked
				try{
					ArrayList<ArrayList<String>> result = BorrowerTable.checkFinesExist(bid.getText());
					if (result.size()==0) throw new Exception("You have no fines!");

					//callnumber, name, count
					Object rowData[][] = new Object[result.size()][2];
					int i = 0;
					for (ArrayList<String> s:result){
						rowData[i] = s.toArray();
						i++;
					}
					//Create and set up the window.
					final JFrame frame = new JFrame("Fines Table");
					
					//Create and set up the content pane.
					FinesGUITable newContentPane = new FinesGUITable(rowData);
					newContentPane.setOpaque(true); //content panes must be opaque
					frame.setContentPane(newContentPane);

					frame.setVisible(true);
					frame.setAlwaysOnTop(true);
					frame.setLocationRelativeTo(searchFrame);
					frame.addWindowListener( new WindowAdapter() {
	                    public void windowClosing(WindowEvent we) {
	                        frame.setVisible(false);
	                    }
	                } );
					
					//Display the window.
					frame.pack();
					frame.setVisible(true);

				}catch(Exception argException){
					final Frame errorFrame = new Frame("Error!");
					Label error = new Label(argException.getMessage());
					errorFrame.add(error);
					errorFrame.pack();
					errorFrame.setVisible(true);
					errorFrame.setAlwaysOnTop(true);
					errorFrame.setLocationRelativeTo(searchFrame);
					errorFrame.addWindowListener( new WindowAdapter() {
						public void windowClosing(WindowEvent we) {

							errorFrame.setVisible(false);
						}
					} );
				}
			}
		});

		searchFrame.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				searchFrame.setVisible(false);
			}
		} );

	}
	/**
	 * Meant to check the paid date values for it being properly.
	 * @author busyer_jonathan
	 *
	 */
	public class PayFineAction implements ActionListener {
		Frame parentFrame;
		public PayFineAction(Frame parentFrame) {
			this.parentFrame = parentFrame;
		}

		/**
		 * Checks for proper values in dates
		 * If dates are correct, set those values into fines table
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			new DatePicker(parentFrame);
		}

	}


}
