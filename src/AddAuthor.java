import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;


public class AddAuthor {

		public AddAuthor(){
						
			final Frame insertFrame = new Frame();
			final JTextField callNo = new JTextField(20);
			final JTextField author = new JTextField(20);
			
			insertFrame.setLayout(new GridLayout(3,2));

			Label callNoLabel = new Label("Call Number* :");
			Label authorLabel = new Label("Additional Author Name* :");

			insertFrame.add(callNoLabel);
			insertFrame.add(callNo);
			insertFrame.add(authorLabel);
			insertFrame.add(author);

			Button submit = new Button("Submit");
			insertFrame.add(new Label("(*)Required fields marked.")); //to pad the submit button to the right
			insertFrame.add(submit);
			insertFrame.setLocationRelativeTo(null);
			insertFrame.pack();
			insertFrame.setTitle("Add An Author");
			insertFrame.setVisible(true);
			callNoLabel.requestFocus();
			
		    submit.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		          //when the submit button is clicked
		        	try{		        		
			        	if (author.getText().equals("")) throw new IllegalArgumentException("Author name must not be blank!");
			        	String quantityAdded = BookTable.addAuthor(callNo.getText(), author.getText());
		        		final Frame successFrame = new Frame("Success!");
		        		Label success = new Label(author.getText() + " was successfully added as an author to this book.");
		        		successFrame.add(success);
		        		successFrame.pack();
		        		successFrame.setVisible(true);
		        		successFrame.setAlwaysOnTop(true);
		        		successFrame.setLocationRelativeTo(insertFrame);
		                successFrame.addWindowListener( new WindowAdapter() {
		                    public void windowClosing(WindowEvent we) {
		                
		                        successFrame.setVisible(false);
		                        insertFrame.dispose();
		                    }
		                } );
			        
		        	}catch(Exception argException){
		        		final Frame errorFrame = new Frame("Error!");
		        		Label error = new Label(argException.getMessage());
		        		errorFrame.add(error);
		        		errorFrame.pack();
		        		errorFrame.setVisible(true);
		        		errorFrame.setAlwaysOnTop(true);
		        		errorFrame.setLocationRelativeTo(insertFrame);
		                errorFrame.addWindowListener( new WindowAdapter() {
		                    public void windowClosing(WindowEvent we) {
		                
		                        errorFrame.setVisible(false);
		                    }
		                } );
		        	}
		        }
		      });
			
	        insertFrame.addWindowListener( new WindowAdapter() {
	            public void windowClosing(WindowEvent we) {
	                insertFrame.setVisible(false);
	            }
	        } );
		}

}
