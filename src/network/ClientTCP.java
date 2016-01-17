package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.*;
import java.util.ArrayList;

import ekrany.Gra;
import modules.Gracz;
import modules.Pocisk;
import modules.Samolot;
import network.modules.GraczTcp;
import network.modules.ObjectSizeFetcher;
import network.modules.PociskTcp;

public class ClientTCP extends Thread
{

	private GraczTcp gracz_tcp;
	public Gracz gracz;
	public ArrayList<GraczTcp> gracze_tcp = new ArrayList<GraczTcp>();

	public boolean start, koniec;
	public int kod_odpowiedzi = 0;
	private String ip_serwera;

	public ClientTCP(String ip)
	{
		this.ip_serwera = ip;
	}

	public void run()
	{
		try
		{
			koniec = false;
			start = false;
			Socket socket = new Socket(InetAddress.getByName(ip_serwera), 4321);
			socket.setTcpNoDelay(true);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			while (!koniec)
			{
				gracz_tcp = getTcpGracz();
				out.writeObject(gracz_tcp);
				out.reset();
				sleep(5);

				if (start)
				{
					try
					{
						gracze_tcp.set(0,  (GraczTcp)in.readObject());
						gracze_tcp.set(1,  (GraczTcp)in.readObject());
						//gracze_tcp.set(2,  (GraczTcp)in.readObject());
						//gracze_tcp.set(3,  (GraczTcp)in.readObject());
						Gra.gracze = gracze_tcp;
					}
					catch (Exception e)
					{
						 System.out.println("Exception Array");
					}
				}
				else
				{
					try
					{
						kod_odpowiedzi = in.readInt();
						if (kod_odpowiedzi == 100)
						{
							start = true;
						}
						gracze_tcp = this.convertGracz((GraczTcp[]) in.readObject());
						Gra.gracze = gracze_tcp;
					}
					catch (Exception e)
					{
						System.out.println("Exception Client");
						continue;
					}
				}

			}
			
			
			
			
			sleep(100);
			gracz_tcp = getTcpGracz();
			out.writeObject(gracz_tcp);
			out.reset();
			socket.close();
			System.out.println("KONIEC KLIENT");
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}
	private ArrayList<GraczTcp> convertGracz(GraczTcp[] gracz)
	{
		ArrayList<GraczTcp> gg = new ArrayList<GraczTcp>();
		int size = gracz.length;
		for(int i =0; i< size; i++)
		{
			if(gracz[i]!= null)
			{
				gg.add(gracz[i]);
			}
		}		
		return gg;
	}

	protected GraczTcp getTcpGracz()
	{
		GraczTcp g = new GraczTcp();
		Samolot s = gracz.getSamolot();
		g.id = (short)gracz.id;
		g.x = (short)s.x;
		g.y = (short)s.y;
		g.kat = (short)s.kat;
		g.punkty_zycia = (short)s.getPunktyZycia();
		g.login = gracz.login;
		g.druzyna = gracz.druzyna;
		g.flaga = gracz.flaga;
		g.fragi = (short)gracz.getFragi();
		g.punkty = (short)gracz.getPunkty();
		ArrayList<PociskTcp> pociski_tcp = new ArrayList<PociskTcp>();

		for (Pocisk po : s.getPociski())
		{
			PociskTcp p = new PociskTcp();
			p.x = (short)po.x;
			p.y = (short)po.y;
			p.kat = (short)po.kat;
			pociski_tcp.add(p);
		}
		g.pociski = pociski_tcp;

		return g;
	}
}
