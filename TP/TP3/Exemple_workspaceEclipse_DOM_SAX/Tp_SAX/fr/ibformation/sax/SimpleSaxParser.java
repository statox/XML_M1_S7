package fr.ibformation.sax;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
public class SimpleSaxParser {
	
	public static Logger logger = Logger.getLogger((SimpleSaxParser.class));
	 /**
     * Contructeur.
     */
    public SimpleSaxParser(String uri) throws SAXException, IOException {
    				XMLReader saxReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");	
    				saxReader.setContentHandler(new SimpleContentHandler());
                    saxReader.parse(uri);
    }

    public static void main(String[] args) {
//            if (1 != args.length ) {
//                    logger.fatal("Usage : SimpleSaxParser fileName");
//                    System.exit(1);
//            }
//
//            String uri = args[0];
    		 
    	
    			String uri="test.xml";
            try {
                    SimpleSaxParser parser = new SimpleSaxParser(uri);
            }catch(SAXParseException e){
            	logger.error("L'erreur s'est produite à la ligne "+e.getLineNumber());
            	logger.error("L'erreur s'est produite à la colonne "+e.getColumnNumber());
            
            } catch (Throwable t) {
            	t.printStackTrace();
            }
    }
}
