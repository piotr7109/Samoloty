package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListaSerwerow
{
	
	
	public static ArrayList<Serwer> getList()
	{
		ArrayList<Serwer> serwery = new ArrayList<Serwer>();
		SQLJDBC baza = new SQLJDBC();
		Statement stmt;
		Connection c = baza.getC(); 
		try
		{
			stmt = c.createStatement();
			String query = String.format("SELECT * FROM t_serwery");
			
			ResultSet rs = stmt.executeQuery( query );
			while ( rs.next() ) 
			{
				Serwer s = new Serwer();
				
				s.setId(rs.getInt("id"));
				s.setIpSerwera(rs.getString("ip_serwera"));
				s.setTrybGry(rs.getString("tryb_gry"));
				s.setTypGry(rs.getString("typ_gry"));
				serwery.add(s);
			}
			rs.close();
			stmt.close();
			c.close();

		}
		catch ( Exception e ) 
		{
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
	    }
		
		return serwery;
	}
}
