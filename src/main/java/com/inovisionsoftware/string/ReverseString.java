package com.inovisionsoftware.string;

public class ReverseString {

	public String reverseString(String input) throws Exception {
		if(input != null)
			return reverseString(input, 0, input.length()-1);
		else 
			return input;
	}
	
	public String reverseString(String input, int revstart, int revend) throws Exception {
		//Handle error conditions;
		if((input == null) || (input.length() <= 1)) return input;
		if(revend > input.length()-1) throw new Exception("End position is greater than string length");
		if(revstart < 0) throw new Exception("Start position is less than 0");
		char[] arr = input.toCharArray();
		int start = revstart;
		int end = revend;
		while(end > start) {
			char temp = arr[start];
			arr[start] = arr[end];
			arr[end] = temp;
			start++; end--;
		}
							//array, start, count
		return new String(arr, revstart, revend - revstart + 1);
	}
	
	public static void main(String args[]) {
		ReverseString r = new ReverseString();
		try {
		System.out.println(r.reverseString("hello world"));
		System.out.println(r.reverseString(""));
		System.out.println(r.reverseString(null));
		System.out.println(r.reverseString("h"));
		System.out.println(r.reverseString("hi"));
		System.out.println(r.reverseString("hi", -1, 1));
		System.out.println(r.reverseString("hi", 0, 10));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
