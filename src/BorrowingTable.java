import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class BorrowingTable {
	
	private static Connection con;
	
	private static final String[] attNames = 
		{"borid", "bid", "callNumber", "copyNo", "outDate", "inDate"}; 
	
	/*
	 * Used for CheckOutBook for the action of the Clerk to check out a bunch of books for a given user.
	 * borid is the unique number to log this transaction, not to be confused with bid, the id of the user borrowing
	 * indate is given, but outdate should be computed dynamically as soon as the item is checked in based on the user's type
	 * therefore, we need to 1.) ensure bid is valid, then 2.) look up type on bid with the borrower table before we proceed
	 * with anything further
	 */
	public static void insertBorrowing(String borid, String bid, String callNumber, String copyNo, 
			  String outDate, String inDate) throws IllegalArgumentException
	{
		String userType = null;
		int userCount = 0;

			try {
				con = db_helper.connect("ora_i7f7", "a71163091");
				
				Statement s= con.createStatement();
				ResultSet rs;
				rs = s.executeQuery("SELECT type FROM BORROWER WHERE id = '" + bid + "'");
				userType = rs.getString("type");
				userCount = rs.getFetchSize();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (userCount == 0) throw new IllegalArgumentException("User does not exist!");
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