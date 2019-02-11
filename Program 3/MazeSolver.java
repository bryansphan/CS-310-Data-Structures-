/**
 * Program #3 
 * Bryan Phan
 * cssc0931 
 */

import data_structures.*;
public class MazeSolver {
	
	private int dimension;
	private MazeGrid GUI;
	private Stack<GridCell> stack; Queue<GridCell> queue;
	
	public MazeSolver(int dimension) {
		this.dimension = dimension;
		GUI = new MazeGrid(this, dimension);
		stack = new Stack<GridCell>();
		queue = new Queue<GridCell>();
	}
	
	//breath first algorithm & enqueue/mark the first cell 
	public void mark() {
		GridCell tmp = GUI.getCell(0,0);
		tmp.setDistance(0);
		queue.enqueue(tmp);
		GUI.markDistance(tmp);
		//mark & enqueue the adjacent cells 
		while (!queue.isEmpty()) {
			GridCell C = queue.dequeue();
			int distance = C.getDistance();
			int x = C.getX();
			int y = C.getY();
			
			GridCell east = GUI.getCell(x+1,y);
			if (GUI.isValidMove(east) && !east.wasVisited()) {
				east.setDistance(distance + 1); //checks neighboring cells
				GUI.markDistance(east);
				queue.enqueue(east);
			}
			GridCell south = GUI.getCell(x,y+1); 
			if (GUI.isValidMove(south) && !south.wasVisited()) {
				south.setDistance(distance + 1); //checks neighboring cells
				GUI.markDistance(south);
				queue.enqueue(south);
			}
			GridCell west = GUI.getCell(x-1,y); 
			if (GUI.isValidMove(west) && !west.wasVisited()) {
				west.setDistance(distance + 1); //checks neighboring cells
				GUI.markDistance(west);
				queue.enqueue(west);
			}
			GridCell north = GUI.getCell(x,y-1); 
			if (GUI.isValidMove(north) && !north.wasVisited()) {
				north.setDistance(distance + 1); //checks neighboring cells
				GUI.markDistance(north);
				queue.enqueue(north);
			}
		}
	}
	
	public boolean move() {
		GridCell terminalCell = GUI.getCell(dimension-1, dimension -1);
		//unreachable, puzzle has no solution 
		if (terminalCell.getDistance() == -1) return false; 
	
		stack.push(terminalCell); //push terminal cell onto the stack 
		//stack.peek looks at what is at the top of the stack 
		while (stack.peek().getDistance() != 0) {
			GridCell C = stack.peek();
			int distance = C.getDistance(); //check this 
			int x = C.getX();
			int y = C.getY();
			//neighboring cells 
			GridCell east = GUI.getCell(x+1,y); 
			GridCell south = GUI.getCell(x,y+1); 
			GridCell west = GUI.getCell(x-1,y); 
			GridCell north = GUI.getCell(x,y-1); 
			
			if (GUI.isValidMove(east) && east.getDistance() < distance) {
				stack.push(east);
			}
			else if (GUI.isValidMove(south) && south.getDistance() < distance) {
				stack.push(south);
			}
			else if (GUI.isValidMove(west) && west.getDistance() < distance) {
				stack.push(west);
			}
			else if (GUI.isValidMove(north) && north.getDistance() < distance) {
				stack.push(north);
			}
		}
		//pop grid cell off the stack and mark it 
		while (!stack.isEmpty()) {
			GUI.markMove(stack.pop());
		}
		return true;	
	}
	
	public void reset() {
		queue.makeEmpty();
		stack.makeEmpty();
	}
	
	public static void main(String[] args) {
		new MazeSolver(40);
	}
}
