package game;

import env3d.advanced.EnvNode;

public class Letter extends EnvNode{
    private char letter;
    
    public Letter(char l, double x, double z, Room room){
        letter = Character.toLowerCase(l);
        setScale(4.0);
        setX(x);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(z); // positionnement au milieu de la profondeur de la room
        
        System.out.println("Letter: "+letter);
        String texturePath = "/models/letter/"+letter+".png";
        setTexture(texturePath);
        setModel("/models/letter/cube.obj");  
    }

    public Letter(char l, double x, double z, Room room, String endroit){
        
        letter = Character.toLowerCase(l);
        if(endroit.equals("apprendre")) {
            setScale(2.5);
        } else{
            setScale(4.0);
        }

        setX(x);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(z); // positionnement au milieu de la profondeur de la room
        
        System.out.println("Letter: "+letter);
        String texturePath = "/models/letter/"+letter+".png";
        
        setTexture(texturePath);
        setModel("/models/letter/cube.obj");  
    }

    public char getLetter() {
        return letter;
    }
}
