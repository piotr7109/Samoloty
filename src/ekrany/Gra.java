package ekrany;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Gracz_mod;
import database.Serwer;
import modules.Gracz;
import modules.Obrazki;
import modules.Pocisk;
import modules.Samolot;
import network.ClientTCP;
import network.modules.GraczTcp;
import network.modules.PociskTcp;
import system.CONST;
import system.PanelGlowny;

public class Gra extends JPanel implements KeyListener
{
	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;

	protected ArrayList<GraczTcp> gracze;
	protected Gracz gracz;
	protected Samolot samolot;
	protected String mapa;
	protected String mapa_src = "";
	protected static int WIDTH, HEIGHT;
	protected final int FPS = 20;
	protected int bullet_time = 15;
	protected int bullet_time_bomb = 30;
	protected int start_x, start_y, start_kat;
	protected Serwer serwer;
	protected int pozycja;
	protected char druzyna;
	protected int czas_do_konca = 600; // sekundy
	protected JLabel czas_label;

	protected ClientTCP klient;

	public Gra(int width, int height, int id_gracza, Serwer serwer)
	{
		HEIGHT = height;
		WIDTH = width;
		this.serwer = serwer;
		pozycja = getPozycja(id_gracza);

		this.setLayout(null);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addKeyListener(this);
		this.setPozycjaStartowa();

		gracz = new Gracz(start_x, start_y, start_kat);
		gracz.id = id_gracza;
		gracz.druzyna = druzyna;
		samolot = gracz.getSamolot();

		startClientTcpThread(serwer.getIpSerwera());

		startTimer();
		ustawLicznik();

	}

	protected void ustawLicznik()
	{
		czas_label = new JLabel(CONST.intToTime(czas_do_konca));
		czas_label.setBounds(WIDTH - 80, 0, 50, 50);
		add(czas_label);
	}

	protected int getPozycja(int id_gracza)
	{

		for (Gracz_mod g : serwer.gracze)
		{
			if (g.id == id_gracza)
			{
				this.druzyna = g.druzyna;
				return g.pozycja;
			}
		}
		return 0;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		// super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		g2d.setColor(Color.BLACK);
		czas_label.setText(CONST.intToTime(czas_do_konca));

		this.rysujSamolotGracza(g2d);
		this.rysujPociskiGracza(g2d);
		this.rysujSamolotyGraczy(g2d);

	}

	protected void startClientTcpThread(String ip_serwera)
	{
		Executor exe = Executors.newFixedThreadPool(1);

		klient = new ClientTCP(ip_serwera);
		klient.gracz = gracz;
		exe.execute(klient);
	}

	protected void rysujSamolotGracza(Graphics2D g2d)
	{
		int x = (int) (samolot.x);
		int y = (int) (samolot.y);
		AffineTransform rotacja = AffineTransform
				.getRotateInstance(Math.toRadians(samolot.kat + 90), samolot.width, samolot.height);
		AffineTransformOp transformacja_op = new AffineTransformOp(rotacja,
				AffineTransformOp.TYPE_BILINEAR);

		g2d.setColor(Color.GREEN);
		g2d.fillRect(x, y - 5, (int) (samolot.getPunktyZycia() / 5 * 3), 4); // pasek
										
		if(gracz.druzyna == 'B')
		{
			if(samolot.getPunktyZycia() >70)
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2, null), x, y, null);
			else if(samolot.getPunktyZycia() >40)
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2_dmg, null), x, y, null);
			else
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2_hard_dmg, null), x, y, null);
		}
		else
		{
			if(samolot.getPunktyZycia() >70)
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot, null), x, y, null);
			else if(samolot.getPunktyZycia() >40)
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot_dmg, null), x, y, null);
			else
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot_hard_dmg, null), x, y, null);
		}

	}

	protected void rysujPociskiGracza(Graphics2D g2d)
	{
		int size = samolot.pociski.size();
		int x;
		int y;
		AffineTransform rotacja;
		AffineTransformOp transformacja_op;
		for (int i = 0; i < size; i++)
		{
			Pocisk pocisk = samolot.pociski.get(i);
			x = (int) (pocisk.x);
			y = (int) (pocisk.y);

			rotacja = AffineTransform.getRotateInstance(Math.toRadians(pocisk.kat + 90),
					pocisk.width, pocisk.height);
			transformacja_op = new AffineTransformOp(rotacja, AffineTransformOp.TYPE_BILINEAR);
			g2d.drawImage(transformacja_op.filter(Obrazki.obrazekPocisk, null), x, y, null);
		}
	}

	protected void rysujSamolotyGraczy(Graphics2D g2d)
	{

		int size = gracze.size();
		int x;
		int y;
		AffineTransform rotacja;
		AffineTransformOp transformacja_op;
		for (int i = 0; i < size; i++)
		{
			GraczTcp g = gracze.get(i);
			if (g.id == gracz.id)
			{
				continue;
			}
			x = (int) g.x;// (int) (g.x - CONST.samolot_width);
			y = (int) g.y;// (int) (g.y - CONST.samolot_height);
			rotacja = AffineTransform.getRotateInstance(Math.toRadians(g.kat + 90),
					CONST.samolot_width, CONST.samolot_height);
			transformacja_op = new AffineTransformOp(rotacja, AffineTransformOp.TYPE_BILINEAR);

			if (g.druzyna == gracz.druzyna)
			{
				g2d.setColor(Color.BLUE);
			}
			else
			{
				g2d.setColor(Color.RED);

			}
			g2d.fillRect(x, y - 5, (int) (g.punkty_zycia / 5 * 3), 4); // pasek
																		// ¿ycia
			if (g.druzyna == 'B')
			{
				if(g.punkty_zycia >70)
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2, null), x, y, null);
				else if(g.punkty_zycia >40)
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2_dmg, null), x, y, null);
				else
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2_hard_dmg, null), x, y, null);
			}
			else
			{
				if(g.punkty_zycia >70)
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot, null), x, y, null);
				else if(g.punkty_zycia >40)
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot_dmg, null), x, y, null);
				else
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot_hard_dmg, null), x, y, null);
			}

			int size_pociski = g.pociski.size();

			for (int j = 0; j < size_pociski; j++)
			{
				PociskTcp pocisk = g.pociski.get(j);
				x = (int) (pocisk.x);// - CONST.pocisk_width);
				y = (int) (pocisk.y);// - CONST.pocisk_height);

				rotacja = AffineTransform.getRotateInstance(Math.toRadians(pocisk.kat + 90),
						CONST.pocisk_width, CONST.pocisk_height);
				transformacja_op = new AffineTransformOp(rotacja, AffineTransformOp.TYPE_BILINEAR);

				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekPocisk, null), x, y, null);
			}

		}
	}

	protected void sprawdzKolizjeAll()
	{

		int size = gracze.size();
		for (int i = 0; i < size; i++)
		{
			GraczTcp g = gracze.get(i);
			if (g.id == gracz.id)
				continue;
			if (serwer.getTypGry().equals("TEAM") && gracz.druzyna == g.druzyna)
				continue;

			int size_pociski = g.pociski.size();
			for (int j = 0; j < size_pociski; j++)
			{
				PociskTcp p = g.pociski.get(j);
				if (sprawdzKolizje(p.x, p.y, samolot.x, samolot.y))
				{
					samolot.setPunktyZycia(samolot.getPunktyZycia() - CONST.pocisk_dmg);
					gracze.get(i).pociski.remove(j);
					size_pociski--;
				}
			}
		}
		size = samolot.pociski.size();
		for (int i = 0; i < size; i++)
		{
			Pocisk p = samolot.pociski.get(i);
			if (sprawdzKolizjeZGraczami(p.x, p.y))
			{
				samolot.pociski.remove(i);
				size--;
			}
		}
	}

	// pocisk
	protected boolean sprawdzKolizjeZGraczami(double x, double y)
	{
		gracze = klient.gracze_tcp;
		int size_gracze = gracze.size();
		for (int i = 0; i < size_gracze; i++)
		{
			GraczTcp g = gracze.get(i);
			if (g.id == gracz.id)
				continue;
			if (serwer.getTypGry().equals("TEAM") && gracz.druzyna == g.druzyna)
				continue;

			if (sprawdzKolizje(x, y, g.x, g.y))
			{
				return true;
			}
		}
		return false;
	}

	// pocisk, samolot
	protected boolean sprawdzKolizje(double x, double y, double x1, double y1)
	{
		double x_p = x + CONST.pocisk_width / 2;
		double y_p = y + CONST.pocisk_height / 2;

		if ((x_p > x1 && x_p < x1 + CONST.samolot_width * 2)
				&& (y_p > y1 && y_p < y1 + CONST.samolot_height * 2))
		{
			return true;
		}
		return false;
	}

	protected void zasadyGry()
	{
		zasadyTimera();
		zasadySmierci();

	}

	protected boolean timer_enabled = true;

	protected void zasadyTimera()
	{
		if (timer_enabled && serwer.getTrybGry().equals("DM_CLASSIC"))
		{
			remove(czas_label);
			timer2.cancel();
			timer2.purge();
		}
	}

	protected void zasadySmierci()
	{
		if (smierc == 0)
		{
			if (samolot.getPunktyZycia() <= 0)
			{
				smierc();
			}
		}
		if (smierc > 0)
		{
			smierc--;
			samolot.setPunktyZycia(samolot.getPunktyZycia() + 1);
		}
	}

	public int smierc = 0;

	protected void smierc()
	{
		if (serwer.getTrybGry().equals("DM_TIME") || serwer.getTrybGry().equals("CTF"))
		{
			smierc = 100;
			samolot.x = start_x;
			samolot.y = start_y;
		}
		else
		{
			klient.koniec = true;

		}
	}

	protected void aktualizujWspolrzedne()
	{
		this.klient.gracz = gracz;
		if (smierc == 0)
		{
			samolot.aktualizujWspolrzedne();
		}

	}

	protected void setPozycjaStartowa()
	{
		if (serwer.getTypGry().equals("TEAM"))
		{
			if (druzyna == 'A')
			{
				start_x = 50;
				start_y = 50;
				start_kat = 45;
			}
			else
			{
				start_x = 1200;
				start_y = 960;
				start_kat = 225;
			}
		}
		else
		{
			switch (pozycja)
			{
			case 1:
				start_x = 50;
				start_y = 50;
				start_kat = 45;
				break;
			case 2:
				start_x = 1200;
				start_y = 50;
				start_kat = 135;
				break;
			case 3:
				start_x = 50;
				start_y = 960;
				start_kat = 315;
				break;
			case 4:
				start_x = 1200;
				start_y = 960;
				start_kat = 225;
				break;
			}
		}
	}

	public void keyTyped(KeyEvent e)
	{}

	protected boolean key_pressed = false;
	protected int key;

	public void keyReleased(KeyEvent e)
	{
		key_pressed = false;
	}

	public void keyPressed(KeyEvent e)
	{
		key = e.getKeyCode();
		key_pressed = true;

		// System.out.println(e.getKeyCode());

	}

	protected int bullet_time_index = 0;

	protected void strzel()
	{
		if (bullet_time_index < bullet_time)
		{
			bullet_time_index++;
		}
		else
		{
			bullet_time_index = 0;
			samolot.dodajPocisk("normalny");
		}

	}

	protected int bullet_time_bomb_index = 0;

	protected void strzelBomba()
	{
		if (bullet_time_bomb_index < bullet_time_bomb)
		{
			bullet_time_bomb_index++;
		}
		else
		{
			bullet_time_bomb_index = 0;
			samolot.dodajPocisk("bomba");
		}

	}

	void startTimer()
	{
		java.util.Timer timer = new java.util.Timer();
		TimerTask timerTask = new TimerTask()
		{
			@Override
			public void run()
			{
				procesTimera();
			}
		};
		timer.scheduleAtFixedRate(timerTask, 0, FPS);

	}

	java.util.Timer timer2;

	protected void startLicznik()
	{
		timer2 = new java.util.Timer();
		TimerTask timerTask2 = new TimerTask()
		{
			@Override
			public void run()
			{
				czas_do_konca--;
			}
		};
		timer2.scheduleAtFixedRate(timerTask2, 0, 1000);
	}

	public void procesTimera()
	{
		gracze = klient.gracze_tcp;
		if (klient.start)
		{
			if (czas_do_konca == 600)
			{
				startLicznik();
				czas_do_konca--;

			}
			this.aktualizujWspolrzedne();
			if (smierc == 0)
			{
				sprawdzKolizjeAll();
			}
			zasadyGry();

		}

		if (key_pressed) // sprawdz, czy klawisz klawiatury jest wciœniêty
		{
			if (key == 39) // zmieñ kierunek samolotu
			{
				samolot.kat += 4;
			}
			else if (key == 37)
			{
				samolot.kat -= 4;
			}
			if (smierc == 0)
			{
				if (key == 32) // spacja
				{
					strzel();
				}
				if (key == 17) // lewy CTRL
				{
					strzelBomba();
				}
			}

		}
		repaint();
	}

}
