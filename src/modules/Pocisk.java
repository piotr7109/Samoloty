package modules;

import java.awt.Point;

public class Pocisk 
{
	
	public double x,y, speed;
	private String typ;
	private double start_x, start_y;
	private int max_czas_zycia = 500;
	
	private double kat;
	public Pocisk(double x, double y, double kat, String typ)
	{
		this.x = start_x = x;
		this.y = start_y = y;
		this.kat = kat;
		this.typ =typ;
		
		switch(typ)
		{
			case "normalny":
				speed = 6;
				break;
			case "bomba":
				speed = 4;
				break;
		}
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
