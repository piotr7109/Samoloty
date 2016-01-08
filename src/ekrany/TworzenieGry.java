package ekrany;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TworzenieGry extends JPanel
{
	/**
	 * 
	 */
	private final int WIDTH = 100;
	private final int HEIGHT = 25;
	private final Point start = new Point(100,50);
	
	private String ip_servera;
	String[] typy_gry = {"TEAM","FFA"};
	String[] tryby_gry = {"CTF", "DM_CLASSIC", "DM_TIME"};
	
	public JComboBox<String> typ_gry, tryb_gry;
	public JLabel ip_label;
	public JButton stworz_gre;
	
	

	private static final long serialVersionUID = 1L;

	public TworzenieGry()
	{
		this.setLayout(null);
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
		
		ip_label = new JLabel("IP: "+ip_servera);
		ip_label.setBounds(0, 0, WIDTH*2, HEIGHT);
		
		stworz_gre = new JButton("Stwórz grê");
		stworz_gre.setBounds(start.x+WIDTH*4, start.y, WIDTH, HEIGHT);
		
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
		
		typ_gry.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		tryb_gry.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		
		typ_gry.setSize(WIDTH, HEIGHT);
		tryb_gry.setSize(WIDTH, HEIGHT);
		
		typ_gry.setLocation(start.x, start.y-30);
		tryb_gry.setLocation(start.x+WIDTH*2, start.y-30);
		
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

}
