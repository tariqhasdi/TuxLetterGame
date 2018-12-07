package game;

/**
 *
 * @author Tariq
 */

import env3d.Env;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author gladen
 */
public abstract class Jeu {

    // Une énumération pour définir les choix de l'utilisateur
    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    private Env env;
    private Room mainRoom;
    private Tux tux;
    private ArrayList<Letter> lettres;
    private Profil profil;
    private Dico dico;
    
    private final Room menuRoom;
    EnvText textNomJoueur;
    EnvText textMenuQuestion;
    EnvText textMenuJeu1;
    EnvText textMenuJeu2;
    EnvText textMenuJeu3;
    EnvText textMenuJeu4;
    EnvText textMenuPrincipal1;
    EnvText textMenuPrincipal2;
    EnvText textMenuPrincipal3;
    
    EnvText textNiveauQuestion;
    EnvText textNiveau1;
    EnvText textNiveau2;
    EnvText textNiveau3;
    EnvText textNiveau4;
    EnvText textNiveau5;


    public Jeu() {

        // Crée un nouvel environnement
        this.env = new Env();

        // Instancie une Room
        this.mainRoom = new Room();

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        this.env.setCameraXYZ(50, 60, 175);
        this.env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        this.env.setDefaultControl(false);
        
        // Instancie le dico
        this.dico = new Dico("cheminFichierDico");

        // Textes affichés à l'écran
        textMenuQuestion = new EnvText(env, "Voulez vous ?", 200, 300);
        textMenuJeu1 = new EnvText(env, "1. Commencer une nouvelle partie ?", 250, 280);
        textMenuJeu2 = new EnvText(env, "2. Charger une partie existante ?", 250, 260);
        textMenuJeu3 = new EnvText(env, "3. Sortir de ce jeu ?", 250, 240);
        textMenuJeu4 = new EnvText(env, "4. Quitter le jeu ?", 250, 220);
        textNomJoueur = new EnvText(env, "Choisissez un nom de joueur : ", 200, 300);
        textMenuPrincipal1 = new EnvText(env, "1. Charger un profil de joueur existant ?", 250, 280);
        textMenuPrincipal2 = new EnvText(env, "2. Créer un nouveau joueur ?", 250, 260);
        textMenuPrincipal3 = new EnvText(env, "3. Sortir du jeu ?", 250, 240);

        textNiveauQuestion = new EnvText(env, "Choisir un niveau  ?", 200, 300);
        textNiveau1 = new EnvText(env, "1. Niveau 1", 250, 280);
        textNiveau2 = new EnvText(env, "2. Niveau 2", 250, 260);
        textNiveau3 = new EnvText(env, "3. Niveau 3", 250, 240);
        textNiveau4 = new EnvText(env, "4. Niveau 4", 250, 220);
        textNiveau5 = new EnvText(env, "5. Niveau 5", 250, 200);
    }

    /**
     * Gère le menu principal
     *
     */
    public void execute() {
        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
        env.exit();
    }

    /**
     * Teste si une code clavier correspond bien à une lettre
     *
     * @return true si le code est une lettre
     */
    private Boolean isLetter(int codeKey) {
        return codeKey == Keyboard.KEY_A || codeKey == Keyboard.KEY_B || codeKey == Keyboard.KEY_C || codeKey == Keyboard.KEY_D || codeKey == Keyboard.KEY_E
                || codeKey == Keyboard.KEY_F || codeKey == Keyboard.KEY_G || codeKey == Keyboard.KEY_H || codeKey == Keyboard.KEY_I || codeKey == Keyboard.KEY_J
                || codeKey == Keyboard.KEY_K || codeKey == Keyboard.KEY_L || codeKey == Keyboard.KEY_M || codeKey == Keyboard.KEY_N || codeKey == Keyboard.KEY_O
                || codeKey == Keyboard.KEY_P || codeKey == Keyboard.KEY_Q || codeKey == Keyboard.KEY_R || codeKey == Keyboard.KEY_S || codeKey == Keyboard.KEY_T
                || codeKey == Keyboard.KEY_U || codeKey == Keyboard.KEY_V || codeKey == Keyboard.KEY_W || codeKey == Keyboard.KEY_X || codeKey == Keyboard.KEY_Y
                || codeKey == Keyboard.KEY_Z;
    }
    
    private Boolean isNiveau(int codeKey) {
        return codeKey == Keyboard.KEY_1 || codeKey == Keyboard.KEY_2 
                || codeKey == Keyboard.KEY_3 || codeKey == Keyboard.KEY_4
                || codeKey == Keyboard.KEY_5 ;
    }

    /**
     * Récupère une lettre à partir d'un code clavier
     *
     * @return une lettre au format String
     */
    private String getLetter(int letterKeyCode) {
        Boolean shift = false;
        if (this.env.getKeyDown(Keyboard.KEY_LSHIFT) || this.env.getKeyDown(Keyboard.KEY_RSHIFT)) {
            shift = true;
        }
        env.advanceOneFrame();
        String letterStr = "";
        if ((letterKeyCode == Keyboard.KEY_SUBTRACT || letterKeyCode == Keyboard.KEY_MINUS)) {
            letterStr = "-";
        } else {
            letterStr = Keyboard.getKeyName(letterKeyCode);
        }
        if (shift) {
            return letterStr.toUpperCase();
        }
        return letterStr.toLowerCase();
    }

    /**
     * Permet de saisir le nom d'un joueur et de l'affiche à l'écran durant la saisie
     *
     * @return le nom du joueur au format String
     */
    private String getNomJoueur() {
        textNomJoueur.modify("Choisissez un nom de joueur : ");
        int touche = 0;
        String nomJoueur = "";
        while (!(nomJoueur.length() > 0 && touche == Keyboard.KEY_RETURN)) {
            touche = 0;
            while (!isLetter(touche) && touche != Keyboard.KEY_MINUS && touche != Keyboard.KEY_SUBTRACT && touche != Keyboard.KEY_RETURN) {
                touche = env.getKey();
                env.advanceOneFrame();
            }
            if (touche != Keyboard.KEY_RETURN) {
                if ((touche == Keyboard.KEY_SUBTRACT || touche == Keyboard.KEY_MINUS) && nomJoueur.length() > 0) {
                    nomJoueur += "-";
                } else {
                    nomJoueur += getLetter(touche);
                }
                textNomJoueur.modify("Choisissez un nom de joueur : " + nomJoueur);
            }
        }
        textNomJoueur.erase();
        return nomJoueur;
    }

    
    /**
     * Menu principal
     *
     * @return le choix du joueur
     */
    private MENU_VAL menuPrincipal() {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;

        // restaure la room du menu
        env.setRoom(menuRoom);

        // affiche le menu principal
        textMenuQuestion.display();
        textMenuPrincipal1.display();
        textMenuPrincipal2.display();
        textMenuPrincipal3.display();

        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        // efface le menu
        textMenuQuestion.erase();
        textMenuPrincipal1.erase();
        textMenuPrincipal2.erase();
        textMenuPrincipal3.erase();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                // charge le profil de ce joueur si possible
                this.profil = new Profil(nomJoueur);
                if (  profil.charge(nomJoueur) ) {
                    // lance le menu de jeu et récupère le choix à la sortie de ce menu de jeu
                    choix = menuJeu();
                } else {
                    // sinon continue (et boucle dans ce menu)
                    choix = MENU_VAL.MENU_CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur,dateCourant());
                // lance le menu de jeu et récupère le choix à la sortie de ce menu de jeu
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                // le choix est de sortir du jeu (quitter l'application)
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }

    
    /**
     * Menu de jeu
     *
     * @return le choix du joueur une fois la partie terminée
     */
    private MENU_VAL menuJeu() {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);

            // affiche le menu de jeu
            textMenuQuestion.display();
            textMenuJeu1.display();
            textMenuJeu2.display();
            textMenuJeu3.display();
            textMenuJeu4.display();

            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // efface le menu
            textMenuQuestion.erase();
            textMenuJeu1.erase();
            textMenuJeu2.erase();
            textMenuJeu3.erase();
            textMenuJeu4.erase();

            // restaure la room du jeu
            env.setRoom(this.mainRoom);

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                    int niveau = menuNiveau();
                    //this.dico = new Dico("src/xml/dico.xml");
                    //String mot = this.dico.getMotDepuisListeNiveaux(niveau);
                    String mot = motATrouve(niveau);
                    mot = this.dico.getMotDepuisListeNiveaux(niveau);
                    String date = dateCourant();
                    
                    System.out.println(niveau);
                    System.out.println(mot);
                    System.out.println(date);
                    
                    // .......... dico ...........
                    // crée un nouvelle partie
                    partie = new Partie( date , mot , niveau );
                    
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil .........
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;
                    /*
                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante
                    // demander de fournir une date
                    // ..........
                    // tenter de trouver une partie à cette date
                    partie = new Partie(//?!!#??);
                    // .......
                    // Si partie trouvée, recupère le mot de la partie existante, sinon ???
                    // ..........
                    // ..........
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil ........
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;
                */
                    
                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }
    
    private int menuNiveau() {
        int niveau = 0;

        // restaure la room du menu
        env.setRoom(menuRoom);

        // affiche le menu des niveaux
        textNiveauQuestion.display();
        textNiveau1.display();
        textNiveau2.display();
        textNiveau3.display();
        textNiveau4.display();
        textNiveau5.display();

        // vérifie qu'une touche 1, 2, 3, 4 ou 5 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2
                || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4
                || touche == Keyboard.KEY_5)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        // efface le menu des niveaux
        textNiveauQuestion.erase();
        textNiveau1.erase();
        textNiveau2.erase();
        textNiveau3.erase();
        textNiveau4.erase();
        textNiveau5.erase();

        // restaure la room du jeu
        env.setRoom(this.mainRoom);
        
        switch ( touche )
        {
            case Keyboard.KEY_1 :
                                    niveau = 1;
                                    break;
            case Keyboard.KEY_2 :
                                    niveau = 2;
                                    break;
            case Keyboard.KEY_3 :
                                    niveau = 3;
                                    break;
            case Keyboard.KEY_4 :
                                    niveau = 4;
                                    break;
            case Keyboard.KEY_5 :
                                    niveau = 5;
                                    break;
        }
        return niveau;
    }

    /**
     * Méthode joue(partie : Partie) : void
     * 
     */
    public void joue(Partie partie) {
        
        // Instancie la liste des lettres
        this.lettres = new ArrayList<Letter>();
        
        ArrayList<Letter> lettresATrouvees = new ArrayList<Letter>();// Pour un premier affichage du mot à trouver avant de commencer de jouer
        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        this.env.setRoom(mainRoom);
        
        // Instancie un Tux
        this.tux = new Tux( this.env , this.mainRoom );
        
        // Ajout tux dans l'env.
        this.env.addObject(this.tux);

        // récupération d'un mot de dictionnaire
        String mot = partie.getMot();
        
        // Affichage du mot à trouver avant de commencer à jouer
        if (mot.length() > 0) { // mot non vide 
            int pas = this.mainRoom.getWidth() / (mot.length() + 1);
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
            
            // Actualisation de la vue
            env.advanceOneFrame();
            
            // Garder un état de vue pendant un temps waitTemps défini ci-dessous4
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

            }

            for (Letter l : lettres) {
                env.addObject(l);
            }

            démarrePartie(partie);

            Boolean finished;
            finished = false;
            while (!finished && !this.lettres.isEmpty() ) {

                // Contrôles globaux du jeu (sortie, ...)
                //1 is for escape key
                if (env.getKey() == 1) {
                    finished = true;
                }

                // Contrôles des déplacements de Tux (gauche, droite, ...)
                // ... (sera complété plus tard) ...
                // Ici, on applique les regles
                finished = appliqueRegles(partie);

                // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
                this.tux.déplace();
                env.advanceOneFrame();
            }

            // Ici on peut calculer des valeurs lorsque la partie est terminée
            terminePartie(partie);
        }
    }
    
    private String motATrouve(int niveau){
        
        this.dico.ajouteMotADico(1, "nounou");
        this.dico.ajouteMotADico(1, "maman");
        this.dico.ajouteMotADico(1, "papa");
        this.dico.ajouteMotADico(1, "doudou");
        
        this.dico.ajouteMotADico(2, "calin");
        this.dico.ajouteMotADico(2, "manger");
        this.dico.ajouteMotADico(2, "boire");
        
        // récupération d'un mot de dictionnaire
        return this.dico.getMotDepuisListeNiveaux(niveau);
    }
    
    private String dateCourant(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    protected ArrayList<Letter> getLettres(){
        return this.lettres;
    }
    
    protected Env getEnv(){
        return this.env;
    }
    
    /**
     * Méthode abstraite démarrePartie(partie : Partie) : void
     * 
     */
    abstract void démarrePartie(Partie partie);
    
    /**
     * Méthode abstraite appliqueRègles(partie : Partie) : void
     * 
     */
    abstract boolean appliqueRegles(Partie partie);
    
    /**
     * Méthode abstraite terminePartie(partie : Partie) : void
     * 
     */
    abstract void terminePartie(Partie partie);
    
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
        return rand.nextInt(this.mainRoom.getWidth() + 1 );
    }
    
    private int coordonnéeZAléatoire(){
        Random rand = new Random();
        return rand.nextInt(this.mainRoom.getDepth() + 1 );
    }
    
    private boolean lettreChevauchée(Letter l){
        if (    l.getX() < l.getScale() 
             ||  
                l.getX() > this.mainRoom.getWidth() - l.getScale()
             ||
                l.getZ() < l.getScale() 
             ||  
                l.getZ() > this.mainRoom.getDepth() - l.getScale()
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
    

