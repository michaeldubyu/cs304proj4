import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class SearchBook {

	public SearchBook(){
		
		final Frame searchFrame = new Frame("Search For Books");
		final JTextField title = new JTextField(20);
		final JTextField author = new JTextField(20);
		final JTextField subject = new JTextField(20);
		
		searchFrame.setLayout(new GridLayout(4,2));

		Label titleLabel = new Label("Title :");
		Label authorLabel = new Label("Author :");
		Label subjectLabel = new Label("Subject :");

		searchFrame.add(titleLabel);
		searchFrame.add(title);
		searchFrame.add(authorLabel);
		searchFrame.add(author);
		searchFrame.add(subjectLabel);
		searchFrame.add(subject);
		
		Button submit = new Button("Submit");
		searchFrame.add(new Label("(*)Required fields marked.")); //to pad the submit button to the right
		searchFrame.add(submit);
		searchFrame.setLocationRelativeTo(null);
		searchFrame.pack();
		searchFrame.setVisible(true);
		title.requestFocus();	
		
		submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          //when the submit button is clicked
	        	try{
	        		ArrayList<ArrayList<String>> result = BookTable.searchBook(title.getText(),author.getText(),subject.getText());
	        		if (result.size()==0) throw new Exception("There were no results for your query. Please try again.");
	        		
	        		final Frame successFrame = new Frame("Results");

	        		//callnumber, name, count
	        		Object rowData[][] = new Object[result.size()][3];
	        		int i = 0;
	        		for (ArrayList<String> s:result){
	        			rowData[i] = s.toArray();
	        			i++;
	        		}
	        		Object columnNames[] = { "Call Number", "Title", "Author" };
	        		JTable table = new JTable(rowData, columnNames);

	        		JScrollPane scroll = new JScrollPane(table);
	        		
	        		successFrame.add(scroll);
	        		successFrame.pack();
	        		successFrame.setVisible(true);
	        		successFrame.setAlwaysOnTop(true);
	        		successFrame.setLocationRelativeTo(searchFrame);
	                successFrame.addWindowListener( new WindowAdapter() {
	                    public void windowClosing(WindowEvent we) {
	                        successFrame.setVisible(false);
	                    }
	                } );
	        	}catch(Exception argException){
	        		final Frame errorFrame = new Frame("Error!");
	        		Label error = new Label(argException.getMessage());
	        		errorFrame.add(error);
	        		errorFrame.pack();
	        		errorFrame.setVisible(true);
	        		errorFrame.setAlwaysOnTop(true);
	        		errorFrame.setLocationRelativeTo(searchFrame);
	                errorFrame.addWindowListener( new WindowAdapter() {
	                    public void windowClosing(WindowEvent we) {
	                
	                        errorFrame.setVisible(false);
	                    }
	                } );
	        	}
	        }
	      });
		
        searchFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                searchFrame.setVisible(false);
            }
        } );
		
	}

}
