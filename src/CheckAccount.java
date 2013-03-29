import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class CheckAccount {

	public CheckAccount(){
		
		final Frame searchFrame = new Frame("Check User Account");
		final JTextField bid = new JTextField(20);
		
		searchFrame.setLayout(new GridLayout(4,2));

		Label bidLabel = new Label("Account ID* :");
//		Label authorLabel = new Label("Author :");
//		Label subjectLabel = new Label("Subject :");

		searchFrame.add(bidLabel);
		searchFrame.add(bid);
//		searchFrame.add(authorLabel);
//		searchFrame.add(author);
//		searchFrame.add(subjectLabel);
//		searchFrame.add(subject);
		
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
	        		ArrayList<ArrayList<String>> outResult = BorrowerTable.checkOut(bid.getText());
	        		ArrayList<ArrayList<String>> holdResult = BorrowerTable.checkHolds(bid.getText());
	        		ArrayList<ArrayList<String>> fineResult = BorrowerTable.checkFines(bid.getText());
	        		//if (result.size()==0) throw new Exception("There were no results for your query. Please try again.");
	        		
	        		final Frame successFrame = new Frame("Results");
	        		successFrame.setLayout(new GridBagLayout());
	        		//successFrame.getParent().setSize(80, 10);

	        		//callnumber, name, count
	        		Object outrowData[][] = new Object[outResult.size()][3];
	        		int i = 0;
	        		for (ArrayList<String> s:outResult){
	        			outrowData[i] = s.toArray();
	        			i++;
	        		}
	        		Object holdrowData[][] = new Object[holdResult.size()][3];
	        		i = 0;
	        		for (ArrayList<String> s:holdResult){
	        			holdrowData[i] = s.toArray();
	        			i++;
	        		}
	        		Object finerowData[][] = new Object[fineResult.size()][3];
	        		i = 0;
	        		for (ArrayList<String> s:fineResult){
	        			finerowData[i] = s.toArray();
	        			i++;
	        		}
	        		Label outLabel = new Label("Books Currently Checked Out :");
	        		successFrame.add(outLabel);
	        		Object outcolumnNames[] = { "Call Number", "Title", "Author" };
	        		JTable outtable = new JTable(outrowData, outcolumnNames);

	        		JScrollPane outScroll = new JScrollPane(outtable);
	        		
	        		successFrame.add(outScroll);

	        		Label holdLabel = new Label("Hold Requests :");
	        		successFrame.add(holdLabel);
	        		Object holdcolumnNames[] = { "Call Number","Title", "Date Issued" };
	        		JTable holdtable = new JTable(holdrowData, holdcolumnNames);

		        	JScrollPane holdScroll = new JScrollPane(holdtable);
		        		
		        	successFrame.add(holdScroll);
		        	
		    		Label fineLabel = new Label("Outstanding Fines :");
	        		successFrame.add(fineLabel);
	        		Object finecolumnNames[] = { "Amount", "Date Issued" };
	        		JTable finetable = new JTable(finerowData, finecolumnNames);

		        	JScrollPane fineScroll = new JScrollPane(finetable);
		        		
		        	successFrame.add(fineScroll);
		        	
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
	        		argException.printStackTrace();
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
