package task_3.xmlStuff;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

class XmlDocumentReciever {

	XMLEventReader xmlEventReader;
	DocumentBuilder documentBuilder;
	InputStream inputStream;

	public XmlDocumentReciever(InputStream inputStream) throws ParserConfigurationException, XMLStreamException {
		this.inputStream = inputStream;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		documentBuilder = dbf.newDocumentBuilder();
	
		XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
		xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
		xmlEventReader.nextEvent();
	}
	
	public Document receive() throws XMLStreamException, SAXException, IOException{
		
		XMLEvent xmlEvent;
		while((xmlEvent = xmlEventReader.nextEvent())!=null) {
			if (xmlEvent.isCharacters()) {
				break;
			} 
		};
		
		Characters stringedDocument = (Characters) xmlEvent;
		return documentBuilder.parse(new ByteArrayInputStream(stringedDocument.getData().getBytes("UTF-8")));
	}

	
	public void close() {
		try{
			inputStream.close();
		} catch (Exception e) {
		
		}
	}

}