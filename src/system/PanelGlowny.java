package system;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import database.Gracz_mod;
import database.Serwer;
import ekrany.Gra;
import ekrany.Lobby;
import ekrany.Serwery;
import ekrany.TworzenieGry;
import ekrany.Ustawienia;

public class PanelGlowny extends JFrame
{

	private static final long serialVersionUID = 1L;
	private static PanelGlowny frame;
	private static int id_gracza;
	static JTabbedPane tab_panel;
	private Ustawienia ustawienia;
	private Serwery serwery;
	private TworzenieGry tworzenie_gry;
	private static Lobby lobby;

	public PanelGlowny()
	{
		this.setTitle("Samoloty");
		this.setSize(new Dimension(SETTINGS.width, SETTINGS.height));
		id_gracza = (int) (Math.random() * 1000);
		// this.add(EkranGry());
		this.add(inicjalizujKomponenty());

	}

	private JPanel inicjalizujKomponenty()
	{
		JPanel panel = new JPanel(new BorderLayout(10, 10));

		tab_panel = new JTabbedPane();

		panel.add(tab_panel, BorderLayout.CENTER);
		// EkranUstawien();
		// EkranGry();
		// EkranTworzeniaGry();
		EkranListySerwerow();

		return panel;
	}

	private static void EkranLobby(int id_serwera, boolean admin)
	{
		lobby = new Lobby(id_serwera, admin, id_gracza);
		tab_panel.add(lobby, "Lobby");

	}

	private void EkranTworzeniaGry()
	{
		tworzenie_gry = new TworzenieGry();
		tab_panel.add(tworzenie_gry, "Tworzenie gry");
		EkranTworzeniaGryAction();
	}

	private void EkranTworzeniaGryAction()
	{
		tworzenie_gry.stworz_gre.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// zapisz do bazy
				Serwer serwer = new Serwer();
				serwer.setIpSerwera(tworzenie_gry.ip_servera);
				serwer.setTypGry(tworzenie_gry.typ_gry.getSelectedItem().toString());
				serwer.setTrybGry(tworzenie_gry.tryb_gry.getSelectedItem().toString());
				int id_serwera = serwer.insert();

				// + przekieruj do Lobby

				Gracz_mod gracz = new Gracz_mod();
				gracz.druzyna = 'A';
				gracz.id = id_gracza;
				gracz.id_serwera = id_serwera;
				gracz.login = SETTINGS.login;
				gracz.gotowy = 0;
				gracz.pozycja = 0;

				serwer.addGracz(gracz);
				tab_panel.removeAll();

				EkranLobby(id_serwera, true);

			}
		});
	}

	private void EkranListySerwerow()
	{
		serwery = new Serwery();
		tab_panel.add(serwery, "Lista serwerów");
		EkranListySerwerowAction();
	}

	private void EkranListySerwerowAction()
	{
		serwery.stworz.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				tab_panel.remove(serwery);
				EkranTworzeniaGry();
				repaint();
				validate();

			}
		});
	}

	public static void EkranListySerwerowDolaczAction(JButton dolacz)
	{
		int id_serwera = Integer.parseInt(dolacz.getName());

		Gracz_mod gracz = new Gracz_mod();
		gracz.druzyna = 'A';
		gracz.id = id_gracza;
		gracz.id_serwera = id_serwera;
		gracz.login = SETTINGS.login;
		gracz.gotowy = 0;
		gracz.pozycja = 0;

		Serwer serwer = new Serwer();
		serwer.setId(id_serwera);
		serwer.addGracz(gracz);
		tab_panel.removeAll();

		EkranLobby(id_serwera, false);

	}

	private void EkranUstawien()
	{
		ustawienia = new Ustawienia();
		tab_panel.add(ustawienia, "Ustawienia");
		EkranUstawienAction();
	}

	private void EkranUstawienAction()
	{
		ustawienia.zapisz.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				SETTINGS.width = ustawienia.width.getValue();
				SETTINGS.height = ustawienia.height.getValue();
				SETTINGS.login = ustawienia.login.getText();
				setSize(new Dimension(SETTINGS.width, SETTINGS.height));
				tab_panel.remove(ustawienia);
				EkranListySerwerow();
				repaint();
				validate();

			}
		});
	}

	private JPanel EkranGry()
	{
		Gra gra = new Gra(SETTINGS.width, SETTINGS.height, id_gracza);
		tab_panel.addKeyListener(gra);
		tab_panel.setFocusable(true);
		tab_panel.add(gra, "Fajt");
		return gra;
	}

	public static void createAndShowGui()
	{
		frame = new PanelGlowny();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent)
			{
				if (JOptionPane.showConfirmDialog(frame, "Na pewno chcesz wyjœæ?",
						"Na pewno?", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					Gracz_mod.usunGracza(id_gracza);
					System.exit(0);
				}
			}
		});
	}
}
