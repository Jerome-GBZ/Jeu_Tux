package game;

/**
 *
 * @author riad7
 */
public class LanceurDeJeu {
    public static void main(String[] args) {
        //Instancie un nouveau jeu
        Jeu jeu = new JeuDevineLeMotOrdre();
        //Execute le jeu
        jeu.execute();
    }
}
