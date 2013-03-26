import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTextField;


public class AddBook {
		
	
	/**
	 * Instantiates an add book dialog, with menues and structures
	 * @param m
	 */
	public AddBook(Frame m){
		final Button btnAdd;
		final Frame bookFrame = new Frame();
		final JTextField callNumber = new JTextField(20);
		final JTextField isbn = new JTextField(20);
		final JTextField title = new JTextField(20);
		final JTextField mainAuthor = new JTextField(20);
		final JTextField publisher = new JTextField(20);
		final JTextField year = new JTextField(20);
		final JTextField amount = new JTextField(20);
		final JTextField subjects = new JTextField(20);


		
		Label callLabel = new Label("Call Number: ");
		Label isbnLabel = new Label("ISBN: ");
		Label titleLabel = new Label("Title: ");
		Label authorLabel = new Label("Main Author: ");
		Label publisherLabel = new Label("Publisher: ");
		Label yearLabel = new Label("Year Published: ");
		Label amountLabel = new Label("Amount: ");
		Label subjectsLabel = new Label("Subjects: ");

		bookFrame.setLayout(new GridLayout(11,2));		
		bookFrame.add(callLabel);
		bookFrame.add(callNumber);
		bookFrame.add(isbnLabel);
		bookFrame.add(isbn);
		bookFrame.add(titleLabel);
		bookFrame.add(title);
		bookFrame.add(authorLabel);
		bookFrame.add(mainAuthor);
		bookFrame.add(publisherLabel);
		bookFrame.add(publisher);
		bookFrame.add(yearLabel);
		bookFrame.add(year);
		bookFrame.add(amountLabel);
		bookFrame.add(amount);
		bookFrame.add(subjectsLabel);
		bookFrame.add(subjects);
		
		btnAdd = new Button("Finish Adding the Book");
		btnAdd.setActionCommand("add book");
		bookFrame.add(btnAdd);
		bookFrame.setLocationRelativeTo(m);
		bookFrame.pack();
		bookFrame.setVisible(true);
		callLabel.requestFocus();
		bookFrame.setTitle("Add Book Form");
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String subjectText = subjects.getText();
				List<String> subjectsArray = Arrays.asList(subjectText.split("\\s*,\\s*"));
				//TODO: do error checking on these
				String sub1 = subjectsArray.get(0);
				String sub2 = subjectsArray.get(1);
				String sub3 = subjectsArray.get(2);
				BookTable.insertBook(callNumber.getText(), isbn.getText(), title.getText(), mainAuthor.getText(), publisher.getText(), year.getText(), amount.getText(), sub1, sub2, sub3);
			}
		});
		bookFrame.addWindowListener( new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				bookFrame.setVisible(false);
			}
		});
	}

			
	
}
