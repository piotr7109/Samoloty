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

import javax.swing.JPanel;

import modules.Gracz;
import modules.Obrazki;
import modules.Pocisk;
import modules.Samolot;
import modules.TypGry;
import network.ClientTCP;
import network.modules.GraczTcp;
import network.modules.PociskTcp;
import system.CONST;

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
	protected TypGry typ_gry;
	protected static int WIDTH, HEIGHT;
	protected final int FPS = 20;
	protected int bullet_time = 15;
	protected int bullet_time_bomb = 30;
	protected int start_x, start_y;

	protected ClientTCP klient;

	public Gra(int width, int height, int id_gracza, String ip_serwera)
	{
		HEIGHT = height;
		WIDTH = width;
		this.setLayout(null);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addKeyListener(this);
		this.setPozycjaStartowa();
		gracz = new Gracz(start_x, start_y, 0);
		gracz.id = id_gracza;
		samolot = gracz.getSamolot();
		startClientTcpThread(ip_serwera);

		startTimer();

	}

	@Override
	protected void paintComponent(Graphics g)
	{
		// super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		g2d.setColor(Color.BLACK);

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
																				// ¿ycia
		g2d.setColor(Color.BLACK);

		g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot, null), x, y, null);

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
			rotacja = AffineTransform.getRotateInstance(Math.toRadians(g.kat + 90),
					CONST.samolot_width, CONST.samolot_height);
			transformacja_op = new AffineTransformOp(rotacja, AffineTransformOp.TYPE_BILINEAR);

			g2d.setColor(Color.DARK_GRAY);
			g2d.fillRect(x, y - 5, (int) (g.punkty_zycia / 5 * 3), 4); // pasek
																		// ¿ycia
			g2d.setColor(Color.BLACK);

			g2d.drawImage(transformacja_op.filter(Obrazki.obrazekSamolot, null), x, y, null);

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
			int size_pociski = g.pociski.size();
			for (int j = 0; j < size_pociski; j++)
			{
				PociskTcp p = g.pociski.get(j);
				if (sprawdzKolizje(p.x, p.y, samolot.x, samolot.y))
				{
					samolot.setPunktyZycia(samolot.getPunktyZycia() - 10);
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
		zasadySmierci();

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
			samolot.setPunktyZycia(samolot.getPunktyZycia()+1);
		}
	}

	protected int smierc = 0;

	protected void smierc()
	{
		smierc = 100;
		samolot.x = start_x;
		samolot.y = start_y;
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
		start_x = 100;
		start_y = 100;
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

	public void procesTimera()
	{
		if (klient.start)
		{

			this.aktualizujWspolrzedne();
			if (smierc == 0)
			{
				sprawdzKolizjeAll();
			}
			zasadyGry();
		}

		repaint();
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

}
