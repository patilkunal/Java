package com.inovision.http;

import java.net.*;
import java.io.*;

/**
 *  Rainmaker Security library. 
 *  Use this library to validate and lookup password from Rainmaker.
 *  
 *  How to use it
 *	RMSecurity security = new RMSecurity();
 *	security.setHost("lennon.dev.itlogon.com");
 *	security.setPort(8191);
 *	
 *	//To verify password
 *	security.verifyPassword(loginid, password); //return true or false.
 *	
 *	//To get password
 *	password = security.lookupPassword(loginid); //returns null on error.
 *	
 * To get Errorcode and Errormessage call getResultCode and getResultMessage
 *
 *@author     Kunal Patil
 *@created    July 8, 2003
 *@version    1.1
 */

public class RMSecurity
{

private String sHost;
private int iPort=80;
private String sPath;
private String sPassword;

private boolean bResultCode;
private String sResultMessage;

public RMSecurity()
{
	sHost = null;	
	iPort = 80;
	sPath = "";
}	

public RMSecurity(String host, int port, String path)
{
	sHost = host;
	iPort = port;
	sPath = path;
	sPassword = null;	
}

public void setHost(String s)
{
	sHost=s;
}

public String getHost()
{
	return sHost;
}

public void setPort(int i)
{
	iPort = i;
}

public int getPort()
{
	return iPort;
}

private void connect(String action, String loginid, String password) throws Exception
{
	if((sHost == null) || sHost.equals("")) throw new Exception("No Password host specified");
	if(sPath == null) sPath = "";
	bResultCode = false;	

	try {
	        // Construct data
	        String data = URLEncoder.encode("action") + "=" + URLEncoder.encode(action);
	        data += "&" + URLEncoder.encode("loginid") + "=" + URLEncoder.encode(loginid);
	        data += "&" + URLEncoder.encode("password") + "=" + URLEncoder.encode(password);
	    
	        InetAddress addr = InetAddress.getByName(sHost);
	        Socket socket = new Socket(addr, iPort);
	    
	        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
	        wr.write("POST "+ sPath +" HTTP/1.0\r\n");
	        wr.write("Content-Length: "+data.length()+"\r\n");
	        wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
	        wr.write("\r\n");
	    
	        // Send data
	        wr.write(data);
	        wr.flush();
	    
	        // Get response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        String line;
	        while ((line = rd.readLine()) != null) {
	            // Process line...
	            
	            //System.out.println(line);
	            if(line.toLowerCase().startsWith("resultcode"))
	            {
	            	//System.out.println("Found resultcode");
	            	line = line.toLowerCase();
	            	if(line.substring(line.indexOf("=")+1, line.length()-1).equals("ok"))
	            		bResultCode = true;
	            	else
	            		bResultCode = false;	            		            	
	            }
	            
	            if(bResultCode && line.toLowerCase().startsWith("password") )
	            {
	            	sPassword = line.substring((line.indexOf('=') + 1), line.length()-1 );
	            }
	            
	            if(!bResultCode && line.toLowerCase().startsWith("resultmessage") )
	            {
	            	sResultMessage = line.substring((line.indexOf("=") + 1), line.length()-1 );
	            }
	            
	        }
	        wr.close();
	        rd.close();
	        wr = null;
	        rd = null;
	        socket.close();
	        socket = null;
	        addr = null;
	        data = null;
	    } 
	    catch (Exception e) {
	    	e.printStackTrace();
	    	throw new Exception(e.toString());
	    }
	
}

public boolean verifyPassword(String sLoginid, String sPassword) throws Exception
{
	connect("VERIFY", sLoginid, sPassword);
	return bResultCode;
}

public String lookupPassword(String sLoginid)
{
	try{
	connect("LOOKUP", sLoginid, "");
	if(bResultCode)
		return sPassword;
	else
		return null;
	}
	catch(Exception e)
	{
		return null;
	}
}

public boolean getResultCode()
{
	return bResultCode;
}

public String getResultMessage()
{
	return sResultMessage;
}



}	