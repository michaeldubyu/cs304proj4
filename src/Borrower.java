import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class Borrower {

	/**
	 * Instantiate the Clerk sub menus and structure.
	 */
	public Borrower(){
		final Button addBorrower;
		final Button checkOut;
		final Button processReturn;
		final Button checkOverDue;
		
		Frame borrowerFrame = new Frame();
		ActionListener al = new MyActionListener();
		
		borrowerFrame.setLayout(new GridLayout(4,1));
		
		addBorrower = new Button("Search For Books");
		addBorrower.setActionCommand("search");
		addBorrower.addActionListener(al);
		borrowerFrame.add(addBorrower);
		
		checkOut = new Button("Check Account");
		checkOut.setActionCommand("check account");
		checkOut.addActionListener(al);
		borrowerFrame.add(checkOut);

		processReturn = new Button("Place a Hold Request");
		processReturn.setActionCommand("hold");
		processReturn.addActionListener(al);
		borrowerFrame.add(processReturn);
		
		checkOverDue = new Button("Pay Fines");
		checkOverDue.setActionCommand("pay");
		checkOverDue.addActionListener(al);
		borrowerFrame.add(checkOverDue);
		
		borrowerFrame.pack();
		borrowerFrame.setTitle("MDMJ Library Systems - Borrower Actions");
		borrowerFrame.setLocationRelativeTo(null);  
		borrowerFrame.setVisible(true);
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
