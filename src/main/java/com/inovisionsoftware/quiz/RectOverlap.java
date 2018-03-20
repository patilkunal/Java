package com.inovisionsoftware.quiz;

public class RectOverlap {

	public static boolean overlap( Rect a, Rect b){
	    return( a.ul.x <= b.lr.x &&
	            a.ul.y >= b.lr.y &&
	            a.lr.x >= b.ul.x &&
	            a.lr.y <= b.ul.y );
	}

	public static boolean overlap2( Rect a, Rect b){
	    return( //((a.lr.y < b.ul.y) && (a.lr.x > b.ul.x) ) 	    		
	    		//||
	    		((b.lr.y < a.ul.y) && (b.lr.x > a.ul.x))
	    		);
	}
	
}
