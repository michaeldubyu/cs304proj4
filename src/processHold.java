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
		Label copyNoLabel = new Label("Borrower ID* :");

		insertFrame.add(callNoLabel);
		insertFrame.add(bid);
		insertFrame.add(copyNoLabel);
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
	        		BorrowerTable.placeHold(callNo.getText(), bid.getText());
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