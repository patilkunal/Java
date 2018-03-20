package com.inovisionsoftware.quiz;
import java.util.ArrayList;
import java.util.List;

/**
 * TakeATour class organizes various routes on given grid to start from (0,0) and end on (0, maxy)
 * It orchestras and advances each route and keeps track of completed routes.
 * 
 * @author Kunal
 *
 */
public class TakeATour {
	
	private int maxx;
	private int maxy;
	public static int routeCount = 1;
	int completedroutes = 0;
	List<Route> routes = null;
	
	public TakeATour(int x, int y) {
		maxx = x;
		maxy = y;
		routes = new ArrayList<Route>();
	}
	
	/**
	 * This is the main routine of the class. It creates first initial route to start from (0,0). 
	 * As the first route advances, it create additional routes and which in turn create more to find out
	 * how many possible routes from the start point to end point. 
	 * 
	 * @return integer count of possible routes for given grid
	 */
	public int compute() {
		//create first initial route with point (0,0) as start point
		Route route = new Route(maxx, maxy,null);
		route.addPoint(new Point(0, 0));
		routes.add(route);
		List<Route> newlist = new ArrayList<Route>();
		List<Route> finishlist = new ArrayList<Route>();
		do { //loop over the routes until we have exhausted the main route list 
			for(Route rt : routes) {
				if(!rt.isDone()) {
					//advance the route to next point
					Route[] newroutes = rt.advance();
					//if we get new routes, add them to the new route list for now
					if(newroutes != null) {
						for(Route nr : newroutes) {
							if(nr != null) newlist.add(nr);				
						}
					}
				}
				//if route is done, add it to the finish list
				if(rt.isDone()) {
					finishlist.add(rt);
				}
			}
			//go over the routes which has been marked as done
			for(Route frt : finishlist) {
				if(frt.hasReachedEnd() && frt.visitedAllPoints()) {
					//increment counter if the route has reached the end point and 
					//also visited all the points
					completedroutes++;
				}
				//for memory management remove the route from the main list
				routes.remove(frt);
			}
			//empty the list
			finishlist.clear();
			//add the newly found routes to the main list
			routes.addAll(newlist);
			//clear the new route list 
			newlist.clear();
		} while(routes.size() > 0);
		
		return completedroutes;
	}
	
	public int getCompletedRoutes() {
		return completedroutes;
	}
	

	/**
	 * Main entry point for this program. It expects width and height of the grid as command line
	 * parameters
	 * @param args - command line parameters
	 */
	public static void main(String[] args) {
		if(args.length >= 2) {
			int x = Integer.parseInt(args[0]);
			int y = Integer.parseInt(args[1]);
			TakeATour tour = null;
			tour = new TakeATour(x, y);
			System.out.println(tour.compute());
		} else {
			//We can just output zero instead
			System.out.println("Hey! I need the width and height of the grid " + args[0]);
		}
	}
	
	/**
	 * Route class represents the individual route on the grid.
	 * 
	 * @author Kunal
	 *
	 */
	private class Route {
		private boolean[][] points;
		private int count;
		private int maxpoints;
		private int gridx;
		private int gridy;
		private boolean done;
		private boolean endReached;
		private int routeNumber;
		private Point lastPoint;
				
		public Route(int gridx, int gridy, boolean[][] pts) {
			//increment the route counter and assign a unique value to identify this route
			this.routeNumber = routeCount++;
			//copy and calculate few parameters
			this.gridx = gridx;
			this.gridy = gridy;
			this.maxpoints = gridx * gridy;
			//two dimensional boolean array representing the grid
			points = new boolean[gridx][gridy];
			count=0; //points counter
			initialize(pts);
		}
		
		/**
		 * Initialize the array of points that this route would visit.
		 * For a spawned route, pts would contain the points earlier route has visited 
		 * @param pts - boolean array to copy as already visited points
		 */
		private void initialize(boolean[][] pts) {
			for(int i=0; i < gridy; i++) {
				for(int j=0; j < gridx; j++) {
					points[j][i] = false;
				}
			}
			//copy the visited points
			if(pts != null) {
				for(int i=0; i < gridy; i++) {
					for(int j=0; j < gridx; j++) {						
						points[j][i] = pts[j][i];
						if(pts[j][i]) count++; //increment points counter for visited points
					}
				}
			}
		}
		
		/**
		 * Advances the route to the next possible point. This function also spawns additional routes
		 * if it finds it has more than one way to go about. 
		 * 
		 * @return Array of additional routes. Null if no additional routes are possible.
		 */
		public Route[] advance() {
			Route[] newroutes = null;
			//if we are done do not advance the route
			if(!done) {
				//get next possible points for this route
				Point pts[] = getNextPoints();
				//if we have no more points to go, this route is done.
				if(pts[0] == null)  {
					done = true;
					//we are precalculating the flag so that we can release the points array
					checkEndReached();
					//and clear memory
					points = null;
					return null;
				}
				//With the points, other than the first one, if any, we spawn more routes
				if(pts.length > 1) {
					newroutes = new Route[pts.length - 1];
					for(int i=1; i < pts.length; i++) {
						if(pts[i] != null) {
							//initialize a new route and set all the points this route has visited
							newroutes[i-1] = new Route(gridx, gridy, getPoints());
							//add the new point as one more point visited.
							newroutes[i-1].addPoint(pts[i]);
						}
					}
				}
				//for our route we pick the first point returned.
				addPoint(pts[0]);
			}
			return newroutes;
		}
		
		/**
		 * Checks that all the X axis points on the left side of the given point have been 
		 * visited.   
		 * @param Point p
		 */
		private void checkAllLeftXVisited(Point p) {
			int startx = p.x;
			//if it is one of other than first two points
			if(startx > 1) {
				//check that we have visited all the points on left side
				for(int i = startx; i >= 0; i--) {
					//if we find even one then we are done
					if(!points[i][p.y])
						done = true; 
				}
			}
		}

		/**
		 * Checks that all the X axis points on the right side of the given point have been 
		 * visited.   
		 * @param Point p
		 */
		private void checkAllRightXVisited(Point p) {
			int startx = p.x;
			for(int i = startx; i < gridx-1; i++) {
				//if we find even one then we are done
				if(!points[i][p.y])
					done = true; 
			}
		}
		
		/**
		 * Checks that all the Y axis points above the given point have been visited
		 * @param Point p
		 */
		private void checkAllTopYVisited(Point p) {
			int ycount = p.y;
			//if it is one of other than first two points
			if(ycount > 1) {
				for(int i=ycount; i >=0; i--) {
					if(!points[p.x][i])
						done =true;
				}
			}
		}
		
		/**
		 * Returns the two dimensional boolean array which represents the points this route visited
		 * @return two dimensional boolean array
		 */
		public boolean[][] getPoints() {
			return points;
		}
		
		/**
		 * Add a new visited point to this route
		 * Additionally this routine also calculates other facts like whether the route is complete and
		 * has reached the destination or if route has taken a path which would make this route never 
		 * reach the destination and hence helps to prevent any future calculations.
		 * 
		 * @param Point p to add to this route
		 */
		public void addPoint(Point p) {
			//save the last point for next advance of the route
			lastPoint = p;
			if(!hasVisited(p)) {
				points[p.x][p.y] = true;
			}
			count++; //increment the points visited
			//if we have covered all the points, then we are done
			if(count >= maxpoints) done=true;
			//check the point just added is the last point and flag that we have reached the end point
			checkEndReached();
			
			//check for possibility that the route would never finish
			//and mark that we are done before even trying further calculations
			if(!done) {
				if((p.y == 0)) {
					checkAllLeftXVisited(p);
				}
				if(p.y == gridy -1) {
					checkAllRightXVisited(p);
				}
				if((p.x == 0) || (p.x == gridx-1)) {
					checkAllTopYVisited(p);
				}
			}
		}
		
		/**
		 * This returns next possible points which are not visited by this route
		 * 
		 * @return array of Point object. Returns array with null objects if none are possible
		 */
		private Point[] getNextPoints() {
			int startx = lastPoint.x;
			int starty = lastPoint.y;
			//there would be max four points that we could go about
			Point[] nextpoints = new Point[4];
			Point calculatedpoint = null;
			int pointcount=0;
			//increment/decrement x/y to figure out next possible route
			if((startx + 1) < gridx) {
				calculatedpoint = new Point((startx+1), starty);
				if(!hasVisited(calculatedpoint))
					nextpoints[pointcount++] = calculatedpoint;
			}
			if((starty + 1) < gridy) {
				calculatedpoint = new Point(startx, (starty+1));
				if(!hasVisited(calculatedpoint))
					nextpoints[pointcount++] = calculatedpoint;
			}
			if((startx -1) >= 0) {
				calculatedpoint = new Point((startx-1), starty);
				if(!hasVisited(calculatedpoint))
					nextpoints[pointcount++] = calculatedpoint;
			}
			if((starty -1) >= 0) {
				calculatedpoint = new Point(startx, (starty-1));
				if(!hasVisited(calculatedpoint))
					nextpoints[pointcount++] = calculatedpoint;
			}
			return nextpoints;
		}
		
		/**
		 * Checks if we have covered the last point. Flags that route is done if true.
		 */
		private void checkEndReached() {
			//get the last point we have and see if it's co-ordinate is the end point
			endReached = points[0][gridy-1];
			if(endReached) done = true;			
		}

		/**
		 * Returns true if we have reached the destination
		 * @return true or false
		 */
		public boolean hasReachedEnd() {
			return endReached;
		}
		
		/**
		 * Returns true if we have visited all the possible points
		 * @return true or false
		 */
		public boolean visitedAllPoints() {
			//easy way of doing it, hard way we can iterate over the points array
			return (count == maxpoints);
		}
		
		/**
		 * Returns true if route is finished his route and can advance no more
		 * 
		 * @return
		 */
		public boolean isDone() {
			return done;
		}
		
		/**
		 * Checks and returns if route has already visited the point denoted by x and y value of Point object
		 * @param Point p
		 * @return true or false
		 */
		private boolean hasVisited(Point p) {
			return points[p.x][p.y];
		}
		
		/**
		 * Java object equality function
		 */
		@Override
		public boolean equals(Object obj) {
			if(obj == null) return false;
			Route other = (Route)obj;
			return this.routeNumber == other.routeNumber;
		}
		
		@Override
		public int hashCode() {
			return new Long(routeNumber).hashCode();
		}
	}
	
	/**
	 * Point class to conveniently pass x and y co-ordinates around
	 * 
	 * @author Kunal
	 *
	 */
	private static class Point {
		public int x;
		public int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			if(obj == null) return false;
			Point other = (Point) obj;			
			return (this.x == other.x) && (this.y == other.y);
		}
	}
	

}
