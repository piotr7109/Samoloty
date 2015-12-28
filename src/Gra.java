import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import modules.Gracz;
import modules.Pocisk;
import modules.Samolot;
import modules.TypGry;

public class Gra extends JPanel implements KeyListener
{
	protected ArrayList<Gracz> gracze;
	protected Gracz gracz;
	protected Samolot samolot;
	protected String mapa;
	protected String mapa_src = "";
	protected TypGry typ_gry;
	protected static int WIDTH, HEIGHT;
	protected final int FPS = 20;
	protected int bullet_time = 15;
	protected int bullet_time_bomb = 30;


	public Gra(int width, int height)
	{
		HEIGHT = height;
		WIDTH = width;
		this.setLayout(null);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addKeyListener(this);
		gracz = new Gracz(100, 100, 0);
		samolot = gracz.getSamolot();
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
		this.rysujPociski(g2d);
		/*
		 * BufferedImage imageA; try { imageA = ImageIO.read(new
		 * File("indeks.jpeg")); AffineTransform at = new AffineTransform();
		 * //at.translate(getWidth() / 2, getHeight() / 2);
		 * at.rotate(Math.PI/4); at.translate(-imageA.getWidth()/2+200,
		 * -imageA.getHeight()/2+100); g2d.drawImage(imageA, at, null); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */
	}

	protected void rysujSamolotGracza(Graphics g2d)
	{

		samolot.aktualizujWspolrzedne();
		g2d.drawOval((int) samolot.x, (int) samolot.y, 15, 15);
	}

	protected void rysujPociski(Graphics g2d)
	{
		int size = samolot.pociski.size();
		for (int i = 0; i < size; i++)
		{
			Pocisk pocisk = samolot.pociski.get(i);
			if (pocisk.aktualizujWspolrzedne())
			{
				g2d.drawOval((int) pocisk.x, (int) pocisk.y, 5, 5);
			} else
			{
				samolot.pociski.remove(i);
				size--;

			}
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

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

	private int bullet_time_index = 0;

	private void strzel()
	{
		if (bullet_time_index < bullet_time)
		{
			bullet_time_index++;
		} else
		{
			bullet_time_index = 0;
			samolot.dodajPocisk("normalny");
		}

	}

	private int bullet_time_bomb_index = 0;

	private void strzelBomba()
	{
		if (bullet_time_bomb_index < bullet_time_bomb)
		{
			bullet_time_bomb_index++;
		} else
		{
			bullet_time_bomb_index = 0;
			samolot.dodajPocisk("bomba");
		}

	}

	private void zmien_kierunek()
	{
		switch (key)
		{
		case 39: // skrêæ w lewo
			samolot.kat += 4;

			break;
		case 37: // skrêæ w prawo
			samolot.kat -= 4;
			break;
		}
	}

	void startTimer()
	{
		// use java.util Timer rather than javax.swing Timer
		// to avoid running intensive simulation code on the swing thread
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
		repaint();
		if (key_pressed) // sprawdz, czy klawisz klawiatury jest wciœniêty
		{
			if (key == 39 || key == 37) // zmieñ kierunek samolotu
			{
				zmien_kierunek();
			}
			if (key == 32)
			{
				strzel();
			}
			if (key == 17)
			{
				strzelBomba();
			}

		}
	}

}
