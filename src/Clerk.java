<<<<<<< HEAD
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
=======
import java.awt.*;
import java.awt.event.*;
>>>>>>> 6db346f7b96d40a7f332429686e01270f6986deb

public class Clerk {

	/**
	 * Instantiate the Clerk sub menus and structure.
	 */
	public Clerk(Frame m){
		final Button addBorrower;
		final Button checkOut;
		final Button processReturn;
		final Button checkOverDue;
		
<<<<<<< HEAD
		Frame clerkFrame = new Frame();
=======
		final Frame clerkFrame = new Frame();
>>>>>>> 6db346f7b96d40a7f332429686e01270f6986deb
		ActionListener al = new MyActionListener();
		
		clerkFrame.setLayout(new GridLayout(4,1));
		
		addBorrower = new Button("Add A Borrower");
		addBorrower.setActionCommand("add borrower");
		addBorrower.addActionListener(al);
		clerkFrame.add(addBorrower);
		
		checkOut = new Button("Checkout Books");
		checkOut.setActionCommand("check out");
		checkOut.addActionListener(al);
		clerkFrame.add(checkOut);

		processReturn = new Button("Return Books");
		processReturn.setActionCommand("return");
		processReturn.addActionListener(al);
		clerkFrame.add(processReturn);
		
		checkOverDue = new Button("Check Overdues");
		checkOverDue.setActionCommand("check overdues");
		checkOverDue.addActionListener(al);
		clerkFrame.add(checkOverDue);
		
		clerkFrame.pack();
        clerkFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
<<<<<<< HEAD
                System.exit(0);
=======
                clerkFrame.setVisible(false);
>>>>>>> 6db346f7b96d40a7f332429686e01270f6986deb
            }
        } );
		clerkFrame.setTitle("MDMJ Library Systems - Clerk Management System");
		clerkFrame.setLocationRelativeTo(m);  
		clerkFrame.setVisible(true);
	}

	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {	
			//wait 10 seconds until timeout
			if (e.getActionCommand()=="add borrower"){
				System.out.println("add borrower");
			}
			else if (e.getActionCommand()=="check out"){
				System.out.println("check out");
			}else if (e.getActionCommand()=="return"){
				System.out.println("return");
			}else if (e.getActionCommand()=="check overdues"){
				System.out.println("check overdues");
			}
		}
	}
	
}
