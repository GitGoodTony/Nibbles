public class Snake {
	private SnakeNode front, back;
	
	public Snake(int x, int y) {
		this.front = new SnakeNode(x, y);
		this.back = front;
	}
	
	public int[] moveSnake(Direction direction) {
		SnakeNode newFront = null;
		
		switch (direction) {
		    case left:  newFront = new SnakeNode(front.getX() - 1, front.getY()); break;
		    case right: newFront = new SnakeNode(front.getX() + 1, front.getY()); break;
		    case up:    newFront = new SnakeNode(front.getX(), front.getY() - 1); break;
		    case down:  newFront = new SnakeNode(front.getX(), front.getY() + 1); break;
		}
		
		if (back == front) {
			this.front.setNext(newFront);
			this.front = newFront;
			
			return back.getCoordinates();
		}
		
		else {
			int[] backCoordinates = back.getCoordinates();
			
			SnakeNode temp = back;
			while (temp.getNext() != null) {
				temp.setCoordinates(temp.getNext().getCoordinates());
				temp = temp.getNext();
			}
			
			this.front.setNext(newFront);
			this.front = newFront;
			this.back = back.getNext();
			
			return backCoordinates;
		}
	}
	
	public SnakeNode getBack() {
		return this.back;
	}
	
	public SnakeNode getFront() {
		return this.front;
	}
	
	public void extendSnake(int x, int y) {
		SnakeNode newBack = new SnakeNode(x, y);
		newBack.setNext(back);
		
		this.back = newBack;
	}
}
