import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CheckOverdues {

	//instantiate the results of the query - what query?
	public CheckOverdues(Frame frame){

		final Frame searchFrame = new Frame("Books on Loan");
		final JTextField subject = new JTextField(20);

		searchFrame.setLayout(new GridLayout(	2,1));

		Button submit = new Button("Submit");
		searchFrame.add(submit);
		searchFrame.setLocationRelativeTo(frame);
		//when the submit button is clicked
		try{
			ArrayList<ArrayList<String>> result = BorrowingTable.displayOverdueItems();

			final Frame successFrame = new Frame("Results");
			if(result.size() > 0){
				//callnumber, name, count
				Object rowData[][] = new Object[result.size()][3];
				int i = 0;
				for (ArrayList<String> s:result){
					rowData[i] = s.toArray();
					i++;
				}
				Object columnNames[] = { "Borrower ID", "Borrower Name", "Title", "Out Date", "email", "Borrower Type" };
				JTable table = new JTable(rowData, columnNames);
				JScrollPane scroll = new JScrollPane(table);
				successFrame.add(scroll);
			}
			else {
				JLabel noResults = new JLabel("There are no fines outstanding");
				successFrame.add(noResults);
			}
			successFrame.pack();
			successFrame.setVisible(true);
			successFrame.setAlwaysOnTop(true);
			successFrame.setLocationRelativeTo(searchFrame);
			successFrame.addWindowListener( new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					successFrame.setVisible(false);
					searchFrame.dispose();

				}
			} );
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


		searchFrame.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				searchFrame.setVisible(false);
			}
		} );

	}

}
