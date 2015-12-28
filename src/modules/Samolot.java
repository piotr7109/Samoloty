package modules;
import java.util.ArrayList;


public class Samolot 
{
	public double x, y;
	private int punkty_zycia;
	public ArrayList<Pocisk> pociski;
	private int ilosc_bomb;
	public double kat;
	
	public Samolot( int x, int y, double kat)
	{
		punkty_zycia = 100;
		ilosc_bomb = 5;
		this.kat = kat;
		this.x = x;
		this.y = y;
		pociski = new ArrayList<Pocisk>();
	}
	public void aktualizujWspolrzedne()
	{
		this.x += Math.cos(Math.toRadians(this.kat))*3;
		this.y += Math.sin(Math.toRadians(this.kat))*3;
		
	}
	
	public int getPunkty_zycia()
	{
		return punkty_zycia;
	}
	public void setPunkty_zycia(int punkty_zycia)
	{
		this.punkty_zycia = punkty_zycia;
	}
	public int getIlosc_bomb()
	{
		return ilosc_bomb;
	}
	public void setIlosc_bomb(int ilosc_bomb)
	{
		this.ilosc_bomb = ilosc_bomb;
	}
	
	
	public ArrayList<Pocisk> getPociski()
	{
		return pociski;
	}
	public void dodajPocisk(String typ)
	{
		Pocisk pocisk = new Pocisk(this.x, this.y, this.kat, typ);
		this.pociski.add(pocisk);
	}
	
	
	
}
