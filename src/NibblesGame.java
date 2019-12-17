import java.util.Arrays;

public class NibblesGame {
	private int[] appleCoordinates;
	private int score;
	private Snake player;
	private Direction playerDirection, playerTempDirection;
	
	private final int SPEED;
	
	private boolean isAlive;
	
	public NibblesGame(int dist) {
		this.player = new Snake((int) (Math.random() * 150 + 10), 100, dist);
		this.appleCoordinates = new int[2];
		this.appleCoordinates[0] = 30;
		this.appleCoordinates[1] = 30;
		this.SPEED = 30;
		
		this.playerDirection = Direction.up;
		this.playerTempDirection = this.playerDirection;
		
		this.isAlive = true;
	}
	
	
	// Getter methods
	public int getSpeed()              { return this.SPEED; }
	public boolean playerAlive()       { return this.isAlive; }
	public Direction getDirection()    { return this.playerDirection; }
	public Snake getPlayer()           { return this.player; }
	public int[] getAppleCoordinates() { return this.appleCoordinates; }
	public int getScore()              { return this.score; }
	// END OF GETTER METHODS
	
	public void move() {
		this.player.moveSnake(playerDirection);
	}
	
	public void setDirection() {
		// Finalize the snake's new direction
		Direction prevDirection = this.playerDirection;
		this.playerDirection = this.playerTempDirection;
		this.resetSnake(prevDirection);
	}
	
	public void setTempDirection(Direction newDirection) {
		this.playerTempDirection = newDirection;
	}
	
	public void checkAppleCollision() {
		for (int xIncrease = 0; xIncrease <= 2; xIncrease++) {
			for (int yIncrease = 0; yIncrease <= 2; yIncrease++) {
				SnakeNode temp = this.player.getBack();
				
				// Check the entire red box representing the apple to each of the snake's body nodes
				while (temp != null) {
					int[] tempCoordinates = {appleCoordinates[0] + xIncrease, appleCoordinates[1] + yIncrease};
					
					if (Arrays.equals(tempCoordinates, temp.getCoordinates())) {
						player.extendSnake();
						player.extendSnake();
						player.extendSnake();
						this.updateApple();
						
						score += 1;
					}
					
					temp = temp.getNext();
				}
			}
		}
	}
	
	public void checkCollision(int x, int y) {
		SnakeNode front = player.getFront();
		
		// Check if snake is out of bounds
		if (front.getX() < 0 || front.getX() > 147 || front.getY() < 0 || front.getY() > 147) {
			this.isAlive = false;
			return;
		}
		
		// Check if snake has hit itself
		SnakeNode temp = player.getBack();
		while (temp.getNext() != null) {
			if (Arrays.equals(temp.getCoordinates(), front.getCoordinates())) {
				this.isAlive = false;
			}
			
			temp = temp.getNext();
		}
	}
	
	public void updateApple() {
		// Create new apple coordinates
		int[] newCoords = {(int) (Math.random() * 147), (int) (Math.random() * 147)};
		while (!validPoints(newCoords) || !spotNotTaken(newCoords)) {
			newCoords[0] = (int) (Math.random() * 147);
			newCoords[1] = (int) (Math.random() * 147);
		}

		this.appleCoordinates = newCoords;
	}

	private boolean validPoints(int[] coords) {
		// Ensure coordinates are in the grid
		return coords[0] % 3 == 0 && coords[1] % 3 == 0;
	}
	
	private boolean spotNotTaken(int[] coords) {
		// Ensure the coordinates do not overlap the player
		SnakeNode temp = player.getBack();
		while (temp != null) {
			if (Arrays.equals(temp.getCoordinates(), coords)) {
				return false;
			}
			
			temp = temp.getNext();
		}
		
		return true;
	}
	
	private void resetSnake(Direction prevDirection) {
		SnakeNode temp = this.player.getBack();
		
		// If the snake is not in between the lines, set it back to the lines
		if (this.playerDirection == Direction.left || this.playerDirection == Direction.right) {
			if (prevDirection == Direction.up) {
				while (temp != null) {
					if (temp.getY() % 3 != 0) {
						temp.setY(temp.getY() + (temp.getY() % 3 == 1 ? -1 : -2));
					}
					temp = temp.getNext();
				}
			}
			else {
				while (temp != null) {
					if (temp.getY() % 3 != 0) {
						temp.setY(temp.getY() + (temp.getY() % 3 == 1 ? 2 : 1));
					}
					temp = temp.getNext();
				}
			}
		}
		
		else if (this.playerDirection == Direction.up || this.playerDirection == Direction.down) {
			if (prevDirection == Direction.left) {
				while (temp != null) {
					if (temp.getX() % 3 != 0) {
						temp.setX(temp.getX() + (temp.getX() % 3 == 1 ? -1 : -2));
					}
					temp = temp.getNext();
				}
			}
			else {
				while (temp != null) {
					if (temp.getX() % 3 != 0) {
						temp.setX(temp.getX() + (temp.getX() % 3 == 1 ? 2 : 1));
					}
					temp = temp.getNext();
				}
			}
		}
	}
}
