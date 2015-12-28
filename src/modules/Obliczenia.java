package modules;
import java.awt.Point;


public class Obliczenia 
{
	
	public static int getDlugosc(Point a, Point b)
    {
    	double dl = 0.0;
    	dl = Math.sqrt(Math.pow(a.getX()-b.getX(),2) + Math.pow(a.getY()-b.getY(),2) );
    	return (int)dl;
    }
	/**
	 * Funnkcja liczy k¹t pomiêdzy punktami a i c wyko¿ystuj¹c punkt œrodkowy b
	 * @param a - punkt start
	 * @param b - punkt wspólny
	 * @param c - punkt koniec
	 * @return k¹t
	 */
	public static int getKat(Point a, Point b, Point c)
    {
		double kat1 = Math.atan2(c.getY()- b.getY(), c.getX()- b.getX());
    	double kat2 = Math.atan2(a.getY()- b.getY(), a.getX()- b.getX());
		
		double kat;
		kat = Math.toDegrees(kat1-kat2);
		if(kat<0)
    		kat = kat+360;
		return (int)(kat);
    }
}
