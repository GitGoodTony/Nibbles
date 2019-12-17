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
		
		// Window height is a multiple of 50 with max height and width of the 4/5ths the screen height
		int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5 * 4;
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
