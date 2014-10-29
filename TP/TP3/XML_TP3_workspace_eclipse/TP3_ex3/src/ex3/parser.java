package ex3;

/*
 * Created on 26 nov. 2003
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 * 
 * Modified on 25 oct. 2014 - Adrien Fabre
 * Purpose: XML TP3 
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class parser {

	//Rem changer Package par ModelElement
	public void parse(String _fichier)
		throws SAXException, ParserConfigurationException, IOException {

		// Charger le document
		FileInputStream _xml_input_file = new FileInputStream(_fichier);

		parse(_xml_input_file);
	}

	public void parse(InputStream _xml_input_file)
		throws SAXException, ParserConfigurationException, IOException {
	
				
		//instancier le contrcuteur de parseurs
		DocumentBuilderFactory _factory = DocumentBuilderFactory.newInstance();

		//ignorer les commentaires dans les fichiers XML parsés
		_factory.setIgnoringComments(true);
		
		// créer un parseur
		DocumentBuilder _builder = _factory.newDocumentBuilder();

		// Charger le document
		Document doc = _builder.parse(_xml_input_file);

		// creation of the new document
		transform(doc);
		
	}
	
	
	// main transforming function
	public int transform (Document doc){
		
		// Getting the persons under root node
		NodeList personList = doc.getDocumentElement().getChildNodes();
		
		// the resulting file
		PrintWriter out;
		try {
			out = new PrintWriter( new FileOutputStream("./gender-sorted.xml") );
			
			// insertion of the DTD
			insertDTD(out);
			
			out.println("<list>");
			
			// for each node containing a person, we transform it
			for (int i=0; i<personList.getLength(); ++i){
				Node n = personList.item(i);
				
				if (isElementNode(n)){
					personTemplate(out, n, 0);
				}
			}		
			
			out.println("</list>");
	        out.close();
	        out.flush();
	        	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public void insertDTD (PrintWriter out){
		String XMLDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";
		String internalDTD = "<!DOCTYPE list [ "
				+ "\n\t<!ELEMENT list (man | woman)*>"
				+ "\n\t<!ELEMENT man (sons, daughters)>"
				+ "\n\t<!ATTLIST man name CDATA #REQUIRED>"
				+ "\n\t<!ELEMENT woman (sons, daughters)>"
				+ "\n\t<!ATTLIST woman name CDATA #REQUIRED>"
				+ "\n\t<!ELEMENT sons (man)*>"
				+ "\n\t<!ELEMENT daughters (woman)*>"
				+ "\n]>\n\n";
		out.print(XMLDeclaration);
		out.print(internalDTD);
	}
	
	// This template is used to write a new element man or woman in the new xml
	// It is recursively called to write the children
	// the integer indent is used to correctly indent each element (incremented at each recursive call)
	public void personTemplate(PrintWriter out, Node person, int indent){		
			
		// These nodes contains the node name and the node children of a man
		// (We know their index in the child list thanks to a test)
		Node name 		= person.getChildNodes().item(1);
		Node children 	= person.getChildNodes().item(3);
		
		
		System.out.println("\nOn traite " + name.getTextContent());
		
		// The two next lists will contains the sons and daughter of the people in the current node
		List<Node> sons 		= new ArrayList<Node>();
		List<Node> daughters	= new ArrayList<Node>();
		
		// Sorting the children in the lists
		NodeList childrenList = children.getChildNodes();
		
		
		for (int i=0; i<childrenList.getLength(); ++i){
			Node n 		= childrenList.item(i);
					
			if (isElementNode(n)){
				int gender 	= getGender(n);
								
				if (gender == 1){
					daughters.add(n);
				} else if (gender == -1){
					sons.add(n);
				}
			}
		}	
		
		System.out.println("Cette personne a " + sons.size() + " fils");
		System.out.println("Cette personne a " + daughters.size() + " fille");
		
		// indenting string
		String indt = "\t";
		
		for (int i=0; i<indent; ++i){
			indt = indt + "\t";
		}
		String indt2 = indt + "\t";
		
		// getting the gender of the current person
		int personGender = getGender(person);
		String gender = new String();
		if (personGender==-1){
			gender="man";
			System.out.println("Cette personne est un homme");
		}else{
			gender="woman";
			System.out.println("Cette personne est une femme");
		}
		
		
		// printing in the file the new person and the children
		// note the use od the strings indt and indt2
		out.println(indt 	+ "<" + gender + " name=\"" + name.getTextContent() + "\">");
		out.println(indt2 	+ "<sons>");
		
		for (Node s : sons){
			personTemplate(out, s, indent+2);
		}
		
		out.println(indt2 	+ "</sons>");
		out.println(indt2 	+ "<daughters>");
		
		for (Node d : daughters){
			personTemplate(out, d, indent+2);
		}
		
		out.println(indt2 	+ "</daughters>");
		out.println(indt 	+ "</" + gender + ">");
					
		out.println();
	}
	
	
	// This method simply test is the node is of type Element.ELEMENT_NODE
	// return 	true 	if it is
	//			false 	if not
	public boolean isElementNode (Node n){
		return (n.getNodeType() == Element.ELEMENT_NODE);
	}
	
	// This method return the gender contained in the attribute of a node
	// If the node doesn't have an attribute named 'gender' or as an attribute 'gender' which doesn't contains 'M' or 'F' it return an error
	// return:	1 	for a woman
	//			-1 	for a man
	//			0 	for an error
	public int getGender (Node n){
		
		if (n.getNodeType() == Element.ELEMENT_NODE){
			if(isElementNode(n)){
			
				if (n.getAttributes().item(0).toString().equals("gender=\"M\"")){
					return -1;
				} else if (n.getAttributes().item(0).toString().equals("gender=\"F\"")) {
					return 1;
				} else {
					System.out.println("Error: Wrong XML structure. Can not define the gender");
					return 0;
				}
			}
		}
		return 0;
	}
}