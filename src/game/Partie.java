package game;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Partie{
    
    private final String date;
    private final String mot;
    private final int niveau;
    private int trouve;
    private int temps;
    
    public Partie(String date, String mot, int niveau ){
        this.date = date;
        this.mot = mot;
        this.niveau = niveau;
        this.trouve = 0;
        this.temps = 0;
    }
    
    public Partie(Element partieElt ){
        this.date = partieElt.getElementsByTagName("date").item(0).getTextContent();
        this.mot = partieElt.getElementsByTagName("mot").item(0).getTextContent();
        this.niveau = Integer.parseInt(partieElt.getElementsByTagName("niveau").item(0).getTextContent());
        this.trouve = Integer.parseInt(partieElt.getElementsByTagName("trouve").item(0).getTextContent());
        this.temps = Integer.parseInt(partieElt.getElementsByTagName("temps").item(0).getTextContent());
    }
    
    public Element getPartie(Document doc){
        return (Element )doc.getElementsByTagName("parties");
    }
    
    public void setTrouve(int nbLettresRestantes){
            this.trouve = (int )( ( ( this.mot.length() - nbLettresRestantes ) / this.mot.length() ) * 100 )  ;
    }
    
    public void setTemps(int temps){
        this.temps = temps;
    }
    
    public int getNiveau(){
        return this.niveau;
    }
    
    public String getMot(){
        return this.mot;
    }
    
    public String toString() {
        return "-La date de partie :\n" + this.date
                + "-Le mot de cette partie :\n" + this.mot
                + "-Le niveau du mot :\n" + this.niveau
                + "-Le temps de cette partie : \n" + this.temps
                + "-Le pourcentage de la partie : \n" + this.trouve + "%";
    }
}