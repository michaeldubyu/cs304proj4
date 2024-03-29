import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BorrowingTable {
	
	private static Connection con;
	
	private static final String[] attNames = 
		{"bid", "name", "title", "outDate", "emailAddress", "btype"}; 
	
	private static final long CURRENT_TIME = (System.currentTimeMillis() / 1000L);
	
	/* 
	 * Used for CheckOutBook for the action of the Clerk to check out a bunch of books for a given user.
	 * borid is the unique number to log this transaction, not to be confused with bid, the id of the user borrowing
	 * indate is given, but outdate should be computed dynamically as soon as the item is checked in based on the user's type
	 * therefore, we need to 1.) ensure bid is valid, then 2.) look up type on bid with the borrower table before we proceed
	 * with anything further
	 * returns the transaction id, -1 if it was not successful
	 */
	public static int insertBorrowing(String bid, String callNumber, String copyNo) 
			throws IllegalArgumentException
	{
		String userType = null;
		String inDate = null;
		long outDate = System.currentTimeMillis()/1000;
		int transID = -1;
		
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
		
		
			if (userType == null) throw new IllegalArgumentException("User does not exist!");
			PreparedStatement  ps;
			ps = con.prepareStatement("INSERT INTO borrowing (bid, callnumber, copyno, outdate, indate) VALUES (?,?,?,?,?)");
			//set bid - at this point its already validated, so no longer need to run regex through it
			ps.setString(1, bid);
			
			//set callNumber
		    if (callNumber.equals(""))
				throw new IllegalArgumentException("Call numbers could be " + getCallNumbers());
		    else
		    	ps.setString(2,callNumber);
			
			//set copyNo
		    if (copyNo.equals(""))
		    	throw new IllegalArgumentException("Copy numbers could be " + getCopyNumbers(callNumber));
		    else
		    	ps.setString(3,copyNo);
		    
		    //set outDate - it's guaranted to be of an integer type at this point
			ps.setString(4, Long.toString(outDate));
			
			//set inDate - also guaranteed to be of integer in string format at this point
			ps.setString(5, null);
						
			ps.executeUpdate();
			
			Statement s1 = con.createStatement();
			ResultSet rs1 = s1.executeQuery("SELECT borid FROM borrowing WHERE bid = '" + bid + "' AND callnumber = '" + callNumber +
										"' AND outdate = '" + outDate + "'");
			while (rs1.next()) transID = Integer.parseInt(rs1.getString("borid"));
			
			//update bookcopy with this change
			Statement updateBook;
			updateBook = con.createStatement();
			updateBook.executeUpdate("UPDATE bookcopy SET status = 'out' WHERE callnumber = '" + callNumber + "' AND copyno = '" + copyNo + "'");
			
			con.commit();
			con.close();
		
		}catch (SQLException ex)
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
		return transID;
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
			rs = stmt.executeQuery("SELECT b1.bid, b2.name, b3.title, b1.outDate, b2.emailAddress, b2.btype " +
								   "FROM borrowing b1, borrower b2, book b3, bookCopy b4 " +
								   "WHERE b1.bid=b2.bid AND b1.callNumber = b3.callNumber AND "+ 
								   "b3.callNumber = b4.callNumber AND b1.indate is NULL");
			
			 while(rs.next())
			  {  
				 
				  // Number of seconds in a week is 604800
				  // Students = 2 weeks, Staff = 6 weeks, Faculty = 12 weeks
				  
				  long outDate = Long.valueOf(rs.getString(4)).longValue();
				  String borrowerType = rs.getString(6);
				  
				  boolean studentOverdue = borrowerType.toLowerCase().trim().equals("student") & 
						  outDate + 2*604800 < CURRENT_TIME ; 
				  
				  boolean staffOverdue = borrowerType.toLowerCase().trim().equals("staff") & 
						  outDate + 6*604800 < CURRENT_TIME ; 
				  
				  boolean facultyOverdue = borrowerType.toLowerCase().trim().equals("faculty") & 
						  outDate + 12*604800 < CURRENT_TIME ; 
				  
				  
				  
				  if (studentOverdue || staffOverdue || facultyOverdue)
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
	
	public static String getCallNumbers(){
		String callNumbers = "SELECT callNumber FROM book";
		return getAll(callNumbers);
	}
	public static String getCopyNumbers(String callNumber){
		String copyNumbers = "SELECT copyNo FROM BookCopy where callNumber = '" + callNumber + "' ";
		return getAll(copyNumbers);
	}

	/**
	 * @param query 
	 * @return will turn this query into a list of the results
	 */
	private static String getAll(String query) {
		StringBuilder sb = new StringBuilder();
		try {
			if (con.isClosed()) 			
				con = db_helper.connect("ora_i7f7", "a71163091");
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(query);
			while(results.next()){
				sb.append(results.getString(1)).append(", ");
			}
			// gets rid of last ','
			if (sb.length() > 3) sb.deleteCharAt(sb.length()-2);
			stmt.close();
			results.close();
			con.close();
			// for every 5 queries add new line
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
		return sb.toString();
	}
	
	
	
	private static void closeConnection() {
		try {
			if (!con.isClosed())
				con.close();
		} catch (SQLException e) {
			System.out.println("Dude, connection is giving us some trouble");
		}
	}
	
}