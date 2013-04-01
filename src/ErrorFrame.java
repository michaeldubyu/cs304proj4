import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ErrorFrame extends Frame {


	public ErrorFrame(String message, Frame frame){
		final Frame errorFrame = new Frame("Error!");
		Label error = new Label(message);
		errorFrame.add(error);
		errorFrame.pack();
		errorFrame.setVisible(true);
		errorFrame.setAlwaysOnTop(true);
		errorFrame.setLocationRelativeTo(frame);
		errorFrame.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				errorFrame.setVisible(false);
			}
		} );
	}

}
