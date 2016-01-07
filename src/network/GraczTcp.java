package network;

import java.io.Serializable;
import java.util.ArrayList;


public class GraczTcp implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double x, y, kat;
	public int  punkty_zycia, id;
	public String login;
	public ArrayList<PociskTcp> pociski;
	
}
