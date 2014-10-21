package fr.ibformation.sax;

import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

public class MyParserException extends SAXParseException{

	public MyParserException(String message, Locator locator) {
		super(message, locator);
		SimpleSaxParser.logger.error(locator.getLineNumber());
		SimpleSaxParser.logger.error(locator.getColumnNumber());
	}	

}
