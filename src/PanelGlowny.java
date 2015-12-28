import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;




public class PanelGlowny extends JFrame {

	private static final long serialVersionUID = 1L;
	private static PanelGlowny frame;
	
	public PanelGlowny()
	{
		this.setTitle("Samoloty");
		this.setSize(new Dimension(600,600));
		
		this.add(inicjalizujKomponenty());
		setVisible(true);
		

	}
	private JPanel inicjalizujKomponenty() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        JTabbedPane tab_panel = new JTabbedPane();
        Gra gra = new Gra(600,600);
        tab_panel.addKeyListener(gra);
        tab_panel.setFocusable(true);
        tab_panel.add(gra);
        
        panel.add(tab_panel, BorderLayout.CENTER);
        
        return panel;
    }
	public static void createAndShowGui() {
        frame = new PanelGlowny();
        frame.setVisible(true);
    }
}
