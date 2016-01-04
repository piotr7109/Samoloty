package system;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.*;
import java.util.ArrayList;

import modules.Gracz;

public class ClientTCP extends Thread
{
	public Gracz gracz;
	public ArrayList<Gracz> gracze = new ArrayList<Gracz>();

	public void run()
	{
		try
		{
			boolean koniec = false;
			Socket socket = new Socket(InetAddress.getByName("localhost"), 4321);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			while (!koniec)
			{
				
				out.writeObject(gracz);
				out.reset();
				sleep(50);
				
				try
				{
					gracze = (ArrayList<Gracz>) in.readObject();

				}
				catch (Exception e)
				{
					System.out.println("Exception");
					continue;
				}
				

			}
			socket.close();
			System.out.println("KONIEC KLIENT");
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}
}
