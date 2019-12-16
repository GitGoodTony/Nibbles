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
	private final int SPEED;
	private final Color HEAD_COLOR, BODY_COLOR;
	
	public GamePanel(NibblesGame game, int width, int height) {
		this.game = game;
		this.BLOCK_WIDTH = height / 150;
		this.SPEED = 30;
		
		this.HEAD_COLOR = new Color((int) (Math.random() * 200), (int) (Math.random() * 200), (int) (Math.random() * 200));
		this.BODY_COLOR = new Color((int) (Math.random() * 200), (int) (Math.random() * 200), (int) (Math.random() * 200));
		
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.setDirection();
				game.move();
				game.checkAppleCollision();
				game.checkCollision(game.getPlayer().getFront().getX(), game.getPlayer().getFront().getY());
			}
		};
		
		this.timer = new Timer(1000 / SPEED, action);
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
		
		g.setColor(Color.BLACK);
		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 48; y++) {
				g.drawRect(BLOCK_WIDTH * x * 3,  BLOCK_WIDTH * y * 3, BLOCK_WIDTH * 3, BLOCK_WIDTH * 3);
			}
		}
		
		SnakeNode temp = game.getPlayer().getBack();
		g.setColor(BODY_COLOR);
		while (temp.getNext() != null) {
			g.fillRect(temp.getX() * BLOCK_WIDTH, temp.getY() * BLOCK_WIDTH, BLOCK_WIDTH * 3, BLOCK_WIDTH * 3);
			
			temp = temp.getNext();
		}
		
		g.setColor(HEAD_COLOR);
		g.fillRect(game.getPlayer().getFront().getX() * BLOCK_WIDTH, game.getPlayer().getFront().getY() * BLOCK_WIDTH, BLOCK_WIDTH * 3, BLOCK_WIDTH * 3);
		
		int[] appleCoords = this.game.getAppleCoordinates();
		g.setColor(Color.RED);
		g.fillRect(appleCoords[0] * BLOCK_WIDTH, appleCoords[1] * BLOCK_WIDTH, BLOCK_WIDTH * 3, BLOCK_WIDTH * 3);
		
		this.repaint();
	}
	
	public void changeDirection(KeyEvent e) {
		if (!game.playerAlive()) {
			this.game = new NibblesGame(this.BLOCK_WIDTH * 3);
			ActionListener action = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					game.setDirection();
					game.move();
					game.checkAppleCollision();
					game.checkCollision(game.getPlayer().getFront().getX(), game.getPlayer().getFront().getY());
				}
			};
			
			this.timer = new Timer(1000 / SPEED, action);
			this.timer.setRepeats(true);
			this.timer.setInitialDelay(0);
			this.timer.restart();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (game.getDirection() != Direction.left) {
				game.setTempDirection(Direction.right);
			}
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (game.getDirection() != Direction.right) {
				game.setTempDirection(Direction.left);
			}
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (game.getDirection() != Direction.up) {
				game.setTempDirection(Direction.down);
			}
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (game.getDirection() != Direction.down) {
				game.setTempDirection(Direction.up);
			}
		}
	}
}
