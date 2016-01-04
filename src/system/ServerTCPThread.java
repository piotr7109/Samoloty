package system;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import modules.Gracz;

public class ServerTCPThread extends Thread implements Runnable
{
	private Socket mySocket;
	private int i; // numer watku
	private boolean czy_koniec;
	private Gracz gracz;

	public ServerTCPThread(Socket socket, int i)
	{
		super();
		mySocket = socket;
		this.i = i;
		czy_koniec = false;
	}

	public void run()
	{
		try
		{
			System.out.println(InetAddress.getLocalHost() + "");

			ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(mySocket.getInputStream());
			System.out.println("START");
			while (!czy_koniec)
			{
				try
				{
					this.gracz = (Gracz) in.readObject();
					
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
				out.writeObject(Bufor.gracze);
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