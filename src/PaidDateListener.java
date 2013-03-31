import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


public class PaidDateListener implements TableModelListener {


	JTable table;
	public PaidDateListener(JTable table){
		this.table = table;
		table.getModel().addTableModelListener(this);
	}
	@Override
	public void tableChanged(TableModelEvent e) {

		int row = e.getFirstRow();
		int col = e.getColumn();
		TableModel model = (TableModel)e.getSource();
		String columnName = model.getColumnName(col);
		Object paidDate = model.getValueAt(row, col);
		Object fid = table.getValueAt(row, 0);
		Object fineStartDate = model.getValueAt(row, 1);
		assert(paidDate instanceof String && fineStartDate instanceof String);
		assert(fid instanceof String);
		try {
			BorrowerTable.updatePaidDate((String) fid, (String)paidDate, (String) fineStartDate);
			System.out.println("Table event happened! WOOO!");
		} catch (IllegalArgumentException e1) {
			
			new ErrorFrame(e1.getLocalizedMessage(), null);
		}
			
	}

}
