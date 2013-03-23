import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class BorrowingTable {
	
	private static Connection con;
	
	private static final String[] attNames = 
		{"borid", "bid", "callNumber", "copyNo", "outDate", "inDate"}; 
	
	public static void insertBorrowing(String borid, String bid, String callNumber, String copyNo, 
			  String outDate, String inDate) throws IllegalArgumentException
	{

			try {
				con = db_helper.connect("ora_i7f7", "a71163091");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			PreparedStatement  ps;

			try
			{
				ps = con.prepareStatement("INSERT INTO borrowing VALUES (?,?,?,?,?,?)");

				ps.setString(1, bid);
				ps.setString(2, borid);
				ps.setString(3, callNumber);
				ps.setString(4, copyNo);
				
				if (!outDate.matches("^\\d*$")||outDate.equals(""))
					  throw new IllegalArgumentException("Needs to be UNIX time bro");
				  else
				  {
					  int d = Integer.parseInt(outDate);
					  ps.setInt(5, d);
				  }
				
				if (!inDate.matches("^\\d*$")||inDate.equals(""))
					  throw new IllegalArgumentException("Needs to be UNIX time bro");
				  else
				  {
					  int i = Integer.parseInt(outDate);
					  ps.setInt(6, i);
				  }
				
				

			}
			
			catch (SQLException ex)
			{
			    System.out.println("Message: " + ex.getMessage());
			    try 
			    {
				// undo the insert
				con.rollback();	
			    }
			    catch (SQLException ex2)
			    {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			    }
			}
}
}