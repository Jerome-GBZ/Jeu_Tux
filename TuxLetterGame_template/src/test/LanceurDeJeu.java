package test;

import game.Jeu;
import game.JeuDevineLeMotOrdre;

public class LanceurDeJeu {
    public static void main(String[] args) {
        // Instancie un nouveau jeu
        Jeu jeu = new JeuDevineLeMotOrdre();
        // Execute le jeu
        jeu.execute();
    }
}
