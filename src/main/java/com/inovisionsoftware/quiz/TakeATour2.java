package com.inovisionsoftware.quiz;


public class TakeATour2 {
	
	private int NO_DIRECTION = -1;
	private int DIRECTION_UP = 0;
	private int DIRECTION_RIGHT = 1;
	private int DIRECTION_DOWN = 2;
	private int DIRECTION_LEFT = 4;
	
	private boolean[][] visitedPoints;
	private int maxX;
	private int maxY;
	private int maxPoints;
	private int result;
	private Point currPoint;
	private Point nextPoint;
	private Point endPoint;
	private Point[] stack;
	private int stackPointer=-1;
	
	public TakeATour2(int x, int y) {
		this.maxX = x;
		this.maxY = y;
		maxPoints = x*y;
		currPoint = new Point(0,0);
		visitedPoints = new boolean[x][y];
		stack = new Point[maxPoints];
	}
	
	public int compute() {
		push(currPoint);
		visitedPoints[0][0] = true;
		do {
			nextPoint = null;
			if(currPoint.lastDirection == NO_DIRECTION) {
				nextPoint = stepUp();
			} else if(currPoint.lastDirection == DIRECTION_UP) {
				nextPoint = stepRight();
			} else if(currPoint.lastDirection == DIRECTION_RIGHT) {
				nextPoint = stepDown();
			} else if(currPoint.lastDirection == DIRECTION_DOWN) {
				nextPoint = stepLeft();
			} else {
				stepBack();
				nextPoint = null;
			}
			//if we have already visited the point it cannot be next point
			if((nextPoint != null) && (visitedPoints[nextPoint.x][ nextPoint.y])) {
				nextPoint = null;
			}
			
			if(nextPoint != null) {
				currPoint = nextPoint;
				visitedPoints[nextPoint.x][ nextPoint.y] = true;
				push(nextPoint);
				//did we reached the end
				if((nextPoint.x == 0) && (nextPoint.y == maxY-1) ) {
					//if we covered all points, we have valid route 
					if(stackPointer == maxPoints-1)
						result++;
					//time to go back
					stepBack();
				}
			}
			
		} while(currPoint != null);
		return result;
	}
	
	private void stepBack() {
		Point p = pop();
		//mark the point we popped as not visited
		if(p != null)
			visitedPoints[p.x][p.y] = false;
		//set the current point to the top of the stack
		currPoint = peek();
	}
	
	private Point stepUp() {
		currPoint.lastDirection = DIRECTION_UP;
		int pointx = currPoint.x;
		int pointy = currPoint.y;
		//OPTIMIZE - if we are at left or right edge we always want to go down
		if((pointx == 0) || (pointx == maxX-1)) 
			return null;
		//if we are already at top edge
		if(pointy == 0) {
			return null;
		} else {
			return new Point(pointx, pointy-1);
		}
	}
	
	private Point stepRight() {
		currPoint.lastDirection = DIRECTION_RIGHT;
		int pointx = currPoint.x;
		int pointy = currPoint.y;
		//OPTIMIZE - if we are the bottom edge we always want to go left
		if(pointy == maxY-1)
			return null;

		//if we are already at the extreme right
		if(pointx == maxX-1) {
			return null;
		} else {
			//OPTIMIZE - if we are one point away from top, then the topmost point must be covered
			if((pointy == 1) && !visitedPoints[pointx][0])
				return null;
			//OPTIMIZE - if we are at the top edge and going right, all the x points on the left should be covered
			if(pointy == 0) {
				for(int i=pointx; i >=0; i--){
					if(!visitedPoints[i][pointy])
						return null;
				}
			}
			return new Point(pointx + 1, pointy);
		}
	}
	
	private Point stepDown() {
		currPoint.lastDirection = DIRECTION_DOWN;
		int pointx = currPoint.x;
		int pointy = currPoint.y;
		if(pointy == maxY-1) {
			//if we are already at the bottom
			return null;
		} else {
			//OPTIMIZE - if we are one point away from edge and going down, then the edge point must be covered
			if((pointx == maxX-2) && !visitedPoints[maxX-1][pointy])
				return null;
			if((pointx == 1) && !visitedPoints[0][pointy])
				return null;
			//OPTIMIZE - if we are either edge and going down, all points above (on edge must be covered)
			if((pointx == 0) || (pointx == maxX-1)) {
				for(int i=pointy; i >=0; i--) {
					if(!visitedPoints[pointx][i])
						return null;
				}
			}
			return new Point(pointx, pointy+1);
		}
	}
	
	private Point stepLeft() {
		currPoint.lastDirection = DIRECTION_LEFT;
		int pointx = currPoint.x;
		int pointy = currPoint.y;
		//OPTIMIZE - if we are at top edge we always want to go right
		if(pointy == 0)
			return null;
		if(pointx == 0) {
			return null;
		} else {
			//OPTIMIZE - if we are one point away from bottom, then bottom-most point must be covered
			if((pointy == maxY-2) && !visitedPoints[pointx][maxY-1])
				return null;
			//OPTIMIZE - if we are at the bottom edge and going left, all the x points on the right should be covered
			if(pointy == maxY-1) {
				for(int i=pointx; i < maxX; i++) {
					if(!visitedPoints[i][pointy])
						return null;
				}
			}
			return new Point(pointx-1, pointy);
		}
	}
	
	private void push(Point p) {
		stack[++stackPointer] = p;
	}
	
	private Point pop() {
		Point p = null;
		if(stackPointer >= 0) {
			p = stack[stackPointer];
			stack[stackPointer] = null;
			stackPointer--;
		}
		return p;
	}
	
	private Point peek() {
		if(stackPointer >= 0) {
			return stack[stackPointer];
		} else {
			return null;
		}
	}
	
	private class Point {
		int x;
		int y;
		int lastDirection = NO_DIRECTION;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String toString() {
			return "x=" + x + ", y=" +y;
		}
	}
	
	public static void main(String args[]) {
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
		TakeATour2 t = new TakeATour2(x,y);
		long start = System.currentTimeMillis();
		System.out.println(t.compute());
		long end = System.currentTimeMillis();
		System.out.println("time : " + (end-start));
	}

}
