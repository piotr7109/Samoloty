

import java.io.Serializable;

public class Obj implements Serializable
{

	private static final long serialVersionUID = 1L;
	public int x,y;
	public String nazwa;
	
	public String toString()
	{
		return x+" "+y+" "+nazwa;
	}
}
