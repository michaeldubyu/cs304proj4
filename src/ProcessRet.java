import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProcessRet {

	public ProcessRet(){
		
		//a form for checking out books
		//input: borid (pkey for the table), bid (the user id), callNumber, copy no, outdate
		final Frame insertFrame = new Frame();
		final JTextField callNo = new JTextField(20);
		final JTextField copyNo = new JTextField(20);
		final JTextField bid = new JTextField(20);
		
		insertFrame.setLayout(new GridLayout(3,2));

		Label callNoLabel = new Label("Call No* :");
		Label copyNoLabel = new Label("Copy No* :");
		Label bidLabel = new Label("Borrower ID* :");

		//insertFrame.add(bidLabel);
		//insertFrame.add(bid);		
		insertFrame.add(callNoLabel);
		insertFrame.add(callNo);
		insertFrame.add(copyNoLabel);
		insertFrame.add(copyNo);
		
		Button submit = new Button("Submit");
		insertFrame.add(new Label("(*)Required fields marked.")); //to pad the submit button to the right
		insertFrame.add(submit);
		insertFrame.setLocationRelativeTo(null);
		insertFrame.pack();
		insertFrame.setTitle("Return a Book");
		insertFrame.setVisible(true);
		callNo.requestFocus();
		
	    submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          //when the submit button is clicked
	        	try{
	        		int fine = BorrowerTable.processReturn(callNo.getText(), copyNo.getText());
	        		final Frame fineFrame = new Frame("Book Returns.");
	        		String msg = "";
	        		if (fine==1) msg = "This book was late! A fine has been applied.";
	        		else if (fine==0) msg = "Book successfully returned.";
	        		else if (fine==-1) msg = "Error! Book was never checked out!";

	        		Label error = new Label(msg);
	        		fineFrame.add(error);
	        		fineFrame.pack();
	        		fineFrame.setVisible(true);
	        		fineFrame.setAlwaysOnTop(true);
	        		fineFrame.setLocationRelativeTo(insertFrame);
	                fineFrame.addWindowListener( new WindowAdapter() {
	                    public void windowClosing(WindowEvent we) {
	                        fineFrame.setVisible(false);
	                        insertFrame.dispose();
	                    }
	                } );
	                
	                //check if there exists a hold for this book
	                boolean holdExists = BorrowerTable.checkHoldExists(callNo.getText());
	                if (holdExists){
	                	final Frame holdFrame = new Frame("A Hold Exists!");
	                	Label notification = new Label("The user who requested this book has been notified of this return.");
		        		holdFrame.add(notification);
		        		holdFrame.pack();
		        		holdFrame.setVisible(true);
		        		holdFrame.setAlwaysOnTop(true);
		        		holdFrame.setLocationRelativeTo(insertFrame);
		                holdFrame.addWindowListener( new WindowAdapter() {
		                    public void windowClosing(WindowEvent we) {
		                        holdFrame.setVisible(false);
		                        insertFrame.dispose();
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
