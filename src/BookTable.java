import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	public static int insertCopy(String callNumber, boolean firstBook, int quantity) throws IllegalArgumentException
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
			rs1 = stmt.executeQuery("SELECT * FROM book WHERE callNumber = '" + callNumber+"'");

			if (!rs1.next() && !firstBook) 
			{
				throw new IllegalArgumentException("This call number doesn't exist in the library yet.  If you would like to add it, select 'Add Book'");
			}

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM bookcopy WHERE callNumber = '" + callNumber+"'");

			int copyNumber=1;
			while(rs.next())
			{
				copyNumber++;
			}

			int i = copyNumber;

			while(copyNumber<quantity+i)
			{


				//ps = con.prepareStatement("INSERT INTO BookCopy (callnumber, copyno, status) VALUES (" + callNumber + "," + copyNumber + ",\"in\")");

				ps = con.prepareStatement("INSERT INTO bookCopy VALUES (?,?,?)");

				ps.setString(1, callNumber);
				ps.setString(2, String.valueOf(copyNumber));
				ps.setString(3, "in");
				ps.executeUpdate();

				// commit work 
				con.commit();

				ps.close();
				copyNumber++;

			}
		}catch(SQLException e){
			/* TO BE IMPLEMENTED -- Shouldn't appear, but will allocate a response */

		}
		return quantity;
	}
	
	/*
	 * Inserts an author record for a given callnumber.
	 */
	public static String addAuthor(String callNumber, String author) throws Exception{
		String inserted = "";
		Statement s;
		con = db_helper.connect("ora_i7f7", "a71163091");
		s = con.createStatement();
		
		Statement s2;
		s2 = con.createStatement();
		
		ResultSet rs;
		rs = s.executeQuery("SELECT * FROM BOOK WHERE callnumber = '" +  callNumber + "'");
		if (!rs.next()) throw new IllegalArgumentException("This book doesn't exist!");
		
		ResultSet rs2;
		rs2 = s2.executeQuery("SELECT * FROM HASAUTHOR WHERE callnumber = '" + callNumber + "' AND name = '" + author + "'");
		if (rs2.next()) throw new IllegalArgumentException("This author already exists for this book!");
		
		PreparedStatement ps;
		ps = con.prepareStatement("INSERT INTO HASAUTHOR VALUES (?,?)");
		ps.setString(1,callNumber);
		ps.setString(2,author);
		
		ps.executeUpdate();
		con.commit();
		con.close();

		inserted = author;
		
		return inserted;
	}

	public static void insertBook(String callNumber, String isbn, String title,
			String mainAuthor,String publisher,String year, String copiesAmount, String... subjects) 
					throws IllegalArgumentException, Exception
					{
		ResultSet  rs;
		PreparedStatement ps;
		
		con = db_helper.connect("ora_i7f7", "a71163091");

		//primary key check
		Statement s2;
		ResultSet existRS;
		s2 = con.createStatement();
		existRS = s2.executeQuery("SELECT * FROM book WHERE callnumber = '" + callNumber + "'");
		if (existRS.next()) throw new IllegalArgumentException("A book with this call number already exists!");
		
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
		try
		{
			for (String s : subjects){
				if (!s.equals("")){
					HasSubjectTable.insertHasSubject(callNumber, s);
				}
			}
		} catch (SQLException e1){
			con.rollback();	
			throw new IllegalArgumentException("Problem inserting subjects.");
		} 
		try{
			int amountInt = Integer.parseInt(copiesAmount);
			if(amountInt < 1) throw new IllegalArgumentException("The amount must be more than 1 cent, please enter larger value");
			insertCopy(callNumber, true, amountInt);
		} catch (Exception e){
			throw new Exception("You didn't enter a proper number of copies, must be an integer (at least 1)");
		}
		ps.executeUpdate();

		con.commit();
		}

}
