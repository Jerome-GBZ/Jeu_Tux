package test;

import game.Partie;
import game.Profil;
import org.w3c.dom.Document;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.util.ArrayList;

public class TestPartie {
    public static void main(String[] args) {
        String filename = "data/XML/profil.xml";
        Partie unePartie = new Partie("date", "mot", 1);
        DOMParser parser = new DOMParser();
       
        try {
            parser.parse(filename);
            Document doc = parser.getDocument();
            
            ArrayList<Partie> listP = unePartie.getParties(doc);
            listP.forEach((p) -> System.out.println(p.toString()));
        } catch(Exception e) {
            System.out.println("Erreur: "+e);
        }
        
        // Tester la methode Sauvegarder()
        Partie p1 = new Partie("2021/11/27", "acheter", 2);
        p1.setTemps(14.78);
        p1.setTrouve(98);

        Profil profil = new Profil(filename);
        profil.ajouterPartie(p1);
        profil.sauvegarder(filename);
    }
}
