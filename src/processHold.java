import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class processHold {

	public processHold(){
		
		//a form for checking out books
		//input: borid (pkey for the table), bid (the user id), callNumber, copy no, outdate
		final Frame insertFrame = new Frame();
		final JTextField callNo = new JTextField(20);
		final JTextField bid = new JTextField(20);
		
		insertFrame.setLayout(new GridLayout(3,2));

		Label callNoLabel = new Label("Call No* :");
		Label bidLabel = new Label("Borrower ID* :");

		insertFrame.add(callNoLabel);
		insertFrame.add(callNo);
		insertFrame.add(bidLabel);
		insertFrame.add(bid);
		
		Button submit = new Button("Submit");
		insertFrame.add(new Label("(*)Required fields marked.")); //to pad the submit button to the right
		insertFrame.add(submit);
		insertFrame.setLocationRelativeTo(null);
		insertFrame.pack();
		insertFrame.setTitle("Request a hold");
		insertFrame.setVisible(true);
		callNo.requestFocus();
		
	    submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          //when the submit button is clicked
	        	try{
	        		int hid = BorrowerTable.placeHold(callNo.getText(), bid.getText());
	        		if (hid == -1) throw new IllegalArgumentException("Hold request was not successfully completed! Please check the userid.");
	        		else {
	            		final Frame errorFrame = new Frame("Error!");
		        		Label success = new Label("Holdrequest with ID " + hid + " was successfully added!");
		        		errorFrame.add(success);
		        		errorFrame.pack();
		        		errorFrame.setVisible(true);
		        		errorFrame.setAlwaysOnTop(true);
		        		errorFrame.setLocationRelativeTo(insertFrame);
		                errorFrame.addWindowListener( new WindowAdapter() {
		                    public void windowClosing(WindowEvent we) {
		                        errorFrame.setVisible(false);
		                    }
		                } );		
	        		}
	        	}catch(Exception argException){
	        		final Frame errorFrame = new Frame("Error!");
	        		Label error = new Label(argException.getMessage());
	        		errorFrame.add(error);
	        		errorFrame.pack();
	        		errorFrame.setVisible(true);
	        		errorFrame.setAlwaysOnTop(true);
	        		errorFrame.setLocationRelativeTo(insertFrame);
	                errorFrame.addWindowListener( new WindowAdapter() {
	                    public void windowClosing(WindowEvent we) {
	                        errorFrame.setVisible(false);
	                    }
	                } );
	        	}
	        }
	      });
		
        insertFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                insertFrame.setVisible(false);
            }
        } );
	}

}