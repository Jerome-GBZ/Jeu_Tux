package test;
import game.EditeurDico;
public class TestEditeurDico {
    public static void main(String[] args){
        EditeurDico ed = new EditeurDico();
        ed.lireDOM();
        ed.ajouterMot("Chameau", 4);
        ed.ajouterMot("Rateau", 10);
        ed.ajouterMot("Champion", -1000);
        ed.ajouterMot("Dragon", 1);
    }
}
