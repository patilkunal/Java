package com.inovisionsoftware.quiz;

import java.util.Random;


public class Producer extends Thread {
	private IntBuffer buf;
	
	Producer(IntBuffer buf) {
		this.buf = buf;
	}
	
	@Override
	public void run() {
		Random r = new  Random();
		while(true) {
			int num = r.nextInt();
			buf.add(num);
			System.out.println("Produced : " + num);
		}
	}
}
