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
    private double temps;
    
    public Partie(String date, String mot, int niveau) {
        this.date = date;
        this.mot = mot;
        this.niveau = niveau;
        this.trouve = 0;
        this.temps = 0.0;
    }
    
    // A TESTER
    public Partie(Element partieElt) {
        this.date = partieElt.getAttribute("date");

        if(partieElt.hasAttribute("trouvé")) {
            this.trouve = Integer.parseInt(partieElt.getAttribute("trouvé").replace("%", ""));
        } else {
            this.trouve = 0;
        }

        NodeList list_temps = partieElt.getElementsByTagName("temps");
        if(list_temps.getLength() >= 1) {
            this.temps = Double.valueOf(((Element) list_temps.item(0)).getTextContent());
        } else {
            this.temps = 0;
        }
        
        Element unMot = (Element) partieElt.getElementsByTagName("mot").item(0);
        this.mot = unMot.getTextContent();
        this.niveau = Integer.parseInt(unMot.getAttribute("niveau"));
        
        /*
            System.out.println("Date :   "+date);
            System.out.println("Trouvé : "+trouve);
            System.out.println("Temps : "+temps);
            System.out.println("Niveau : "+niveau);
            System.out.println("Mot :    "+mot);
            System.out.println("");

            <partie date="2013-12-11" trouve="75%">
            <temps>20</temps>
            <mot niveau="2">indice</mot>
            </partie>
        */
    }
    
    // A TESTER
    public ArrayList<Partie> getParties(Document doc) {
        NodeList parties = doc.getElementsByTagName("partie");
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

    public void setTrouve(int trouve) {
        this.trouve = trouve;
    }

    public double getTemps() {
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

    public void setTemps(double temps) {
        this.temps = temps;
    }
    
    public String toString(){
        //A redefinir !
        return "Partie jouée le "+date+"\n"
        +"Mot: "+mot+"\n"
        +"Trouvé: "+trouve+"\n"
        +"Niveau: "+niveau+"\n"
        +"Temps: "+temps+"\n";
    }
}
