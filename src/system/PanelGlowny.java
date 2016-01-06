package system;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ekrany.Gra;
import ekrany.Serwery;
import ekrany.Ustawienia;

public class PanelGlowny extends JFrame
{

	private static final long serialVersionUID = 1L;
	private static PanelGlowny frame;
	private int id_gracza;
	JTabbedPane tab_panel;
	private Ustawienia ustawienia;
	private Serwery serwery;

	public PanelGlowny()
	{
		this.setTitle("Samoloty");
		this.setSize(new Dimension(SETTINGS.width, SETTINGS.height));
		this.id_gracza = (int)(Math.random()*1000);
		//this.add(EkranGry());
		this.add(inicjalizujKomponenty());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		

	}
	private JPanel inicjalizujKomponenty()
	{
		JPanel panel = new JPanel(new BorderLayout(10, 10));

		tab_panel = new JTabbedPane();
		
		panel.add(tab_panel, BorderLayout.CENTER);
		EkranUstawien();
		return panel;
	}
	
	private void EkranListySerwerow()
	{
		serwery = new Serwery();
		tab_panel.add(serwery, "Lista serwerów");
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
	}
}
