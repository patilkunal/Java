package com.inovisionsoftware.quiz;

public class Consumer extends Thread {
	private IntBuffer buf;
	
	Consumer(IntBuffer buf) {
		this.buf = buf;
	}
	
	@Override
	public void run() {
		synchronized (buf) {
			while(true) {
			int num = buf.remove();
			System.out.println("Consumed : " + num);
			}
		}
	}
}
