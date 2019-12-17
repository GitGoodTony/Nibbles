import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private NibblesGame game;
	private Timer timer;
	private int highScore;
	
	private final int BLOCK_WIDTH, SNAKE_WIDTH;
	private final int SPEED;
	private final Color BODY_COLOR;
	
	public GamePanel(NibblesGame game, int width, int height) {
		this.game = game;
		this.highScore = 0;
		this.BLOCK_WIDTH = height / 150;
		this.SNAKE_WIDTH = BLOCK_WIDTH * 3;
		this.SPEED = 30;
		
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
		
		if (game.getScore() > this.highScore) { this.highScore = game.getScore(); }
		
		// Change screen title
		if (!game.playerAlive()) {
			timer.stop();
			((JFrame) SwingUtilities.getWindowAncestor(this)).setTitle("FINAL SCORE: " + game.getScore() + " | HIGH SCORE: " + this.highScore + " | PRESS ANY KEY TO RESTART!");
		}
		
		else {
			((JFrame) SwingUtilities.getWindowAncestor(this)).setTitle("CURRENT SCORE: " + game.getScore() + " | HIGH SCORE: " + this.highScore);
		}
		
		// Draw the grid
		int[] appleCoords = this.game.getAppleCoordinates();
		Color color = game.getPlayer().getFront().getX() == appleCoords[0] || game.getPlayer().getFront().getY() == appleCoords[1] ? Color.GREEN : Color.PINK;
		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 50; y++) {
				if (x == appleCoords[0] / 3 || y == appleCoords[1] / 3) { 
					g.setColor(color); 
					g.fillRect(x * SNAKE_WIDTH,  y * SNAKE_WIDTH, SNAKE_WIDTH, SNAKE_WIDTH);
				}
				else { 
					g.setColor(Color.BLACK); 
					g.drawRect(x * SNAKE_WIDTH, y * SNAKE_WIDTH, SNAKE_WIDTH, SNAKE_WIDTH);
				}
			}
		}
		
		// Draw the snake's body
		SnakeNode temp = game.getPlayer().getBack();
		g.setColor(BODY_COLOR);
		while (temp.getNext() != null) {
			g.fillRect(temp.getX() * BLOCK_WIDTH, temp.getY() * BLOCK_WIDTH, SNAKE_WIDTH, SNAKE_WIDTH);
			
			temp = temp.getNext();
		}
		
		// Draw the snake's head
		try {
			g.drawImage(ImageIO.read(new File("" + game.getDirection() + ".png")).getScaledInstance(SNAKE_WIDTH, SNAKE_WIDTH, Image.SCALE_SMOOTH) ,game.getPlayer().getFront().getX() * BLOCK_WIDTH, game.getPlayer().getFront().getY() * BLOCK_WIDTH, this);
		} catch (IOException e) {}
		
		// Draw the apple
		g.setColor(Color.RED);
		g.fillRect(appleCoords[0] * BLOCK_WIDTH, appleCoords[1] * BLOCK_WIDTH, SNAKE_WIDTH, SNAKE_WIDTH);
		
		this.repaint();
	}
	
	public void changeDirection(KeyEvent e) {
		if (!game.playerAlive()) {
			// Create a new game
			this.game = new NibblesGame(this.SNAKE_WIDTH);
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
		
		// Change snake direction
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
