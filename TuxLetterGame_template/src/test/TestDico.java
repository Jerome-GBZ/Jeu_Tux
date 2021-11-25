/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import game.Dico;

/**
 *
 * @author gambiezj
 */
public class TestDico {
    public static void main(String[] args){
        // Création d'un objet dico
        Dico dico = new Dico("/data/XML/dico.xml");
        
        // Ajouter des mots dans tous les niveaux
        dico.ajouteMotADico(1, "Coucou");
        dico.ajouteMotADico(1, "Aurevoir");
        dico.ajouteMotADico(1, "Top");
        dico.ajouteMotADico(1, "Lundi");
        
        dico.ajouteMotADico(2, "Jeudi");
        dico.ajouteMotADico(2, "Fin");
        
        dico.ajouteMotADico(3, "Encore");
        dico.ajouteMotADico(4, "Lumineux");
        dico.ajouteMotADico(5, "Radieux");
        
        dico.ajouteMotADico(-10, "Formalisation");
        dico.ajouteMotADico(8, "Biere");
        
        try{
            // Chercher un mot aléatoire dans une des liste d'un niveau
            for(int i=0; i<5; i++){
                String mot = dico.getMotDepusiListeNiveaux(i);
                System.out.println("Mot de niveau "+i+" "+mot+"\n");
            }   
        }catch(Exception e){
            System.out.println("erreur : "+e);
        }
    }    
}
