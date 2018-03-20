package com.inovisionsoftware;

public class ExceptionsTest {

	public static void main(String args[]) {
		int x =1;
		try {
			if(x == 1) {
				foo();
			}
		} catch(IllegalArgumentException iae) {
			System.out.println("IllegalArgumentException --");
			iae.printStackTrace();
		}  catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void foo() throws Exception {
		throw new IllegalArgumentException("Some text");
	}
	
	
}
