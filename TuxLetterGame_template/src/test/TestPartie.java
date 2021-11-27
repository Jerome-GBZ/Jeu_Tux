package test;

import game.Partie;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.util.ArrayList;

public class TestPartie {
    public static void main(String[] args) {
        Partie unePartie = new Partie("date", "mot", 1);
        DOMParser parser = new DOMParser();
       
        try{
            parser.parse("data/XML/profil.xml");
            Document doc = parser.getDocument();

            System.out.println(doc);
            // System.out.println(doc.getElementsByTagName("nom"));
            // System.out.println(((Element) doc.getElementsByTagName("nom")).getTextContent());

            ArrayList<Partie> listP = unePartie.getParties(doc);
            listP.forEach((p) -> System.out.println(p.toString()));
        } catch(Exception e) {
            System.out.println("Erreur: "+e);
        }
    }
}
