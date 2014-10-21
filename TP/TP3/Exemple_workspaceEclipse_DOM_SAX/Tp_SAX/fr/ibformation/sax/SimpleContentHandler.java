package fr.ibformation.sax;

import java.io.IOException;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.LocatorImpl;

public class SimpleContentHandler implements ContentHandler {

	static Logger logger = Logger.getLogger((SimpleContentHandler.class));
	
	/**
	 * Constructeur par defaut.
	 */
	public SimpleContentHandler() {
		super();
		// On definit le locator par defaut.
		locator = new LocatorImpl();
	}

	/**
	 * Definition du locator qui permet a tout moment pendant l'analyse, de
	 * localiser le traitement dans le flux. Le locator par defaut indique, par
	 * exemple, le numero de ligne et le numero de caractere sur la ligne.
	 * 
	 * @author smeric
	 * @param value
	 *            le locator a utiliser.
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	public void setDocumentLocator(Locator value) {
		locator = value;
	}

	/**
	 * Evenement envoye au demarrage du parse du flux xml.
	 * 
	 * @throws MyParserException
	 *             en cas de probleme quelquonque ne permettant pas de se lancer
	 *             dans l'analyse du document.
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() throws MyParserException {
		logger.info("Debut de l'analyse du document");
	}

	/**
	 * Evenement envoye a la fin de l'analyse du flux xml.
	 * 
	 * @throws MyParserException
	 *             en cas de probleme quelquonque ne permettant pas de
	 *             considerer l'analyse du document comme etant complete.
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument() throws MyParserException {
		logger.info("Fin de l'analyse du document");
	}

	/**
	 * Debut de traitement dans un espace de nommage.
	 * 
	 * @param prefixe
	 *            utilise pour cet espace de nommage dans cette partie de
	 *            l'arborescence.
	 * @param URI
	 *            de l'espace de nommage.
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String,
	 *      java.lang.String)
	 */
	public void startPrefixMapping(String prefix, String URI)
			throws MyParserException {
		logger.info("Traitement de l'espace de nommage : " + URI
				+ ", prefixe choisi : " + prefix);
	}

	/**
	 * Fin de traitement de l'espace de nommage.
	 * 
	 * @param prefixe
	 *            le prefixe choisi a l'ouverture du traitement de l'espace
	 *            nommage.
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	public void endPrefixMapping(String prefix) throws MyParserException {
		logger.info("Fin de traitement de l'espace de nommage : "
				+ prefix);
	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre une balise xml
	 * ouvrante.
	 * 
	 * @param nameSpaceURI
	 *            l'url de l'espace de nommage.
	 * @param localName
	 *            le nom local de la balise.
	 * @param rawName
	 *            nom de la balise en version 1.0
	 *            <code>nameSpaceURI + ":" + localName</code>
	 * @throws MyParserException
	 *             si la balise ne correspond pas a ce qui est attendu, comme
	 *             par exemple non respect d'une dtd.
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String nameSpaceURI, String localName,
			String rawName, Attributes attributs) throws MyParserException {
		
		logger.info("Ouverture de la balise : " + localName);
		if (!"".equals(nameSpaceURI)) { // espace de nommage particulier
			logger.info("  appartenant a l'espace de nom : "
					+ nameSpaceURI);
		}
		logger.info("  Attributs de la balise : ");

		for (int index = 0; index < attributs.getLength(); index++) { // on
			// parcourt la liste des attributs
			logger.info("     - " + attributs.getLocalName(index)
					+ " = " + attributs.getValue(index));
		}

	}

	/**
	 * Evenement recu a chaque fermeture de balise.
	 * 
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void endElement(String nameSpaceURI, String localName, String rawName)
			throws MyParserException {
		logger.info("Fermeture de la balise : " + localName);

		if (!"".equals(nameSpaceURI)) { // name space non null
			logger.debug("appartenant a l'espace de nommage : " + localName);
		}
	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre des caracteres
	 * (entre deux balises).
	 * 
	 * @param ch
	 *            les caracteres proprement dits.
	 * @param start
	 *            le rang du premier caractere a traiter effectivement.
	 * @param end
	 *            le rang du dernier caractere a traiter effectivement
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	public void characters(char[] ch, int start, int end) throws MyParserException {
		
		logger.info("#PCDATA : " + new String(ch, start, end));

	}

	/**
	 * Recu chaque fois que des caracteres d'espacement peuvent etre ignores au
	 * sens de XML. C'est a dire que cet evenement est envoye pour plusieurs
	 * espaces se succedant, les tabulations, et les retours chariot se
	 * succedants ainsi que toute combinaison de ces trois types d'occurrence.
	 * 
	 * @param ch
	 *            les caracteres proprement dits.
	 * @param start
	 *            le rang du premier caractere a traiter effectivement.
	 * @param end
	 *            le rang du dernier caractere a traiter effectivement
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(char[] ch, int start, int end)
			throws MyParserException {
		try{
		logger.info("espaces inutiles rencontres : ..."
				+ new String(ch, start, end) + "...");
		}catch(Exception e){
			throw new  MyParserException(e.getMessage(),locator) ;
		}
	}

	/**
	 * Rencontre une instruction de fonctionnement.
	 * 
	 * @param target
	 *            la cible de l'instruction de fonctionnement.
	 * @param data
	 *            les valeurs associees a cette cible. En general, elle se
	 *            presente sous la forme d'une serie de paires nom/valeur.
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String,
	 *      java.lang.String)
	 */
	public void processingInstruction(String target, String data)
			throws MyParserException {
		logger.info("Instruction de fonctionnement : " + target);
		logger.info("  dont les arguments sont : " + data);
	}

	/**
	 * Recu a chaque fois qu'une balise est evitee dans le traitement a cause
	 * d'un probleme non bloque par le parser. Très peu utilisé.
	 * 
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	public void skippedEntity(String arg0) throws MyParserException {

	}

	private Locator locator;

	public Object getContent(URLConnection urlc) throws IOException {
		return null;
	}

}
