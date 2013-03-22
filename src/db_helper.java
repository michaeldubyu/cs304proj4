import java.sql.*;

public class db_helper {
	
	
	/**
	 * Static helper class to set up the connection.
	 */
	
	public static Connection connect(String username, String password) throws SQLException{
    	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug",  username, password);	
		return con;
	}
	
	/*
	 * Drops all records from the given table.
	 */
	public static void dropFromTable(Connection con, String table){
        Statement stmt;
        try{
    		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            try{
                stmt = con.createStatement();
                String query = "DELETE FROM " + table;
                int deletedRows=stmt.executeUpdate(query);
                if(deletedRows>0){
                	System.out.println("All rows removed from specified table.");
                }else{
                	System.out.println("Table already empty."); 
                }
                  
	            } catch(SQLException s){
	                        System.out.println("Failed to drop all rows in specified table.");               
	                        s.printStackTrace();
	            }
            // close Connection
            }catch (Exception e){
                    e.printStackTrace();
            }
	}

	/*
	 * Drops all tables from the database.
	 */
	public static void dropAllTables(Connection con){
		Statement qstmt;
		Statement dstmt;
		ResultSet rs;
        try{
    		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    		try{
		            qstmt = con.createStatement();
		            dstmt = con.createStatement();
		            
		            String qQuery = "SELECT * FROM cat";
		            rs = qstmt.executeQuery(qQuery);
	
		            while(rs.next()){
		            	String toDrop = rs.getString("table_name");
		            	String dQuery = "DROP TABLE " + '"' + toDrop + '"' + " purge";
	
		            	dstmt.executeUpdate(dQuery);
		            }
		    		System.out.println("All tables dropped.");

	            } catch(SQLException s){
	                        System.out.println("Failed to drop all tables.");               
	                        s.printStackTrace();
	            }
	        // close Connection
        }catch (Exception e){
                e.printStackTrace();
        }
	}
}
