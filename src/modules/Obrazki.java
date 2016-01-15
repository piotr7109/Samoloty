package modules;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Obrazki
{
	public static BufferedImage obrazekPocisk;
	public static BufferedImage obrazekSamolot, obrazekSamolot2;
	public static BufferedImage obrazekSamolot_dmg, obrazekSamolot2_dmg;
	public static BufferedImage obrazekSamolot_hard_dmg, obrazekSamolot2_hard_dmg;
	
	public static BufferedImage flaga_a;
	public static BufferedImage flaga_b;
	
	public static void ladujObrazki()
	{
		try
		{
			obrazekSamolot = ImageIO.read(new File("gfx/samolot.png"));
			obrazekSamolot2 = ImageIO.read(new File("gfx/samolot.png"));
			
			obrazekSamolot_dmg = ImageIO.read(new File("gfx/samolot.png"));
			obrazekSamolot2_dmg = ImageIO.read(new File("gfx/samolot.png"));
			
			obrazekSamolot_hard_dmg = ImageIO.read(new File("gfx/samolot.png"));
			obrazekSamolot2_hard_dmg = ImageIO.read(new File("gfx/samolot.png"));
			
			flaga_a = ImageIO.read(new File("gfx/pocisk.png"));
			flaga_b = ImageIO.read(new File("gfx/pocisk.png"));
			
			obrazekPocisk = ImageIO.read(new File("gfx/pocisk.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
