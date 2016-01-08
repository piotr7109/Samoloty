package ekrany;

import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Gracz_mod;
import database.Serwer;

public class Lobby extends JPanel
{
	
	private final int WIDTH = 200;
	private final int HEIGHT = 25;
	private final Point start = new Point(10,25);
	
	
	public Serwer serwer;
	private boolean czy_admin;
	private int id_serwera;
	
	public ArrayList<Gracz_mod> gracze;
	
	private JLabel ip_serwera_label, typ_gry_label, tryb_gry_label, czas_label;
	
	public Lobby(int id_serwera, boolean czy_admin)
	{
		this.id_serwera = id_serwera;
		this.czy_admin = czy_admin;
		getSerwer();
		this.setLayout(null);
		
		getDaneSerwera();
		
		
	}
	
	private void getDaneSerwera()
	{
		JLabel dane_serwera = new JLabel("Dane serwera");
		dane_serwera.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		
		ip_serwera_label = new JLabel("IP: "+serwer.getIpSerwera());
		typ_gry_label = new JLabel("TYP GRY: "+serwer.getTypGry());
		tryb_gry_label = new JLabel("TRYB GRY: "+serwer.getTrybGry());
		
		dane_serwera.setBounds(start.x, 0, WIDTH, HEIGHT);
		ip_serwera_label.setBounds(start.x, start.y, WIDTH, HEIGHT);
		typ_gry_label.setBounds(start.x, start.y*2, WIDTH, HEIGHT);
		tryb_gry_label.setBounds(start.x, start.y*3, WIDTH, HEIGHT);
		
		add(dane_serwera);
		add(ip_serwera_label);
		add(typ_gry_label);
		add(tryb_gry_label);
		
		
	}
	
	
	private void getSerwer()
	{
		serwer = new Serwer();
		serwer.setSerwerById(id_serwera);
	}
	
	
	
}
