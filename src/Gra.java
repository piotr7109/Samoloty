import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.TimerTask;


import javax.swing.JPanel;

import modules.Gracz;
import modules.Pocisk;
import modules.Samolot;
import modules.TypGry;

public class Gra extends JPanel implements KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ArrayList<Gracz> gracze;
	protected Gracz gracz;
	protected Samolot samolot; 
	protected String mapa;
	protected String mapa_src = "";
	protected TypGry typ_gry;
	protected static int WIDTH, HEIGHT;
	protected final int FPS = 15;
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
		this.rysujPociskiGracza(g2d);
		

	}
	
	
	

	protected void rysujSamolotGracza(Graphics2D g2d)
	{
		int x =(int)(samolot.x - samolot.width);
		int y =(int)(samolot.y - samolot.height);
		g2d.drawImage(samolot.transformacja_op.filter(samolot.obrazekSamolot, null), x, y, null);
		
	}

	protected void rysujPociskiGracza(Graphics2D g2d)
	{
		int size = samolot.pociski.size();
		int x;
		int y;
		for (int i = 0; i < size; i++)
		{
			Pocisk pocisk = samolot.pociski.get(i);
			x = (int)(pocisk.x - pocisk.width);
			y = (int)(pocisk.y - pocisk.height);
			g2d.drawImage(pocisk.transformacja_op.filter(pocisk.obrazekPocisk, null), x, y, null);
		}
	}
	protected void aktualizujWspolrzedne()
	{
		samolot.aktualizujWspolrzedne();
		
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

	private int bullet_time_index = 0;

	private void strzel()
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

	private int bullet_time_bomb_index = 0;

	private void strzelBomba()
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
		this.aktualizujWspolrzedne();
		repaint();
		if (key_pressed) // sprawdz, czy klawisz klawiatury jest wciœniêty
		{
			if (key == 39 ) // zmieñ kierunek samolotu
			{
				samolot.kat+=4;
			}
			else if(key == 37)
			{
				samolot.kat-=4;
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
