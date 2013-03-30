import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

	

public class BookTable {
	
	private static Connection con;
		
	private static final String[] attNames = 
		{"callNumber", "isbn", "title", "mainAuthor", "publisher", "year"};
	
	public static ArrayList<ArrayList<String>> searchBook(String titleSearch, String authorSearch, String subjectSearch) throws IllegalArgumentException
	{
		if(titleSearch.equals(""))
		{
			titleSearch = "FFFFFFFFFFFFF";
		}
		if(authorSearch.equals(""))
		{
			authorSearch = "FFFFFFFFFFFFF";
		}
		if(subjectSearch.equals(""))
		{
			subjectSearch = "FFFFFFFFFFFFF";
		}
		try {
			con = db_helper.connect("ora_i7f7", "a71163091");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		ResultSet rs;
		Statement stmt;

		try
		{
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(	  "SELECT d.callNumber, d.title, d.mainAuthor, qty FROM book d, "
									+ "(SELECT callNumber, count(*) AS qty FROM ("
									+ "(SELECT * FROM Book b "
									+ "WHERE b.title LIKE '%"+ titleSearch + "%') "
									+ "UNION ALL "
									+ "(SELECT * FROM Book b " 
									+ "WHERE b.mainAuthor LIKE '%" + authorSearch + "%' "
									+ "OR EXISTS (SELECT * FROM HasAuthor h "
									+ "WHERE h.callNumber = b.callNumber "
									+ "AND h.name LIKE '%" + authorSearch + "%'))"
									+ "UNION ALL"
									+ "(SELECT * FROM Book b "
									+ "WHERE EXISTS (SELECT * FROM HasSubject h WHERE "
									+ "h.callNumber = b.callNumber "
									+ "AND h.subject LIKE '%" + subjectSearch + "%'))) "
									+ "GROUP BY callNumber ORDER BY qty desc) c "
									+ "WHERE d.callnumber = c.callnumber");

			
			int i = 0;
			while(rs.next())
			{
				String callNumber = rs.getString("callNumber");
				String title = rs.getString("title");
				String author = rs.getString("mainAuthor");
				
				ResultSet inout;
				inout = stmt.executeQuery("select count(*) as numin from bookcopy where callnumber = '" + callNumber + "' AND status = 'in'");
				inout.next();
				String numin = inout.getString("numin");
				inout = stmt.executeQuery("select count(*) as numout from bookcopy where callnumber = '" + callNumber + "' AND status = 'out'");
				inout.next();
				String numout = inout.getString("numout");
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(0, callNumber);
				temp.add(1, title);
				temp.add(2, author);
				temp.add(3, numin);
				temp.add(4, numout);
				results.add(i,temp);
				i++;
			}
		}catch(Exception e)
		{
			
			e.printStackTrace();
			
		}
		
		return results;
	}
	
	public static void insertCopy(String callNumber, boolean firstBook) throws IllegalArgumentException
	{
		try {
			con = db_helper.connect("ora_i7f7", "a71163091");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ResultSet rs;
		ResultSet rs1;
		Statement stmt;
		PreparedStatement  ps;		
		try
		{
			stmt = con.createStatement();
			rs1 = stmt.executeQuery("SELECT * FROM ItemCopy WHERE callNumber = '" + callNumber+"'");
			
			if (!rs1.next()) 
			{
				throw new IllegalArgumentException("This call number doesn't exist in the library yet.  If you would like to add it, select 'Add Book'");
			}
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM ItemCopy WHERE callNumber = '" + callNumber+"'");
			
			int copyNumber=0;
			while(rs.next())
			{
				copyNumber++;
			}
			
			
			ps = con.prepareStatement("INSERT INTO BookCopy VALUES (" + callNumber + "," + copyNumber + ",\"in\")");
			
			System.out.println(ps);
			  
			ps.executeUpdate();
		
			// commit work 
			con.commit();
		
			ps.close();
		}catch(SQLException e)
		{
			
			/* TO BE IMPLEMENTED -- Shouldn't appear, but will allocate a response */
			
		}
	}
	
	public static void insertBook(String callNumber, String isbn, String title,
			String mainAuthor,String publisher,String year, String amount, String... subjects) 
			throws IllegalArgumentException, Exception
	{
		ResultSet  rs;
		PreparedStatement ps;
		
		try {
		
			con = db_helper.connect("ora_i7f7", "a71163091");
			
			ps = con.prepareStatement("INSERT INTO book VALUES (?,?,?,?,?,?)");
			
			ps.setString(1, callNumber);
			
			if ( (isbn.length()!=10) || (!isbn.matches(".*\\d.*")) )
				throw new IllegalArgumentException("ISBN must be 10 digits");
			else
				ps.setString(2, isbn);
			
			if (mainAuthor.equals("")||(publisher.equals(""))||(title.equals("")))
				throw new IllegalArgumentException("Book must have a title, author and publisher");
			else
				ps.setString(3, title);
				ps.setString(4, mainAuthor);
				ps.setString(5, publisher);
			
			if ( (year.length()!=4) || (!year.matches(".*\\d.*")) )
				throw new IllegalArgumentException("That's not a year, bro");
			else
				ps.setString(6, callNumber);
			
			ps.executeUpdate();
			
			con.commit();
			
			try
			{
				for (String s : subjects){
					if (!s.equals(""))HasSubjectTable.insertHasSubject(callNumber, s);
					System.out.println(s);
				}
			} catch (SQLException e1){
				con.rollback();	
				throw new IllegalArgumentException("Problem inserting subjects.");
	    	} 
			
			insertCopy(callNumber, true);
	    
		
		} catch (Exception e) {
			e.printStackTrace();
			
			//DO SOMETHING WITH THIS EXCEPTION
			//unclean
		}
		
	

		
		
	}

}
