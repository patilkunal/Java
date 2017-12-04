package com.inovisionsoftware.string;

public class ReverseWords {
	ReverseString r = null;
	public ReverseWords() {
		r = new ReverseString();
	}
	
	public String reverseWords(String input) throws Exception {
		String reverse = r.reverseString(input);
		int wordend = 0;
		int wordstart = 0;
		int len = input.length();
		StringBuffer buf = new StringBuffer();
		while(wordend < len) {
			if(reverse.charAt(wordend) != ' ') {
				wordstart = wordend;
				while( (wordend < len) && (reverse.charAt(wordend) != ' '))
					wordend++;
				wordend--;
				buf.append(r.reverseString(reverse, wordstart, wordend));
				buf.append(' ');
			}
			wordend++;
		}
		return buf.toString(); 
	}
	
	public static void main(String args[]) {
		ReverseWords rw = new ReverseWords();
		try {
			System.out.println(rw.reverseWords("Hello a world"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
