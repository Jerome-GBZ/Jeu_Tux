/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author riad7
 */
public class Partie {
    private String date;
    private String mot;
    private int niveau;
    private int trouvé;
    private int temps;
    
    public Partie(String date, String mot, int niveau){
        this.date = date;
        this.mot = mot;
        this.niveau = niveau;
    }
    
    // A FAIRE
    public Partie(Element partieElt) {
        
    }
    
    // A FAIRE
    public Element getPartie(Document doc) {
        return null;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getTrouvé() {
        return trouvé;
    }

    public void setTrouvé(int trouvé) {
        this.trouvé = trouvé;
    }

    public int getTemps() {
        return temps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMot() {
        return mot;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }
    

    public void setTemps(int temps) {
        this.temps = temps;
    }
    
    public String toString(){
        //A redefinir !
        return "Partie jouée le "+date+"\n mot: "+mot;
    }
    
}
