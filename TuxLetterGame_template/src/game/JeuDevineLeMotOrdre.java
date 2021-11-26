/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import de.lessvoid.nifty.NiftyStopwatch;

/**
 *
 * @author riad7
 */
public class JeuDevineLeMotOrdre extends Jeu{

    private int nbLettresRestantes;
    private Chronometre chrono;
    private static int TIME = 5;
    public JeuDevineLeMotOrdre(){
        super();
        nbLettresRestantes = super.lettres.size();
    }

    private boolean tuxTrouveLettre(){
        if(super.collision(super.lettres.get(0))){
            System.out.println("Tux trouve lettre !");
            return true;
        }
        return false;
    }
    
    private int getNbLettresRestantes(){
        return 0;
    }
    
    private int getTemps(){
        return 0;
    }
    
    @Override
    protected void démarrePartie(Partie partie) {

       chrono = new Chronometre(TIME);
       chrono.start();
    }

    @Override
    protected void appliqueRegles(Partie partie) {
        
    }

    @Override
    protected void terminePartie(Partie partie) {
        
    }
    
    @Override
    protected boolean appliqueTemps() {
        if(chrono.remainsTime()) {
            return false;//reste du temps
        } else {
            return true;
        }
    }
}
