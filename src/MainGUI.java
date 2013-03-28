import java.awt.*;
import java.awt.event.*;

public class MainGUI{
	Frame mainFrame;
	Clerk c;
	Librarian l;
	Borrower b;
	/*
	 * instantiate the GUI 
	 */
	public MainGUI(){
		final Button clerk;
		final Button librarian;
		final Button borrower;
		mainFrame = new Frame();
		ActionListener al = new MyActionListener();
		
		//arrange items left to right, then to the next row
		mainFrame.setLayout(new FlowLayout());
		
		clerk = new Button("Clerk Management");
		clerk.setActionCommand("clerk");
		clerk.addActionListener(al);
		mainFrame.add(clerk);
		
		librarian = new Button("Librarian Management");
		librarian.setActionCommand("librarian");
		librarian.addActionListener(al);
		mainFrame.add(librarian);
		
		borrower = new Button("Borrower Management");
		borrower.setActionCommand("borrower");
		borrower.addActionListener(al);
		mainFrame.add(borrower);
		
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
        mainFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        } );
		mainFrame.setTitle("MDMJ Library Systems");
		mainFrame.setVisible(true);
	}	
	
	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {	
			if (e.getActionCommand()=="clerk"){// && c == null){
				c = new Clerk(mainFrame);
			}else if (e.getActionCommand()=="borrower"){// && b == null){
				b = new Borrower(mainFrame);
			}else if (e.getActionCommand()=="librarian"){// && l == null){
				l = new Librarian(mainFrame);
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainGUI();
	}
	




}
