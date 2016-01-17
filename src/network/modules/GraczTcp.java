package network.modules;

import java.io.Serializable;
import java.util.ArrayList;


public class GraczTcp implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public short x, y, kat;
	public short  punkty_zycia, id, punkty, fragi;
	public char druzyna;
	public boolean flaga;
	public String login;
	public ArrayList<PociskTcp> pociski;
	
}
