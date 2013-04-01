import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;



public class BookTable {

	private static Connection con;

	private static final String[] attNames = 
		{"callNumber", "isbn", "title", "mainAuthor", "publisher", "year"};

	public static ArrayList<ArrayList<String>> searchBook(String searchTitle, String searchAuthor, String searchSubject) throws IllegalArgumentException
	{
		
		String title = new String(searchTitle);
		String author = new String(searchAuthor);
		String subject = new String(searchSubject);
		
		if(searchTitle.equals(""))
			title = "FFFFFFFFFFFFF";
		if(searchAuthor.equals(""))
			author = "FFFFFFFFFFFFF";
		if(searchSubject.equals(""))
			subject = "FFFFFFFFFFFFF";
		
		
		
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		
		ResultSet rs;
		
		Statement stmt;
		
		Statement stmtSubject;
		Statement stmtAuthor;
		Statement stmtTitle;
		
		ResultSet rsSubject;
		ResultSet rsAuthor;
		ResultSet rsTitle;
		
		try
		{
			
			con = db_helper.connect("ora_i7f7", "a71163091");
			
			stmt = con.createStatement();
			stmtSubject = con.createStatement();
			stmtAuthor = con.createStatement();
			stmtTitle = con.createStatement();
			
			
			/*
			rs = stmt.executeQuery("SELECT x.callNumber, count(*) as qty FROM " +
					"((Select Distinct s.callNumber FROM hasSubject s WHERE "+
                    "s.subject LIKE '%"+subject+"%') " +
					"UNION "+
                    "(Select distinct b1.callNumber FROM book b1 WHERE b1.title LIKE '%"+title+"%') "+
                    "UNION "+
                    "(Select DISTINCT b.callNumber FROM book b WHERE b.mainAuthor LIKE '%"+author+"%' "+
			                                  "OR EXISTS (SELECT * from hasAuthor a where a.callNumber = b.callNumber "+
					                                      "AND a.name LIKE '%"+author+"%'))) x "
					+ "GROUP BY x.callNumber ORDER BY qty DESC"
					                                      );
			
			*/
			
			
			rsSubject = stmtSubject.executeQuery("Select Distinct b.callNumber FROM book b, hasSubject s WHERE b.callNumber = s.callNumber "+
			                                     "AND s.subject LIKE '%"+subject+"%'");
			
			rsTitle = stmtTitle.executeQuery("Select distinct b.callNumber FROM book b WHERE b.title LIKE '%"+title+"%'");
			
			rsAuthor = stmtAuthor.executeQuery("Select DISTINCT b.callNumber FROM book b WHERE b.mainAuthor LIKE '%"+author+"%'"+
			                                  "OR EXISTS (SELECT * from hasAuthor a where a.callNumber = b.callNumber "+
					                                      "AND a.name LIKE '%"+author+"%')");
			
			
			
			
			
			ArrayList<String> hits = new ArrayList<String>();
			
			while(rsSubject.next()){
				hits.add(rsSubject.getString(1));}
			
			while(rsTitle.next()){
				hits.add(rsTitle.getString(1));}
			
			while(rsAuthor.next()){
				hits.add(rsAuthor.getString(1));}
			
			Collections.sort(hits);
			
			ArrayList<String> oneHit = new ArrayList<String>();
			ArrayList<String> twoHits = new ArrayList<String>();
			ArrayList<String> threeHits = new ArrayList<String>();
			
			while (hits.size()>0){
				
				if (hits.size()>2&&hits.get(0).equals(hits.get(1))&&hits.get(1).equals(hits.get(2)))
				{threeHits.add(hits.get(0));
				hits.remove(0);hits.remove(0);hits.remove(0);}
				
				else if (hits.size()>1&&hits.get(0).equals(hits.get(1)))
				{twoHits.add(hits.get(0));
				hits.remove(0);hits.remove(0);}
				
				else {oneHit.add(hits.get(0)); hits.remove(0);}
			}
			
			hits.addAll(threeHits);hits.addAll(twoHits);hits.addAll(oneHit);
			
			
			
			
			
			
			
			
			
			
			/*
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
			*/
			
			
			int j = 0;
			while(j < hits.size())
			{
				String callNumber = hits.get(j);
				
				ResultSet atts;
				atts = stmt.executeQuery("select * from book where callnumber = '" + callNumber + "'");
				atts.next();
				String currentTitle = atts.getString("title");
				String currentAuthor = atts.getString("mainAuthor");
				
				ResultSet inout;
				inout = stmt.executeQuery("select count(*) as numin from bookcopy where callnumber = '" + callNumber + "' AND status = 'in'");
				inout.next();
				String numin = inout.getString("numin");
				inout = stmt.executeQuery("select count(*) as numout from bookcopy where callnumber = '" + callNumber + "' AND status = 'out'");
				inout.next();
				String numout = inout.getString("numout");
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(callNumber);
				temp.add(currentTitle);
				temp.add(currentAuthor);
				temp.add(numin);
				temp.add(numout);
				results.add(temp);
				j++;
				
				
			}
			con.commit();
			con.close();
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

	public static void insertBook(String callNumber, String isbn, String title,
			String mainAuthor,String publisher,String year, String copiesAmount, String... subjects) 
					throws IllegalArgumentException, Exception
					{
		ResultSet  rs;
		PreparedStatement ps;


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



		try
		{
			for (String s : subjects){
				if (!s.equals("")){
					HasSubjectTable.insertHasSubject(callNumber, s);
					System.out.println(s);
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
