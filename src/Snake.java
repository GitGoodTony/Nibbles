public class Snake {
	private SnakeNode front, back;
	private final int NODE_DISTANCE;
	
	public Snake(int x, int y, int distance) {
		this.front = new SnakeNode(x, y);
		this.NODE_DISTANCE = 16;
		this.back = new SnakeNode(x - distance, y);
		
		this.back.setNext(front);
	}
	
	public void moveSnake(Direction direction) {
		SnakeNode newFront = null;
		
		// Create the new front of the snake
		switch (direction) {
		    case left:  newFront = new SnakeNode(front.getX() - 1, front.getY()); break;
		    case right: newFront = new SnakeNode(front.getX() + 1, front.getY()); break;
		    case up:    newFront = new SnakeNode(front.getX(), front.getY() - 1); break;
		    case down:  newFront = new SnakeNode(front.getX(), front.getY() + 1); break;
		}
			
		// "Move" the snake
		this.front.setNext(newFront);
		this.front = newFront;
		this.back = back.getNext();
	}
	
	public SnakeNode getBack() {
		return this.back;
	}
	
	public SnakeNode getFront() {
		return this.front;
	}
	
	public void extendSnake() {
		// Make a node at the old end of the snake
		int xDiff = (back.getX() - back.getNext().getX()) * NODE_DISTANCE;
		int yDiff = (back.getY() - back.getNext().getY()) * NODE_DISTANCE;
		System.out.println(xDiff + " " + yDiff);
		SnakeNode newBack = new SnakeNode(back.getX() + xDiff, back.getY() + yDiff);

		newBack.setNext(back);
		
		this.back = newBack;
	}
}
