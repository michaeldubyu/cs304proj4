<<<<<<< HEAD
import java.sql.*;
=======
>>>>>>> 6db346f7b96d40a7f332429686e01270f6986deb
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
<<<<<<< HEAD
		final Button checkOverDue;
		
		Frame borrowerFrame = new Frame();
		ActionListener al = new MyActionListener();
		
		borrowerFrame.setLayout(new GridLayout(3,1));
=======
		
		final Frame librarianFrame = new Frame();
		ActionListener al = new MyActionListener();
		
		librarianFrame.setLayout(new GridLayout(3,1));
>>>>>>> 6db346f7b96d40a7f332429686e01270f6986deb
		
		addBook = new Button("Add a Book");
		addBook.setActionCommand("add book");
		addBook.addActionListener(al);
<<<<<<< HEAD
		borrowerFrame.add(addBook);
=======
		librarianFrame.add(addBook);
>>>>>>> 6db346f7b96d40a7f332429686e01270f6986deb
		
		genCheckOutReport = new Button("Generate a Checkout Report");
		genCheckOutReport.setActionCommand("checkout report");
		genCheckOutReport.addActionListener(al);
<<<<<<< HEAD
		borrowerFrame.add(genCheckOutReport);
=======
		librarianFrame.add(genCheckOutReport);
>>>>>>> 6db346f7b96d40a7f332429686e01270f6986deb

		genPopularReport = new Button("Generate a Popular Book Report");
		genPopularReport.setActionCommand("popular report");
		genPopularReport.addActionListener(al);
<<<<<<< HEAD
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
=======
		librarianFrame.add(genPopularReport);
		
		librarianFrame.pack();
		librarianFrame.setLocationRelativeTo(m);
        librarianFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                librarianFrame.setVisible(false);
            }
        } );
		librarianFrame.setTitle("MDMJ Library Systems - Librarian Management");
		librarianFrame.setVisible(true);
>>>>>>> 6db346f7b96d40a7f332429686e01270f6986deb
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
