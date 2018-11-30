
package test;

import game.Dico;

/**
 *
 * @author Tariq
 */
public class TestDico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Dico dico = new Dico("");
        dico.ajouteMotADico(1, "Serge");
        dico.ajouteMotADico(1, "tariq");
        dico.ajouteMotADico(1, "rémy");
        dico.ajouteMotADico(1, "Sophie");
        dico.ajouteMotADico(1, "Majda");
        dico.ajouteMotADico(1, "Rayan");
        
        dico.ajouteMotADico(2, "Serge");
        
        dico.ajouteMotADico(3, "Rayan");
        dico.ajouteMotADico(3, "Majda");
        dico.ajouteMotADico(3, "Rémy");
        
        for ( int i = 1 ; i <= 5 ; i++ ){
            System.out.println("mot = "+dico.getMotDepuisListeNiveaux(i)+" niveu : "+i);
        }
        
        for (int i = 1; i <= 5; i++) {
            System.out.println("Niveau "+i);
            for (int j = 1; j < 100; j++) {
                System.out.println("mot = " + dico.getMotDepuisListeNiveaux(i));
            }
            System.out.println("\n");
        }
        
    }
}
