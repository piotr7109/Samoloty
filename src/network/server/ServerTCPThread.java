package network.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

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
			while (!czy_koniec)
			{
				try
				{
					this.gracz = (GraczTcp) in.readObject();

					if (this.i == Bufor.gracze.size())
					{
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
					Bufor.gracze.remove(this.i);
					System.out.println("EXCEPTION SERWER");
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
					out.writeObject(this.toArray());
				}
				else
				{
					out.writeObject(this.toArray());
				}
				out.flush();
				sleep(50);

			}

			mySocket.close();
			System.out.println("Klient " + i + " roz³¹czony\n");

		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}
	private GraczTcp[] toArray()
	{
		GraczTcp[] g = new GraczTcp[4];
		
		int size = Bufor.gracze.size();
		for(int j =0; j< 4 ;j++)
		{
			g[j] = null;
		}
		
		for(int i=0; i<size; i++)
		{

			g[i] = Bufor.gracze.get(i);
		}
		
		
		return g;
	}
	

}