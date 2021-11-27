package game;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Dico {
    
    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    
    private String cheminFichierDico;
    
    public Dico(String cheminFichierDico){
        this.cheminFichierDico = cheminFichierDico;
        listeNiveau1 = new ArrayList();
        listeNiveau2 = new ArrayList();
        listeNiveau3 = new ArrayList();
        listeNiveau4 = new ArrayList();
        listeNiveau5 = new ArrayList();
        
        lireDictionnaireDOM(getCheminFichierDico(), "dico.xml");
    }
    
    public String getMotDepusiListeNiveaux(int niveau){
        String mot = "error";
        switch(vérifieNiveau(niveau)){
            case 1:
                mot = getMotDepuisListe(listeNiveau1);
                break;
            case 2:
                mot = getMotDepuisListe(listeNiveau2);
                break;
            case 3:
                mot = getMotDepuisListe(listeNiveau3);
                break;
            case 4:
                mot = getMotDepuisListe(listeNiveau4);
                break;
            case 5:
                mot = getMotDepuisListe(listeNiveau5);
                break;
            default:
                System.out.println("getMotDepusiListeNiveaux: Erreur du niveau saisie");
        }
        return mot;
    }
    
    public void ajouteMotADico(int niveau, String mot){
        switch(vérifieNiveau(niveau)){
            case 1:
                listeNiveau1.add(mot);
                break;
            case 2:
                listeNiveau2.add(mot);
                break;
            case 3:
                listeNiveau3.add(mot);
                break;
            case 4:
                listeNiveau4.add(mot);
                break;
            case 5:
                listeNiveau5.add(mot);
                break;
            default:
                System.out.println("ajouteMotADico: Erreur du niveau saisie");
        }
    }
    
    public String getCheminFichierDico(){
        return "data/XML/";
    }
    
    private int vérifieNiveau(int niveau){
        if(niveau > 5)
            niveau = 5;
        else if (niveau < 1)
            niveau = 1;
        return niveau;
    }
    
    private String getMotDepuisListe(ArrayList<String> list){
        int r = (int) (Math.random() * (list.size()-1));
        System.out.println("Valeur de R : "+ r);
        return list.get(r);
    }
    
    public void lireDictionnaireDOM(String path, String filename){
        DOMParser parser = new DOMParser();
        String filepath = path +filename;
       
        try{
            parser.parse(filepath);
            Document doc = parser.getDocument();
            NodeList mots = doc.getElementsByTagName("mot");

            for(int i=0; i<mots.getLength(); i++){
                Element mot = (Element) mots.item(i);
                String contenuMot = mot.getTextContent();
                int niveau = Integer.parseInt(mot.getAttribute("niveau"));
             
                ajouteMotADico(niveau, contenuMot);
            }
        }catch(Exception e){
            System.out.println("Erreur: "+e);
        }
    }
}
