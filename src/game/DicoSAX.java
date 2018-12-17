/**
 *
 * @author Tariq
 */
package game;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.xml.sax.*;

public class DicoSAX implements ContentHandler {
    
    public Document _doc;
    
    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    
    private String cheminFichierDico;

    //flags nous indiquant la position du parseur 
    private boolean inMot;
    private boolean inDictionnaire;
    
    //buffer nous permettant de récupérer les données  
    private StringBuffer buffer;
    private Dico dico;
    private int niveau;
    
    public DicoSAX(){ 
		super(); 
	} 
    
   
    public Dico getDico(){
        return this.dico;
    }
    public void setDico(Dico dict){
        this.dico = dict;
    }
    
    
    //détection d'ouverture de balise 
	public void startElement(String uri, String localName, 
			String qName, Attributes attributes) throws SAXException{ 
		if(qName.equals("dictionnaire")){ 
			inDictionnaire = true; 
		}else if(qName.equals("mot")){ 
			try{ 
				niveau = Integer.parseInt(attributes.getValue("niveau")); 
			}catch(Exception e){ 
				//erreur, le contenu de id n'est pas un entier 
				throw new SAXException(e); 
			} 
			buffer = new StringBuffer(); 
			inMot = true; 
		}
	} 
	//détection fin de balise 
	public void endElement(String uri, String localName, String qName) 
			throws SAXException{ 
		if(qName.equals("dictionnaire")){ 
			inDictionnaire = false; 
		}else if(qName.equals("mot")){ 
                       
                        dico.ajouteMotADico(niveau, buffer.toString());
		System.out.println(niveau+" | " + buffer.toString()); 
			buffer = null; 
			inMot = false; 
                }else{ 
			//erreur, on peut lever une exception 
			throw new SAXException("Balise "+qName+" inconnue."); 
		}           
	} 
	//détection de caractères 
	public void characters(char[] ch , int start, int length ) throws SAXException{ 
		String lecture = new String( ch , start , length ); 
		if ( buffer != null ) buffer.append(lecture);        
	} 
	//début du parsing 
	public void startDocument() throws SAXException {
                
		System.out.println("Début du parsing"); 
	} 
	//fin du parsing 
	public void endDocument() throws SAXException { 
		System.out.println("Fin du parsing"); 
	}    
    
}

