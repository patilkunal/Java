package com.inovision.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Sends email using javax.mail api. Include mail.jar and activation.jar 
 * compile and run this program.
 */

public class mailsend
{
	public static void main(String args[])
	{
	try
	{
		String host = "mailhost.inovis.com";
		InternetAddress from = new InternetAddress("kunalpatil@yahoo.com");
		InternetAddress recipient = new InternetAddress("kunal.patil@inovis.com");
		
		//Get System properties
		Properties props = new Properties();
		//Setup default parameters 
		props.put("mail.transport.protocol","smtp");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");
		//Create Session
		Session session = Session.getInstance(props);
		//Create Message
		MimeMessage mesg = new MimeMessage(session);
		mesg.setFrom(from);
		mesg.setSubject("test email");
		String messageText = "PLease ignore this email";
		mesg.setText(messageText);
		mesg.addRecipient(Message.RecipientType.TO, recipient);
		//Send message
		javax.mail.Transport.send(mesg);
	}
	catch(Exception e)
	{
		System.out.println("An error occured: " + e);
	}
	}
}
