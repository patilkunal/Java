package com.inovision.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegEx1 {

	private static final Pattern PATTERN = Pattern.compile("(\\\\{2}[uU])([0-9A-Fa-f]{4})");
	
	public static void main(String args[]) {
		StringBuffer buffer = new StringBuffer("HBO\\u2120. Epix\\\\u3030 kjsdh HBO\\u2121  EPIX\\\\u4334 jdhajsdh");
		String str = buffer.toString();		
		System.out.println("Orig: " + str);
		System.out.println("REPL 0: " + str.replaceAll("\\\\{2}[uU]([0-9A-Fa-f]{4})", "\\\\u$1"));
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\\\{2}[uU])([0-9A-Fa-f]{4})");
		Matcher m = pattern.matcher(str);
		int regionstart = 0;
		/*
		if(m.matches()) {
			System.out.println(m.groupCount());
			System.out.println(m.group());
			//System.out.println(m.replaceAll("\\\\u"));
		} else {
			System.out.println("no match");
		}
		*/
		
		while(m.find()) {
			
			regionstart = m.start();
			System.out.println("Matching at pos: " + regionstart);
			System.out.println("Group count " + m.groupCount());
			System.out.println("Group 2 " + m.group(2));
			//System.out.println("Group 1 " + m.group(2));
			//m.region(regionstart, str.length());
			//m.replaceFirst("\\1");
			String str2 = m.replaceFirst(m.group());
			System.out.println("Replace: " + str2);
			m = pattern.matcher(str2);
		}
		
				/*
		int pos = 1;
		while(pos > 0 && pos < str.length()) {
			System.out.println("Pos: " + pos);
			pos = str.indexOf("\\\\u", pos);
			System.out.println("Pos: " + pos);
			pos++;
		}
		
		str.repl
		*/
		
	}
	
	/*
	private void func() {
		//Unicode generator double encodes existing unicode chars with double backslash (\u2120 converts to \\u2120)
		//Following is to restore single backslash
		StringWriter writer = ((StringWriter) jGen.getOutputTarget());
		StringBuffer buffer = writer.getBuffer();
		String bufstr = buffer.toString();
		
		Matcher m = PATTERN.matcher(bufstr);
		
		while(m.find()) {
			//Kunal - Possible bug in JDK that it treats 4 backslash as single in final string.
			//if this call put two instead of one, we need to fix here.
			bufstr = m.replaceFirst("\\\\u" + m.group(2));
			m = PATTERN.matcher(bufstr);
		}
		
	}
	*/
}
