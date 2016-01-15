package ekrany;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import network.modules.GraczTcp;

public class EkranKoniec extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Point start = new Point(100, 50);
	private Point size = new Point(200, 25);
	private ArrayList<GraczTcp> gracze;
	public JButton powrot = new JButton("Lista serwerów");

	public EkranKoniec(ArrayList<GraczTcp> gracze)
	{
		this.setLayout(null);
		this.gracze = gracze;
		getDaneDruzyn();
		getNaglowek();
		getDaneGraczy();
		powrotButton();
		
	}
	
	private void powrotButton()
	{
		powrot.setBounds(0,0, 200,25);
		
		add(powrot);
	}
	
	private int getDaneDruzyn()
	{

		for (GraczTcp g : gracze)
		{
			if (g.druzyna == 'X')
				return 0;
		}
		int wynik_a = 0;
		int wynik_b = 0;
		for (GraczTcp g : gracze)
		{
			if (g.druzyna == 'A')
			{
				wynik_a += g.punkty;
			}
			else
			{
				wynik_b += g.punkty;
			}
		}
		char druzyna_zwycieska;
		if (wynik_a > wynik_b)
			druzyna_zwycieska = 'A';
		else
			druzyna_zwycieska = 'B';

		JLabel wynik = new JLabel("Wygra³a dru¿yna " + druzyna_zwycieska);
		wynik.setBounds(start.x+200, start.y - 50, 200, 25);

		add(wynik);
		return 0;
	}

	private void getDaneGraczy()
	{
		for (GraczTcp g : gracze)
		{
			JLabel id = new JLabel(g.id + "");
			JLabel login = new JLabel(g.login);
			JLabel punkty = new JLabel(g.punkty + "");
			JLabel zabicia = new JLabel(g.fragi + "");
			JLabel druzyna = new JLabel(g.druzyna + "");

			id.setBounds(start.x, start.y, size.x, size.y);
			login.setBounds(start.x * 2, start.y, size.x, size.y);
			punkty.setBounds(start.x * 3, start.y, size.x, size.y);
			zabicia.setBounds(start.x * 4, start.y, size.x, size.y);
			druzyna.setBounds(start.x * 5, start.y, size.x, size.y);
			start.y += 25;
			add(id);
			add(login);
			add(punkty);
			add(zabicia);
			add(druzyna);

		}
	}

	private void getNaglowek()
	{
		JLabel id = new JLabel("ID");
		JLabel login = new JLabel("LOGIN");
		JLabel punkty = new JLabel("PUNKTY");
		JLabel zabicia = new JLabel("ZABICIA");
		JLabel druzyna = new JLabel("DRU¯YNA");

		id.setBounds(start.x, start.y, size.x, size.y);
		login.setBounds(start.x * 2, start.y, size.x, size.y);
		punkty.setBounds(start.x * 3, start.y, size.x, size.y);
		zabicia.setBounds(start.x * 4, start.y, size.x, size.y);
		druzyna.setBounds(start.x * 5, start.y, size.x, size.y);
		start.y += 30;
		add(id);
		add(login);
		add(punkty);
		add(zabicia);
		add(druzyna);
	}
}
