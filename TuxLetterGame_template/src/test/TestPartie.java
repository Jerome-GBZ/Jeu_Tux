package test;

import game.Partie;
import game.Profil;
import org.w3c.dom.Document;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.util.ArrayList;

public class TestPartie {
    public static void main(String[] args) {
        String filename = "data/XML/profil.xml";
        Partie p1 = new Partie("2021/11/28", "acheter", 2, 0, 0);
        /*
        // Tester la methode getParties()
        try {
            DOMParser parser = new DOMParser();
            parser.parse(filename);
            Document doc = parser.getDocument();
            
            ArrayList<Partie> listP = p1.getParties(doc);
            listP.forEach((p) -> System.out.println(p.toString()));
        } catch(Exception e) {
            System.out.println("Erreur: "+e);
        }
        */

        // Tester la methode ajouterJoeur()
        p1.setTemps(14.78);
        p1.setTrouve(98);

        Profil profil = new Profil("mathis", "2000/02/02");
        profil.ajouterPartie(p1);

        // Tester la methode Sauvegarder()
        profil.sauvegarder(filename);
    }
}
