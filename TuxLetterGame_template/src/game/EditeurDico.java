package game;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import utils.XMLUtil;

public class EditeurDico{
    private Document doc;
    private ArrayList<String> motsDico;

    private static final String FILEPATH_DICO = "data/XML/dico.xml"; 

    public EditeurDico(){
        motsDico = new ArrayList<>();
        lireDOM();
    }

    public void lireDOM() {
        motsDico.clear();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(FILEPATH_DICO));
            NodeList mots = doc.getElementsByTagName("mot");
            afficherNodeList(mots);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ajouterMot(String mot, int niveau){
        if(niveau <1 || niveau >5) {
            System.out.println("Niveau saisie invalide il doit être compris entre 1 et 5.");
        } else{
            if(!verifieMotExiste(mot)) {
                System.out.println("Ajout du mot !");
                Element dico = (Element) doc.getElementsByTagName("dictionnaire").item(0);
                Element newMot = doc.createElement("mot");
                newMot.setTextContent(mot);
                String sniveau = ""+niveau;
                newMot.setAttribute("niveau", sniveau);
                dico.appendChild(newMot);
                toXML();
            }else{
                System.out.println("le mot exist déjà veuillez en choisir un autre !");
            }
        }
        
    } 

    private void toXML(){
        try {
            XMLUtil.DocumentTransform.writeDoc(doc, FILEPATH_DICO);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Verifie si le mot ajouté est un nouveau mot
     */
    private boolean verifieMotExiste(String mot){
        boolean trouve = false;
        int i = 0;
        while(i<motsDico.size() && !trouve){
            if(motsDico.get(i).equals(mot))
                trouve = true;
            i++;
        }
        return trouve;
    }

    private void afficherNodeList(NodeList nodeList){
        for(int i=0; i <nodeList.getLength(); i++){
            String mot = nodeList.item(i).getTextContent();
            System.out.println("mot à l'indice "+i+" : "+ mot);
            motsDico.add(mot);
        }
    }
}