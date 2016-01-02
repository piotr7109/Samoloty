package modules;

import java.io.Serializable;

public class Gracz implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int login;
	private int ip;
	private Samolot samolot;
	private int punkty;
	private int fragi;
	
	public Gracz(int x, int y, double kat)
	{
		Samolot samolot = new Samolot(x, y, kat);
		this.samolot = samolot;
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getLogin()
	{
		return login;
	}
	public void setLogin(int login)
	{
		this.login = login;
	}
	public int getIp()
	{
		return ip;
	}
	public void setIp(int ip)
	{
		this.ip = ip;
	}
	public Samolot getSamolot()
	{
		return samolot;
	}
	public void setSamolot(Samolot samolot)
	{
		this.samolot = samolot;
	}
	public int getPunkty()
	{
		return punkty;
	}
	public void setPunkty(int punkty)
	{
		this.punkty = punkty;
	}
	public int getFragi()
	{
		return fragi;
	}
	public void setFragi(int fragi)
	{
		this.fragi = fragi;
	}
	
	

}
