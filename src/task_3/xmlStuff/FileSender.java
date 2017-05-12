package task_3.xmlStuff;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Base64;

public class FileSender {
    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException {
        System.out.println("Input file name:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        File file = new File(br.readLine());
        FileInputStream fileInput = new FileInputStream(file);
        byte[] fileData = new byte[(int) file.length()];
        fileInput.read(fileData);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document document = builder.newDocument();

        Element root = document.createElement("message");
        CDATASection cdata = document.createCDATASection(Base64.getEncoder().encodeToString(fileData));
        document.appendChild(root);
        root.appendChild(cdata);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(System.out));
    }
}
