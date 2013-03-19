import java.sql.*;

public class db_helper {
static Connection conn;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
	    	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_i7f7", "a71163091");
			
			//run what you want from here
			
			dropAllTables();
            //close afterwards
			conn.close();
          
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Drops all records from the given table.
	 */
	public static void dropFromTable(String table){
        Statement stmt;
        try{
    		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            try{
                stmt = conn.createStatement();
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
	public static void dropAllTables(){
		Statement qstmt;
		Statement dstmt;
		ResultSet rs;
		String[] tables;
        try{
    		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    		try{
		            qstmt = conn.createStatement();
		            dstmt = conn.createStatement();
		            
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
