package network;

import java.net.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerTCP extends Thread
{
	public static void main(String args[])
	{

		try
		{
			int i = 0;// numer serwera
			ServerSocket serverSocket = new ServerSocket(4321);
			System.out.println(InetAddress.getLocalHost() + "");
			Executor exe = Executors.newFixedThreadPool(10);
			while (true)
			{
				Socket clientSocket = serverSocket.accept();
				if (clientSocket.isConnected() == true)
				{
					
					ServerTCPThread gniazdo = new ServerTCPThread(clientSocket, i);
					exe.execute(gniazdo);
					i++;
				}
			}
			//serverSocket.close();

		}
		catch (Exception e)
		{
			System.err.println(e);
		}

	}
}
