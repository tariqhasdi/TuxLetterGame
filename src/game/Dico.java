
package game;

/**
 *
 * @author Tariq
 */


import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author firsovol
 */
public class Dico {
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
}
