import java.awt.*;
import java.awt.event.*;

public class Borrower {

	/**
	 * Instantiate the Clerk sub menus and structure.
	 */
	public Borrower(Frame m){
		final Button search;
		final Button checkAccount;
		final Button hold;
		final Button payFines;

		final Frame borrowerFrame = new Frame();
		ActionListener al = new MyActionListener();
		
		borrowerFrame.setLayout(new GridLayout(4,1));
		
		search = new Button("Search For Books");
		search.setActionCommand("search");
		search.addActionListener(al);
		borrowerFrame.add(search);
		
		checkAccount = new Button("Check Account");
		checkAccount.setActionCommand("check account");
		checkAccount.addActionListener(al);
		borrowerFrame.add(checkAccount);

		hold = new Button("Place a Hold Request");
		hold.setActionCommand("hold");
		hold.addActionListener(al);
		borrowerFrame.add(hold);
		
		payFines = new Button("Pay Fines");
		payFines.setActionCommand("pay");
		payFines.addActionListener(al);
		borrowerFrame.add(payFines);
		
		borrowerFrame.pack();
		borrowerFrame.setTitle("MDMJ Library Systems - Borrower Actions");
        borrowerFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                borrowerFrame.setVisible(false);
            }
        } );
		borrowerFrame.setLocationRelativeTo(m);  
		borrowerFrame.setVisible(true);
	}

	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {	
			//wait 10 seconds until timeout
			if (e.getActionCommand()=="search"){
				new SearchBook();
			}
			else if (e.getActionCommand()=="check account"){

			}else if (e.getActionCommand()=="hold"){
				new processHold();
			}else if (e.getActionCommand()=="pay"){
				new PayFines();
			}
		}
	}
	
}
