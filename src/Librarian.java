import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class Librarian {

	/**
	 * Instantiate the Clerk sub menus and structure.
	 */
	public Librarian(Frame m){
		final Button addBook;
		final Button genCheckOutReport;
		final Button genPopularReport;
		final Button checkOverDue;
		
		Frame borrowerFrame = new Frame();
		ActionListener al = new MyActionListener();
		
		borrowerFrame.setLayout(new GridLayout(3,1));
		
		addBook = new Button("Add a Book");
		addBook.setActionCommand("add book");
		addBook.addActionListener(al);
		borrowerFrame.add(addBook);
		
		genCheckOutReport = new Button("Generate a Checkout Report");
		genCheckOutReport.setActionCommand("checkout report");
		genCheckOutReport.addActionListener(al);
		borrowerFrame.add(genCheckOutReport);

		genPopularReport = new Button("Generate a Popular Book Report");
		genPopularReport.setActionCommand("popular report");
		genPopularReport.addActionListener(al);
		borrowerFrame.add(genPopularReport);
		
		borrowerFrame.pack();
		borrowerFrame.setLocationRelativeTo(m);
        borrowerFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        } );
		borrowerFrame.setTitle("MDMJ Library Systems - Librarian Management");
		borrowerFrame.setVisible(true);
	}

	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {	
			//wait 10 seconds until timeout
			if (e.getActionCommand()=="add book"){
				
			}
			else if (e.getActionCommand()=="checkout report"){
				
			}else if (e.getActionCommand()=="popular report"){
				
			}
		}
	}
	
}
