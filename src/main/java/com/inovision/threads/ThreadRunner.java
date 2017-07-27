package com.inovision.threads;

public class ThreadRunner
{
	public static void main(String args[])
	{
		MyThread t = new MyThread();
		t.start();
		System.out.println("ThreadRunner complete 1");
		MyThreadRunnable t1 = new MyThreadRunnable();		
		Thread t2 = new Thread(t1);
		t2.start();
		System.out.println("ThreadRunner complete 2");
	}
}