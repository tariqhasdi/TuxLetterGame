package game;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;

/*
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
*/
public class Profil {
    
    public Document _doc;

    private String nom;
    private String dateNaissance;
    private String avatar;
    private ArrayList<Partie> parties;

    // Cree un nouveau profil
    public Profil(String nomJoueur, String dateNaissance ) {
        this.nom = nomJoueur;
        this.dateNaissance = dateNaissance;
        this.avatar = "";
        this.parties = new ArrayList<Partie>();
    }
    
    public Profil(String nomFichier ) {
        _doc = fromXML(nomFichier);
        // parsing à compléter
        // ?!#?!
        
          
        this.nom = _doc.getElementsByTagName("ns1:nom").item(0).getTextContent();
        this.dateNaissance = _doc.getElementsByTagName("ns1:anniversaire").item(0).getTextContent();
        this.avatar = _doc.getElementsByTagName("ns1:avatar").item(0).getTextContent();
        this.parties = null;
        //this.parties = getParties(_doc.getElementsByTagName("ns1:parties") );

    }
    /*
    private ArrayList<Partie> getParties(NodeList partieNodeList){
        ArrayList<Partie> parties = new ArrayList<Partie>();
        int taille = partieNodeList.getLength();
        for ( int i = 0 ; i < taille ; i++ ){
            parties.add( new Partie((Element )partieNodeList.item(i) ) );
        
        return parties;   
    }*/
    
    public boolean charge(String nomJoueur){
        String dateNaissance = "";
        Profil profil = new Profil(nomJoueur, dateNaissance);
        return true;   
    }

    // Cree un DOM à partir d'un fichier XML
    public Document fromXML(String nomFichier) {
        Document document = null;
        DOMParser parser = null;
        try {
            parser = new DOMParser();
            parser.parse(nomFichier);
            return document = parser.getDocument();
            //return XMLUtil.DocumentFactory.fromFile(nomFichier);
        }catch(Exception e){
          
        }
        /*} catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return null;
    }
    
    public String toString(){
        return "nom : " + this.nom  
                + "\n, dateNaissance : " + this.dateNaissance
                + "\n, avatar : "+this.avatar;
    }
/*
    // Sauvegarde un DOM en XML
    public void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(_doc, nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/
    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }

    /// Takes a date in profile format: dd/mm/yyyy and returns a date
    /// in XML format (i.e. ????-??-??)
    public static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }

} 