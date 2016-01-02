package modules;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Samolot implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double x, y, width, height, kat;
	private int punkty_zycia, ilosc_bomb;
	public ArrayList<Pocisk> pociski;

	private transient AffineTransform rotacja;
	public transient AffineTransformOp transformacja_op;

	public Samolot(int x, int y, double kat)
	{
		punkty_zycia = 100;
		ilosc_bomb = 5;
		this.kat = kat;
		this.x = x;
		this.y = y;
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

		// obrót obrazka o k¹t
		rotacja = AffineTransform.getRotateInstance(Math.toRadians(this.kat + 90), width, height);
		transformacja_op = new AffineTransformOp(rotacja, AffineTransformOp.TYPE_BILINEAR);

		// aktualizacja wspó³rzêdnych pocisków
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
		Obrazki.ladujObrazki();
		width = Obrazki.obrazekSamolot.getWidth() / 2;
		height = Obrazki.obrazekSamolot.getHeight() / 2;

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
