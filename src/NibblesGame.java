import java.util.Arrays;

public class NibblesGame {
	private int[] appleCoordinates;
	private int score;
	private Snake player;
	private Direction playerDirection, playerTempDirection;
	
	private boolean isAlive;
	
	public NibblesGame(int dist) {
		this.player = new Snake((int) (Math.random() * 150 + 10), 100, dist);
		this.appleCoordinates = new int[2];
		this.appleCoordinates[0] = 30;
		this.appleCoordinates[1] = 30;
		
		this.playerDirection = Direction.up;
		this.playerTempDirection = this.playerDirection;
		
		this.isAlive = true;
	}
	
	public void move() {
		this.player.moveSnake(playerDirection);
	}
	
	public void setDirection() {
		this.playerDirection = this.playerTempDirection;
		this.resetSnake();
	}
	
	public void setTempDirection(Direction newDirection) {
		this.playerTempDirection = newDirection;
	}
	
	public void checkAppleCollision() {
		SnakeNode front = player.getFront();
		if (Arrays.equals(appleCoordinates, front.getCoordinates())) {
			player.extendSnake();
			player.extendSnake();
			player.extendSnake();
			this.updateApple();
			
			score += 1;
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
	
	public boolean playerAlive() {
		return this.isAlive;
	}
	
	public Direction getDirection() {
		return this.playerDirection;
	}
	
	public Snake getPlayer() {
		return this.player;
	}
	
	public int[] getAppleCoordinates() {
		return this.appleCoordinates;
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
	
	public int getScore() {
		return this.score;
	}
	private boolean validPoints(int[] coords) {
		return coords[0] % 3 == 0 && coords[1] % 3 == 0;
	}
	
	private boolean spotNotTaken(int[] coords) {
		SnakeNode temp = player.getBack();
		while (temp != null) {
			if (Arrays.equals(temp.getCoordinates(), coords)) {
				return false;
			}
			
			temp = temp.getNext();
		}
		
		return true;
	}
	
	private void resetSnake() {
		SnakeNode temp = this.player.getBack();
		
		if ((this.playerDirection == Direction.left || this.playerDirection == Direction.right) && this.player.getFront().getY() % 3 != 0) {
			int yAdjust = this.player.getFront().getY() % 3 == 1 ? 2 : 1;
			while (temp != null) {
				temp.setY(temp.getY() + yAdjust);
				
				temp = temp.getNext();
			}
		}
		
		else if ((this.playerDirection == Direction.up || this.playerDirection == Direction.down) && this.player.getFront().getX() % 3 != 0){
			int xAdjust = this.player.getFront().getX() % 3 == 1 ? 2 : 1;
			while (temp != null) {
				temp.setX(temp.getX() + xAdjust);
				
				temp = temp.getNext();
			}
		}
	}
}
