package com.inovision.xml;

import java.io.*;
import org.w3c.dom.*;

import org.xml.sax.*;
import javax.xml.parsers.*;


public class testxml
{
  private static String iFile = "steve.xml";
  private static String xmltype = "CommonPartyRole";


public static void main(String args[])
{
boolean debug = true;

	try{
       if(debug) {System.out.println("opening file: " + iFile );; }
	File tFile = new File(iFile);
       if(debug) {System.out.println("file opened: " + iFile );; }
	DocumentBuilderFactory DF=DocumentBuilderFactory.newInstance();
	DF.setExpandEntityReferences(true);
	DF.setExpandEntityReferences(false);
	DF.setValidating(false);

	DocumentBuilder parser = DF.newDocumentBuilder();
       if(debug) {System.out.println("Parsing file: " + iFile );; }
 	Document document = parser.parse(tFile);
       if(debug) {System.out.println("Getting element at: " + xmltype ); }

 	String sSupplierID="";
 	 NodeList nList = document.getElementsByTagName(xmltype);
 	 
        if(debug) {System.out.println("sSupplierID Count: " + nList.getLength() ); }

  	for (int idx=0;idx < nList.getLength();idx ++)
  	{
  		//sSupplierPosition
  		
  		Element nElem=(Element)nList.item(idx);
  		sSupplierID=nElem.getNodeValue();
  		if ( nElem.getNodeType() == Node.TEXT_NODE)	System.out.println("text type node");
        if(debug) {System.out.println("sSupplierID : " +idx+"-"+ sSupplierID+"-"+Node.ELEMENT_NODE+"-"+nElem.getNodeType() + " - " + nElem.getFirstChild().getNodeValue()); }
	
	}
	}
	catch(Exception e)
	{
		e.printStackTrace();	
	}
}
}