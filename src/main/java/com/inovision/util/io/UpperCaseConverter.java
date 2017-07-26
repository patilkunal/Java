/*
 * This is an example of extending InputStream reader class
 * In this example all the lower case ANSI characters are converted 
 * to upper case before returning to the calling program.
 * In Design Patterns this is called as DECORATOR Pattern.
 *
 */
package com.inovision.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author kpatil
 *
 */
public final class UpperCaseConverter extends InputStream {

	private InputStream in;
	/**
	 * 
	 */
	public UpperCaseConverter(InputStream in) {
		this.in = in;
	}

	public int read() throws IOException 
	{
	      int byteRead = in.read();

	      //If the character falls in range of lowercase a-z
	      if ((byteRead > 96) && (byteRead < 123)) 
	      {
	    	  //Convert it to uppercase A-Z
	         byteRead -= 32;
	      }
    	  return byteRead;
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	public int read(byte[] b, int off, int len) throws IOException 
	{
	      if (b == null) {
	          throw new NullPointerException();
	       } else if ((off < 0) || (len < 0) || ((off + len) > b.length)) {
	          throw new IndexOutOfBoundsException();
	       } else if (len == 0) {
	          return 0;
	       }

	      //IOException to first call to read in not caught 
	      int c = read();
	      if (c == -1) {
	         return -1;
	      }

	      b[off] = (byte) c;

	      int i = 1;
	      for (; i < len; i++) {
	    	  
	    	  try
	    	  {
	    		  c = read();
	    	  }
	    	  catch(IOException ioe)
	    	  {
	    		//Catch IOException and return the number of bytes read   
	    	  }
	         if (c == -1) {
	            break;
	         }
	         b[off + i] = (byte) c;
	      }

	      return i;
	      
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#read(byte[])
	 */
	public int read(byte[] b) throws IOException 
	{
	      if (b == null) 
	      {
	          throw new NullPointerException();
	      } 
	      else if (b.length == 0)
	      		{
	    	  		return 0;
	      		}

	      //IOException to first call to read in not caught 
	      int c = read();
	      if (c == -1) {
	         return -1;
	      }
	      
	      int i=0;
	      b[i] = (byte) c;
	      
	      for(; i < b.length; i++)
	      {
	    	  try
	    	  {
	    		  c = read();
	    	  }
	    	  catch(IOException ioe)
	    	  {
	    		//Catch IOException and return the number of bytes read   
	    	  }
	         if (c == -1) {
	            break;
	         }
	         b[i] = (byte) c;	    	  
	      }
	      
	      return i;
	}

}
