package com.inovision.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RaceConditionExample {

	public static void main(String args[]) throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(10);
		final Counter counter = new Counter();
		for(int i=0; i < 1000; i++) {
			service.submit(() -> counter.increment());
		}
		service.shutdown();
		service.awaitTermination(60, TimeUnit.SECONDS);
		
		System.out.println("Final count is : " + counter.getCount());
	}
	
	public static class Counter {
		volatile int count = 0;
		
		public void increment() {
			this.count++;
		}
		
		public int getCount() {
			return this.count;
		}
	}
}