import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private NibblesGame game;
	private Timer timer;
	
	private final int BLOCK_WIDTH;
	
	public GamePanel(NibblesGame game, int width, int height) {
		this.game = game;
		this.BLOCK_WIDTH = height / 50;
		
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] coords = game.move();
				game.checkCollision(coords[0], coords[1]);
			}
		};
		
		this.timer = new Timer(250, action);
		this.timer.setRepeats(true);
		this.timer.setInitialDelay(0);
		this.timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (!game.playerAlive()) {
			timer.stop();
		}
		
		SnakeNode temp = game.getPlayer().getBack();
		g.setColor(Color.BLACK);
		while (temp != null) {
			g.drawRect(temp.getX() * BLOCK_WIDTH, temp.getY() * BLOCK_WIDTH, BLOCK_WIDTH, BLOCK_WIDTH);
			
			temp = temp.getNext();
		}
		
		int[] appleCoords = this.game.getAppleCoordinates();
		g.setColor(Color.RED);
		g.drawRect(appleCoords[0] * BLOCK_WIDTH, appleCoords[1] * BLOCK_WIDTH, BLOCK_WIDTH, BLOCK_WIDTH);
		
		this.repaint();
	}
	
	public void changeDirection(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (game.getDirection() != Direction.left) {
				game.setDirection(Direction.right);
			}
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (game.getDirection() != Direction.right) {
				game.setDirection(Direction.left);
			}
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (game.getDirection() != Direction.up) {
				game.setDirection(Direction.down);
			}
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (game.getDirection() != Direction.down) {
				game.setDirection(Direction.up);
			}
		}
	}
}
