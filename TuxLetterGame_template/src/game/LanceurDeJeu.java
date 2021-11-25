/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author riad7
 */
public class LanceurDeJeu {
    public static void main(String[] args){
        //Instancie un nouveau jeu
        Jeu jeu = new JeuDevineLeMotOrdre();
        //Execute le jeu
        jeu.execute();
    }
}
