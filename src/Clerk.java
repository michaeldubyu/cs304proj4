
import java.awt.*;
import java.awt.event.*;

public class Clerk {

	final Frame clerkFrame;
	/**
	 * Instantiate the Clerk sub menus and structure.
	 */
	public Clerk(Frame m){
		final Button addBorrower;
		final Button checkOut;
		final Button processReturn;
		final Button checkOverDue;
		
		clerkFrame = new Frame();

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
                clerkFrame.setVisible(false);
                clerkFrame.dispose();
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
				new AddBorrower();
			}
			else if (e.getActionCommand()=="check out"){
				new CheckOutBook();
			}else if (e.getActionCommand()=="return"){
				new ProcessRet();
			}else if (e.getActionCommand()=="check overdues"){
				new CheckOverdues(clerkFrame);
			}
		}
	}
	
}
