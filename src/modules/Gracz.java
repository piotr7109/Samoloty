package modules;

import java.io.Serializable;

public class Gracz implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id, id_serwera;
	public String login;
	public char druzyna;
	private int ip;
	private Samolot samolot;
	private int punkty;
	private int fragi;
	public boolean flaga;
	
	public Gracz(int x, int y, double kat, Obrazki o)
	{
		Samolot samolot = new Samolot(x, y, kat, o );
		this.samolot = samolot;
		flaga = false;
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
