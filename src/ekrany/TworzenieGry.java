package ekrany;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import system.SETTINGS;

public class TworzenieGry extends JPanel
{
	/**
	 * 
	 */
	private final int WIDTH = 100;
	private final int HEIGHT = 25;
	private final Point start = new Point(100,50);
	
	public String ip_servera;
	String[] typy_gry = {"TEAM","FFA"};
	String[] tryby_gry = {"CTF", "DM_CLASSIC", "DM_TIME"};
	String[] poziomy = {"SLOW","NORMAL", "FAST"};
	
	public JComboBox<String> typ_gry, tryb_gry, poziom;
	private JLabel ip_label;
	public JButton stworz_gre;
	private Image tlo;
	

	private static final long serialVersionUID = 1L;

	public TworzenieGry()
	{
		this.setLayout(null);
		
		
		try
		{
			tlo = ImageIO.read(new File("gfx/background.png"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pobierzIp();
		ustawParametry();

	}

	private void ustawParametry()
	{
		getNaglowek();
		
		
		
		typ_gry = new JComboBox<String>(typy_gry); 
		typ_gry.setBounds(start.x, start.y, WIDTH, HEIGHT);
		typGryEvent();
		
		tryb_gry = new JComboBox<String>(tryby_gry); 
		tryb_gry.setBounds(start.x+WIDTH*2, start.y, WIDTH, HEIGHT);
		
		poziom = new JComboBox<String>(poziomy);
		poziom.setBounds(start.x+WIDTH*4, start.y, WIDTH, HEIGHT);
		
		ip_label = new JLabel("IP: "+ip_servera);
		ip_label.setBounds(0, 0, WIDTH*2, HEIGHT);
		ip_label.setForeground(SETTINGS.kolorCzcionki);
		
		stworz_gre = new JButton("Stwórz grê");
		stworz_gre.setBounds(start.x+WIDTH*6, start.y, WIDTH, HEIGHT);
		
		
		add(poziom);
		add(stworz_gre);
		add(ip_label);
		add(tryb_gry);
		add(typ_gry);
	}
	
	private void typGryEvent()
	{
		typ_gry.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String typ = typ_gry.getSelectedItem().toString();
				
				switch(typ)
				{
					case "TEAM":
						tryby_gry = new String[]{"CTF", "DM_CLASSIC", "DM_TIME"};
						break;
					case "FFA":
						tryby_gry = new String[]{"DM_CLASSIC", "DM_TIME"};
						break;
				}
				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>( tryby_gry );
				tryb_gry.setModel(model);
				
				
				
			}
		});
	}
	
	private void getNaglowek()
	{
		JLabel typ_gry = new JLabel("TYP");
		JLabel tryb_gry = new JLabel("TRYB");
		JLabel poziom = new JLabel("SZYBKOŒÆ");
		
		typ_gry.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		tryb_gry.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		poziom.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		
		typ_gry.setForeground(SETTINGS.kolorCzcionki);
		tryb_gry.setForeground(SETTINGS.kolorCzcionki);
		poziom.setForeground(SETTINGS.kolorCzcionki);
		
		typ_gry.setSize(WIDTH, HEIGHT);
		tryb_gry.setSize(WIDTH, HEIGHT);
		poziom.setSize(WIDTH, HEIGHT);
		
		typ_gry.setLocation(start.x, start.y-30);
		tryb_gry.setLocation(start.x+WIDTH*2, start.y-30);
		poziom.setLocation(start.x+WIDTH*4, start.y-30);
		
		add(poziom);
		add(typ_gry);
		add(tryb_gry);
	}
	private void pobierzIp()
	{
		try
		{
			ip_servera = InetAddress.getLocalHost().getHostAddress() + "";
		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void paintComponent(Graphics g)
	{

		//super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		
	
		g2d.drawImage(tlo, 0, 0 , this.getWidth(), this.getHeight(), this);
		
	
	}

}
