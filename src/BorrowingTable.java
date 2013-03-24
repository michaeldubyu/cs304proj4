import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class BorrowingTable {
	
	private static Connection con;
	
	private static final String[] attNames = 
		{"bid", "name", "callNumber", "copyNo", "inDate"}; 
	
	private static final long CURRENT_TIME = (System.currentTimeMillis() / 1000L);
	
	/* 
	 * Used for CheckOutBook for the action of the Clerk to check out a bunch of books for a given user.
	 * borid is the unique number to log this transaction, not to be confused with bid, the id of the user borrowing
	 * indate is given, but outdate should be computed dynamically as soon as the item is checked in based on the user's type
	 * therefore, we need to 1.) ensure bid is valid, then 2.) look up type on bid with the borrower table before we proceed
	 * with anything further
	 */
	public static void insertBorrowing(String bid, String callNumber, String copyNo) 
			throws IllegalArgumentException
	{
		String userType = null;
		long inDate = 0;
		long outDate = System.currentTimeMillis()/1000;
		
		if (bid.equals("")) throw new IllegalArgumentException("Borrower ID cannot be empty!");
		
		try {
			con = db_helper.connect("ora_i7f7", "a71163091");
			Statement s = con.createStatement();
			ResultSet rs;
			
			//get the borrower type
			rs = s.executeQuery("SELECT * FROM BORROWER WHERE bid = '" + bid + "'");
			
			while (rs.next()) {
				userType = rs.getString("btype").trim().toLowerCase();
			}
			
			//try to cast L indate to a time
			switch (userType){
				case("student"):
					inDate = outDate + 1209600; //2 weeks in seconds we add on
					break;
				case("faculty"):
					inDate = outDate + 7257600; //12 weeks
					break;
				case("staff"):
					inDate = outDate + 3628800; //6 weeks
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
			ps.setString(4, Long.toString(outDate));
			
			//set inDate - also guaranteed to be of integer in string format at this point
			ps.setString(5, Long.toString(inDate));
			
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
	
	public static ArrayList<ArrayList<String>> displayOverdueItems()
	{
		
		Statement  stmt;
		ResultSet  rs;
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		try {
			con = db_helper.connect("ora_i7f7", "a71163091");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT bid, name, callNumber, copyNo, inDate " +
								   "FROM borrowing, borrower " +
								   "WHERE borrowing.bid=borrower.bid");
			
			 while(rs.next())
			  {  
				  
				  long inDate = Long.valueOf(rs.getString(6)).longValue();
				  
				  if (inDate<CURRENT_TIME)
				  {
					  ArrayList<String> aBorrowing = new ArrayList<String>();
					  
					  for (String anAttribute: attNames)
						  {
						  aBorrowing.add(rs.getString(anAttribute));
					  }
					  result.add(aBorrowing);
				  }
				
				  
		
			  }
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  
		
		
		
		
		
		return result;
	}
	
	
	
}