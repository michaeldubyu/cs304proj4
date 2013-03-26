import java.sql.*;
import java.util.ArrayList;

public class Reports {
	
	private static Connection con;
	
	
	/*
	 * Generate a report with the most popular items in a given year. The librarian provides 
	 * a year and a number n. The system lists out the top n books that where borrowed the 
	 * most times during that year. The books are ordered by the number of times they were borrowed.
	*/
	public static ArrayList<ArrayList<String>> mostPopularBooks(String year, String quantity) throws IllegalArgumentException
	{
		int quantityNumber = Integer.parseInt(quantity);
		int yearNumber = Integer.parseInt(year);
		int yearStart = (yearNumber -1970)*31556926;
		int yearEnd = (yearNumber -1970+1)*31556926;
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		Statement  stmt;
		ResultSet  rs;
		

		try {
				con = db_helper.connect("ora_i7f7", "a71163091");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		try
		{
		stmt = con.createStatement();
		
		rs = stmt.executeQuery("SELECT b1.callNumber, COUNT(*) as qty FROM book b1, " +
				"(SELECT * FROM borrowing WHERE outDate >= "+yearStart+
				" AND inDate <= "+yearEnd+") b2 " +
				"WHERE b1.callNumber = b2.callNumber "+
				"GROUP BY b1.callNumber ORDER BY qty DESC");
		
		int i = 0;
		
		while ((i<quantityNumber)&&(rs.next()))
		{
			ArrayList<String> aPopularBook = new ArrayList<String>();
			aPopularBook.add(rs.getString(1));
			aPopularBook.add(rs.getString(2));
			result.add(aPopularBook);
			i++;
		}
				
		
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		return result;
		
	}
	
	/*
	 * Generate a report with all the books that have been checked out. For each book the report
	 * shows the date it was checked out and the due date. The system flags the items that are 
	 * overdue. The items are ordered by the book call number.  If a subject is provided the report 
	 * lists only books related to that subject, otherwise all the books that are out are listed by 
	 * the report.
	*/
	
	public static ArrayList<ArrayList<String>> lentItemsReport(String subject)
	{
		try {
			con = db_helper.connect("ora_i7f7", "a71163091");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Statement  stmt;
		ResultSet  rs;
		
		try {
			
			if (subject.equals(""))
			{
			
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT * FROM borrowing ORDER BY callNumber ASC");
				
				while(rs.next())
				{
					
					
					
				}
				
			
			
			
			}
			
			
			
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
}