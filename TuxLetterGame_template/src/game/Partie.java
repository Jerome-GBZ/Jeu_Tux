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
    
    public Partie(String date, String mot, int niveau, int trouve, double temps) {
        this.date = date;
        this.mot = mot;
        this.niveau = niveau;
        this.trouve = trouve;
        this.temps = temps;
    }
    
    /**
     * Créer un objet à partir d'un element XML partie
     * @param partieElt : Element
     */
    public Partie(Element partieElt) {
        // this.date = partieElt.getAttribute("date");
        this.date =  xmlDateToProfileDate( partieElt.getAttribute("date") );

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
    }
    


    /**
     * GETTER ATTRIBUTES
     */
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
    

    @Override
    public String toString(){
        return "Partie jouée le "+date+"\n"
        +"Mot: "+mot+"\n"
        +"Trouvé: "+trouve+"\n"
        +"Niveau: "+niveau+"\n"
        +"Temps: "+temps+"\n";
    }

    public ArrayList<Partie> getListPartieNonFini(Profil profil) {
        ArrayList<Partie> parties = new ArrayList<>();

        Element joueur = profil.ChargerJoueur(profil.getNom());
        NodeList partiesNodeList = joueur.getElementsByTagName("partie");
        
        for (int i = 0; i < partiesNodeList.getLength(); i++) {
            Element partieElm = (Element) partiesNodeList.item(i);
            if(partieElm.hasAttribute("trouvé")) {
                if( Integer.parseInt(partieElm.getAttribute("trouvé").replace("%", "")) < 100) {
                    parties.add( new Partie(partieElm) );
                }
            } else {
                parties.add( new Partie(partieElm) );
            }
        }

        return parties;
    }

    private String xmlDateToProfileDate(String xmlDate){
        String date;

        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";

        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";

        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }
}
