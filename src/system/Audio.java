package system;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio implements Runnable
{
	private String type = "";
	public volatile boolean play = true;

	public Audio(String t)
	{
		// TODO Auto-generated constructor stub
		this.type = t;
	}

	public void run()
	{
		try
		{
			AudioInputStream audioInputStream = null;

			audioInputStream = AudioSystem.getAudioInputStream(new File("audio/" + type + ".wav").getAbsoluteFile());

			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			
			if (type.equals("theme"))
			{
				clip.loop(12);
			}
			if(type.equals("cantina"))
			{
				clip.loop(10);
				while(play)
				{
					System.out.println("true");
				}
				System.out.println("false");
				clip.stop();
			}

		}
		catch (Exception ex)
		{
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}
	

}
