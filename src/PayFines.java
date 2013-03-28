import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class PayFines {

	public PayFines(){
		
		final Frame searchFrame = new Frame("Fine Lookup");
		final JTextField bid = new JTextField(20);
		
		searchFrame.setLayout(new GridLayout(2,2));

		Label bidLabel = new Label("Borrower ID* :");
		
		searchFrame.add(bidLabel);
		searchFrame.add(bid);
		
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
	        		
	        		final Frame successFrame = new Frame("Fines");

	        		//callnumber, name, count
	        		Object rowData[][] = new Object[result.size()][2];
	        		int i = 0;
	        		for (ArrayList<String> s:result){
	        			rowData[i] = s.toArray();
	        			i++;
	        		}
	        		Object columnNames[] = { "Amount" , "Issued Date", "Paid Date"};
	        		JTable table = new JTable(rowData, columnNames);
	        		JScrollPane scroll = new JScrollPane(table);
	        		
	        		successFrame.add(scroll);
	        		successFrame.pack();
	        		successFrame.setVisible(true);
	        		successFrame.setAlwaysOnTop(true);
	        		successFrame.setLocationRelativeTo(searchFrame);
	                successFrame.addWindowListener( new WindowAdapter() {
	                    public void windowClosing(WindowEvent we) {
	                        successFrame.setVisible(false);
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
	        }
	      });
		
        searchFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                searchFrame.setVisible(false);
            }
        } );
		
	}

}
