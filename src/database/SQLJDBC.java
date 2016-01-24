package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLJDBC 
{
	private String host = "21659.m.tld.pl";
	private String database = "baza59_jagielnia";
	private String user = "admin59_jagielnia";
	private String password = "Jagielnia123@"; 
	
	private Connection c = null;
	
	public Connection getC() 
	{
		return c;
	}


	public SQLJDBC()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://"+host+"/"+database, user, password);
			c.createStatement().execute("SET NAMES utf8");
			System.out.println("Opened database successfully");
			
			
		} 
		catch ( Exception e ) 
		{
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
	    }
	    System.out.println("Operation done successfully");
		
		
	}
	public void queryOpertaion(String query) 
	{
		Statement stmt;
		try
		{
			stmt = c.createStatement();
			stmt.executeUpdate(query );
			stmt.close();
			c.close();

		}
		catch ( SQLException e ) 
		{
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
	    }
	}

	
	
	
}