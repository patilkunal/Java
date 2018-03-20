package com.inovisionsoftware.quiz;

public class DiningPhilosophers {

	private Object[] forks;
	private Philosopher[] philosophers;
	
	public DiningPhilosophers(int num) {
		forks = new Object[num];
		philosophers = new Philosopher[num];
		for(int i=0; i < num; i++) {
			forks[i] = new Object[num];
			//workaround for a deadlock
			if(i ==0) {
				philosophers[i] = new Philosopher(i, (i+1)%num, i);	
			} else 
			{
				philosophers[i] = new Philosopher(i, i, (i+1)%num);					
			}
		}
	}
	
	public void startEating() throws InterruptedException {
		for(int i=0; i < philosophers.length; i++) {
			philosophers[i].start();
		}
		philosophers[0].join();
	}
	
	
	private class Philosopher extends Thread {
		private int fork1;
		private int fork2;
		private int id;
		
		public Philosopher(int id, int fork1, int fork2) {
			this.id = id;
			this.fork1 = fork1;
			this.fork2 = fork2;
		}
		
		@Override
		public void run() {
			status("Ready to eat using forks " + fork1 + " and " + fork2);
			pause();
			while(true) {
				status("Attempting fork : " + fork1);
				synchronized (forks[fork1]) {
					status("Got fork : " + fork1);
					status("Attempting fork : " + fork2);
					synchronized(forks[fork2]) {
						status("Got fork : " + fork2);
						status("Eating");
					}
				}
			}
		}
		
		private void status(String msg) {
			System.out.println("Philosopher : " + this.id + " : " + msg);
		}
		
		private void pause() {
			try {
				sleep(200);
			} catch(InterruptedException e) { }
		}
	}
	
	public static void main(String args[]) {
		DiningPhilosophers d = new DiningPhilosophers(5);
		
		try {
			d.startEating();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
