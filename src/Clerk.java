import java.sql.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.BoxLayout;

public class Clerk {

	/**
	 * Instantiate the Clerk sub menus and structure.
	 */
	public Clerk(){
		final Button addBorrower;
		final Button checkOut;
		final Button processReturn;
		final Button checkOverDue;
		
		Frame clerkFrame = new Frame();
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
		clerkFrame.setTitle("MDMJ Library Systems - Clerk Management System");
		clerkFrame.setLocationRelativeTo(null);  
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
