package modules;

import java.awt.Point;
import java.io.Serializable;

public class Pocisk implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double x,y, speed, width, height, kat;
	public String typ;
	
	private double start_x, start_y;
	private int max_czas_zycia = 900;
	
	
	public Pocisk(double x, double y, double kat, String typ, Obrazki o)
	{
		this.x = start_x = x;
		this.y = start_y = y;
		this.kat = kat;
		this.typ =typ;
		
		switch(this.typ)
		{
			case "normalny":
				speed = 6;
				break;
			case "bomba":
				speed = 4;
				break;
		}
		width = o.obrazekPocisk.getWidth();
		height = o.obrazekPocisk.getHeight();
	}
	
	public boolean aktualizujWspolrzedne()
	{
		this.x += Math.cos(Math.toRadians(this.kat))*speed;
		this.y += Math.sin(Math.toRadians(this.kat))*speed;
		if(obliczCzasZycia() >=max_czas_zycia)
		{
			return false;
		}
		return true;
	}
	private int obliczCzasZycia()
	{
		int czas_zycia;
		
		Point a = new Point((int)start_x, (int)start_y);
		Point b = new Point((int)x, (int)y);
		czas_zycia = Obliczenia.getDlugosc(a, b);
		return czas_zycia;
	}
	
}
