import java.io.IOException;
import java.sql.*;

public class BorrowerTable {
	
	private static Connection con;
	
	public static void insertBorrower(String bid, String password, String name, String address, int phone, String email, int sinOrStNo, Date expiryDate, String type)
	throws IllegalArgumentException
    {
	
	PreparedStatement  ps;
	  
	try
	{
	  ps = con.prepareStatement("INSERT INTO borrower VALUES (?,?,?,?,?,?,?,?,?)");
	
	  //Set bid
	  ps.setString(1, bid);
	  
	  //Set password
	  if (!((password.matches(".*\\d.*"))&&(!password.matches("^\\d*$"))))
		  throw new IllegalArgumentException("Password must contain letters and numbers");
	  if ((7<password.length())&&(password.length())<13)
		  throw new IllegalArgumentException("Password must be between 8 and 12 characters");
	  else
		  ps.setString(2, password.toString());
	  
	  //Set name
	  if (name.equals(""))
		  throw new IllegalArgumentException("Invalid name");
	  else
		  ps.setString(3,name);
	  
	  //Set name
	  if (name.equals(""))
		  throw new IllegalArgumentException("Invalid name");
	  else
		  ps.setString(3,name);
	  
	  //Set email
	  if (!email.matches(".*@*"))
		  throw new IllegalArgumentException("Invalid email address");
	  else
		  ps.setString(6, email);
	  
	  
		  
	  
	  


	  System.out.print("\nBranch Address: ");
	  baddr = in.readLine();
	  
	  if (baddr.length() == 0)
          {
	      ps.setString(3, null);
	  }
	  else
	  {
	      ps.setString(3, baddr);
	  }
	 
	  System.out.print("\nBranch City: ");
	  bcity = in.readLine();
	  ps.setString(4, bcity);

	  System.out.print("\nBranch Phone: ");
	  String phoneTemp = in.readLine();
	  if (phoneTemp.length() == 0)
	  {
	      ps.setNull(5, java.sql.Types.INTEGER);
	  }
	  else
	  {
	      bphone = Integer.parseInt(phoneTemp);
	      ps.setInt(5, bphone);
	  }

	  ps.executeUpdate();

	  // commit work 
	  con.commit();

	  ps.close();
	}
	catch (IOException e)
	{
	    System.out.println("IOException!");
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
