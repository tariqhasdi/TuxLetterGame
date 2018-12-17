package game;
/**
 *
 * @author Tariq
 */

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.util.ArrayList;
import java.util.Random;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class Dico {
    
    public Document _doc;
    
    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    
    private String cheminFichierDico;
    
    public Dico(String cheminFichierDico){
        listeNiveau1 = new ArrayList<String>();
        listeNiveau2 = new ArrayList<String>();
        listeNiveau3 = new ArrayList<String>();
        listeNiveau4 = new ArrayList<String>();
        listeNiveau5 = new ArrayList<String>();
        
        this.cheminFichierDico = cheminFichierDico;
        
        _doc = fromXML(cheminFichierDico);

        NodeList dicoNodeList = _doc.getElementsByTagName("ns1:mot"); 
        
        System.out.println("nm de mot dans dic est "+dicoNodeList.getLength());
        
        for(int i =0 ; i <dicoNodeList.getLength(); i++) 
        {
            
            int j = 2 * i + 1;
            //int niveau = Integer.parseInt(dicoNodeList.item(j).getAttributes().getNamedItem("niveau").getNodeValue() );
            int niveau = Integer.parseInt(dicoNodeList.item(i).getAttributes().getNamedItem("niveau").getNodeValue());
            String mot = new String( dicoNodeList.item(i).getTextContent()  );
                    //.item(i).getTextContent();
            switch (niveau) {
                    case 1 :
                        this.listeNiveau1.add(mot);
                        break;
                    case 2 :
                        this.listeNiveau2.add(mot);
                        break;
                    case 3 :
                        this.listeNiveau3.add(mot);
                        break;
                    case 4 :
                        this.listeNiveau4.add(mot);
                        break;
                    case 5 :
                        this.listeNiveau5.add(mot);
                        break;
            }     
        }
    }
    
    public String getMotDepuisListeNiveaux(int niveau){   
        switch (vérifieNiveau(niveau)) {
            case 1:
                return this.getMotDepuisListe(listeNiveau1);
            case 2:
                return this.getMotDepuisListe(listeNiveau2);
            case 3:
                return this.getMotDepuisListe(listeNiveau3);
            case 4:
                return this.getMotDepuisListe(listeNiveau4);
            case 5:
                return this.getMotDepuisListe(listeNiveau5);
            default :
                return this.getMotDepuisListe(listeNiveau1);
        }
    }
    
    public void ajouteMotADico(int niveau, String mot) {
        switch (vérifieNiveau(niveau)) {
            case 1:
                this.listeNiveau1.add(mot);
                break;
            case 2:
                this.listeNiveau2.add(mot);
                break;
            case 3:
                this.listeNiveau3.add(mot);
                break;
            case 4:
                this.listeNiveau4.add(mot);
                break;
            case 5:
                this.listeNiveau5.add(mot);
                break;
        }
    }
    
    public String getCheminFichierDico(){
        return this.cheminFichierDico;
    }
    
    private int vérifieNiveau(int niveau){
        if(niveau >= 1 && niveau <= 5){
            return niveau;
        }
        return 1;
    }
    
    private String getMotDepuisListe(ArrayList<String> list){
        String mot = "vide";
        Random rand = new Random();
        if ( !list.isEmpty() ){
            int nbAleatoire = rand.nextInt( list.size() );
            mot = list.get(nbAleatoire);
        }
        return mot;
    }
    
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
}
