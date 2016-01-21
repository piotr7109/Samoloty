package ekrany;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import modules.Obrazki;

public class Ustawienia extends JPanel
{
	/**
	 * 
	 */
	private final int WIDTH = 400;
	private final int HEIGHT = 25;
	
	private static final long serialVersionUID = 1L;
	public JButton zapisz;
	public JSlider width, height;
	public JTextField login;
	
	private JLabel width_label, height_label;
	private JLabel max_width_label, min_width_label, max_height_label, min_height_label, login_label;
	
	public Ustawienia()
	{
		this.setLayout(null);
		
		width = new JSlider();
		width_label = new JLabel();
		height = new JSlider();
		height_label = new JLabel();
		login = new JTextField();
		zapisz = new JButton("Zapisz");
		
		
		
		ustawParametry();
		dodajEventy();
		dodajKontrolkiDoSceny();
		dodajEtykiety();
	}
	
	private void dodajEtykiety()
	{
		max_width_label = new JLabel(width.getMaximum()+"");
		min_width_label = new JLabel(width.getMinimum()+"");
		max_height_label = new JLabel(height.getMaximum()+"");
		min_height_label = new JLabel(height.getMinimum()+"");
		login_label = new JLabel("Login");
		Dimension d = new Dimension(50, 50);
		
		max_width_label.setSize(d);
		min_width_label.setSize(d);
		max_height_label.setSize(d);
		min_height_label.setSize(d);
		login_label.setSize(d);
		
		
		min_width_label.setLocation(width.getX()-50, width.getY() - width.getHeight()/2);
		max_width_label.setLocation(width.getX()+width.getWidth(), width.getY()- width.getHeight()/2);
		min_height_label.setLocation(height.getX()-50, height.getY()- height.getHeight()/2);
		max_height_label.setLocation(height.getX()+height.getWidth(), height.getY()- height.getHeight()/2);
		
		login_label.setLocation(login.getX()-50, login.getY()- login.getHeight()/2);
		
		add(max_width_label);
		add(min_width_label);
		add(max_height_label);
		add(min_height_label);
		add(login_label);
	}
	
	private void dodajKontrolkiDoSceny()
	{
		add(width);
		add(width_label);
		add(height);
		add(height_label);
		add(login);
		add(zapisz);
	}
	
	private void ustawParametry()
	{
		width.setMaximum(1920);
		width.setMinimum(1024);
		width.setSize(new Dimension(WIDTH, HEIGHT));
		width.setLocation(100, 100);
		width_label.setText(width.getMinimum()+"");
		width_label.setSize(new Dimension(50, 50));
		width_label.setLocation(WIDTH/2+100, 50);
		
		height.setMaximum(1080);
		height.setMinimum(768);
		height.setSize(new Dimension(WIDTH, HEIGHT));
		height.setLocation(100, 200);
		height_label.setText(height.getMinimum()+"");
		height_label.setSize(new Dimension(50, 50));
		height_label.setLocation(WIDTH/2+100, 150);
		
		login.setSize(new Dimension(200, HEIGHT));
		login.setLocation(100, 250);
		
		zapisz.setSize(new Dimension(150, 25));
		zapisz.setLocation(WIDTH-50,250);
		
	}
	
	
	private void dodajEventy()
	{
		width.addChangeListener(new ChangeListener()
		{
			
			@Override
			public void stateChanged(ChangeEvent e)
			{
				width_label.setText(width.getValue()+"");
				repaint();
			}
		});
		height.addChangeListener(new ChangeListener()
		{
			
			@Override
			public void stateChanged(ChangeEvent e)
			{
				height_label.setText(height.getValue()+"");
				repaint();
				
			}
		});
		
	
	}
	protected void paintComponent(Graphics g)
	{

		//super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		Image img = null;
		try
		{
			img = ImageIO.read(new File("gfx/background.png"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		g2d.drawImage(img, this.getX() , this.getY() , this.getWidth(), this.getHeight(), this);
		
	
	}
}
