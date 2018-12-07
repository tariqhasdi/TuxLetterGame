
package game;

import env3d.Env;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


/**
 *
 * @author Tariq
 */

public class JeuDevineLeMotOrdre extends Jeu{
    
    private final int TEMPSLIMITE = 20;

    private int nbLettresRestantes;
    private Chronomètre chrono;
    
    public JeuDevineLeMotOrdre(){
        super();
        this.chrono = new Chronomètre(TEMPSLIMITE);
    }
    
    protected void démarrePartie(Partie partie){
        this.chrono.start();
        this.nbLettresRestantes = this.getLettres().size();
    }
    
    protected boolean appliqueRegles(Partie partie) {
        if ( this.chrono.remainsTime() && this.nbLettresRestantes > 0 ) {
            if (this.tuxTrouveLettre()) {
                this.nbLettresRestantes--;
                Env env = getEnv();
                env.removeObject(this.getLettres().get(0));
                getLettres().remove(0);
            }
        } else {
            // instructions pour arrêter le jeu
            return true;
        }
        return false;
    }
    
    protected void terminePartie(Partie partie){
        this.chrono.stop();
        int temps = (int )this.chrono.getTime();
        if ( temps < TEMPSLIMITE )
            partie.setTemps(temps);
        partie.setTrouve(nbLettresRestantes);
        
    }
    
    private boolean tuxTrouveLettre(){
        ArrayList<Letter> lettres = this.getLettres();
        if ( !lettres.isEmpty() ){
            Letter l = lettres.get(0);
            if (   collision(l)  ){
                return true;        
            }
        }
        return false;
    }
    
    private int getNbLettresRestantes(){
        return this.nbLettresRestantes;
    }
    
    private int getTemps(){
        return this.chrono.getSeconds();
    }
    
}
