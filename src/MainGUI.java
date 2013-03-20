import java.awt.*;
import java.awt.event.*;

public class MainGUI{
	private static final long serialVersionUID = 4149825008429377286L;

	/*
	 * instantiate the GUI 
	 */
	public MainGUI(){
		final Button clerk;
		final Button librarian;
		final Button borrower;
		Frame mainFrame = new Frame();
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
		mainFrame.setTitle("MDMJ Library Systems");
		mainFrame.setVisible(true);
	}	
	
	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {	
			//wait 10 seconds until timeout
			if (e.getActionCommand()=="clerk"){
				System.out.println("clerk!");
				Clerk c = new Clerk();
			}
			else if (e.getActionCommand()=="borrower"){
				System.out.println("borrower!");
			}else if (e.getActionCommand()=="librarian"){
				System.out.println("librarian!");
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainGUI m = new MainGUI();
	}



}
