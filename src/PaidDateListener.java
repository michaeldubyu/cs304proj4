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
		Object data = model.getValueAt(row, col);
		Object fid = table.getValueAt(row, 0);
		assert(data instanceof String);
		assert(fid instanceof String);
		System.out.println("Table event happened! WOOO!");
		
		BorrowerTable.insertPaidDate((String) fid, (String)data);
			
	}

}
