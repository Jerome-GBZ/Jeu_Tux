/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env.EnvTextMap;
import env3d.Env;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author riad7
 */
public abstract class Jeu {
    
    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }
    
    //CONSTANTES
    private static final String FILEPATH_PROFIL = "data/XML/profil.xml";
    //VAR
    private Env env;
    private Room room;
    private Profil profil;
    private Tux tux;
    public ArrayList<Letter> lettres;
    private final Dico dico;
    protected EnvTextMap menuText;                         //text (affichage des texte du jeu)
    protected EnvTextMap gameText;                         //text (affichage des texte du jeu)
    private final Room mainRoom;
    private final Room menuRoom;
    
    
    public Jeu() {
        // Crée un nouvel environnement
        env = new Env();
        
        // Instancie une Room du menu principal
        mainRoom = new Room();
        
        // Instancie une Room du menu
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");
        
        //Instancie la room du tux
        room = new Room();
        
        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        profil = new Profil("data/XML/profil.xml");
        
        //Instancie le conteneur de lettres
        lettres = new ArrayList();
        // Dictionnaire
        dico = new Dico("dico.xml");

        // instancie le menuText
        menuText = new EnvTextMap(env);
        
        // Textes affichés à l'écran
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);
    }
    
    public void execute(){
        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        System.out.println("game.Jeu.execute()");
        
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        
        this.env.setDisplayStr("Au revoir !", 300, 30);
        env.exit();
    }
    
    
    // fourni
    private String getNomJoueur() {
        String nomJoueur = "";
        menuText.getText("NomJoueur").display();
        nomJoueur = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nomJoueur;
    }

    
    // fourni, à compléter
    private MENU_VAL menuJeu() {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);
            // affiche menu
            menuText.getText("Question").display();
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu2").display();
            menuText.getText("Jeu3").display();
            menuText.getText("Jeu4").display();
            
            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 
            || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 
            || touche == Keyboard.KEY_4)) {
                if(env.getKeyDown() == 75) {
                    //Touche 4
                    touche = 5;
                } else if(env.getKeyDown() == 79) {
                    //Touche 1
                    touche = 2;
                } else if (env.getKeyDown() == 80) {
                    //Touche 2
                    touche = 3;
                }  else if (env.getKeyDown() == 81) {
                    //Touche 3
                    touche = 4;
                } else {
                    touche = 0;
                }
                
                env.advanceOneFrame();
            }

            // nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu2").clean();
            menuText.getText("Jeu3").clean();
            menuText.getText("Jeu4").clean();

            // restaure la room du jeu
            env.setRoom(mainRoom);

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                    // .......... dico.******
                    // crée un nouvelle partie
                    partie = new Partie("2021-09-7", "test", 2);
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    profil.sauvegarder(FILEPATH_PROFIL);
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante
                    partie = new Partie("2018-09-7", "test", 1); //XXXXXXXXX
                    // Recupère le mot de la partie existante
                    // ..........
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

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

    private MENU_VAL menuPrincipal() {
        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;

        // restaure la room du menu
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("Principal1").display();
        menuText.getText("Principal2").display();
        menuText.getText("Principal3").display();
               
        // vérifie qu'une touche 1, 2 ou 3 est pressée
        System.out.println("game.Jeu.menuPrincipal()");
        int touche = 0;
        
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            if(env.getKeyDown() == 79 || touche == (Keyboard.KEY_NUMPAD1 + Keyboard.KEY_NUMLOCK)) {
                touche = 2;
            } else if (env.getKeyDown() == 80) {
                touche = 3;
            }  else if (env.getKeyDown() == 81) {
                touche = 4;
            } else {
                touche = 0;
            }
            
            env.advanceOneFrame();
        }

        System.out.println("Touche sortante : "+touche);

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                // charge le profil de ce joueur si possible
                if (profil.charge(nomJoueur)) {
                    choix = menuJeu();
                } else {
                    choix = MENU_VAL.MENU_SORTIE;//CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur, "2000/01/30");
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }
    
    public void joue(Partie partie){
        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        env.setRoom(room);
 
        // Instancie un Tux
        tux = new Tux(env, room);
        String mot = "salut";
        char[] tab = mot.toCharArray();
        int spacing = 15; // Espace de 15 pixel
        int middleWord = mot.length()/2; // Moitié d'un mot
        int startPos = (room.getWidth() / 2) - (middleWord * 20);// Position de départ
        for(int i=0; i<mot.length(); i++){
           Letter l = new Letter(tab[i], startPos,50, room);
           lettres.add(l);
           env.addObject(l); 
           startPos+=spacing;
        }
        
        env.addObject(tux);
         
        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        démarrePartie(partie);
         
        // Boucle de jeu
        Boolean finished;
        finished = false;
        gameText = new EnvTextMap(env);
        
        while (!finished) {
            finished = appliqueTemps(new OnJeuCallback() {
                @Override
                public void onTimeSpentListener(int time) {
                    // TODO Auto-generated method stub
                    if(gameText.getText("time") != null)
                        gameText.getText("time").clean();
                    gameText.addText("Time: "+time, "time", 10, 420);
                    gameText.getText("time").display();
                    
                }
            });
            
            tux.déplace();
            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true;
            }
 
            // Ici, on applique les regles
            appliqueRegles(partie);
 
            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }
 
        if(gameText.getText("time") != null)
            gameText.getText("time").clean();
        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);
    }
    
    protected abstract void démarrePartie(Partie partie);
    
    protected abstract void appliqueRegles(Partie partie);
    
    protected abstract void terminePartie(Partie partie);
    
    protected abstract boolean appliqueTemps(OnJeuCallback callback);
    
    protected double distance(Letter letter){
        //Cordonnées cartesienne de notre lettre
        double x = letter.getX();
        double y = letter.getY();
        double z = letter.getZ();

        
        //Cordonnées cartesuenne de notre tux
        double xT = tux.getX();
        double yT = tux.getY();
        double zT = tux.getZ();
        
        //Formule DISTANCE = racine((x-xT)²+ (y-yT)²)
        System.out.println("distance = "+ Math.sqrt(Math.pow(x-xT, 2)+Math.pow(y-yT, 2)+Math.pow(z-zT, 2))+"\n");
        return Math.sqrt(Math.pow(x-xT, 2)+Math.pow(y-yT, 2)+Math.pow(z-zT, 2));
    }
    
    protected boolean collision(Letter letter){
        if(distance(letter)==0.0){
            System.out.println("Collision détéctée avec"
                    + "la lettre "+ letter);
        }
        if(distance(letter) <= 5.5){
            lettreTrouve(letter);
        }
        return distance(letter) <= 5.5;
    }

    private void lettreTrouve(Letter letter){
        if(lettres.size() != 0)
            env.removeObject(letter);
            lettres.remove(0);
    }
    
    
    
    
    
}
