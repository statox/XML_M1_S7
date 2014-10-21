package fr.ibformation.DOM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.apache.log4j.Logger;


public class DOMParsing {

static Logger logger = Logger.getLogger((DOMParsing.class));	
	
public static void extract(Document doc, String url) throws IOException {

	
	NodeList root = doc.getElementsByTagName("book");
    
    for (int i = 0; i < root.getLength(); i++) {
      
      Element chapter = (Element) root.item(i);
      NodeList examples = chapter.getElementsByTagName("section");
      
      for (int j = 0; j < examples.getLength(); j++) {
        
        Element example = (Element) examples.item(j);
        String fileName = example.getAttribute("difficulty");
        // All examples should have id attributes but it's safer
        // not to assume that
        if (fileName == null) {
          throw 
           new IllegalArgumentException("Missing id on example"); 
        }
        NodeList programlistings 
         = example.getElementsByTagName("figure");
        // Each example is supposed to contain exactly one 
        // programlisting, but we should verify that
        if (programlistings.getLength() != 1) {
          throw new 
           IllegalArgumentException("Missing programlisting"); 
        }
        Element programlisting = (Element) programlistings.item(0);
        
        // Extract text content; this is a little tricky because
        // these often contain CDATA sections and entity
        // references which can be represented as separate nodes
        // so we can't just ask for the first text node child of
        // each program listing.
        String code = getText(programlisting);
        
        // write code into a file
        File dir = new File("c:\\examples12/" + i);
        dir.mkdirs();
        File file = new File(dir, fileName);
        System.out.println(file);
        FileOutputStream fout = new FileOutputStream(file+".txt");
        Writer out = new OutputStreamWriter(fout, "UTF-8");
        // Buffering almost always helps performance a lot
        out = new BufferedWriter(out);
        out.write(code);
        // Be sure to remember to flush and close your streams
        out.flush();
        out.close();
        
      } // end examples loop
      
    } // end chapters loop

  }
  
  public static String getText(Node node) {
    
    // We need to retrieve the text from elements, entity
    // references, CDATA sections, and text nodes; but not
    // comments or processing instructions
    int type = node.getNodeType();
    if (type == Node.COMMENT_NODE 
     || type == Node.PROCESSING_INSTRUCTION_NODE) {
       return "";
    } 
    
    StringBuffer text = new StringBuffer();

    String value = node.getNodeValue();
    if (value != null) text.append(value);
    if (node.hasChildNodes()) {
      NodeList children = node.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        Node child = children.item(i);  
        text.append(getText(child));
      }
    }
    
    return text.toString();
    
  }
  
  public static void main(String[] args) {

    if (args.length < 1) {
      logger.fatal("Usage: java xmlFile");
      return;
    }
    
    String url = args[0];
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(true);
      
      DocumentBuilder parser = factory.newDocumentBuilder();
      parser.setErrorHandler(new ValidityRequired());
      
      // Read the document
      // this will make a first parsing of the document
     Document document = parser.parse(url); 
     
     // Extract the examples
     extract(document, url);
     
     document = parser.newDocument();
     Element racine = (Element) document.createElement("books");
     document.appendChild(racine);

     for (int i = 1; i < 4; i++) {
       Element livre = (Element) document.createElement("book");
       Element titre = (Element) document.createElement("title");
       titre.appendChild(document.createTextNode("Titre "+i));
       livre.appendChild(titre);
       Element auteur = (Element) document.createElement("author");
       auteur.appendChild(document.createTextNode("Auteur "+i));
       livre.appendChild(auteur);
       Element editeur = (Element) document.createElement("section");
       editeur.appendChild(document.createTextNode("Section "+i));
       livre.appendChild(editeur);
       racine.appendChild(livre);
     }

     XMLSerializer ser = new XMLSerializer(new FileOutputStream("c:"+System.getProperty("file.separator")+"TestDOM.xml",true), new OutputFormat("xml", "UTF-8", true));
     ser.serialize(document);
    }
    catch (SAXException e) {
      System.out.println(e);
    }
    catch (IOException e) { 
      System.out.println(
       "Due to an IOException, the parser could not read " + url
      ); 
      System.out.println(e);
    }
    catch (FactoryConfigurationError e) { 
      System.out.println("Could not locate a factory class"); 
    }
    catch (ParserConfigurationException e) { 
      System.out.println("Could not locate a JAXP parser"); 
    } 
     
  } // end main
  
}

// Make validity errors fatal
class ValidityRequired implements ErrorHandler {

  public void warning(SAXParseException e)
    throws SAXException {
    // ignore warnings  
  }
  
  public void error(SAXParseException e)
   throws SAXException {
    // Mostly validity errors. Make them fatal.
    throw e;
  }
  
  public void fatalError(SAXParseException e)
   throws SAXException {
    throw e;
  }
  
}