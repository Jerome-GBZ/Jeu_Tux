package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import org.lwjgl.input.Keyboard;

public class Tux extends EnvNode {
    private Env env;
    private Room room;
    
    public Tux(Env env, Room room){
        this.env = env;
        this.room = room;

        setScale(0.08);
        setX(this.room.getWidth()/2);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(this.room.getDepth()/2); // positionnement au milieu de la profondeur de la room
        
        setTexture("/assets/player/nouveauTux.jpg");
        setModel("/assets/player/nouveauTux.obj");  
    }
    
    /*
     * Déplacement de notre personnage avec les fleches directionnels ou avec ZQSD
     */
    public void déplace(){
        CollisionDetection collisionDetection = testeRoomCollision();
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
            // Haut
            if(collisionDetection != CollisionDetection.COLLISION_BACK 
            && collisionDetection != CollisionDetection.COLLISION_CORNER_BACK_LEFT
            && collisionDetection != CollisionDetection.COLLISION_CORNER_BACK_RIGHT){
                this.setRotateY(180);
                this.setZ(this.getZ() - 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
            // Gauche
            if(collisionDetection != CollisionDetection.COLLISION_LEFT && collisionDetection != CollisionDetection.COLLISION_CORNER_BACK_LEFT
            &&collisionDetection != CollisionDetection.COLLISION_CORNER_FRONT_LEFT){   
                this.setRotateY(0);
                this.setRotateX(0);
                this.setX(this.getX() - 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'droite' ou D
            // Droite
            if(collisionDetection != CollisionDetection.COLLISION_RIGHT && collisionDetection != CollisionDetection.COLLISION_CORNER_BACK_RIGHT
            &&collisionDetection != CollisionDetection.COLLISION_CORNER_FRONT_RIGHT
            ){
                this.setRotateY(0);
                this.setRotateX(0);
                this.setX(this.getX() + 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'down' ou S
            // Bas
            if(collisionDetection != CollisionDetection.COLLISION_FRONT && collisionDetection != CollisionDetection.COLLISION_CORNER_FRONT_RIGHT
            && collisionDetection != CollisionDetection.COLLISION_CORNER_FRONT_LEFT){
                this.setRotateY(0);
                this.setZ(this.getZ() + 1.0);
            }
        }

        
    }

    /*
     * On vérifie que notre personnage est bien dans la box
     */
    private CollisionDetection testeRoomCollision(){
        double x = getX();
        double z = getZ();
        double personnageSize = 5;

        if(x==personnageSize && z==personnageSize){
            return CollisionDetection.COLLISION_CORNER_BACK_LEFT;
        }else if(z == personnageSize && x == room.getWidth()-personnageSize){
            return CollisionDetection.COLLISION_CORNER_BACK_RIGHT;
        }else if(x == personnageSize && z == room.getDepth()-personnageSize){
            return CollisionDetection.COLLISION_CORNER_FRONT_LEFT;
        }else if(x == room.getWidth()-personnageSize&& z == room.getDepth()-personnageSize){
            return CollisionDetection.COLLISION_CORNER_FRONT_RIGHT;
        }

        if(x == personnageSize) { 
            return CollisionDetection.COLLISION_LEFT;
        } else if(x == room.getWidth()-personnageSize) { 
            return CollisionDetection.COLLISION_RIGHT;
        } else if(z == personnageSize) { 
            return CollisionDetection.COLLISION_BACK;
        } else if(z == room.getDepth()-personnageSize) { 
            return CollisionDetection.COLLISION_FRONT;
        } else {
            return CollisionDetection.NOTHING;
        }
    }
}
