package network.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import database.Serwer;
import network.modules.Bufor;
import network.modules.GraczTcp;

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
					continue;

				}
				if(!start)
				{
					System.out.println(ile_graczy+" ile ma byæ:"+Bufor.gracze.size());
					
					if(ile_graczy!=Bufor.gracze.size())
					{
						out.writeInt(666);
					}
					else
					{
						start = true;
						Serwer s = new Serwer();
						s.setId(id_serwera);
						s.delete();
						out.writeInt(100);
					}
					out.writeObject(Bufor.gracze);
				}
				else
				{
					out.writeInt(100);
					out.writeObject(Bufor.gracze);
				}
				
				out.reset();

			}

			mySocket.close();
			System.out.println("SERWER " + i + " zakonczyl prace\n");

		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}

}