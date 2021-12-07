package game;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.Format;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import utils.XMLUtil;

public class Profil {
    private String nom;
    private String dateNaissance;
    private String avatar;
    private ArrayList<Partie> parties;
    public Document _doc;
    private static final String filepathAvatar = "data/AVATAR/";
    private static final String filepathProfil = "data/XML/profil.xml";
    
    public Profil(String nom, String dateNaissance) {
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.avatar = "player1.svg";

        if(!this.JoeurExist(nom)) {
            sauvegarderJoueur(filepathProfil);
        }

        parties = new ArrayList<>();
    }
    
    public Profil(String nomJ, boolean test) {
        parties = new ArrayList<>();

        try {
            Element joueur = this.ChargerJoueur(nomJ);
            if(joueur != null) {
                nom = ((Element) joueur.getElementsByTagName("nom").item(0)).getTextContent();
                dateNaissance = xmlDateToProfileDate(((Element) joueur.getElementsByTagName("anniversaire").item(0)).getTextContent());
                avatar = getCheminAvatar()+((Element) joueur.getElementsByTagName("avatar").item(0)).getTextContent();
            } else {
                nom = "default";
                dateNaissance = "1970/01/01";
                avatar = "player1.svg";
            }
        } catch(Exception e) {
            System.out.println("Erreur: "+e);
        }
    }

    public Profil() {
        nom = "default";
        dateNaissance = "1970/01/01";
        avatar = "player1.svg";

        parties = new ArrayList<>();
    }

    public String getProfilInformations() {
        return "nom: " + nom + "\n" +
        "date de naissance: " + dateNaissance + "\n";
    }
    
    private String getCheminAvatar() {
        return filepathAvatar;
    }

    private String getAvatar() {
        return avatar;
    }
    
    private void toXML(String filename){
        try {
            XMLUtil.DocumentTransform.writeDoc(_doc, filename);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Document fromXML(String filename){
        try {
            return XMLUtil.DocumentFactory.fromFile(filename);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void ajouterPartie(Partie p){
        parties.add(p);
    }
    
    public int getDernierNiveau(){
        int dernierePartie = parties.size() -1;
        return parties.get(dernierePartie).getNiveau();
    }

    public String getNom() {
        return this.nom;
    }

    public String toString() {
        return "Profile :"
                + "\n nom = " + nom
                + "\n dateNaissance = " + dateNaissance; //To change body of generated methods, choose Tools | Templates.
    }
    
    public void sauvegarderJoueur(String filename){
        try{
            System.out.println("Sauvegarde d'un nouveau profil en cours...");
            _doc = fromXML(filename);

            Element profils = (Element) _doc.getElementsByTagName("profils").item(0);

            Element newProfil = _doc.createElement("profil");
            Element newName = _doc.createElement("nom");
            Element newAvatar = _doc.createElement("avatar");
            Element newBirthday = _doc.createElement("anniversaire");
            Element newParties = _doc.createElement("parties");

            /*  Exemple profil :
                <profil>
                    <nom>jerome</nom>
                    <avatar>player2.svg</avatar>
                    <anniversaire>2000-03-22</anniversaire>
                    <parties>
                        ...
                    </parties>
                </profil>
            */

            // Ajout contenu :
            newName.setTextContent(this.nom.toLowerCase());
            newAvatar.setTextContent(getAvatar());
            newBirthday.setTextContent(this.dateNaissance);

            // Ajout des nodes à profils :
            newProfil.appendChild(newName);
            newProfil.appendChild(newAvatar);
            newProfil.appendChild(newBirthday);
            newProfil.appendChild(newParties);

            profils.appendChild(newProfil);

            toXML(filename); 
            
            System.out.println("Sauvegarde du profil fini..");
            
        } catch(Exception e) {
            System.out.println("Erreur: "+e);
        }
    }
    
    public void sauvegarder(String filename) {
        try{
            _doc = fromXML(filename);
            Element unJoueur = this.ChargerJoueur(this.getNom());
            
            if(unJoueur != null) {
                Element partiesElem = (Element) unJoueur.getElementsByTagName("parties").item(0);
            
                for (int i = 0; i < this.parties.size(); i++) {
                    System.out.println("Partie n°"+(i+1));
                    
                    // Création des nodes
                    Element newPartie = _doc.createElement("partie");
                    Element newMot = _doc.createElement("mot");
                    Element newTemps = _doc.createElement("temps");
    
                    // Implémentation des nodes
                    // node partie :
                    newPartie.setAttribute("date", profileDateToXmlDate(this.parties.get(i).getDate()));
                    newPartie.setAttribute("trouvé", String.valueOf(this.parties.get(i).getTrouve())+"%");
                    // node Mot :
                    newMot.setAttribute("niveau", String.valueOf(this.parties.get(i).getNiveau()));
                    newMot.setTextContent(this.parties.get(i).getMot());
                    // Node Temps :
                    newTemps.setTextContent(String.valueOf(this.parties.get(i).getTemps()));
    
                    // Ajout des nodes à notre partie :
                    newPartie.appendChild(newTemps);
                    newPartie.appendChild(newMot);
    
                    // Ajout des nodes à notre liste de partie :
                    partiesElem.appendChild(newPartie);
                }
            }

            toXML(filename);
            System.out.println("Sauvegarder !");
        } catch(Exception e) {
            System.out.println("Erreur: "+e);
        }
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
    
    private String profileDateToXmlDate(String profileDate){
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }
    
    public boolean JoeurExist(String nomJ) {
        boolean joueurTrouve = false;
        int i = 0;
        nomJ = nomJ.toLowerCase();

        try {
            _doc = fromXML(filepathProfil);
            NodeList list_profil = _doc.getElementsByTagName("profil");

            while(!joueurTrouve && i < list_profil.getLength()) {
                String nom_existant = ((Element) _doc.getElementsByTagName("nom").item(i)).getTextContent();
                System.out.println("Joueur existant : "+nom_existant);

                if(nom_existant.equals(nomJ)) {
                    joueurTrouve = true;
                    System.out.println("Return true");
                }
                i++;
            }
        } catch(Exception e) {
            System.out.println("Erreur: "+e);
        }

        if(!joueurTrouve)
            System.out.println("Return false");
        
        return joueurTrouve;
    }

    private Element ChargerJoueur(String nomJ) {
        boolean joueurTrouve = false;
        int i = 0;
        Element joueur = null;

        try {
            DOMParser parser = new DOMParser();
            _doc = fromXML(filepathProfil);
            parser.parse(filepathProfil);
            _doc = parser.getDocument();

            NodeList list_profil = _doc.getElementsByTagName("profil");

            while(!joueurTrouve || i < list_profil.getLength()) {
                String nom_existant = ((Element) _doc.getElementsByTagName("nom").item(i)).getTextContent();
                System.out.println("Joueur existant : "+nom_existant);
                System.out.println("Joueur à chercher : "+nomJ);

                if(nomJ.equals(nom_existant)) {
                    joueurTrouve = true;
                    joueur = (Element) _doc.getElementsByTagName("profil").item(i);
                    // System.out.println("i : "+i+" - Vrai");
                    System.out.println("Return Joueur");
                } // else {
                    // System.out.println("i : "+i+" - Faux");
                // }

                i++;
            }
        } catch(Exception e) {
            System.out.println("Erreur: "+e);
        }
        
        if(!joueurTrouve)
            System.out.println("Return null");
        
        String joueurSelected = ((Element) joueur.getElementsByTagName("nom").item(0)).getTextContent();
        System.out.println("Joueur select : "+joueurSelected);

        return joueur;
    }

    public void ChargerProfil(String nomP) {
        Element unJoueur = ChargerJoueur(nomP);

        if(unJoueur != null) {
            this.nom = ((Element) unJoueur.getElementsByTagName("nom").item(0)).getTextContent();
            this.dateNaissance = xmlDateToProfileDate(((Element) unJoueur.getElementsByTagName("anniversaire").item(0)).getTextContent());
            this.avatar = getCheminAvatar()+((Element) unJoueur.getElementsByTagName("avatar").item(0)).getTextContent();
        } else {
            this.nom = "default";
            this.dateNaissance = "1970/01/01";
            this.avatar = "player1.svg";
        }
    }

    

    public ArrayList<Joueur> LesMeilleursJoueur(String filename) {
        _doc = fromXML(filename);
        NodeList list_profil = _doc.getElementsByTagName("profil");

        ArrayList<Joueur> meilleursJoueur = new ArrayList<>();
        
        for (int i = 0; i < list_profil.getLength(); i++) {
            Element joueur = (Element) _doc.getElementsByTagName("profil").item(i);
            NodeList parties = joueur.getElementsByTagName("partie");
            
            String nom = ((Element) joueur.getElementsByTagName("nom").item(0)).getTextContent();
            double scoreGlobal = 0.0;
            int coefficientTot = 0;


            for (int j = 0; j < parties.getLength(); j++) {
                Element partie = (Element) parties.item(j);
                Element mot = (Element) partie.getElementsByTagName("mot").item(0);
                int trouve = 0;
                int niveau = 0;

                // Si l'attribut trouvé existe on supprime le % à la fin s'il existe
                if(partie.hasAttribute("trouvé")) {
                    String trouver = partie.getAttribute("trouvé");
                    if (trouver != null && trouver.length() > 0 && trouver.charAt(trouver.length() - 1) == '%') {
                        trouver = trouver.substring(0, trouver.length() - 1);
                    }

                    trouve = Integer.parseInt( trouver );
                }

                if(mot.hasAttribute("niveau")) {
                    niveau = Integer.parseInt( mot.getAttribute("niveau") );  
                }

                coefficientTot += niveau;
                scoreGlobal += niveau*trouve;
            }

            if(coefficientTot > 0) {
                NumberFormat formatter = new DecimalFormat("#0.00");
                meilleursJoueur.add(new Joueur(nom, scoreGlobal/coefficientTot )); // formatter.format()
            } else {
                meilleursJoueur.add(new Joueur(nom, 0));
            }
        }
        
        meilleursJoueur = TrieTableau(meilleursJoueur);

        return meilleursJoueur;
    }

    public ArrayList<Joueur> TrieTableau(ArrayList<Joueur> tab) {
        for (int i = 0; i <= tab.size() - 2; i++) {
            for (int j = tab.size() - 1; j > i; j--) {
                if(tab.get(j).estInferieur(tab.get(j-1))) {
                    Joueur jAutre = tab.get(j);

                    tab.remove(j);
                    tab.add(j, tab.get(j-1));

                    tab.remove(j-1);
                    tab.add(j-1, jAutre);
                }
            }
        }

        return tab;
    }
}
