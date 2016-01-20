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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Gracz_mod;
import database.Serwer;
import modules.Flaga;
import modules.Gracz;
import modules.Obrazki;
import modules.Pocisk;
import modules.Samolot;
import network.ClientTCP;
import network.modules.GraczTcp;
import network.modules.PociskTcp;
import system.Audio;
import system.CONST;
import system.PanelGlowny;
import system.SETTINGS;

public class Gra extends JPanel implements KeyListener
{
	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;

	public ArrayList<GraczTcp> gracze;
	protected Gracz gracz;
	protected Samolot samolot;
	protected String mapa;
	protected String mapa_src = "";
	protected static int WIDTH, HEIGHT;
	protected final int FPS = 15;
	protected int bullet_time = 15;
	protected int bullet_time_bomb = 30;
	protected int start_x, start_y, start_kat;
	protected Serwer serwer;
	protected int pozycja;
	protected char druzyna;
	protected int czas_do_konca = 600; // sekundy
	protected double multiplier;
	protected JLabel czas_label;
	protected JLabel punkty, fragi;
	protected int rel_x, rel_y;
	protected int map_width, map_height;

	public Flaga flaga_a, flaga_b;

	protected ClientTCP klient;
	protected PanelGlowny panel;

	public Gra(int width, int height, int id_gracza, Serwer serwer, PanelGlowny panel_glowny)
	{
		ExecutorService sound = Executors.newCachedThreadPool();
		sound.execute(new Audio("theme"));
		HEIGHT = height;
		WIDTH = width;
		map_width = 1500;
		map_height = 1500;
		rel_x = rel_y = 0;
		panel = panel_glowny;
		this.serwer = serwer;
		pozycja = getPozycja(id_gracza);

		this.setLayout(null);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addKeyListener(this);
		this.setPredkoscGry();
		this.setPozycjaStartowa();

		gracz = new Gracz(start_x, start_y, start_kat);
		gracz.id = id_gracza;
		gracz.druzyna = druzyna;
		gracz.login = SETTINGS.login;
		samolot = gracz.getSamolot();

		startClientTcpThread(serwer.getIpSerwera());
		setFlaga();

		startTimer();
		ustawLicznik();
		ustawPunkty();

	}

	protected void setPredkoscGry()
	{
		switch (serwer.getPoziom())
		{
			case "SLOW":
				multiplier = 0.7;
				break;
			case "NORMAL":
				multiplier = 1;
				break;
			case "FAST":
				multiplier = 1.6;
				break;

		}
	}

	protected void setFlaga()
	{
		if (serwer.getTrybGry().equals("CTF"))
		{
			flaga_a = new Flaga(50, 50);
			flaga_b = new Flaga(map_width - 150, map_height - 150);
		}
	}

	protected void ustawPunkty()
	{
		fragi = new JLabel("Liczba zabiæ:" + gracz.getFragi());
		punkty = new JLabel("Punkty:" + gracz.getPunkty());

		fragi.setBounds(0, 0, 100, 25);
		punkty.setBounds(0, 25, 100, 25);

		add(fragi);
		add(punkty);
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

	protected void startClientTcpThread(String ip_serwera)
	{
		Executor exe = Executors.newFixedThreadPool(1);

		klient = new ClientTCP(ip_serwera);
		klient.gracz = gracz;
		exe.execute(klient);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		// super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.GREEN);
		g2d.fillRect(0, 0, 2000, 2000);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0+rel_x, 0+rel_y, map_width, map_height);
		g2d.setColor(Color.BLACK);
		czas_label.setText(CONST.intToTime(czas_do_konca));

		this.rysujSamolotGracza(g2d);
		this.rysujPociskiGracza(g2d);
		this.rysujSamolotyGraczy(g2d);

		if (serwer.getTrybGry().equals("CTF"))
		{
			this.rysujFlage(g2d);
		}

	}

	protected void rysujFlage(Graphics2D g2d)
	{
		g2d.drawImage(Obrazki.flaga_a, null, flaga_a.x+rel_x, flaga_a.y+rel_y);

		g2d.drawImage(Obrazki.flaga_b, null, flaga_b.x+rel_x, flaga_b.y+rel_y);

	}

	protected void rysujSamolotGracza(Graphics2D g2d)
	{
		int x = (int) (samolot.x);
		int y = (int) (samolot.y);
		AffineTransform rotacja = AffineTransform.getRotateInstance(Math.toRadians(samolot.kat + 90), samolot.width, samolot.height);
		AffineTransformOp transformacja_op = new AffineTransformOp(rotacja, AffineTransformOp.TYPE_BILINEAR);

		g2d.setColor(Color.GREEN);
		g2d.fillRect(x+rel_x, y - 5+rel_y , (int) (samolot.getPunktyZycia() / 5 * 3), 4); // pasek

		if (gracz.druzyna == 'B')
		{
			if (samolot.getPunktyZycia() > 70)
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2, null), x+rel_x, y+rel_y, null);
			else if (samolot.getPunktyZycia() > 40)
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2_dmg, null), x+rel_x, y+rel_y, null);
			else
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2_hard_dmg, null), x+rel_x, y+rel_y, null);
		}
		else
		{
			if (samolot.getPunktyZycia() > 70)
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot, null), x+rel_x, y+rel_y, null);
			else if (samolot.getPunktyZycia() > 40)
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot_dmg, null), x+rel_x, y+rel_y, null);
			else
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot_hard_dmg, null), x+rel_x, y+rel_y, null);
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

			rotacja = AffineTransform.getRotateInstance(Math.toRadians(pocisk.kat), pocisk.width, pocisk.height);
			transformacja_op = new AffineTransformOp(rotacja, AffineTransformOp.TYPE_BILINEAR);
			
			if(gracz.druzyna =='B')
			{
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekPocisk2, null), x+rel_x, y+rel_y, null);
			}
			else
			{
				g2d.drawImage(transformacja_op.filter(Obrazki.obrazekPocisk, null), x+rel_x, y+rel_y, null);
			}
		}
	}

	protected void rysujSamolotyGraczy(Graphics2D g2d)
	{
		gracze = klient.gracze_tcp;
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
			rotacja = AffineTransform.getRotateInstance(Math.toRadians(g.kat + 90), CONST.samolot_width, CONST.samolot_height);
			transformacja_op = new AffineTransformOp(rotacja, AffineTransformOp.TYPE_BILINEAR);

			if (g.druzyna == gracz.druzyna)
			{
				g2d.setColor(Color.BLUE);
			}
			else
			{
				g2d.setColor(Color.RED);

			}
			g2d.fillRect(x+rel_x, y - 5+rel_y, (int) (g.punkty_zycia / 5 * 3), 4); // pasek
																		// ¿ycia
			if (g.druzyna == 'B')
			{
				if (g.punkty_zycia > 70)
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2, null), x+rel_x, y+rel_y, null);
				else if (g.punkty_zycia > 40)
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2_dmg, null), x+rel_x, y+rel_y, null);
				else
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot2_hard_dmg, null), x+rel_x, y+rel_y, null);
			}
			else
			{
				if (g.punkty_zycia > 70)
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot, null), x+rel_x, y+rel_y, null);
				else if (g.punkty_zycia > 40)
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot_dmg, null), x+rel_x, y+rel_y, null);
				else
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot_hard_dmg, null), x+rel_x, y+rel_y, null);
			}

			int size_pociski = g.pociski.size();

			for (int j = 0; j < size_pociski; j++)
			{
				PociskTcp pocisk = g.pociski.get(j);
				x = (int) (pocisk.x);// - CONST.pocisk_width);
				y = (int) (pocisk.y);// - CONST.pocisk_height);

				rotacja = AffineTransform.getRotateInstance(Math.toRadians(pocisk.kat), CONST.pocisk_width, CONST.pocisk_height);
				transformacja_op = new AffineTransformOp(rotacja, AffineTransformOp.TYPE_BILINEAR);
				
				if(g.druzyna =='B')
				{
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekPocisk2, null), x+rel_x, y+rel_y, null);
				}
				else
				{
					g2d.drawImage(transformacja_op.filter(Obrazki.obrazekPocisk, null), x+rel_x, y+rel_y, null);
				}
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
				if (sprawdzKolizje2(p.x, p.y, samolot.x, samolot.y))
				{
					if (gracz.flaga)
						samolot.setPunktyZycia(samolot.getPunktyZycia() - (int) (CONST.pocisk_dmg * 2));
					else
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
				gracz.setPunkty(gracz.getPunkty() + 1);
				if (g.punkty_zycia < 6)
				{
					ExecutorService sound = Executors.newCachedThreadPool();
					sound.execute(new Audio("smierc"));
					gracz.setFragi(gracz.getFragi() + 1);
					gracz.setPunkty(gracz.getPunkty() + 20);
				}
				return true;
			}
		}
		return false;
	}

	protected boolean sprawdzKolizje2(double x, double y, double x1, double y1)
	{
		double x_p = x + CONST.pocisk_width / 2;
		double y_p = y + CONST.pocisk_height / 2;

		if ((x_p > x1-7 && x_p < x1 + CONST.samolot_width * 2 +7) && (y_p > y1-7 && y_p < y1 + CONST.samolot_height * 2 +7))
		{
			ExecutorService sound = Executors.newCachedThreadPool();
			sound.execute(new Audio("trafienie"));
			return true;
		}
		return false;
	}
	
	// pocisk, samolot
	protected boolean sprawdzKolizje(double x, double y, double x1, double y1)
	{
		double x_p = x + CONST.pocisk_width / 2;
		double y_p = y + CONST.pocisk_height / 2;

		if ((x_p > x1 && x_p < x1 + CONST.samolot_width * 2) && (y_p > y1 && y_p < y1 + CONST.samolot_height * 2))
		{
			ExecutorService sound = Executors.newCachedThreadPool();
			sound.execute(new Audio("trafienie"));
			return true;
		}
		return false;
	}

	// obiekt, pole
	protected boolean sprawdzFlage(int x, int y, int x1, int y1)
	{

		if ((x > x1 && x < x1 + 50) && (y > y1 && y < y1 + 150))
		{
			return true;
		}
		return false;
	}

	protected void zasadyGry()
	{
		zasadyTimera();
		if (serwer.getTrybGry().equals("CTF"))
		{
			zasadyFlaga();
		}
		zasadySmierci();
		graniceEkranu();
		zasadyKoniec();

	}

	protected void zasadyFlaga()
	{
		if (!gracz.flaga)
		{
			if (gracz.druzyna == 'A')
			{
				if (!flaga_b.zajeta)
				{
					if (sprawdzKolizje(flaga_b.x, flaga_b.y, samolot.x, samolot.y))
					{
						flaga_b.zajeta = true;
						gracz.flaga = true;
					}
				}
			}
			else
			{
				if (!flaga_a.zajeta)
				{
					if (sprawdzKolizje(flaga_a.x, flaga_a.y, samolot.x, samolot.y))
					{
						flaga_a.zajeta = true;
						gracz.flaga = true;
					}
				}
			}
		}
		else
		{
			if (sprawdzFlage(flaga_b.x, flaga_b.y, 0, 0)) 
			{
				flaga_b.zajeta = false;
				gracz.flaga = false;
				gracz.setPunkty(gracz.getPunkty() + 100);
			}
			if (sprawdzFlage(flaga_a.x, flaga_a.y, WIDTH - 200, HEIGHT - 200))
			{
				flaga_a.zajeta = false;
				gracz.flaga = false;
				gracz.setPunkty(gracz.getPunkty() + 100);
			}
		}
	}

	protected void graniceEkranu()
	{
		if (samolot.x < 0)
		{
			samolot.x = 100;
			samolot.setPunktyZycia(samolot.getPunktyZycia() - 50);
		}
		if (samolot.x + 60 > map_width)
		{
			samolot.x = map_width - 100 - CONST.samolot_width * 2;
			samolot.setPunktyZycia(samolot.getPunktyZycia() - 50);
		}
		if (samolot.y < 0)
		{
			samolot.y = 100;
			samolot.setPunktyZycia(samolot.getPunktyZycia() - 50);
		}
		if (samolot.y + 60 > map_height)
		{
			samolot.y = map_height - 100 - CONST.samolot_height * 2;
			samolot.setPunktyZycia(samolot.getPunktyZycia() - 50);
		}
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
		if (smierc <= 0)
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
	protected boolean smierc_koniec = false;

	protected void smierc()
	{
		samolot.setPunktyZycia(0);
		ExecutorService sound = Executors.newCachedThreadPool();
		sound.execute(new Audio("smierc"));
		if (serwer.getTrybGry().equals("DM_TIME") || serwer.getTrybGry().equals("CTF"))
		{
			smierc = 100;
			samolot.x = start_x;
			samolot.y = start_y;

			if (serwer.getTrybGry().equals("CTF"))
			{
				if (gracz.flaga)
				{
					if (gracz.druzyna == 'A')
					{
						flaga_b.x = WIDTH - 50;
						flaga_b.y = HEIGHT - 50;
						flaga_b.zajeta = false;
					}
					else if (gracz.druzyna == 'B')
					{
						flaga_a.x = 50;
						flaga_a.y = 50;
						flaga_a.zajeta = false;
					}

				}
				gracz.flaga = false;
			}
		}
		else
		{
			smierc_koniec = true;
		}
	}

	protected void zasadyKoniec()
	{
		if (serwer.getTrybGry().equals("DM_TIME") || serwer.getTrybGry().equals("CTF"))
		{
			if (czas_do_konca <= 0)
			{
				koniec();
			}
		}
		else
		{
			int martwi = 0;
			int a = 0;
			int b = 0;
			int a_ilosc = 0;
			int b_ilosc = 0;
			for (GraczTcp g : gracze)
			{
				if (g.druzyna == 'B')
					b_ilosc++;
				else
					a_ilosc++;
				if (g.punkty_zycia <= 0)
				{
					martwi++;
					if (g.druzyna == 'B')
						b++;
					else
						a++;
				}

			}
			if (serwer.getTypGry().equals("TEAM"))
			{
				if (b == b_ilosc || a == a_ilosc)
					koniec();
			}
			if (martwi == gracze.size() - 1)
			{
				koniec();
			}
		}
	}

	protected void koniec()
	{
		smierc_koniec = true;
		timer.cancel();
		timer.purge();
		timer2.cancel();
		timer2.purge();
		panel.EkranKoniec(gracze);
		klient.koniec = true;
	}

	protected void flagaWspolrzedne()
	{
		boolean a, b;
		a = true;
		b = true;
		setFlaga();
		for (GraczTcp g : gracze)
		{
			if (g.flaga)
			{
				if (a && g.druzyna == 'B')
				{
					flaga_a.x = (int) g.x;
					flaga_a.y = (int) g.y;
					flaga_a.zajeta = true;
					a = false;
				}
				if (b && g.druzyna == 'A')
				{
					flaga_b.x = (int) g.x;
					flaga_b.y = (int) g.y;
					flaga_b.zajeta = true;
					b = false;
				}
			}
		}
	}

	protected void aktualizujWspolrzedne()
	{
		rel_x = (int)(WIDTH/2 - samolot.x);
		rel_y =(int)(HEIGHT/2 - samolot.y);
		if(rel_x>0)
		{
			rel_x = 0;
		}
		else if( samolot.x + WIDTH/2 >= map_width )
		{
			rel_x = (int)( WIDTH - map_width);
		}
		if(rel_y>0)
		{
			rel_y= 0;
		}
		else if( samolot.y + HEIGHT/2 >= map_height )
		{
			rel_y = (int)( HEIGHT - map_height);
		}

		fragi.setText("Liczba zabiæ:" + gracz.getFragi());
		punkty.setText("Punkty:" + gracz.getPunkty());

		if (serwer.getTrybGry().equals("CTF"))
		{
			flagaWspolrzedne();
		}
		if (smierc == 0)
		{
			samolot.aktualizujWspolrzedne();
		}
		this.klient.gracz = gracz;

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
				start_x = map_width-100;
				start_y = map_height-100;
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
					start_x = map_width-100;
					start_y = 50;
					start_kat = 135;
					break;
				case 3:
					start_x = 50;
					start_y = map_height-100;
					start_kat = 315;
					break;
				case 4:
					start_x = map_width-100;
					start_y = map_height-100;
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
			ExecutorService sound = Executors.newCachedThreadPool();
			sound.execute(new Audio("strzal"));
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
			ExecutorService sound = Executors.newCachedThreadPool();
			sound.execute(new Audio("bomba"));
		}

	}

	java.util.Timer timer;

	void startTimer()
	{
		timer = new java.util.Timer();
		TimerTask timerTask = new TimerTask()
		{
			@Override
			public void run()
			{
				procesTimera();
			}
		};
		timer.scheduleAtFixedRate(timerTask, 0, (int) (FPS / multiplier));

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
		if (!smierc_koniec)
		{
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
		}
		else
		{
			this.aktualizujWspolrzedne();
			zasadyGry();
			samolot.x = -100;
			samolot.y = -100;
		}
		repaint();
	}

}
