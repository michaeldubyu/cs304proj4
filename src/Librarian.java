import java.awt.*;
import java.awt.event.*;

public class Librarian {

	private Frame mainFrame;
	public boolean addHasBeenCalled = false;

	/**
	 * Instantiate the Clerk sub menus and structure.
	 */
	public Librarian(Frame m){
		final Button addBook;
		final Button addCopy;
		final Button addAuthor;
		final Button genCheckOutReport;
		final Button genPopularReport;
		final Frame librarianFrame = new Frame();
		ActionListener al = new MyActionListener();
		
		librarianFrame.setLayout(new GridLayout(5,1));
		
		addBook = new Button("Add a Book");
		addBook.setActionCommand("add book");
		addBook.addActionListener(al);
		
		addCopy = new Button("Add a Copy Of an Existing Book");
		addCopy.setActionCommand("add copy");
		addCopy.addActionListener(al);
		
		addAuthor = new Button("Add An Author to an Existing Book");
		addAuthor.setActionCommand("add author");
		addAuthor.addActionListener(al);
		
		librarianFrame.add(addBook);		
		librarianFrame.add(addCopy);
		librarianFrame.add(addAuthor);
		
		genCheckOutReport = new Button("Generate a Report of Books on Loan");
		genCheckOutReport.setActionCommand("checkout report");
		genCheckOutReport.addActionListener(al);

		librarianFrame.add(genCheckOutReport);
		genPopularReport = new Button("Generate a Report of Popular Books");
		genPopularReport.setActionCommand("popular report");
		genPopularReport.addActionListener(al);
		librarianFrame.add(genPopularReport);
		mainFrame = librarianFrame;
		librarianFrame.pack();
		librarianFrame.setLocationRelativeTo(m);
        librarianFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                //librarianFrame.setVisible(false);
                librarianFrame.dispose();
            }
        } );
		librarianFrame.setTitle("MDMJ Library Systems - Librarian Management");
		librarianFrame.setVisible(true);
	}

	class MyActionListener implements ActionListener{


		public void actionPerformed(ActionEvent e) {	
			//wait 10 seconds until timeout
			if (e.getActionCommand()=="add book"){// && !addHasBeenCalled){
				new AddBook(mainFrame);
				addHasBeenCalled = true;
			}
			else if (e.getActionCommand() == "add copy"){
				new AddCopy();
			}
			else if (e.getActionCommand()=="checkout report"){
				new BorrowedReport();
			}else if (e.getActionCommand()=="popular report"){
				new PopularReport();
			}else if (e.getActionCommand()=="add author"){
				new AddAuthor();
			}
		}
	}
	
}
