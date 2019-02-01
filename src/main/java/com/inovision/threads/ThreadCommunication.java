package com.inovision.threads;

public class ThreadCommunication {

	public static class Customer {
		int amount = 1000;
		
		synchronized void withdraw(int amount) {
			System.out.println("Withdrawing ...");
			if(this.amount < amount) {
				System.out.println("Insufficient balance ... waiting for deposit ...");
				try {
					wait();
				} catch(InterruptedException ie) {
					ie.printStackTrace();
				}
			}
			this.amount -= amount;
			System.out.println("Withdraw complete ...");
		}
		
		synchronized void deposit(int amount)  {
			System.out.println("Depositing ...");
			this.amount += amount;
			System.out.println("Deposit complete.");
			notify();
		}
	}
	
	public static void main(String args[]) {
		final Customer c = new Customer();
		Thread t1 = new Thread() {
			public void run() {
				c.withdraw(1500);
			}
		};
		
		Thread t2 = new Thread() {
			public void run() {
				c.deposit(2000);
			}
		};
		
		t1.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t2.start();
		
	}
}
