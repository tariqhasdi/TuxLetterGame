/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import env3d.Env;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Tariq
 */
public abstract class JeuRemplace {
    
    protected Env env;
    private Room room;
    private Tux tux;
    private ArrayList<Letter> lettres;
    private Profil profil;
    private Dico dico;
    
    public JeuRemplace(){
 
        // Crée un nouvel environnement
        this.env = new Env();
 
        // Instancie une Room
        this.room = new Room();
        
        // Instancie la liste des lettres
        this.lettres = new ArrayList<Letter>();
     
        // Règle la camera
        this.env.setCameraXYZ(50, 60, 175);
        this.env.setCameraPitch(-20);
 
        // Désactive les contrôles par défaut
        this.env.setDefaultControl(false);
        
        // Instancie un profil par défaut
        this.profil = new Profil("nomFicher");
        
        // Instancie le dico
        this.dico = new Dico("cheminFichierDico");
 
    }
    
    public void execute() {
 
        // pour l'instant, nous nous contentons d'appeler la méthode joue comme cela
        // et nous créons une partie vide, juste pour que cela fonctionne
                // Remplissage de dico
        
        joue(new Partie("24/11/2018 14:08","mot",1));
         
        // Affichage résulat de la partie jouer
        
        
        // Détruit l'environnement et provoque la sortie du programme 
        this.env.exit();
    }
                
    public void joue(Partie partie) {
        
        ArrayList<Letter> lettresATrouvees = new ArrayList<Letter>();
        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        this.env.setRoom(room);
        
        // Instancie un Tux
        this.tux = new Tux( this.env , this.room );
        
        // Ajout tux dans l'env.
        this.env.addObject(this.tux);

        this.dico.ajouteMotADico(1, "nounou");
        this.dico.ajouteMotADico(1, "maman");
        this.dico.ajouteMotADico(1, "papa");
        
        // récupération d'un mot de dictionnaire
        String mot = this.dico.getMotDepuisListeNiveaux(1);  
        
        if (mot.length() > 0) { // mot non vide
            // Affichage du mot à trouver
            int pas = this.room.getWidth() / (mot.length() + 1);
            for (int i = 0; i < mot.length(); i++) {
                char c = mot.charAt(i);
                Letter l;
                int x = pas * ( 1 + i );
                l = new Letter( c , x , 100 );
                lettresATrouvees.add(l);
            }
            
            for (Letter l : lettresATrouvees) {
                this.env.addObject(l);
            }
            long waitTemps = (long) (5 * Math.pow(10, 9));
            long start = System.nanoTime();
            while ( System.nanoTime() - start < waitTemps );
            
            for (Letter l : lettresATrouvees) {
                this.env.removeObject(l);
            }

            // Initialiser des valeurs pour une nouvelle partie
            for (int i = 0; i < mot.length(); i++) {
                char c = mot.charAt(i);
                Letter l;
                //if ( cubeNonVide() ){
                do {
                    double x = coordonnéeXAléatoire();
                    double z = coordonnéeZAléatoire();
                    l = new Letter(c, x, z);
                } while (lettreChevauchée(l));

                this.lettres.add(l);
                /*}
            else{
                c = ' ';
            }*/

            }

            for (Letter l : lettres) {
                env.addObject(l);
            }

            démarrePartie(partie);

            //int pas = (int )100 / ( this.lettres.size() + 1 );
            //int x = pas;
            // Boucle de jeu
            Boolean finished;
            finished = false;
            while (!finished && !this.lettres.isEmpty()) {

                // Contrôles globaux du jeu (sortie, ...)
                //1 is for escape key
                if (env.getKey() == 1) {
                    finished = true;
                }

                // Contrôles des déplacements de Tux (gauche, droite, ...)
                // ... (sera complété plus tard) ...
                // Ici, on applique les regles
                appliqueRegles(partie);

                System.out.println("Continuer ... ");

                // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
                this.tux.déplace();
                env.advanceOneFrame();
            }

            // Ici on peut calculer des valeurs lorsque la partie est terminée
            terminePartie(partie);
        }
    }
    
    protected ArrayList<Letter> getLettres(){
        return this.lettres;
    }
    
    protected void démarrePartie(Partie partie){}
    
    protected void appliqueRegles(Partie partie){}
    
    protected void terminePartie(Partie partie){}
    
    protected double distance(Letter letter ){
        return Math.sqrt (  Math.pow( this.tux.getX() - letter.getX() , 2 )  +  Math.pow( this.tux.getZ() - letter.getZ() , 2 ) );
    }
    
    protected boolean collision(Letter letter ){
        if (   this.distance(letter)   <   this.tux.getScale() + letter.getScale()   )
            return true;
        return false;
    }
    
    private int coordonnéeXAléatoire(){
        Random rand = new Random();
        return rand.nextInt( this.room.getWidth() + 1 );
    }
    
    private int coordonnéeZAléatoire(){
        Random rand = new Random();
        return rand.nextInt( this.room.getDepth() + 1 );
    }
    
    private boolean lettreChevauchée(Letter l){
        if (    l.getX() < l.getScale() 
             ||  
                l.getX() > this.room.getWidth() - l.getScale()
             ||
                l.getZ() < l.getScale() 
             ||  
                l.getZ() > this.room.getDepth() - l.getScale()
            )
                return true;
        for ( Letter lettre : this.lettres ) {
            if (    Math.abs(lettre.getX() -  l.getX() )      <      2 * l.getScale()
                &&
                    Math.abs(lettre.getZ() -  l.getZ() )      <      2* l.getScale()
                    )
                return true;
        }
        return false;
    }
    
    
    /*
    private boolean cubeNonVide(){
        Random rand = new Random();
        if ( rand.nextInt( 2 ) == 0 )
            return false;
        return true;
    }*/
   
}

