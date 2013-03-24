import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class CheckOutBook {

	public CheckOutBook(){
		
		//a form for checking out books
		//input: borid (pkey for the table), bid (the user id), callNumber, copy no, outdate
		final Frame insertFrame = new Frame();
		final JTextField bid = new JTextField(20);
		final JTextField callNo = new JTextField(20);
		final JTextField copyNo = new JTextField(20);
		final JTextField inDate = new JTextField(20);
		
		insertFrame.setLayout(new GridLayout(6,2));

		Label bidLabel = new Label("Borrower ID* :");
		Label callNoLabel = new Label("Call No* :");
		Label copyNoLabel = new Label("Copy No* :");
		Label inDateLabel = new Label("In Date* (UNIX TIME) :");

		insertFrame.add(bidLabel);
		insertFrame.add(bid);
		insertFrame.add(callNoLabel);
		insertFrame.add(callNo);
		insertFrame.add(copyNoLabel);
		insertFrame.add(copyNo);
		
		Button submit = new Button("Submit");
		insertFrame.add(new Label("(*)Required fields marked.")); //to pad the submit button to the right
		insertFrame.add(submit);
		insertFrame.setLocationRelativeTo(null);
		insertFrame.pack();
		insertFrame.setTitle("Check Out A Book");
		insertFrame.setVisible(true);
		bid.requestFocus();
		
	    submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          //when the submit button is clicked
	        	try{
		        	BorrowingTable.insertBorrowing(bid.getText(), 
		        			callNo.getText(), copyNo.getText());
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
