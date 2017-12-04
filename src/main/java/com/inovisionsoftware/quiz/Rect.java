package com.inovisionsoftware.quiz;

public class Rect {
	public Point ul;
    public Point lr;

    public Rect( Point ul, Point lr ){
        this.ul = ul;
        this.lr = lr;
    }

	
	public static class Point {
		public int x;
	    public int y;

	    public Point( int x, int y ){
	        this.x = x;
	        this.y = y;
	   }

	}

}
