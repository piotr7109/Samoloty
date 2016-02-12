package modules;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Obrazki
{
	public BufferedImage obrazekPocisk;
	public BufferedImage obrazekPocisk2;
	public BufferedImage obrazekSamolot, obrazekSamolot2;
	public BufferedImage obrazekSamolot_dmg, obrazekSamolot2_dmg;
	public BufferedImage obrazekSamolot_hard_dmg, obrazekSamolot2_hard_dmg;

	public BufferedImage flaga_a;
	public BufferedImage flaga_b;

	public BufferedImage tlo;
	public Image tlo2;
	
	public BufferedImage background;
	public Image background2;

	public void ladujObrazki()
	{
		try
		{
			obrazekSamolot = ImageIO.read(getClass().getResource("/wing.png"));
			obrazekSamolot2 = ImageIO.read(getClass().getResource("/tie.png"));

			obrazekSamolot_dmg = ImageIO.read(getClass().getResource("/wing.png"));
			obrazekSamolot2_dmg = ImageIO.read(getClass().getResource("/tie.png"));

			obrazekSamolot_hard_dmg = ImageIO.read(getClass().getResource("/wing.png"));
			obrazekSamolot2_hard_dmg = ImageIO.read(getClass().getResource("/tie.png"));

			flaga_a = ImageIO.read(getClass().getResource("/rebel.png"));
			flaga_b = ImageIO.read(getClass().getResource("/empire.png"));

			obrazekPocisk = ImageIO.read(getClass().getResource("/red_bullet.png"));
			obrazekPocisk2 = ImageIO.read(getClass().getResource("/green_bullet.png"));

			tlo = ImageIO.read(getClass().getResource("/tlo.png"));
			tlo2 = (Image) tlo;
			
			background = ImageIO.read(getClass().getResource("/background.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
