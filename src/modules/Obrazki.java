package modules;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Obrazki
{
	public static BufferedImage obrazekPocisk;
	public static BufferedImage obrazekPocisk2;
	public static BufferedImage obrazekSamolot, obrazekSamolot2;
	public static BufferedImage obrazekSamolot_dmg, obrazekSamolot2_dmg;
	public static BufferedImage obrazekSamolot_hard_dmg, obrazekSamolot2_hard_dmg;

	public static BufferedImage flaga_a;
	public static BufferedImage flaga_b;

	public static BufferedImage tlo;
	public static Image tlo2;
	
	public static BufferedImage background;
	public static Image background2;

	public static void ladujObrazki()
	{
		try
		{
			obrazekSamolot = ImageIO.read(new File("gfx/wing.png"));
			obrazekSamolot2 = ImageIO.read(new File("gfx/tie.png"));

			obrazekSamolot_dmg = ImageIO.read(new File("gfx/wing.png"));
			obrazekSamolot2_dmg = ImageIO.read(new File("gfx/tie.png"));

			obrazekSamolot_hard_dmg = ImageIO.read(new File("gfx/wing.png"));
			obrazekSamolot2_hard_dmg = ImageIO.read(new File("gfx/tie.png"));

			flaga_a = ImageIO.read(new File("gfx/rebel.png"));
			flaga_b = ImageIO.read(new File("gfx/empire.png"));

			obrazekPocisk = ImageIO.read(new File("gfx/red_bullet.png"));
			obrazekPocisk2 = ImageIO.read(new File("gfx/green_bullet.png"));

			tlo = ImageIO.read(new File("gfx/tlo.png"));
			tlo2 = (Image) tlo;
			
			background = ImageIO.read(new File("gfx/background.png"));
			background2 = (Image) background ;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
