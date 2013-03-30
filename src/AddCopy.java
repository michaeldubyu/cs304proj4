import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;


public class AddCopy {

		public AddCopy(){
			
			//create a form for the user to input : bid, name, password, address, phone, email, sinstnum, expirydate, type
			
			final Frame insertFrame = new Frame();
			final JTextField callNo = new JTextField(20);
			final JTextField quantity = new JTextField(20);
			
			insertFrame.setLayout(new GridLayout(3,2));

			Label callNoLabel = new Label("Call Number* :");
			Label quantityLabel = new Label("Quantity* :");

			insertFrame.add(callNoLabel);
			insertFrame.add(callNo);
			insertFrame.add(quantityLabel);
			insertFrame.add(quantity);

			Button submit = new Button("Submit");
			insertFrame.add(new Label("(*)Required fields marked.")); //to pad the submit button to the right
			insertFrame.add(submit);
			insertFrame.setLocationRelativeTo(null);
			insertFrame.pack();
			insertFrame.setTitle("Add A Borrower");
			insertFrame.setVisible(true);
			callNoLabel.requestFocus();
			
		    submit.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		          //when the submit button is clicked
		        	try{
		        		int q;
		        		if (quantity.getText().equals("")) q = 1;
		        		else q = Integer.parseInt(quantity.getText());
		        		
			        	int quantityAdded = BookTable.insertCopy(callNo.getText(),false, q);
		        		final Frame successFrame = new Frame("Success!");
		        		Label success = new Label(quantity.getText() + " copies were successfully added.");
		        		successFrame.add(success);
		        		successFrame.pack();
		        		successFrame.setVisible(true);
		        		successFrame.setAlwaysOnTop(true);
		        		successFrame.setLocationRelativeTo(insertFrame);
		                successFrame.addWindowListener( new WindowAdapter() {
		                    public void windowClosing(WindowEvent we) {
		                
		                        successFrame.setVisible(false);
		                        insertFrame.dispose();
		                    }
		                } );
			        
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
