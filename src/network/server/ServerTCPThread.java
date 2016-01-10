package network.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import database.Serwer;
import network.modules.Bufor;
import network.modules.GraczTcp;
import system.CONST;

public class ServerTCPThread extends Thread implements Runnable
{
	private Socket mySocket;
	private int i; // numer watku
	private boolean czy_koniec;
	private GraczTcp gracz;
	private int ile_graczy;
	private int id_serwera;

	public ServerTCPThread(Socket socket, int i, int ile_graczy, int id_serwera)
	{
		super();
		mySocket = socket;
		this.i = i;
		czy_koniec = false;
		this.ile_graczy = ile_graczy;
		this.id_serwera = id_serwera;
	}

	public void run()
	{
		try
		{
			boolean start = false;
			int kod_odpowiedzi = 100;
			
			System.out.println(InetAddress.getLocalHost() + "");
			ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(mySocket.getInputStream());
			System.out.println("START");
			while (!czy_koniec)
			{
				try
				{
					this.gracz = (GraczTcp) in.readObject();

					if (this.i == Bufor.gracze.size())
					{
						System.out.println("ADDS");
						Bufor.gracze.add(this.gracz);
					}
					else
					{
						Bufor.gracze.set(this.i, this.gracz);
					}

				}
				catch (Exception e)
				{
					czy_koniec = true;
					continue;

				}
				if(!start)
				{
					
					if(ile_graczy!=Bufor.gracze.size())
					{
						out.writeInt(666);
					}
					else
					{
						start = true;
						Serwer s = new Serwer();
						s.setId(id_serwera);
						s.setSerwerById(id_serwera);
						s.delete();
						
						out.writeInt(100);
					}
					out.writeObject(Bufor.gracze);
				}
				else
				{
					out.writeInt(kod_odpowiedzi);
					out.writeObject(Bufor.gracze);
				}
				
				out.reset();

			}

			mySocket.close();
			System.out.println("Klient " + i + " roz³¹czony\n");

		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}
	/*protected boolean sprawdzKolizjeZGraczami(int x, int y)
	{
		gracze = klient.gracze_tcp;
		int size_gracze = gracze.size();
		int x1;
		int y1;
		for (int i = 0; i < size_gracze; i++)
		{
			GraczTcp g = gracze.get(i);
			if (g.id == gracz.id)
				continue;
			x1 = (int) (g.x - CONST.samolot_width);
			y1 = (int) (g.y - CONST.samolot_height);
			if(sprawdzKolizje(x1, y1, CONST.pocisk_width, CONST.pocisk_height , x, y, CONST.samolot_width, CONST.samolot_height))
			{
				return true;
			}
		}
		return false;
	}
	
	protected boolean sprawdzKolizje(double x, double y, int w, int h, double x1, double y1, int w1, int h1)
	{
		double x_p = x + w / 2;
		double y_p = y + h / 2;

		if ((x_p > x1 && x_p < x1 + w1)
				&& (y_p > y1 && y_p < y1 + h1))
		{
			return true;
		}
		return false;
	}*/

}