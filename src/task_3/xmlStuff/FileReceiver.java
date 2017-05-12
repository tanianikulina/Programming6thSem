package task_3.xmlStuff;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Base64;

public class FileReceiver {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document document = builder.parse("out.xml");
        CDATASection cdata = (CDATASection) document.getElementsByTagName("message").item(0).getFirstChild();

        byte[] fileData = Base64.getDecoder().decode(cdata.getData());

        FileOutputStream out = new FileOutputStream("sentFile.txt");
        out.write(fileData);
        out.flush();
        out.close();
    }
}
