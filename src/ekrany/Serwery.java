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

import database.ListaSerwerow;
import database.Serwer;
import system.PanelGlowny;
import system.SETTINGS;

public class Serwery extends JPanel
{

	private static final long serialVersionUID = 1L;

	private final Point start = new Point(100, 50);
	private ArrayList<Serwer> lista_serwerow;

	public JButton stworz = new JButton("Stwórz grê");
	public ArrayList<JButton> dolacz = new ArrayList<JButton>();

	JButton refresh = new JButton("Odœwierz");
	PanelGlowny panel_glowny;
	
	
	public Serwery(PanelGlowny p)
	{
		panel_glowny = p;
	}

	protected void paintComponent(Graphics g)
	{

		this.setLayout(null);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, SETTINGS.width, SETTINGS.height);
		g2d.setColor(Color.BLACK);
		refreshButton();
		setListaSerwerow();

		dodajListeSerwerow();
	}

	private void refreshButton()
	{
		refresh = new JButton("Odœwierz");
		refresh.setSize(new Dimension(100, 25));
		refresh.setLocation(0, 0);
		add(refresh);

		refresh.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeAll();
				repaint();

			}
		});
	}

	private void dodajListeSerwerow()
	{
		int i = 0;
		final int x = 150;
		final int y = 20;
		Dimension d = new Dimension(x, y);
		getNaglowek(x, y, d);

		for (Serwer serwer : lista_serwerow)
		{
			int ile_graczy = serwer.getGracze().size();
			JButton dolacz = new JButton("Do³¹cz");
			JLabel id_serwera = new JLabel(serwer.getId() + "");
			JLabel typ_gry = new JLabel(serwer.getTypGry());
			JLabel tryb_gry = new JLabel(serwer.getTrybGry());
			JLabel ip_serwera = new JLabel(serwer.getIpSerwera());
			JLabel ilosc_graczy = new JLabel(ile_graczy + "");

			id_serwera.setSize(d);
			typ_gry.setSize(d);
			tryb_gry.setSize(d);
			ip_serwera.setSize(d);
			ilosc_graczy.setSize(d);
			dolacz.setSize(d);

			id_serwera.setLocation(start.x, start.y + i * y);
			typ_gry.setLocation(start.x * 2, start.y + i * y);
			tryb_gry.setLocation(start.x * 3, start.y + i * y);
			ip_serwera.setLocation(start.x * 4, start.y + i * y);
			ilosc_graczy.setLocation(start.x * 5, start.y + i * y);
			dolacz.setLocation(start.x * 6, start.y + i * y);
			
			
			
			add(id_serwera);
			add(typ_gry);
			add(tryb_gry);
			add(ip_serwera);
			add(ilosc_graczy);
			
			if(ile_graczy < 4)
			{
				add(dolacz);
				dodajEvent(dolacz);
				dolacz.setName(serwer.getId() + "");
				this.dolacz.add(dolacz);
			}
			i++;
			
			
		}
	}

	private void dodajEvent(final JButton dolacz)
	{
		dolacz.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				panel_glowny.EkranListySerwerowDolaczAction(dolacz);
				repaint();
				validate();

			}
		});
	}

	private void getNaglowek(int x, int y, Dimension d)
	{
		JLabel id_serwera = new JLabel("ID");
		JLabel typ_gry = new JLabel("TYP");
		JLabel tryb_gry = new JLabel("TRYB");
		JLabel ip_serwera = new JLabel("IP");
		JLabel ilosc_graczy = new JLabel("ILOŒÆ GRACZY");

		id_serwera.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		typ_gry.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		tryb_gry.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		ip_serwera.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		ilosc_graczy.setFont(new Font(Font.SERIF, Font.BOLD, 16));

		id_serwera.setSize(d);
		typ_gry.setSize(d);
		tryb_gry.setSize(d);
		ip_serwera.setSize(d);
		ilosc_graczy.setSize(d);

		id_serwera.setLocation(start.x, start.y - y);
		typ_gry.setLocation(start.x * 2, start.y - y);
		tryb_gry.setLocation(start.x * 3, start.y - y);
		ip_serwera.setLocation(start.x * 4, start.y - y);
		ilosc_graczy.setLocation(start.x * 5, start.y - y);

		add(id_serwera);
		add(typ_gry);
		add(tryb_gry);
		add(ip_serwera);
		add(ilosc_graczy);

		stworz.setBounds(110, 0, 100, 25);
		add(stworz);
	}

	private void setListaSerwerow()
	{
		lista_serwerow = ListaSerwerow.getList();
	}
}
