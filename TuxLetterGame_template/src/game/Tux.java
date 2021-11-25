/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author riad7
 */
public class Tux extends EnvNode {
    private Env env;
    private Room room;
    
    
    public Tux(Env env, Room room){
        this.env = env;
        this.room = room;
        setScale(4.0);
        setX(this.room.getWidth()/2);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(this.room.getDepth()/2); // positionnement au milieu de la profondeur de la room
        setTexture("/models/tux/tux.png");
        setModel("/models/tux/tux.obj");  
    }
    
    
    public void déplace(){
        CollisionDetection collisionDetection = testeRoomCollision();
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
            // Haut
            if(collisionDetection != CollisionDetection.COLLISION_BACK){
                
                this.setRotateY(180);
                this.setZ(this.getZ() - 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
            if(collisionDetection != CollisionDetection.COLLISION_LEFT){   
                this.setRotateY(0);
                this.setRotateX(0);
                this.setX(this.getX() - 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'droite' ou D
            if(collisionDetection != CollisionDetection.COLLISION_RIGHT){
                this.setRotateY(0);
                this.setRotateX(0);
                this.setX(this.getX() + 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'down' ou S
            // Haut
            if(collisionDetection != CollisionDetection.COLLISION_FRONT){
                this.setRotateY(0);
                this.setZ(this.getZ() + 1.0);
            }
        }

        
    }
    /**
     * On vérifie que notre personnage est bien dans la box
     * 
     */
    private CollisionDetection testeRoomCollision(){
        double x = getX();
        double z = getZ();
        double personnageSize = 5;
        if(x==personnageSize){
            return CollisionDetection.COLLISION_LEFT;
        }else if(x==room.getWidth()-personnageSize){
            return CollisionDetection.COLLISION_RIGHT;
        }else if(z==personnageSize){
            return CollisionDetection.COLLISION_BACK;
        }else if(z==room.getDepth()-personnageSize){
            return CollisionDetection.COLLISION_FRONT;
        }
        return CollisionDetection.NOTHING;
    }
}
