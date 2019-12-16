

public class SnakeNode {
	private int[] coordinates;
	private SnakeNode next;
	
	public SnakeNode(int x, int y) {
		this.coordinates = new int[2];
		
		this.coordinates[0] = x;
		this.coordinates[1] = y;
	}
	
	public int[] getCoordinates() {
		return this.coordinates;
	}
	
	public int getX() {
		return this.coordinates[0];
	}
	
	public int getY() {
		return this.coordinates[1];
	}
	
	public SnakeNode getNext() {
		return this.next;
	}
	
	public void setNext(SnakeNode newNext) {
		this.next = newNext;
	}
	
	public void setCoordinates(int[] coordinates) {
		this.coordinates = coordinates;
	}
}
