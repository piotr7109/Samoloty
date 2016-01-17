package system;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio implements Runnable	
{
	private String type = "";
	public Audio(String t)
	{
		// TODO Auto-generated constructor stub
		this.type = t;
	}
	 public void run() {
	    try {
	    	AudioInputStream audioInputStream = null;
	
	    		audioInputStream = AudioSystem.getAudioInputStream(new File("audio/"+type+".wav").getAbsoluteFile());
	    	
	    	
	    	
	    
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	 }
}
