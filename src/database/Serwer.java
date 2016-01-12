package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Serwer
{
	private int id, start;
	private String typ_gry, tryb_gry, ip_serwera;
	public ArrayList<Gracz_mod> gracze = new ArrayList<Gracz_mod>();
	

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTypGry()
	{
		return typ_gry;
	}

	public void setTypGry(String typ_gry)
	{
		this.typ_gry = typ_gry;
	}

	public String getTrybGry()
	{
		return tryb_gry;
	}

	public void setTrybGry(String tryb_gry)
	{
		this.tryb_gry = tryb_gry;
	}

	public String getIpSerwera()
	{
		return ip_serwera;
	}

	public void setIpSerwera(String ip_serwera)
	{
		this.ip_serwera = ip_serwera;
	}
	
	
	public int getStart()
	{
		return start;
	}

	public ArrayList<Gracz_mod> getGracze()
	{
		setGracze();
		return gracze;
	}

	public int insert()
	{
		
		SQLJDBC baza = new SQLJDBC();
		baza.queryOpertaion(String.format("INSERT INTO t_serwery(typ_gry, tryb_gry, ip_serwera) "
										+ "VALUES('%s', '%s','%s')", typ_gry, tryb_gry, ip_serwera));
		this.id = getLastInserted();
		return id;
		
	}
	public void startSerwer()
	{
		
		SQLJDBC baza = new SQLJDBC();
		baza.queryOpertaion(String.format("UPDATE t_serwery SET start=1 WHERE id=%d", this.id));
		
	}
	
	public void delete()
	{
		
		SQLJDBC baza = new SQLJDBC();
		baza.queryOpertaion(String.format("DELETE FROM t_serwery where id=%d", id));
		baza = new SQLJDBC();
		baza.queryOpertaion(String.format("DELETE FROM t_gracze where id_serwera=%d", id));
		
	}
	public int getLastInserted()
	{
		int id_serwera = 0;
		SQLJDBC baza = new SQLJDBC();
		Statement stmt;
		Connection c = baza.getC(); 
		try
		{
			stmt = c.createStatement();
			String query = String.format("SELECT id FROM t_serwery order by id DESC LIMIT 1");
			
			ResultSet rs = stmt.executeQuery( query );
			while ( rs.next() ) 
			{
				id_serwera = rs.getInt("id");
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
		return id_serwera;
	}
	public void setSerwerById(int id_serwera)
	{
		
		SQLJDBC baza = new SQLJDBC();
		Statement stmt;
		Connection c = baza.getC(); 
		try
		{
			stmt = c.createStatement();
			String query = String.format("SELECT * FROM t_serwery WHERE id=%d", id_serwera);
			
			ResultSet rs = stmt.executeQuery( query );
			while ( rs.next() ) 
			{
				this.id = id_serwera;
				this.ip_serwera = rs.getString("ip_serwera");
				this.tryb_gry = rs.getString("tryb_gry");
				this.typ_gry = rs.getString("typ_gry");
				this.start = rs.getInt("start");
				
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
	}
	public void addGracz(Gracz_mod gracz)
	{
		SQLJDBC baza = new SQLJDBC();
		baza.queryOpertaion(String.format("INSERT INTO t_gracze(id, id_serwera, login, druzyna, pozycja, gotowy) "
										+ "VALUES('%d', '%d','%s','%c', %d, %d)", gracz.id, this.id, gracz.login, gracz.druzyna, gracz.pozycja, gracz.gotowy));
		this.gracze.add(gracz);
	}
	public void gotowyGracz(int id_gracza)
	{
		SQLJDBC baza = new SQLJDBC();
		baza.queryOpertaion(String.format("UPDATE t_gracze SET gotowy=1 WHERE id=%d", id_gracza));
	}
	
	
	private void setGracze()
	{
		SQLJDBC baza = new SQLJDBC();
		Statement stmt;
		Connection c = baza.getC(); 
		try
		{
			stmt = c.createStatement();
			String query = String.format("SELECT * FROM t_gracze WHERE id_serwera = %d",this.id);
			
			ResultSet rs = stmt.executeQuery( query );
			while ( rs.next() ) 
			{
				Gracz_mod g = new Gracz_mod();
				g.id = rs.getInt("id");
				g.druzyna = rs.getString("druzyna").charAt(0);
				g.id_serwera = rs.getInt("id_serwera");
				g.login = rs.getString("login");
				g.gotowy = rs.getInt("gotowy");
				g.pozycja = rs.getInt("pozycja");
				this.gracze.add(g);
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
	}

}
