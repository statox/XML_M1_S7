package ex1;

/*
 * Created on 26 nov. 2003
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 * 
 *  Modified on 20 oct. 2014 - Adrien Fabre
 *  Purpose: XML TP3
 */

import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

public class parser {

	public PrintWriter out = null;

	//Rem changer Package par ModelElement
	public void parse(String _fichier)
		throws SAXException, ParserConfigurationException, IOException {

		// Charger le document
		FileInputStream _xml_input_file = new FileInputStream(_fichier);

		parse(_xml_input_file);
	}

	public void parse(InputStream _xml_input_file)
		throws SAXException, ParserConfigurationException, IOException {

		//création du fichier output
		out = new PrintWriter( new FileOutputStream("./output.html") );
		out.println("<html xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">");
		out.println("<head>");
		out.println("	<title>Your library</title>");
		out.println("	<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("</head>");
		out.println("<body>");
		out.println("	<h1 align=\"left\">Domaines </h1>");
		

				
		//instancier le contrcuteur de parseurs
		DocumentBuilderFactory _factory = DocumentBuilderFactory.newInstance();

		//ignorer les commentaires dans les fichiers XML parsés
		_factory.setIgnoringComments(true);
		
		// créer un parseur
		DocumentBuilder _builder = _factory.newDocumentBuilder();

		// Charger le document
		Document doc = _builder.parse(_xml_input_file);

		// Parser le document
		/* PARTIE A REMPLIR 
		*/
		
//		//Affiche la version de XML
//		System.out.println(doc.getXmlVersion());
//		//Affiche l'encodage
//		System.out.println(doc.getXmlEncoding());  
//		//Affiche s'il s'agit d'un document standalone      
//		System.out.println(doc.getXmlStandalone());

		
		createHTML(doc);
		
		out.println("</body>");
		out.println("</html>");
		out.close();
		out.flush();
	}

	public void createHTML (Document doc){

		// get the root element of he document (Here it's <bib>)
		Element root = doc.getDocumentElement();
		
	
		NodeList rootNodeList = root.getChildNodes();
		
				
		// creation of domains list in top of the document
		for (int i = 0; i<rootNodeList.getLength(); i++) {
		
			if(rootNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
		        Node domainNode = rootNodeList.item(i);
		        NodeList domainChildList = domainNode.getChildNodes();
		        
		        for (int j=0; j<domainChildList.getLength(); j++){
		        	if (domainChildList.item(j).getNodeType() == Node.ELEMENT_NODE){
		        		Node domainChild = domainChildList.item(j);
		        		if (domainChild.getNodeName() == "title"){
		        			DomainListTemplate(domainChild.getTextContent());
		        		}
		        	}
		        }		        
		    }
		}
		
		
		// creation of the sections of the document
		for (int i = 0; i<rootNodeList.getLength(); i++) {
			if(rootNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
		        Node domainNode = rootNodeList.item(i);
		        NodeList domainChildList = domainNode.getChildNodes();
		        
		        for (int j=0; j<domainChildList.getLength(); j++){
		        	if (domainChildList.item(j).getNodeType() == Node.ELEMENT_NODE){
		        		Node domainChild = domainChildList.item(j);
		        		if (domainChild.getNodeName() == "title"){
		        			DomaineTemplate((Element)domainChild, domainChild.getTextContent());
		        		} else if (domainChild.getNodeName() == "bib_ref"){
		        			BibRefTemplate(domainChild);
		        		}
		        	}
		        }		        
		    }
		}
		
	}
	
	// Create this list on top of the document
	public void DomainListTemplate(String name){
		out.println("	<h2><a href=#" + name + ">" + name + "</a></h2>");
	}
	
	// create the banners with the title of each domain
	public void DomaineTemplate(Element domaine, String name) {
		out.println("	<hr>");
		out.println("	<table width=\"100%\" border=\"1\">");
		out.println("		<tr>");
		out.println("   		<td width=\"100%\" bgcolor=\"#C0C0C0\">");
		out.println("				<h2><a name=\"" + name + "\">" + name + "</a></h2>");
		out.println("			</td>");
		out.println("		</tr>");
		out.println("	</table>");
		out.println("	<hr>");
	}
	
	
	public void BibRefTemplate(Node bibRef){
		NodeList bibRefContent = bibRef.getChildNodes();
		String title = "", year = "", author = "", link = "";

		// iterating through the different elements of a bib_ref node
		for (int i=0; i<bibRefContent.getLength(); ++i){
			if (bibRefContent.item(i).getNodeType() == Node.ELEMENT_NODE){
				Node content = bibRefContent.item(i);
				
				// A switch would have been better but it seems that it doesn't work with java 1.6
				// And I don't know how to configure java 1.7 to make it work with DOM API
				if (content.getNodeName() == "title"){
					title = content.getTextContent();
				} else if (content.getNodeName() == "author"){
					author = content.getTextContent();
				} else if (content.getNodeName() == "year"){
					year = content.getTextContent();
				} else if (content.getNodeName() == "weblink"){
					link = content.getTextContent();
				} 

			}
		}
		out.println("	<h3>Titre : " 		+ title 	+ "</h3>");
		out.println("	Auteur(s) : " 		+ author 	+ "<br/>");
		out.println("	Année : " 			+ year 		+ "<br/>");
		out.println("	Lien : <a href=" 	+ link 		+ ">" 		+ link 		+ "</a><br>");
		out.println("	Commentaires: <br>");
		out.println("	<br><hr>");
	}
	
	
	
}