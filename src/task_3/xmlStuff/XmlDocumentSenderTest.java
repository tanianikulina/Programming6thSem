package task_3.xmlStuff;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

class XmlDocumentSenderTest{
	public static void main(String[] args) throws Exception{
		readWrite();
	}
	
	public static void testWrite() throws Exception{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document document = builder.newDocument();
		Element root = document.createElement("root");
		document.appendChild(root);
		XmlDocumentSender sender = new XmlDocumentSender(System.out);
		sender.send(document);
		sender.close();	
	}
	
	public static void testRead() throws Exception{
		//String xml = "";
		XmlDocumentReciever xmlDocumentReciever = new XmlDocumentReciever(new FileInputStream("test.xml"));
		Document document = xmlDocumentReciever.receive();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult(System.out));
	}
	
	public static void readWrite() throws Exception{
		PipedOutputStream pos = new PipedOutputStream();
		PipedInputStream pis = new PipedInputStream(pos);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document document = builder.newDocument();
		Element root = document.createElement("root");
		document.appendChild(root);
		XmlDocumentSender sender = new XmlDocumentSender(pos);
		sender.send(document);
		sender.send(document);
		sender.send(document);
		
		XmlDocumentReciever xmlDocumentReciever = new XmlDocumentReciever(pis);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(new DOMSource(xmlDocumentReciever.receive()), new StreamResult(System.out));		
		transformer.transform(new DOMSource(xmlDocumentReciever.receive()), new StreamResult(System.out));		
		transformer.transform(new DOMSource(xmlDocumentReciever.receive()), new StreamResult(System.out));		
		sender.close();	
	}
}