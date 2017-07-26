package com.inovision.http;

public class getpass 
{

public static void main(String args[])
{

if( args.length < 1 )
{
	System.out.println("\nUsage: java getpass LOGINID\n");
	System.exit(1);
}
String sAction = "LOOKUP";
String sLogin = args[0];
String sPassword = null;

try {
	RMSecurity security = new RMSecurity("lennon.dev.itlogon.com", 8191,"/service/HttpService");
	sPassword = security.lookupPassword(sLogin);
	if(sPassword != null)
		System.out.println(sPassword);
	else
		System.out.println("Error: " + security.getResultMessage());
    } 
    catch (Exception e) {
    	e.printStackTrace();
    }
}
}
