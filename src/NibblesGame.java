import java.util.Arrays;

public class NibblesGame {
	private int[] appleCoordinates;
	private Snake player;
	private Direction playerDirection;
	
	private boolean isAlive;
	
	public NibblesGame() {
		this.player = new Snake((int) (Math.random() * 30 + 10), (int) (Math.random() * 30 + 10));
		this.appleCoordinates = new int[2];
		this.appleCoordinates[0] = 10;
		this.appleCoordinates[1] = 10;
		
		this.playerDirection = Direction.up;
		
		this.isAlive = true;
	}
	
	public int[] move() {
		return this.player.moveSnake(playerDirection);
	}
	
	public void setDirection(Direction newDirection) {
		this.playerDirection = newDirection;
	}
	
	public void checkCollision(int x, int y) {
		SnakeNode front = player.getFront();
		System.out.println(Arrays.toString(appleCoordinates) + Arrays.toString(front.getCoordinates()));
		if (Arrays.equals(appleCoordinates, front.getCoordinates())) {
			player.extendSnake(x, y);
			this.updateApple();
		}
		
		if (front.getX() < 0 || front.getX() > 50 || front.getY() < 0 || front.getY() > 50) {
			this.isAlive = false;
			return;
		}
		
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
		int[] newCoords = {(int) (Math.random() * 50), (int) (Math.random() * 50)};
		while (!spotNotTaken(newCoords)) {
			newCoords[0] = (int) (Math.random() * 50);
			newCoords[1] = (int) (Math.random() * 50);
		}
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
}
