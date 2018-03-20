package com.inovisionsoftware.quiz;


public class TestArrays {
	
	public static void main(String args[]) {
		float[] hashlist = new float[2];
		hashlist[0] = 1f;
		hashlist[1] = 2f;
		float[] hashlist2 = new float[5];
		System.arraycopy(hashlist, 0, hashlist2, 0, hashlist.length);
		hashlist[0] =  hashlist[0];
	}
	
	

}
