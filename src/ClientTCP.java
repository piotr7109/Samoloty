


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.*;
import java.util.Scanner;

 
public class ClientTCP extends Thread  {
	public static void main(String args[]){
	
		
			try {
				int linijka=0;
				String odpowiedza;
				boolean koniec=true;
				
				Socket socket = new Socket(InetAddress.getByName("192.168.0.50"), 4321);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				
				//wys³anie indeksu ankietowanego
				/*System.out.print("Numer indeksu\n");
				Scanner nr = new Scanner(System.in);
				String numer_indeksu=nr.nextLine();*/
				//out.println(numer_indeksu);
				
				//------odczyt pytan
				System.out.print("Wpisz parametry");
				Obj o = new Obj();
				Scanner sc = new Scanner(System.in);
				o.x = Integer.parseInt(sc.nextLine());
				o.y = Integer.parseInt(sc.nextLine());
				o.nazwa = sc.nextLine();
				//out.println(odpowiedza);//przekaz odp
				out.writeObject(o);
				
				while(koniec)
				{
					try
					{
						Obj o2 = (Obj)in.readObject();
						System.out.println(o2.toString());
						sleep(100);
					}
					catch (Exception e) 
		            { 
						continue;

		            }
						
				}
				socket.close();
				System.out.println("Dziêkujemy za poœwiecony czas" );		
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
