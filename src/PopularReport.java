import java.awt.BorderLayout;
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


public class PopularReport {

	public PopularReport(){
		final Frame queryFrame = new Frame("Popular Book Lookup");
		
		queryFrame.setLayout(new GridLayout(3,2));		

		final JTextField year = new JTextField(20);
		final JTextField number = new JTextField(20);
		
		final Label yearLabel = new Label("Year Specified* : ");
		final Label numberLabel = new Label("Number of Books* : ");
	
		queryFrame.add(yearLabel);
		queryFrame.add(year);
		queryFrame.add(numberLabel);
		queryFrame.add(number);
		
		Button submit = new Button("Submit");
		queryFrame.add(new Label("(*)Required fields marked.")); //to pad the submit button to the right
		queryFrame.add(submit);
		queryFrame.pack();
		queryFrame.setLocationRelativeTo(null);
        queryFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                queryFrame.setVisible(false);
            }
        } );
		queryFrame.setVisible(true);
		
	    submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          //when the submit button is clicked
	        	try{
	        		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	        		result = Reports.mostPopularBooks(year.getText(), number.getText());
	        		
	        		final Frame successFrame = new Frame("Results");

	        		//callnumber, name, count
	        		Object rowData[][] = new Object[result.size()][2];
	        		int i = 0;
	        		for (ArrayList<String> s:result){
	        			rowData[i] = s.toArray();
	        			i++;
	        		}
	        		Object columnNames[] = { "Call Number", "Count"};
	        		JTable table = new JTable(rowData, columnNames);

	        		JScrollPane scroll = new JScrollPane(table);
	        		
	        		successFrame.add(scroll);
	        		successFrame.pack();
	        		successFrame.setVisible(true);
	        		successFrame.setAlwaysOnTop(true);
	        		successFrame.setLocationRelativeTo(queryFrame);
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
	        		errorFrame.setLocationRelativeTo(queryFrame);
	                errorFrame.addWindowListener( new WindowAdapter() {
	                    public void windowClosing(WindowEvent we) {
	                
	                        errorFrame.setVisible(false);
	                    }
	                } );
	        	}
	        }
	      });
		
	}

}
