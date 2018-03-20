package com.inovisionsoftware.quiz;



public class TestMain {

	public static void main(String args[]) {
		
		IntBuffer b = new IntBuffer();
		
		Producer p = new Producer(b);
		Consumer c = new Consumer(b);
		
		//p.setPriority(Thread.MIN_PRIORITY);
		//c.setPriority(Thread.MAX_PRIORITY);
		p.start();
		c.start();
		//(new Consumer(b)).start();
		
		
		/*
		DiningPhilosophers d = new DiningPhilosophers(5);
		try {
		d.startEating();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		*/
		/*
		Rect a = new Rect(new Rect.Point(1,3), new Rect.Point(3,1));
		Rect b = new Rect(new Rect.Point(2,3), new Rect.Point(4,0));
		
		System.out.println("Overlap a,b: " + RectOverlap.overlap(a, b));
		System.out.println("Overlap b,a: " + RectOverlap.overlap(b, a));
		System.out.println("Overlap2 a,b: " + RectOverlap.overlap2(a, b));
		System.out.println("Overlap2 b,a: " + RectOverlap.overlap2(b, a));

		Rect a1 = new Rect(new Rect.Point(2,5), new Rect.Point(3,4));
		Rect b1 = new Rect(new Rect.Point(3,2), new Rect.Point(4,1));
		System.out.println("Overlap a1,b1: " + RectOverlap.overlap(a, b));
		System.out.println("Overlap b1,a1: " + RectOverlap.overlap(b, a));
		System.out.println("Overlap2 a1,b1: " + RectOverlap.overlap2(a, b));
		System.out.println("Overlap2 b1,a1: " + RectOverlap.overlap2(b, a));
		*/
	}
	
	
}
