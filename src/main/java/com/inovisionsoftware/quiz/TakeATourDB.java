package com.inovisionsoftware.quiz;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;


public class TakeATourDB {
	
	private int maxx;
	private int maxy;
	public static int routeCount = 1;
	private Connection connection;
	private PreparedStatement saveroutest;
	private PreparedStatement savepointst;
	private PreparedStatement getroutest;
	private PreparedStatement getpointst;
	private PreparedStatement deleteroutest;
	private PreparedStatement deletepointst;
	int completedroutes = 0;
	
	public TakeATourDB(int x, int y) throws SQLException {
		maxx = x;
		maxy = y;
		initialize();
	}
	
	protected void initialize() throws SQLException {
		connection = DriverManager.getConnection("jdbc:hsqldb:file:tours.db;create=true;shutdown=true", "sa", "");
		connection.setAutoCommit(true);
		Statement st = connection.createStatement();
		st.execute("delete from points");
		st.execute("delete from routes");
		saveroutest = connection.prepareStatement("insert into routes(id, done, endreached, visitedall, lastx, lasty) values(?,?,?,?,?,?)");
		savepointst = connection.prepareStatement("insert into points(routeid, pointx, pointy, visited) values(?, ?, ?, ?)");		
		getroutest = connection.prepareStatement("select id, done, endreached, visitedall, lastx, lasty from routes where done=false");
		getpointst = connection.prepareStatement("select pointx, pointy, visited from points where routeid = ?");
		deleteroutest = connection.prepareStatement("delete from routes where id = ?");
		deletepointst = connection.prepareStatement("delete from points where routeid = ?");
	}
	
	protected void shutdown() {
		if(connection != null) {
			try {
				Statement st = connection.createStatement();
				st.execute("delete from points");
				st.execute("delete from routes");				
				connection.close();
			} catch (SQLException e) {
			}
			
		}
		connection = null;
	}
	
	private void saveRoute(Route r) {
		try {
		if(r != null) {
			saveroutest.clearParameters();
			saveroutest.setInt(1, r.routeNumber);
			saveroutest.setBoolean(2, r.done);
			saveroutest.setBoolean(3, r.endReached);
			saveroutest.setBoolean(4, r.visitedAllPoints());
			saveroutest.setInt(5, r.lastPoint.x);
			saveroutest.setInt(6, r.lastPoint.y);
			saveroutest.execute();
			for(int i=0; i < maxy; i++) {
				for(int j=0; j < maxx; j++) {
					if(r.points[j][i]) {
						savepointst.clearParameters();
						savepointst.setInt(1, r.routeNumber);
						savepointst.setInt(2, j);
						savepointst.setInt(3, i);
						savepointst.setBoolean(4, true);
						savepointst.execute();
					}
				}
			}
		}
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	private void deleteRoute(Route r) {
		try {
			if(r != null) {
			deletepointst.clearParameters();
			deletepointst.setInt(1, r.routeNumber);
			deletepointst.execute();
			deleteroutest.clearParameters();
			deleteroutest.setInt(1, r.routeNumber);
			deleteroutest.execute();
			}
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	private Route getNextRoute() {
		Route r = null;
		try {
			getroutest.clearParameters();
			ResultSet rs = getroutest.executeQuery();
			if(rs.next()) {
				getpointst.clearParameters();
				getpointst.setInt(1, rs.getInt(1));
				ResultSet ptrs = getpointst.executeQuery();
				boolean[][] pts = new boolean[maxx][maxy];
				while(ptrs.next()) {
					pts[ptrs.getInt(1)][ptrs.getInt(2)] = true;
				}
				
				r = new Route(maxx, maxy, pts);
				r.routeNumber = rs.getInt(1);
				r.done = false;
				r.endReached = false;
				r.lastPoint = new Point(rs.getInt(5), rs.getInt(6));
			}
		} catch(Exception e)  { e.printStackTrace(); }
		return r;
	}
	
	public int compute() {
		Route route = new Route(maxx, maxy,null);
		route.addPoint(new Point(0, 0));
		saveRoute(route);
		Route rt = null;
		do {
			rt = getNextRoute();
			if(rt != null) {
				while(!rt.isDone()) {
					Route[] newroutes = rt.advance();
					if(newroutes != null) {
						for(Route nr : newroutes) {
							if(nr != null) {
								saveRoute(nr);
							}
						}
					}
				}
				if(rt.hasReachedEnd() && rt.visitedAllPoints()) {
					completedroutes++;
					System.out.println("Completed Routes : " + completedroutes);
				}
				deleteRoute(rt);
			}
		} while(rt != null);
		return completedroutes;
	}
	
	public int getCompletedRoutes() {
		return completedroutes;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length == 2) {
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
		TakeATourDB tour = null;
		try {
			tour = new TakeATourDB(x, y);
			tour.initialize();
			System.out.println(tour.compute());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tour.shutdown();
		}
		} else {
			System.out.println("Hey! I need the width and height of the grid " + args[0]);
		}
	}
	
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
			this.routeNumber = routeCount++;
			this.gridx = gridx;
			this.gridy = gridy;
			this.maxpoints = gridx * gridy;
			points = new boolean[gridx][gridy];
			count=0;
			initialize(pts);
		}
		
		private void initialize(boolean[][] pts) {
			for(int i=0; i < gridy; i++) {
				for(int j=0; j < gridx; j++) {
					points[j][i] = false;
				}
			}
			if(pts != null) {
				for(int i=0; i < gridy; i++) {
					for(int j=0; j < gridx; j++) {						
						points[j][i] = pts[j][i];
						if(pts[j][i]) count++;
					}
				}
			}
		}
		
		public Route[] advance() {
			Route[] newroutes = null;
			//if we are half way check that at least half of the upper half points are covered
			//if(!halfCheck()) done=true;
			if(!done) {
				Point pts[] = getNextPoints();
				//if we have no more points to go, we are done.
				if(pts[0] == null)  {
					done = true;
					checkEndReached();
					//clear memory
					points = null;
					return null;
				}
				//with rest of the points, if any, we spawn more routes
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
		
		private boolean halfCheck() {
			if(count >= maxpoints/2) {
				for(int i=0; i < gridy/2; i++) { 
					for(int j=0; j < gridx; j++) {
						if(points[j][i] == false)
							return false;
					}
				}
			}
			return true;
		}
				
		public boolean[][] getPoints() {
			return points;
		}
		
		public void addPoint(Point p) {
			if(!hasVisited(p)) {
				points[p.x][p.y] = true;
			}
			count++;
			if(count >= maxpoints) done=true;
			lastPoint = p;
			checkEndReached();
		}
		
		/**
		 * will return next two points. if returns only one, then this routes does not spawn next one
		 * implement boolean flags to indicate we reached max x or max y. decrement that coord from then on
		 * 
		 * We can use boolean grid, so that we don't have to compare.
		 * @return
		 */
		private Point[] getNextPoints() {
			int startx = lastPoint.x;
			int starty = lastPoint.y;
			//there would be max four points that we could go about
			Point[] nextpoints = new Point[4];
			Point calculatedpoint = null;
			int pointcount=0;
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
		
		public void checkEndReached() {
			//get the last point we have and see if it's co-ordinate is the end point
			endReached = points[0][gridy-1];
			if(endReached) done = true;			
		}
		
		public boolean hasReachedEnd() {
			return endReached;
		}
		
		public boolean visitedAllPoints() {
			return (count == maxpoints);
		}
		
		public boolean isDone() {
			return done;
		}
		
		private boolean hasVisited(Point p) {
			return points[p.x][p.y];
		}
		
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
