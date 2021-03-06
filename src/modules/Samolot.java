package modules;


import java.io.Serializable;
import java.util.ArrayList;

public class Samolot implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double x, y, width, height, kat;
	private int punkty_zycia, ilosc_bomb;
	public ArrayList<Pocisk> pociski;
	public Obrazki o;

	

	public Samolot(int x, int y, double kat, Obrazki o)
	{
		punkty_zycia = 100;
		ilosc_bomb = 5;
		this.kat = kat;
		this.x = x;
		this.y = y;
		this.o = o;
		pociski = new ArrayList<Pocisk>();
		ladujObrazek();

	}

	public int getPunktyZycia()
	{
		return punkty_zycia;
	}

	public void setPunktyZycia(int punkty_zycia)
	{
		this.punkty_zycia = punkty_zycia;
	}

	public int getIloscBomb()
	{
		return ilosc_bomb;
	}

	public void setIloscBomb(int ilosc_bomb)
	{
		this.ilosc_bomb = ilosc_bomb;
	}

	public void aktualizujWspolrzedne()
	{
		this.x += Math.cos(Math.toRadians(this.kat)) * 3;
		this.y += Math.sin(Math.toRadians(this.kat)) * 3;

		// obr�t obrazka o k�t
		

		// aktualizacja wsp�rz�dnych pocisk�w
		int size = pociski.size();
		for (int i = 0; i < size; i++)
		{
			Pocisk pocisk = pociski.get(i);
			if (!pocisk.aktualizujWspolrzedne())
			{
				pociski.remove(i);
				size--;
			}
		}

	}

	protected void ladujObrazek()
	{
		
		o.ladujObrazki();
		width = o.obrazekSamolot.getWidth() / 2;
		height = o.obrazekSamolot.getHeight() / 2;

	}

	public ArrayList<Pocisk> getPociski()
	{
		return pociski;
	}

	public void dodajPocisk(String typ)
	{
		Pocisk pocisk = new Pocisk(this.x, this.y, this.kat, typ, o);
		this.pociski.add(pocisk);
	}

}
