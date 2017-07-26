package com.inovision.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomEcho
{

static Document document;

public static void main(String args[])
{
	if(args.length != 1)
	{
		System.out.println("Usage: java DomEcho filename");
		System.exit(1);
	}
	
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	try
	{
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse( new File(args[0]));
		Element root = document.getDocumentElement();
		root.appendChild(document.createElement("EXTERNAL_DATA"));
		root.appendChild(document.createElement("EXTERNAL_DATA\\USER"));
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
}
}