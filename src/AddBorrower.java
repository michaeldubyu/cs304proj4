import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddBorrower {

	public AddBorrower(){
		
		//create a form for the user to input : bid, name, password, address, phone, email, sinstnum, expirydate, type
		
		final Frame insertFrame = new Frame();
		final JTextField name = new JTextField(20);
		final JTextField password = new JTextField(20);
		final JTextField bid = new JTextField(20);
		final JTextField address = new JTextField(20);
		final JTextField phone = new JTextField(20);
		final JTextField email = new JTextField(20);
		final JTextField sinstnum = new JTextField(20);
		final JTextField expirydate = new JTextField(20);
		final JTextField type = new JTextField(20);
		
		insertFrame.setLayout(new GridLayout(9,2));

		Label bidLabel = new Label("Borrower ID:");
		Label nameLabel = new Label("Name* :");
		Label passwordLabel = new Label("Password* :");
		Label addressLabel = new Label("Address :");
		Label phoneLabel = new Label("Phone :");
		Label emailLabel = new Label("Email* :");
		Label sinstnumLabel = new Label("SIN/Student Number :");
		Label expiryDateLabel = new Label("Expiry Date (UNIX TIME) :");
		Label typeLabel = new Label("Account Type* :");

		//insertFrame.add(bidLabel);
		//insertFrame.add(bid);
		insertFrame.add(nameLabel);
		insertFrame.add(name);
		insertFrame.add(passwordLabel);
		insertFrame.add(password);
		insertFrame.add(addressLabel);
		insertFrame.add(address);
		insertFrame.add(phoneLabel);
		insertFrame.add(phone);
		insertFrame.add(emailLabel);
		insertFrame.add(email);
		insertFrame.add(sinstnumLabel);
		insertFrame.add(sinstnum);
		insertFrame.add(expiryDateLabel);
		insertFrame.add(expirydate);
		insertFrame.add(typeLabel);
		insertFrame.add(type);

		Button submit = new Button("Submit");
		insertFrame.add(new Label("(*)Required fields marked.")); //to pad the submit button to the right
		insertFrame.add(submit);
		insertFrame.setLocationRelativeTo(null);
		insertFrame.pack();
		insertFrame.setTitle("Add A Borrower");
		insertFrame.setVisible(true);
		bid.requestFocus();
		
	    submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          //when the submit button is clicked
	        	try{
		        	BorrowerTable.insertBorrower(password.getText(), name.getText(), address.getText(),
		        			phone.getText(),email.getText(), sinstnum.getText(),expirydate.getText(), type.getText());
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
