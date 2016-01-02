
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
				try
				{
					System.out.println("Wczytaj");
					Object o = (Object) in.readObject();
					
					gracze = (ArrayList<Gracz>) o;

					System.out.println("gracze wczytaj");
					// Obj o2 = (Obj) in.readObject();
					// System.out.println(o2.toString());
					// sleep(100);

				}
				catch (Exception e)
				{
					System.out.println("Exception");
					continue;
				}
				// System.out.println(gracz.getSamolot().x);
				out.writeObject(gracz);
				out.reset();
				sleep(50);

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
