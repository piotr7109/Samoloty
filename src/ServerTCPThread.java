


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
 
public class ServerTCPThread extends Thread implements Runnable {
	Socket mySocket;
	int i;//numer watku
	String poprane_odp[] = new String[5];
	public ServerTCPThread(Socket socket , int i)
	{
		super();
		mySocket = socket;
		this.i= i;
	}

	public void run() 
	{	
		try {
			String odpowiedzi_studenta="";
			System.out.println(InetAddress.getLocalHost()+"");
			int [] tab = new int [4];
			ExecutorService exe1 = Executors.newFixedThreadPool(1);
			int p=0;					
			String numer_studenta;		
			
			ObjectOutputStream out =new ObjectOutputStream(mySocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(mySocket.getInputStream());
			//numer_studenta=in.readLine();
			
			//out.write("asdas"+numer_studenta);
			int ilosc_odpowiedzi =0;
			int pytanie = 0;
			String odpowiedzi = "";
			boolean czy_odpowiedz = true;
			Obj o;
			while(pytanie <5)
			{
				/*if(czy_odpowiedz)
				{
					
					//out.println(pytania[pytanie]);
					czy_odpowiedz = false;
					pytanie++;
				}*/
				try
				{
					o = (Obj)in.readObject();
					o.nazwa+="tutaj by³em";
					out.writeObject(o);
				}
				catch (Exception e) 
	           { 
					continue;

	            }
				/*if(in.readObject().getClass()== Obj.class)//---jezeli podaje wejscie
				{
					
					out.writeObject(o);
					//odpowiedzi+=zmienna_do_odpo+"\n";
					czy_odpowiedz= true;
					
				}*/
				
			}
			
			//zapis.close();
			mySocket.close();		
			System.out.println(odpowiedzi);
			System.out.println("SERWER " + i +" zakonczyl prace\n");

		} catch (Exception e) {
			System.err.println(e);
		}
	}
	

}