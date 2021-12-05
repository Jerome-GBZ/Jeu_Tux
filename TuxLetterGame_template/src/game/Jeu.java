package game;

import env.EnvTextMap;
import env3d.Env;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleFunction;

import org.lwjgl.input.Keyboard;

public abstract class Jeu {
    
    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }
    
    //CONSTANTES
    private static final String filepathXML = "data/XML/";
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
    private ArrayList<Letter> motTrouve;
    
    
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
        profil = new Profil();
        
        // Instancie le conteneur de lettres
        lettres = new ArrayList<>();
        motTrouve = new ArrayList<>();

        // Dictionnaire
        dico = new Dico(filepathXML);

        // Instancie le menuText
        menuText = new EnvTextMap(env);
        
        // Textes affichés à l'écran
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);

        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("Le joueur entré n'existe pas !", "J_not_exist", 150, 300);
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
            env.setRoom(menuRoom);

            // affiche menu
            menuText.getText("Question").display();
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu2").display();
            menuText.getText("Jeu3").display();
            menuText.getText("Jeu4").display();
            
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException e) {
                System.out.println(e);
            }

            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            System.out.println("Touche Avant : "+touche);

            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                if(touche == 75) { // Touche 4
                    touche = 5;
                } else if(touche == 79) { // Touche 1
                    touche = 2;
                } else if (touche == 80) { // Touche 2
                    touche = 3;
                }  else if (touche == 81) { // Touche 3
                    touche = 4;
                } else {
                    touche = env.getKeyDown();
                }
                
                env.advanceOneFrame();
            }

            // Nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu2").clean();
            menuText.getText("Jeu3").clean();
            menuText.getText("Jeu4").clean();
            // Restaure la room du jeu
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

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    profil.sauvegarder(FILEPATH_PROFIL);
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    profil.sauvegarder(FILEPATH_PROFIL);
                    playTheGame = MENU_VAL.MENU_SORTIE;
                    break;
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
            if(env.getKeyDown() == 79) { // Touche 1
                touche = 2;
            } else if (env.getKeyDown() == 80) { // Touche 2
                touche = 3;
            }  else if (env.getKeyDown() == 81) { // Touche 3
                touche = 4;
            } else {
                touche = env.getKeyDown();
            }
            env.advanceOneFrame();

        }
        

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();

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
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
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
        for(int i=0; i<mot.length(); i++) {
            int randomPositionX = (int) (Math.random() * (room.getWidth()));
            int randomPositionZ = (int) (Math.random() * (room.getDepth()));
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

            //On applique le chronometre lors du lancement du jeu
            //L'interface va update le chronomètre chaque secondes
            finished = appliqueTemps(new OnJeuCallback() {
                @Override
                public void onTimeSpentListener(int time) {
                    if(gameText.getText("time") != null)
                        gameText.getText("time").clean();
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

        //Calcul de trouvé = score
        int score = 0;
        if(motTrouve.size() > 0) {
            score = totalNblettres / motTrouve.size();
        }
        partie.setTrouve(score*100);
        
        if(gameText.getText("time") != null) {
            gameText.getText("time").clean();
        }
        
        // Ici on peut calculer des valeurs lorsque la partie est terminée
        // if(finished) {
        profil.ajouterPartie(partie);
        // }

        terminePartie(partie);
    }


    /**
     * La frame choisir niveau permet à l'utilisateur de choisir un niveau 
     * afin de pouvoir apprendre des mots à son rythme
     * @return level compris entre 1 et 5
     */
    private int frameChoisirNiveau(){
        env.setRoom(mainRoom);
        
        menuText.addText("Choisissez un niveau [ 1 simple -> 5 difficile] ", "choixLvl", 200, 300);
        menuText.addText("4. Niveau 1", "lvl1", 250, 280);
        menuText.addText("5. Niveau 2", "lvl2", 250, 260);
        menuText.addText("6. Niveau 3", "lvl3", 250, 240);
        menuText.addText("7. Niveau 4", "lvl4", 250, 220);
        menuText.addText("8. Niveau 5", "lvl5", 250, 200);
        menuText.getText("choixLvl").display();
        menuText.getText("lvl1").display();
        menuText.getText("lvl2").display();
        menuText.getText("lvl3").display();
        menuText.getText("lvl4").display();
        menuText.getText("lvl5").display();

        

        int touche = 0;
        int levelSelected = 0;
         
    
        while (!(touche == Keyboard.KEY_4 || touche == Keyboard.KEY_5 || touche == Keyboard.KEY_6 || touche == Keyboard.KEY_7 || touche == Keyboard.KEY_8)) {
            System.out.println("Touche : "+touche);
            
            touche = env.getKeyDown(); // avant : 0
            
            System.out.println("Touche : "+touche);

            env.advanceOneFrame();
        }

        menuText.getText("choixLvl").clean();
        menuText.getText("lvl1").clean();
        menuText.getText("lvl2").clean();
        menuText.getText("lvl3").clean();
        menuText.getText("lvl4").clean();
        menuText.getText("lvl5").clean();
        
        levelSelected = touche-4;
        System.out.println("Level avant : "+levelSelected);

        if(levelSelected > 5) {
            levelSelected = 5;
        } else if(levelSelected < 1) {
            levelSelected = 1;
        }
        System.out.println("Level apres : "+levelSelected);

        return levelSelected;
    }

    /**
     * Cette frame contient une dark box qui contient le mot à apprendre (dans l'ordre)
     * On a 5s pour apprendre le mot !
     */
    private void frameApprendreLeMot(String mot){
        env.setRoom(menuRoom);
        char[] tab = mot.toCharArray();
        int spacing = 15; // Espace de 15 pixel
        int middleWord = mot.length()/2; // Moitié d'un mot
        int startPos = (room.getWidth() / 2) - (middleWord * 20);// Position de départ
        ArrayList<Letter> motTmp = new ArrayList<>();

        for(int i=0; i<mot.length(); i++){
           Letter l = new Letter(tab[i], startPos,50, room);
           env.addObject(l); 
           motTmp.add(l);
           startPos+=spacing;
        }

        // Duree pour voir le mot en avanace : 5 sec
        Chronometre chrono = new Chronometre(5);
        chrono.start();

        while(chrono.remainsTime()){
            if(menuText.getText("time") != null)
                menuText.getText("time").clean();
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
        motTrouve.add(letter);
        afficherLettreSelectionnee(letter);
        if(lettres.size() != 0){
            env.removeObject(letter);
            lettres.remove(0);
        }
    }

    private void afficherLettreSelectionnee(Letter letter){
        int startPos = (room.getWidth() / 2) - (lettres.size() * 5);
        Letter l = new Letter(letter.getLetter(), startPos,room.getDepth(), room);
        l.setScale(1.0);
        l.setY(room.getHeight());
        env.addObject(l); 
    }
    
    
    
    
    
}
