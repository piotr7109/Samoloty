package system;

import java.util.ArrayList;

import database.Gracz_mod;
import database.ListaSerwerow;
import database.Serwer;


public class Test
{

	public static void main(String[] args)
	{
		/*Gracz_mod g = new Gracz_mod();
		g.druzyna = 'A';
		g.id = 10;
		g.login = "Zoœka99";
		Serwer s = new Serwer();
		s.setIpSerwera("192.168.0.11");
		s.setTypGry("TEAM");
		s.setTrybGry("DM");
		s.insert();
		s.addGracz(g);*/
		ArrayList<Serwer> s = ListaSerwerow.getList();
		
		for(int i =0; i< s.size(); i++)
		{
			System.out.println(s.get(i).getId()+ " "+ s.get(i).getIpSerwera());
		}
		
		
	}

}
