package modules;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Obrazki
{
	public static BufferedImage obrazekPocisk;
	public static BufferedImage obrazekSamolot;
	
	public static void ladujObrazki()
	{
		try
		{
			obrazekSamolot = ImageIO.read(new File("gfx/samolot.png"));
			obrazekPocisk = ImageIO.read(new File("gfx/pocisk.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
