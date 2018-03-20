package com.inovisionsoftware.quiz;
public class IntBuffer {
	private int index;
	private int[] buffer = new int[8];

	public synchronized void add(int num) {
		// while( index == 7 ){
		System.out.println("add : " + index);
		if (index == 7) {
			try {
				// Thread.yield();
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		{
			System.out.println("add Got the lock");
			buffer[index++] = num;
			notify();
		}
	}

	public synchronized int remove() {
		int ret;
		// while( index < 1 ){
		System.out.println("Remove : " + index);
		if (index < 1) {
			try {

				// Thread.yield();
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("remove Got the lock");
		ret = buffer[--index];
		notify();

		return ret;
	}
}
