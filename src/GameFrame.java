import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class GameFrame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	private GamePanel panel;

	public GameFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
		while (SCREEN_HEIGHT % 50 != 0) { SCREEN_HEIGHT--; }
		
		this.setPreferredSize(new Dimension(SCREEN_HEIGHT, SCREEN_HEIGHT));
		this.setVisible(true);
		this.pack();
		this.setResizable(false);
		
		panel = new GamePanel(new NibblesGame(SCREEN_HEIGHT / 50), SCREEN_HEIGHT, SCREEN_HEIGHT);
		this.add(panel);
		panel.setLayout(null);
		
		this.addKeyListener(this);
		this.revalidate();
	}
	
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		this.panel.changeDirection(e);
	}
}
