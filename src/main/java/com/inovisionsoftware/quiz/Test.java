package com.inovisionsoftware.quiz;
public class Test {

public static void main(String args[]) {
	System.out.println(reverseString2("1234567890 1234567890"));
	char[] source = {'1', '2', '3', '4', '5'};
	reverseString3(source);
	System.out.println(new String(source));
	System.out.println("The number is : "  + (1 + strtonum("123")));
	System.out.println("The string for : " + numtoStr(0));
	
	System.out.println("Search found at " + binSearch(new int[] {30, 29, 27, 21, 19, 18, 15, 11, 8, 2, 1}, 18));
}

public static int strtonum(String str) {
	if(str == null)
		return 0;
	boolean isneg = false;
	int i = 0;
	int retval = 0;
	int len = str.length();
	
	if(str.charAt(0) == '-') {
		isneg = true;
		i = 1;
	}
	while(i < len) {
		retval *= 10;
		retval += str.charAt(i++) - '0';
	}
	if(isneg)
		retval *= -1;
	return retval;
}

public static String reverseString(String input) {
	StringBuffer dest = new StringBuffer();
	for(int i=input.length() - 1; i >= 0; i--) {
		dest.append(input.charAt(i));
	}
	return dest.toString();
}

public static String reverseString2(String input) {
	char[] dest = new char[input.length()];
	int j=0;
	for(int i=input.length() - 1; i >= 0; i--) {
		dest[j++] = input.charAt(i);
	}
	return new String(dest);
}

public static void reverseString3(char[] source) {
	char temp;
	int start=0;
	int end = source.length - 1;
	while(end > start) {
		temp = source[start];
		source[start] = source[end];
		source[end] = temp;
		start++; end--;
	}
}

public static int strToInt( String str ){
    int i = 0, num = 0;
    boolean isNeg = false;
    int len = str.length();

    if( str.charAt(0) == '-' ){
        isNeg = true;
        i = 1;
    }

    while( i < len ){
        num *= 10;
        num += ( str.charAt(i++) - '0' );
    }

    if( isNeg )
        num *= -1;

    return num;
}

public static String numtoStr(int input) {
	if(input == 0) return "0";
	int MAX_DIGITS = 10;
	int num = input;
	
	StringBuffer buf = new StringBuffer();
	if(num < 0) {
		buf.append("-");
		num *= -1;
	}
	
	char[] temp = new char[MAX_DIGITS];
	int i = 0;
	while(num != 0) {
		temp[i++] = (char)('0' + (num % 10));
		num = num / 10;
	}
	
	for(int j=i-1; j >= 0; j--) {
		buf.append(temp[j]);
	}
	return buf.toString();
}

public static int binSearch(int[] arr, int target) {
	return binSearch(arr, 0, arr.length, target);
}
//arr is sorted in descending order
public static int binSearch(int[] arr, int lower, int upper, int target) {
	int middle = lower + (upper - lower) /2;
	if(target == arr[middle]) return middle;
	
	if(target < arr[middle]) {
		return binSearch(arr, middle + 1, upper, target);
	} else {
		return binSearch(arr, lower, middle - 1, target);
	} 
}

public static void findPermutations(String input){
	char[] firstchar = new char[1];
	char[] vararr = new char[input.length() - 1];
	for(int i=0; i < input.length(); i++) {
		firstchar[0] = input.charAt(i);
		permute(firstchar,vararr);
	}
}

public static void permute(char[] base, char[] vararr) {
	if(vararr.length == 2) {
		String finalbase = new String(base);		
		System.out.println(finalbase + vararr[0] + vararr[1]);
		System.out.println(finalbase + vararr[1] + vararr[0]);
	} else {
		String newbasestr = new String(base) + vararr[0];
		char[] newvararr = new char[vararr.length - 1];
		System.arraycopy(vararr, 1, newvararr, 0, newvararr.length);
		permute(newbasestr.toCharArray(), newvararr);
	}
}



}