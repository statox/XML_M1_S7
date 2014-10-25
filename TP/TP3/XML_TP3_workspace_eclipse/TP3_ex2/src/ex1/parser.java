package ex1;

/*
 * Created on 26 nov. 2003
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 * 
 *  Modified on 25 oct. 2014 - Adrien Fabre
 *  Purpose: XML TP3
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
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

		boolean again=true;
		do{
			// Asking what the user want to do
			System.out.println("What do you want to do?");
			System.out.println("	1. Read an element");
			System.out.println("	2. Delete an element");
			System.out.println("	3. Quit");
			
			Scanner sc 		= new Scanner(System.in);
			int choice 		= 0;
			String name 	= "";
			do{
				choice = sc.nextInt();
				
				
				if (choice==1){		// choice: Read
					Scanner sc1 = new Scanner(System.in);
					System.out.println("Please input the name of the element: ");
					name = sc1.nextLine();
					
					readElement(doc.getDocumentElement(), name);
						
				} else if (choice == 2){	// choice: delete
					Scanner sc1 = new Scanner(System.in);
					System.out.println("Please input the name of the element: ");
					name = sc1.nextLine();
					
					System.out.println("\n\nDocument before deletion:\n");
					try {
						printToConsole(toWriter(doc));
					} catch (Exception e) {
						e.printStackTrace();
					}
	
					deleteElement(doc, name);
					System.out.println("\n\nDocument after deletion:\n");
					
					try {
						printToConsole(toWriter(doc));
						printtoFile(toWriter(doc));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (choice == 3){
					System.out.println("Exit");
					again=false;
				}else {	// wrong choice
					System.out.println("Please input 1 or 2");
				}
				
			}while (choice < 1 || choice > 3 );
		}while (again);
	}
	
	// recursive method to iterate through all the nodes of the Document
	public void readElement (Node node, String name){
		
		// output the element if it matches the requested one
		if (node.getNodeName().equals(name)){
			System.out.println(node.getNodeName() + " : " + node.getTextContent());
		}
	    

	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	            //calls this method for all the children which is Element
	            readElement(currentNode, name);
	        }
	    }
	}	
	
	// iterative method to iterate through all the nodes of the Document
	public void deleteElement (Document doc, String name){
		// getting the elements matching the requested one
		NodeList nodeList = doc.getElementsByTagName(name);
		
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node node = nodeList.item(i);
	        
	        System.out.println(node.getNodeName() + "\t:\t" + node.getTextContent());
	        
	        // removing the node
	        node.getParentNode().removeChild(node);
	    }

        // Normalize the DOM tree, puts all text nodes in the
        // full depth of the sub-tree underneath this node
        doc.normalize();
        
	}
	
	// utility to put the document in a writer to print it somewhere
	public static final Writer toWriter(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        
        return out;
        
    }
	
	// print the document on the screen
	public void printToConsole (Writer out){
		System.out.println(out.toString());
	}
	
	// print the document on a physical file
	public void printtoFile (Writer out){
		PrintWriter outFile;
		try {
			outFile = new PrintWriter( new FileOutputStream("./result.xml") );
			
			outFile.println(out.toString());
	        
	        outFile.close();
	        outFile.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
	}
}