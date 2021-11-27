package game;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Partie {
    private String date;
    private String mot;
    private int niveau;
    private int trouve;
    private int temps;
    
    public Partie(String date, String mot, int niveau) {
        this.date = date;
        this.mot = mot;
        this.niveau = niveau;
        this.trouve = 0;
    }
    
    // A TESTER
    public Partie(Element partieElt) {
        this.date = partieElt.getAttribute("date");
        this.trouve = Integer.parseInt(partieElt.getAttribute("trouvé"));

        NodeList list_mot = partieElt.getElementsByTagName("mot");
        this.mot = ((Element) list_mot.item(0)).getTextContent();
        this.niveau = Integer.parseInt(((Element) list_mot.item(0)).getAttribute("niveau"));
        
        /*
         *  <partie date="2013-12-11" trouve="75%">
         *      <mot niveau="2">indice</mot>
         *  </partie>
         */
    }
    
    // A TESTER
    public ArrayList<Partie> getParties(Document doc) {
        NodeList parties = doc.getElementsByTagName("partie");
        System.out.println(parties.item(0));
        ArrayList<Partie> listParties = new ArrayList<>();
        
        for (int i = 0; i < parties.getLength(); i++) {
            listParties.add(new Partie((Element) parties.item(i)));
        }
        
        return listParties;
    }

    public Element getPartie(Document doc) {
        return null;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getTrouve() {
        return trouve;
    }

    public void settrouve(int trouve) {
        this.trouve = trouve;
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
