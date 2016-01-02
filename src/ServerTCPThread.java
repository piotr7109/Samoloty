


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
 
public class ServerTCPThread extends Thread implements Runnable 
{
	private Socket mySocket;
	private int i;//numer watku
	private boolean czy_koniec;
	
	
	
	public ServerTCPThread(Socket socket , int i)
	{
		super();
		mySocket = socket;
		this.i= i;
		czy_koniec = false;
	}

	public void run() 
	{	
		try {
			System.out.println(InetAddress.getLocalHost()+"");	
			
			ObjectOutputStream out =new ObjectOutputStream(mySocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(mySocket.getInputStream());
			
			Obj o;
			while(!czy_koniec)
			{
				try
				{
					/*o = (Obj)in.readObject();
					o.nazwa+="tutaj by³em";
					out.writeObject(o);*/
				}
				catch (Exception e) 
				{
					continue;

	            }
				
			}
			
			mySocket.close();		
			System.out.println("SERWER " + i +" zakonczyl prace\n");

		} catch (Exception e) {
			System.err.println(e);
		}
	}
	

}