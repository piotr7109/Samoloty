package database;

public class Gracz_mod
{
	public int id, id_serwera, pozycja;
	public String login;
	public char druzyna;
	public int gotowy;
	
	public static void usunGracza(int id_gracza)
	{
		SQLJDBC baza = new SQLJDBC();
		baza.queryOpertaion(String.format("DELETE FROM t_gracze where id=%d", id_gracza));
	}
}
