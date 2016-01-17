package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.*;
import java.util.ArrayList;

import modules.Gracz;
import modules.Pocisk;
import modules.Samolot;
import network.modules.GraczTcp;
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
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			while (!koniec)
			{
				gracz_tcp = getTcpGracz();
				out.writeObject(gracz_tcp);
				out.reset();
				sleep(100);

				if (start)
				{
					try
					{
						kod_odpowiedzi = in.readInt();
						gracze_tcp = (ArrayList<GraczTcp>) in.readObject();
					}
					catch (Exception e)
					{
						System.err.println(e);
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
						gracze_tcp = (ArrayList<GraczTcp>) in.readObject();
					}
					catch (Exception e)
					{
						System.out.println("Exception");
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
