package ex1;

/*
 * Created on 26 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 * 
 * Modified on 25 oct. 2014 - Adrien Fabre
 * Purpose: XML TP3 
 * 
 */


/**
 * @author Salim
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 * 
 * 
 * 
 */

public class Transf {

	public static void main(String[] args) {
		try {
			System.out.println("debut");
			parser parseur  = new parser();
			
			String filename = "./mail.xml";
			
			parseur.parse(filename);		
			
			
			System.out.println("fin");
		
		}
		catch (Exception e ){
			e.printStackTrace();
		}
		
	}
}
