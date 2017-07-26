package com.inovision.util.io;

import java.io.*;

public class FileReaderTest
{
	public static void main(String args[])
	{
		try{
		FileInputStream in = new FileInputStream("x.txt");
		UpperCaseConverter conv = new UpperCaseConverter(in);
		BufferedReader reader = new BufferedReader(new InputStreamReader(conv));
		String line=null;
		while( (line=reader.readLine()) != null)
			System.out.println(line);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}