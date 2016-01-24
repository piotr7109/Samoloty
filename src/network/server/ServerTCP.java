package network.server;

import java.net.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerTCP extends Thread
{
	int ile_graczy;
	private int id_serwera;
	public static boolean gotowi;
	public ServerTCP(int ile_graczy, int id_serwera)
	{
		this.ile_graczy = ile_graczy;
		this.id_serwera = id_serwera;
	}
	public void run()
	{

		try
		{
			int i = 0;// numer serwera
			ServerSocket serverSocket = new ServerSocket(4321);
			System.out.println(InetAddress.getLocalHost() + "");
			Executor exe = Executors.newFixedThreadPool(4);
			gotowi = false;
			boolean czy_trwa = true;
			while (czy_trwa)
			{
				if(gotowi)
				{
					czy_trwa = false;
				}
				Socket clientSocket = serverSocket.accept();
				if (clientSocket.isConnected() == true)
				{
					
					ServerTCPThread gniazdo = new ServerTCPThread(clientSocket, i, ile_graczy, id_serwera);
					exe.execute(gniazdo);
					i++;
				}
			}
			serverSocket.close();

		}
		catch (Exception e)
		{
			System.err.println(e);
		}

	}
}
