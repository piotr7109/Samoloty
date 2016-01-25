package system;

public class CONST
{
	public static final int pocisk_width = 30;
	public static final int pocisk_height = 32;

	public static final int samolot_width = 30;
	public static final int samolot_height = 30;

	public static int pocisk_dmg = 5;
	public static int bomb_dmg = 10;

	public static String intToString(int numer)
	{
		if (numer == 0)
			return "NIE";
		else
			return "TAK";
	}

	public static String intToTime(int sekundy)
	{
		String czas = "";

		int g, m, s;
		g = m = s = 0;
		s = sekundy;

		/*while (s - 3600 >= 0)
		{
			g++;
			s -= 3600;
		}
		if (g < 10)
			czas += "0" + g + ":";
		else
			czas += "" + g + ":";
		 */
		while (s - 60 >= 0)
		{
			m++;
			s -= 60;
		}
		if (m < 10)
			czas += "0" + m + ":";
		else
			czas += "" + m + ":";

		if (s < 10)
			czas += "0" + s;
		else
			czas += s;

		return czas;
	}
}
