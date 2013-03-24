import java.sql.*;
import java.util.ArrayList;

public class HasSubjectTable {
	
	private static Connection con;
	
	public static void insertHasSubject(String callNumber, String subject) throws SQLException
	{
		try {
			con = db_helper.connect("ora_i7f7", "a71163091");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PreparedStatement  ps;
		
		
			ps = con.prepareStatement("INSERT INTO hasSubject VALUES (?,?)");
			ps.setString(1, callNumber);
			ps.setString(2, subject);
			ps.executeUpdate();
			
		
		
	}

}
