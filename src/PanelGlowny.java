import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class PanelGlowny extends JFrame
{

	private static final long serialVersionUID = 1L;
	private static PanelGlowny frame;
	private int width, height;

	public PanelGlowny()
	{
		width = 1200;
		height = 900;
		this.setTitle("Samoloty");
		this.setSize(new Dimension(width, height));

		this.add(inicjalizujKomponenty(1));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private JPanel inicjalizujKomponenty(int id_gracza)
	{
		JPanel panel = new JPanel(new BorderLayout(10, 10));

		JTabbedPane tab_panel = new JTabbedPane();
		Gra gra = new Gra(width, height, id_gracza);
		tab_panel.addKeyListener(gra);
		tab_panel.setFocusable(true);
		tab_panel.add(gra, "Fajt");

		panel.add(tab_panel, BorderLayout.CENTER);

		return panel;
	}

	public static void createAndShowGui()
	{
		frame = new PanelGlowny();
		frame.setVisible(true);
	}
}
