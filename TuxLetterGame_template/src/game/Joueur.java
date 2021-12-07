package game;

import java.util.ArrayList;

public class Joueur implements Comparable {
    private String nom;
    private double scoreTotal;

    public Joueur(String nom, double scoreTotal) {
        this.nom = nom;
        this.scoreTotal = scoreTotal;
    }

    public String getNom() {
        return nom;
    }

    public double getScoreTotal() {
        return scoreTotal;
    }

    @Override
    public boolean estInferieur(Comparable unJoueur) {
        double compareScoreTot = ((Joueur) unJoueur).getScoreTotal();

        if(this.getScoreTotal() < compareScoreTot) {
            return true;
        } else {
            return false;
        }
    }
}