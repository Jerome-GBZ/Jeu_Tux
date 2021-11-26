/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    
    public Profil(String nom, String dateNaissance){
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.avatar = getCheminAvatar()+"player1.svg";
        parties = new ArrayList();
    }
    
    public Profil(String filename){
        _doc = fromXML(filename);
        DOMParser parser = new DOMParser();
       
        try{
            parser.parse(filename);
            _doc = parser.getDocument();
            nom = ((Element) _doc.getElementsByTagName("nom")).getTextContent();
            dateNaissance = xmlDateToProfileDate(((Element) _doc.getElementsByTagName("anniversaire")).getTextContent());
            avatar = getCheminAvatar()+((Element) _doc.getElementsByTagName("avatar")).getTextContent();
        } catch(Exception e) {
            System.out.println("Erreur: "+e);
        }
    }
    
    private String getCheminAvatar() {
        return "data/AVATAR";
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

    public String toString() {
        return "Profile :"
                + "\n nom = " + nom
                + "\n dateNaissance = " + dateNaissance; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void sauvegarder(String filename){
        DOMParser parser = new DOMParser();
       
        try{
            parser.parse(filename);
            Document doc = parser.getDocument();
            
            Element partiesElem = (Element) doc.getElementsByTagName("parties");
            
            System.out.println("game.Profil.sauvegarder()");
            for (int i = 0; i < this.parties.size(); i++) {
                System.out.println("Partie n°"+i);
                
                //Création des nodes
                Element newPartie = doc.createElement("partie");
                Element newMot = doc.createElement("mot");
                Element newTemps = doc.createElement("temps");

                //Implémentation des nodes
                // node partie :
                newPartie.setAttribute("date", profileDateToXmlDate(this.parties.get(i).getDate()));
                newPartie.setAttribute("trouvé", String.valueOf(this.parties.get(i).getTrouvé()));
                // node Mot :
                newMot.setAttribute("niveau", String.valueOf(this.parties.get(i).getNiveau()));
                newMot.setTextContent(this.parties.get(i).getMot());
                // Node Temps :
                newTemps.setTextContent(String.valueOf(this.parties.get(i).getTemps()));

                //Ajout des nodes à notre partie :
                newPartie.appendChild(newMot);
                newPartie.appendChild(newTemps);

                //Ajout des nodes à notre liste de partie :
                partiesElem.appendChild(newPartie);
            }
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
    
    public boolean charge(String nom){
        DOMParser parser = new DOMParser();
       
        try{
            parser.parse("data/XML/profil.xml");
            Document doc = parser.getDocument();
            
            String nom_existant = ((Element) doc.getElementsByTagName("nom")).getTextContent();
            System.out.println("Joueur existant : "+nom_existant);

            if(nom_existant.equals(nom)) {
                System.out.println("Return true");
                return true;
            }
        } catch(Exception e) {
            System.out.println("Erreur: "+e);
        }

        System.out.println("Return false");
        return false;
    }
}
