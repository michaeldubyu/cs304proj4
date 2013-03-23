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
	public static void insertBorrowing(String bid, String callNumber, String copyNo, String inDate) 
			throws IllegalArgumentException
	{
		String userType = null;
		int outDate = 0;

		if (bid.equals("")) throw new IllegalArgumentException("Borrower ID cannot be empty!");
		
		try {
			con = db_helper.connect("ora_i7f7", "a71163091");
			Statement s = con.createStatement();
			ResultSet rs;
			
			//get the borrower type
			rs = s.executeQuery("SELECT * FROM BORROWER WHERE bid = '" + bid + "'");
			
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				userType = rs.getString("btype").trim().toLowerCase();
			}
			
			//try to cast the indate to a time
			outDate = Integer.parseInt(inDate);
			switch (userType){
				case("student"):
					outDate += 1209600; //2 weeks in seconds we add on
					break;
				case("faculty"):
					outDate += 7257600; //12 weeks
					break;
				case("staff"):
					outDate += 3628800; //6 weeks
					break;
			}
		}catch (NumberFormatException e){
			throw new IllegalArgumentException("In date is not of valid format - needs to be numbers and nonempty.");				
		}catch (Exception e) {}
		
		if (userType == null) throw new IllegalArgumentException("User does not exist!");
		
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("INSERT INTO borrowing (bid, callnumber, copyno, outdate, indate) VALUES (?,?,?,?,?)");
			
			//set bid - at this point its already validated, so no longer need to run regex through it
			ps.setString(1, bid);
			
			//set callNumber
		    if (callNumber.equals(""))
				throw new IllegalArgumentException("Call number cannot be empty!");
		    else
		    	ps.setString(2,callNumber);
			
			//set copyNo
		    if (copyNo.equals(""))
		    	throw new IllegalArgumentException("Copy number cannot be empty!");
		    else
		    	ps.setString(3,copyNo);
		    
		    //set outDate - it's guaranted to be of an integer type at this point
			ps.setString(4, Integer.toString(outDate));
			
			//set inDate - also guaranteed to be of integer in string format at this point
			ps.setString(5, inDate);
			
			System.out.println(ps);
			
			ps.executeUpdate();
			con.commit();
			con.close();
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