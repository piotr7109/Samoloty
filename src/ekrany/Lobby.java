package ekrany;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Gracz_mod;
import database.Serwer;
import system.CONST;
import system.PanelGlowny;
import system.SETTINGS;

public class Lobby extends JPanel
{

	private final int WIDTH = 200;
	private final int HEIGHT = 25;
	private final Point start = new Point(10, 25);

	public Serwer serwer;
	private boolean czy_admin;
	private int id_serwera;
	public ArrayList<Gracz_mod> gracze;
	private int id_gracza;

	private JLabel ip_serwera_label, typ_gry_label, tryb_gry_label, czas_label;

	JButton refresh = new JButton("Od�wierz");
	JButton gotowy = new JButton("GOTOWY");
	public JButton start_button = new JButton("START!");

	public Lobby(int id_serwera, boolean czy_admin, int id_gracza)
	{
		this.id_serwera = id_serwera;
		this.czy_admin = czy_admin;
		this.id_gracza = id_gracza;

	}

	protected void paintComponent(Graphics g)
	{

		this.setLayout(null);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, SETTINGS.width, SETTINGS.height);
		g2d.setColor(Color.BLACK);
		getSerwer();
		this.setLayout(null);

		getDaneSerwera();
		getDaneGraczy();

		refreshButton();
		gotowyButton();

		if (czy_admin)
		{
			startButton();
		}
	}

	private void getDaneGraczy()
	{
		int width = 100;
		graczeNaglowek(width);
		int i = 1;
		int start_x = start.x + 300;
		for (Gracz_mod g : serwer.getGracze())
		{
			JLabel id = new JLabel(g.id + "");
			JLabel login = new JLabel(g.login);
			JLabel druzyna = new JLabel(g.druzyna + "");
			JLabel pozycja = new JLabel(g.pozycja + "");
			JLabel gotowy = new JLabel(CONST.intToString(g.gotowy));

			id.setBounds(start_x, start.y + i * 25, width, HEIGHT);
			login.setBounds(start_x + width, start.y + i * 25, width, HEIGHT);
			druzyna.setBounds(start_x + width * 2, start.y + i * 25, width, HEIGHT);
			pozycja.setBounds(start_x + width * 3, start.y + i * 25, width, HEIGHT);
			gotowy.setBounds(start_x + width * 4, start.y + i * 25, width, HEIGHT);

			add(id);
			add(login);
			add(druzyna);
			add(pozycja);
			add(gotowy);
			i++;
		}

	}

	private void graczeNaglowek(int width)
	{
		int start_x = start.x + 300;
		JLabel dane_graczy = new JLabel("Gracze");
		dane_graczy.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		dane_graczy.setBounds(start_x, 0, WIDTH, HEIGHT);
		add(dane_graczy);

		JLabel id = new JLabel("ID");
		JLabel login = new JLabel("Login");
		JLabel druzyna = new JLabel("Dru�yna");
		JLabel pozycja = new JLabel("Pozycja");
		JLabel gotowy = new JLabel("Gotowy?");

		id.setBounds(start_x, start.y, width, HEIGHT);
		login.setBounds(start_x + width, start.y, width, HEIGHT);
		druzyna.setBounds(start_x + width * 2, start.y, width, HEIGHT);
		pozycja.setBounds(start_x + width * 3, start.y, width, HEIGHT);
		gotowy.setBounds(start_x + width * 4, start.y, width, HEIGHT);

		add(id);
		add(login);
		add(druzyna);
		add(pozycja);
		add(gotowy);
	}

	private void getDaneSerwera()
	{
		JLabel dane_serwera = new JLabel("Dane serwera");
		dane_serwera.setFont(new Font(Font.SERIF, Font.BOLD, 20));

		ip_serwera_label = new JLabel("IP: " + serwer.getIpSerwera());
		typ_gry_label = new JLabel("TYP GRY: " + serwer.getTypGry());
		tryb_gry_label = new JLabel("TRYB GRY: " + serwer.getTrybGry());

		dane_serwera.setBounds(start.x, 0, WIDTH, HEIGHT);
		ip_serwera_label.setBounds(start.x, start.y, WIDTH, HEIGHT);
		typ_gry_label.setBounds(start.x, start.y * 2, WIDTH, HEIGHT);
		tryb_gry_label.setBounds(start.x, start.y * 3, WIDTH, HEIGHT);

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

	private void startButton()
	{
		
		start_button.setSize(new Dimension(100, 25));
		start_button.setLocation(10, 250);

		add(start_button);

		
	}

	private void gotowyButton()
	{
		gotowy = new JButton("GOTOWY");
		gotowy.setSize(new Dimension(100, 25));
		gotowy.setLocation(10, 150);

		add(gotowy);

		gotowy.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				serwer.gotowyGracz(id_gracza);
				removeAll();
				repaint();

			}
		});
	}

	private void refreshButton()
	{
		refresh = new JButton("Od�wierz");
		refresh.setSize(new Dimension(100, 25));
		refresh.setLocation(10, 200);
		add(refresh);

		refresh.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeAll();
				repaint();
				if (serwer.getStart() == 1)
				{
					PanelGlowny.startGra(serwer);
					
				}
			}
		});
	}

}