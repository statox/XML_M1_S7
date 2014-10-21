package fr.ibformation.DOM;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MyDOMParser {

	static Logger logger = Logger.getLogger((MyDOMParser.class));

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
//		if (args.length < 1) {
//			logger.fatal("Usage: java xmlFile");
//			return;
//		} 
		//String url = args[0];
		
		String url = "TestDOMXML.xml";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
			// activer la validation XML
			factory.setValidating(true) ;


			// instancier le parser de mon document
			DocumentBuilder parser = factory.newDocumentBuilder();
			// instancier un gestionnaire d'erreur qui arrète tout à la moindre
			// erreur
			parser.setErrorHandler(new ValidityRequiredErrorHandler()) ;
			
			//	Maintenant parser documenté
			//  Récuperer un handler sur le document parsé
			logger.info("Parsing du document "+url) ;
			Document document = parser.parse(url); 
			manipulerDocument(document) ;
			
		} catch (Exception e) {
			logger.fatal("The following exception terminated the program: "+e);
		}

	}
	
	/* Cette methode recherche tous les noeuds sections puis affiche la difficulté associée */
	public static void manipulerDocument(Document doc) throws IOException {
		logger.debug("Looking for <section> tags..") ;
		NodeList sections = doc.getElementsByTagName("section");
		logger.debug("Found "+ sections.getLength() +" <section> tags..") ;
		
		
		for (int j = 0; j < sections.getLength(); j++) {
	        
	        Element section = (Element) sections.item(j);
	        String difficulty = section.getAttribute("difficulty");
	        // All examples should have id attributes but it's safer
	        // not to assume that
	        if (difficulty == null) {
	          difficulty = "inconnu" ; 
	        }
	        
	        // ici je suppose que la DTD me garantie que le premier element est une section
	   
	        Node titreN =  section.getFirstChild().getNextSibling() ;
	        if (titreN!=null && (titreN.getNodeType()==Node.ELEMENT_NODE)&& titreN.getNodeName().contains("title")) {
	        	Element title = (Element) titreN ;
		        logger.info("Section trouvée avec titre \""+getText(title)+"\" et degrée de difficulté "+difficulty) ;
	        	
	        }
		}
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
}

//	Inner class for error validation: Make validity errors fatal
class ValidityRequiredErrorHandler implements ErrorHandler {

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


