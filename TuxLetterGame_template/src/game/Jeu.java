package game;

import env.EnvTextMap;
import env3d.Env;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
// import java.util.function.ToDoubleFunction;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

public abstract class Jeu {
    
    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }
    
    //CONSTANTES
    private static final String filepathXML = "data/XML/";
    private static final String FILEPATH_PROFIL = "data/XML/profil.xml";
    private static final String FILEPATH_PLATEAU = "data/XML/plateau.xml";

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
    private ArrayList<Letter> motTrouve;
    
    
    public Jeu() {
        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room du menu principal
        mainRoom = new Room(FILEPATH_PLATEAU);
        
        // Instancie une Room du menu
        menuRoom = new Room();

        //Instancie la room du tux
        room = new Room(FILEPATH_PLATEAU);
        
        // Règle la camera
        env.setCameraXYZ(50, 50, 150);
        env.setCameraPitch(0);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        profil = new Profil();
        
        // Instancie le conteneur de lettres
        lettres = new ArrayList<>();
        motTrouve = new ArrayList<>();

        // Dictionnaire
        dico = new Dico(filepathXML);

        // Instancie le menuText
        menuText = new EnvTextMap(env);
        
        // Textes affichés à l'écran
        // menuText.addText("Voulez vous ?", "Question", 200, 300);
        // menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        // menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        // menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        // menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);
        
         menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        // menuText.addText("Le joueur entré n'existe pas !", "J_not_exist", 150, 300);
        // menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        // menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        // menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);
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
    
    
    // Reccuperer le nom du joueur saisie au clavier par utilisateur
    private String getNomJoueur() {
        String nomJoueur = "";

        menuText.getText("NomJoueur").display();
        nomJoueur = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();

        return nomJoueur;
    }

    
    // Menu selectionner une partie ou sortir du jeu
    private MENU_VAL menuJeu() {
        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        do {
            // restaure la room du menu
            env.setCameraXYZ(50, 50, 150);
            env.setCameraPitch(0);
            menuRoom.setTextureNorth("menu/menuJeu.png");
            env.setRoom(menuRoom);
            
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException e) {
                System.out.println(e);
            }

            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            // System.out.println("Touche Avant : "+touche);

            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                if( env.getMouseButtonClicked() == 0                        // ( Y , X )
                    && (env.getMouseY() <= 435 && env.getMouseX() >= 130)   // (435,130)
                    && (env.getMouseY() <= 435 && env.getMouseX() <= 500)   // (435,500)
                    && (env.getMouseY() >= 340 && env.getMouseX() >= 130)   // (340,130)
                    && (env.getMouseY() >= 340 && env.getMouseX() <= 500) ) // (340,500) 
                { // Quand on clique sur le bouton "Nouvelle partie"
                    touche = 2;
                } else if( env.getMouseButtonClicked() == 0                 // ( Y , X )
                    && (env.getMouseY() <= 325 && env.getMouseX() >= 130)   // (325,130)
                    && (env.getMouseY() <= 325 && env.getMouseX() <= 500)   // (325,500)
                    && (env.getMouseY() >= 230 && env.getMouseX() >= 130)   // (230,130)
                    && (env.getMouseY() >= 230 && env.getMouseX() <= 500) ) // (230,500) 
                { // Quand on clique sur le bouton "Partie existante"
                    touche = 3;
                } else if( env.getMouseButtonClicked() == 0                 // ( Y , X )
                    && (env.getMouseY() <= 215 && env.getMouseX() >= 130)   // (215,130)
                    && (env.getMouseY() <= 215 && env.getMouseX() <= 500)   // (215,500)
                    && (env.getMouseY() >= 120 && env.getMouseX() >= 130)   // (120,130)
                    && (env.getMouseY() >= 120 && env.getMouseX() <= 500) ) // (120,500) 
                { // Quand on clique sur le bouton "Classement"
                    touche = 4;
                } else if( env.getMouseButtonClicked() == 0                 // ( Y , X )
                    && (env.getMouseY() <= 90 && env.getMouseX() >= 130)    // (90,130)
                    && (env.getMouseY() <= 90 && env.getMouseX() <= 190)    // (90,190)
                    && (env.getMouseY() >= 50 && env.getMouseX() >= 130)    // (50,130)
                    && (env.getMouseY() >= 50 && env.getMouseX() <= 190) )  // (50,190) 
                { // Quand on clique sur le bouton "Quitter"
                    touche = 5;
                }
                
                // System.out.println("Souris Y: "+env.getMouseY()+" - X: "+env.getMouseX() );

                env.advanceOneFrame();
            }

            System.out.println("Menu Jeu - Touche: "+touche);
            env.soundPlay("/audio/click.wav");

            // Restaure la room du jeu
            menuRoom.setTextureEast("textures/black.png");
            menuRoom.setTextureWest("textures/black.png");
            menuRoom.setTextureNorth("textures/black.png");
            menuRoom.setTextureBottom("textures/black.png");
            env.setRoom(mainRoom);
            
            // Décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                    // crée un nouvelle partie
                    partie = new Partie("2021/11/28", "bonjour", 1, 0, 0);

                    joue(partie);
                    
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------
                // A FAIRE / TO DO            
                case Keyboard.KEY_2: // charge une partie existante
                    partie = new Partie("2021-11-28", "bonjour", 1, 0, 0);

                    joue(partie);
                    
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;
                
                // -------------------------------------
                // Touche 3 : Classement des meilleurs joueurs
                // -------------------------------------
                case Keyboard.KEY_3:
                    menuHighScore(profil);
                    menuJeu();
                    break;

                // -----------------------------------------
                // Touche 4 : Revenir au menu Principal
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    profil.sauvegarder(FILEPATH_PROFIL);

                    menuPrincipal();

                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);

        return playTheGame;
    }

    private MENU_VAL menuPrincipal() {
        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;

        env.soundLoop("/audio/TheLoomingBattle.OGG");


        // restaure la room du menu
        env.setCameraXYZ(50, 50, 150);
        env.setCameraPitch(0);
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("menu/menuPrincipal.png");
        menuRoom.setTextureBottom("textures/black.png");
        env.setRoom(menuRoom);
        
        // vérifie qu'une touche 1, 2 ou 3 est pressée
        System.out.println("game.Jeu.menuPrincipal()");
        int touche = 0;

        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4 || touche == Keyboard.KEY_5)) {                                                         
            if( env.getMouseButtonClicked() == 0                        // ( Y , X )
                && (env.getMouseY() <= 430 && env.getMouseX() >= 170)   // (430,170)
                && (env.getMouseY() <= 430 && env.getMouseX() <= 460)   // (430,460)
                && (env.getMouseY() >= 360 && env.getMouseX() >= 170)   // (360,170)
                && (env.getMouseY() >= 360 && env.getMouseX() <= 460) ) // (360,460) 
            {   // Quand on clique sur le bouton "Joueur existant"
                touche = 2;
            } else if( env.getMouseButtonClicked() == 0                   // ( Y , X )
                  && (env.getMouseY() <= 345 && env.getMouseX() >= 170)   // (345,170)
                  && (env.getMouseY() <= 345 && env.getMouseX() <= 460)   // (345,460)
                  && (env.getMouseY() >= 270 && env.getMouseX() >= 170)   // (270,170)
                  && (env.getMouseY() >= 270 && env.getMouseX() <= 460) ) // (270,460) 
            {   // Quand on clique sur le bouton "Nouveau joueur"
                touche = 3;
            } else if( env.getMouseButtonClicked() == 0                   // ( Y , X )
                && (env.getMouseY() <= 250 && env.getMouseX() >= 170)     // (250,170)
                && (env.getMouseY() <= 250 && env.getMouseX() <= 460)     // (250,460)
                && (env.getMouseY() >= 180 && env.getMouseX() >= 170)     // (180,170)
                && (env.getMouseY() >= 180 && env.getMouseX() <= 460) )   // (180,460) 
            {   // Quand on clique sur le bouton "Ajouter mot"
                touche = 4;
            } else if( env.getMouseButtonClicked() == 0                  // ( Y , X )
                && (env.getMouseY() <= 165 && env.getMouseX() >= 170)    // (165,170)
                && (env.getMouseY() <= 165 && env.getMouseX() <= 460)    // (165,460)
                && (env.getMouseY() >= 90 && env.getMouseX() >= 170)     // (90,170)
                && (env.getMouseY() >= 90 && env.getMouseX() <= 460) )   // (90,460) 
            {   // Quand on clique sur le bouton "Classement"
                touche = 5;
            } else if( env.getMouseButtonClicked() == 0                 // ( Y , X )
                && (env.getMouseY() <= 100 && env.getMouseX() >= 130)   // (80,130)
                && (env.getMouseY() <= 100 && env.getMouseX() <= 170)   // (80,170)
                && (env.getMouseY() >= 50 && env.getMouseX() >= 130)    // (55,130)
                && (env.getMouseY() >= 50 && env.getMouseX() <= 170) )  // (55,170) 
            {   // Quand on clique sur le bouton "Quitter"
                touche = 6;
            }

            // System.out.println("Souris Y: "+env.getMouseY()+" - X: "+env.getMouseX() );

            env.advanceOneFrame();
        }

        System.out.println("Menu Principal - Touche: "+touche);

        // Reset les paramètres de texture :
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");
        env.setRoom(menuRoom);
        env.soundStop("/audio/TheLoomingBattle.OGG");

        env.soundPlay("/audio/click.wav");
        // Décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // Demande le nom du joueur existant
                nomJoueur = getNomJoueur();

                if (profil.JoeurExist(nomJoueur)) {
                    profil.ChargerProfil(nomJoueur);

                    choix = menuJeu();
                } else {
                    choix = menuPrincipal(); //CONTINUE;
                }

                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();

                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur, "2000-01-30");

                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Ajouter un mot
            // -------------------------------------
            case Keyboard.KEY_3:
                // choix = MENU_VAL.MENU_SORTIE;
                break;
            
            // -------------------------------------
            // Touche 4 : Classement des meilleurs joueurs
            // -------------------------------------
            case Keyboard.KEY_4:
                menuHighScore(profil);
                menuPrincipal();
                break;

            // -------------------------------------
            // Touche 5 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_5:
                choix = MENU_VAL.MENU_SORTIE;
                break;
        }

        return choix;
    }
    
    public void joue(Partie partie){   
        motTrouve.clear();
        lettres.clear();
        env.advanceOneFrame();

        int level = frameChoisirNiveau();
        System.out.println("level = "+ level);

        String mot = dico.getMotDepusiListeNiveaux(level);
        
        partie.setMot(mot);
        partie.setNiveau(level);
        frameApprendreLeMot(mot);
        env.setRoom(room);
 
        // Instancie un Tux
        tux = new Tux(env, room);
        char[] tab = mot.toCharArray();
        int[][] positionLettres = new int[mot.length()][2];
        for(int i=0; i<mot.length(); i++) {
            int randomPositionX = (int) (Math.random() * (room.getWidth()-5) + 5);
            int randomPositionZ = (int) (Math.random() * (room.getDepth()-5 )+ 5);
            while(!verifiePositionLettreValide(positionLettres, randomPositionX, randomPositionZ, i)){
                randomPositionX = (int) (Math.random() * (room.getWidth()-5) + 5);
                randomPositionZ = (int) (Math.random() * (room.getDepth()-5 )+ 5);
            }
            positionLettres[i][0]=randomPositionX;
            positionLettres[i][1]=randomPositionZ;
            Letter l = new Letter(tab[i], randomPositionX,randomPositionZ, room);
            lettres.add(l);
            env.addObject(l); 
        }
        int totalNblettres = lettres.size();
        env.addObject(tux);
         
        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        démarrePartie(partie);
         
        // Boucle de jeu
        Boolean finished;
        finished = false;
        gameText = new EnvTextMap(env);
        
        while (!finished) {
            // On applique le chronometre lors du lancement du jeu
            // L'interface va update le chronomètre chaque secondes
            finished = appliqueTemps(new OnJeuCallback() {
                @Override
                public void onTimeSpentListener(int time) {
                    if(gameText.getText("time") != null) {
                        gameText.getText("time").clean();
                    }

                    gameText.addText("Time: "+(30-time), "time", 10, 420);
                    gameText.getText("time").display();
                    partie.setTemps(time);
                }
            });
            
            tux.déplace();
 
            // Ici, on applique les regles
            if(env.getKeyDown() == Keyboard.KEY_SPACE) {
                appliqueRegles(partie);
            }
            
            // 1 is for escape key
            if(env.getKey() == 1 || lettres.size() == 0){
                finished = true;
            }
 
            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }

        gameText.getText("time").clean();
        //Calcul de trouvé = score
        int score = 0;
        if(motTrouve.size() > 0) {
            score = (motTrouve.size()*100) / totalNblettres;
        }
        System.out.println("totalNblettres: "+totalNblettres);
        System.out.println("motTrouve: "+motTrouve+" - taille: "+motTrouve.size());        
        System.out.println("score: "+score);

        partie.setTrouve(score);
        
        if(gameText.getText("time") != null) {
            gameText.getText("time").clean();
        }
        
        // Ici on peut calculer des valeurs lorsque la partie est terminée
        profil.ajouterPartie(partie);
        
        frameRecompenses(score);

        terminePartie(partie);
    }


    /**
     * La frame choisir niveau permet à l'utilisateur de choisir un niveau 
     * afin de pouvoir apprendre des mots à son rythme
     * @return level compris entre 1 et 5
     */
    private int frameChoisirNiveau(){
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        mainRoom.setTextureNorth("menu/menuNiveau.png");
        menuRoom.setTextureBottom("textures/black.png");
        env.setCameraXYZ(50, 30, 150);
        env.setCameraPitch(0);
        env.setRoom(mainRoom);

        int touche = 0;
         
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4 || touche == Keyboard.KEY_5)) {
            if( env.getMouseButtonClicked() == 0                        // ( Y , X )
                && (env.getMouseY() <= 355 && env.getMouseX() >= 195)   // (355,195)
                && (env.getMouseY() <= 355 && env.getMouseX() <= 440)   // (355,440)
                && (env.getMouseY() >= 320 && env.getMouseX() >= 195)   // (320,195)
                && (env.getMouseY() >= 320 && env.getMouseX() <= 440) ) // (320,440) 
            { 
                touche = 2;
            } else if( env.getMouseButtonClicked() == 0                   // ( Y , X )
                  && (env.getMouseY() <= 310 && env.getMouseX() >= 195)   // (310,195)
                  && (env.getMouseY() <= 310 && env.getMouseX() <= 440)   // (310,440)
                  && (env.getMouseY() >= 270 && env.getMouseX() >= 195)   // (270,195)
                  && (env.getMouseY() >= 270 && env.getMouseX() <= 440) ) // (270,440) 
            {
                touche = 3;
            } else if( env.getMouseButtonClicked() == 0                   // ( Y , X )
                  && (env.getMouseY() <= 260 && env.getMouseX() >= 195)   // (260,195)
                  && (env.getMouseY() <= 260 && env.getMouseX() <= 440)   // (260,440)
                  && (env.getMouseY() >= 220 && env.getMouseX() >= 195)   // (220,195)
                  && (env.getMouseY() >= 220 && env.getMouseX() <= 440) ) // (220,440) 
            {
                touche = 4;
            } else if( env.getMouseButtonClicked() == 0                   // ( Y , X )
                && (env.getMouseY() <= 210 && env.getMouseX() >= 195)     // (210,195)
                && (env.getMouseY() <= 210 && env.getMouseX() <= 440)     // (210,440)
                && (env.getMouseY() >= 170 && env.getMouseX() >= 195)     // (170,195)
                && (env.getMouseY() >= 170 && env.getMouseX() <= 440) )   // (170,440) 
            {
                touche = 5;
            } else if( env.getMouseButtonClicked() == 0                   // ( Y , X )
                && (env.getMouseY() <= 160 && env.getMouseX() >= 195)     // (160,195)
                && (env.getMouseY() <= 160 && env.getMouseX() <= 440)     // (160,440)
                && (env.getMouseY() >= 120 && env.getMouseX() >= 195)     // (120,195)
                && (env.getMouseY() >= 120 && env.getMouseX() <= 440) )   // (120,440) 
            {
                touche = 6;
            }

            env.advanceOneFrame();
        }
        
        env.soundPlay("/audio/click.wav");

        env.setCameraXYZ(50, 60, 175); 
        env.setCameraPitch(-20);
        mainRoom.resetRoom(FILEPATH_PLATEAU);
        env.setRoom(mainRoom);

        touche--;

        if(touche > 5) {
            touche = 5;
        } else if(touche < 1) {
            touche = 1;
        }

        System.out.println("Level apres : "+touche);

        return touche;
    }

    /**
     * Cette frame contient une dark box qui contient le mot à apprendre (dans l'ordre)
     * On a 5s pour apprendre le mot !
     */
    private void frameApprendreLeMot(String mot){
        env.setRoom(menuRoom);

        char[] tab = mot.toCharArray();
        int spacing = 8; // Espace de 15 pixel
        int startPos = 15;// Position de départ
        ArrayList<Letter> motTmp = new ArrayList<>();
        menuRoom.setTextureNorth("menu/menuJeu.png");
        for(int i=0; i<mot.length(); i++){
           Letter l = new Letter(tab[i], startPos, 50, room, "apprendre");
           env.addObject(l); 
           motTmp.add(l);
           startPos+=spacing;
        }

        // Duree pour voir le mot en avanace : 5 sec
        Chronometre chrono = new Chronometre(5);
        chrono.start();

        while(chrono.remainsTime()){
            if(menuText.getText("time") != null) {
                menuText.getText("time").clean();
            }

            menuText.addText("Temps restant pour apprendre le mot: "+(5-chrono.getSeconds()), "time", 150, 420);
            menuText.getText("time").display();
            
            env.advanceOneFrame();
        }

        for(int i=0; i<motTmp.size(); i++){
            env.removeObject(motTmp.get(i));
        }

        if(menuText.getText("time") != null) {
            menuText.getText("time").clean();
        }        
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
        //System.out.println("distance = "+ Math.sqrt(Math.pow(x-xT, 2)+Math.pow(y-yT, 2)+Math.pow(z-zT, 2))+"\n");
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
        
        env.soundPlay("/audio/collect.wav");
        motTrouve.add(letter);
        afficherLettreSelectionnee(letter);
        if(lettres.size() != 0){
            env.removeObject(letter);
            lettres.remove(0);
        }
    }

    private void afficherLettreSelectionnee(Letter letter){
        int startPos = (room.getWidth() / 2) - (lettres.size() * 5);
        Letter l = new Letter(letter.getLetter(), startPos,0, room,"apprendre");
        env.addObject(l); 
    }
    
    
    private void frameRecompenses(int score) {
        System.out.println("score enregistré: "+ score);
        
        env.setCameraPitch(0);
        env.soundPlay("/audio/won.wav");

        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        if(score == 100) {
            menuRoom.setTextureNorth("recompense/mot_complet.png");
        } else if (score > 60) {
            menuRoom.setTextureNorth("recompense/moitie_de_mot.png");
        } else if(score <= 60 && score > 30) {
            menuRoom.setTextureNorth("recompense/mot_pas_trouve.png");
        } else {
            menuRoom.setTextureNorth("recompense/mot_vraiment_pas_trouve.png");
        }

        env.setRoom(menuRoom);
        
        int touche = 0; // 200 : bouton cliqué
        while(touche != 200){
            if( env.getMouseButtonClicked() == 0                        // ( Y , X )
                && (env.getMouseY() <= 100 && env.getMouseX() >= 290)   // (100,290)
                && (env.getMouseY() <= 100 && env.getMouseX() <= 350)   // (100,350)
                && (env.getMouseY() >= 40 && env.getMouseX() >= 290)   // (40,290)
                && (env.getMouseY() >= 40 && env.getMouseX() <= 350) ) // (40,350) 
            {
                touche = 200;
            }

            env.advanceOneFrame();
        }

        env.soundPlay("/audio/click.wav");
    }

    public void menuHighScore(Profil p) {
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("menu/menuHighScore.png");
        menuRoom.setTextureBottom("textures/black.png");
        env.setCameraXYZ(50, 40, 150);
        env.setCameraPitch(0);
        env.setRoom(menuRoom);

        ArrayList<Profil> meilleursJoueur = p.LesMeilleursJoueur(FILEPATH_PROFIL);

        int posX = 285;
        int posY = 420;
        int nbIteration = 0;
        if(meilleursJoueur.size() >= 4) {
            nbIteration = 4;
        } else {
            nbIteration = meilleursJoueur.size();
        }

        for (int i = 0; i < nbIteration; i++) {
            String nomJ = "Joueur"+(i+1);
            String scoreJ = "ScoreJoueur"+(i+1);
            posX = 285+7*(meilleursJoueur.get(i).getNom().length()+2);

            menuText.addText(meilleursJoueur.get(i).getNom(), nomJ, 280, posY); // 280 410 - 
            
            menuText.addText(String.valueOf( meilleursJoueur.get(i).getScoreTotal() ), scoreJ, posX, posY);

            menuText.getText(nomJ).display();
            menuText.getText(scoreJ).display();

            posY -= 85;
        }

        int touche = 0;
        while (touche != 200 ) {                                                         
            if( env.getMouseButtonClicked() == 0                       // ( Y , X )
                && (env.getMouseY() <= 125 && env.getMouseX() >= 125)  // (125,125)
                && (env.getMouseY() <= 125 && env.getMouseX() <= 180)  // (125,180)
                && (env.getMouseY() >= 90 && env.getMouseX() >= 125)   // (90,125)
                && (env.getMouseY() >= 90 && env.getMouseX() <= 180) ) // (90,180) 
            { 
                touche = 200;
            }

            //  System.out.println("Souris Y: "+env.getMouseY()+" - X: "+env.getMouseX());

            env.advanceOneFrame();
        }

        for (int i = 0; i < nbIteration; i++) {
            String nomJ = "Joueur"+(i+1);
            String scoreJ = "ScoreJoueur"+(i+1);

            menuText.getText(nomJ).clean();
            menuText.getText(scoreJ).clean();
        }

        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");
        env.setRoom(menuRoom);
    }

    /**
     * Verifie que la position de la lettre n'est dans rayon de 15 pixel de celles déjà présentes
     * @param lettres les positions des autres lettres
     * @param x,y position de la lettre que l'on cherche à valider
     */

     private boolean verifiePositionLettreValide(int[][] positionsLettres, int x, int z, int fin){
        boolean estValide = true;
        int i = 0;
        while(i<fin && estValide){
            double mDistance = Math.sqrt(Math.pow(positionsLettres[i][0]-x, 2)+Math.pow(positionsLettres[i][1]-z, 2));
            if(mDistance < 15){
                estValide = false;
            }
            i++;
        }
      
        return estValide;
     }
}

