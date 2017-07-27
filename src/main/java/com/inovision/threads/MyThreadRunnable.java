package com.inovision.threads;

public class MyThreadRunnable implements Runnable
{
	public void run()
	{
		for(int i=0; i < 25; i++)
			System.out.print(i + " | ");
	}
}