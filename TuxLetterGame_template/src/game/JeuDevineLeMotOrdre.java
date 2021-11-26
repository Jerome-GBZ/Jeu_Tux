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
public class JeuDevineLeMotOrdre extends Jeu{

    private int nbLettresRestantes;
    private Chronometre chrono;
    private static int TIME = 2500;
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
        return nbLettresRestantes--;
    }
    
    
    
    @Override
    protected void démarrePartie(Partie partie) {
       chrono = new Chronometre(TIME);
       chrono.start();
    }

    @Override
    protected void appliqueRegles(Partie partie) {
        if(tuxTrouveLettre())
            getNbLettresRestantes();
    }

    @Override
    protected void terminePartie(Partie partie) {
        
    }
    
    @Override
    protected boolean appliqueTemps(OnJeuCallback callback) {
        if(chrono.remainsTime()) {
            callback.onTimeSpentListener(chrono.getSeconds());
            return false;
        } else {
            return true;
        }
    }

}
