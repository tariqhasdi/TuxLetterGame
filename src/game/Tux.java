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
 * @author Tariq
 */
public class Tux extends EnvNode {
    
    private Env env;
    private Room room;
    
    public Tux(Env env,Room room){
        
        this.env = env;
        this.room = room;

        setScale(4.0);
        setX( room.getWidth() / 2 );// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1 ); // positionnement en hauteur basé sur la taille de Tux
        setZ( room.getDepth() / 2 ); // positionnement au milieu de la profondeur de la room
        setTexture("models/tux/tux.png");
        setModel("models/tux/tux.obj");     
    }
    
    public void déplace() {
            
        
            if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
                // Haut

                this.setRotateY(180);
                if ( !this.testeRoomCollision( this.getX()  , this.getZ() - 1 ) ){
                    this.setZ(this.getZ() - 1);
                }
            }

            if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q

                // Gauche
                this.setRotateY(270);
                if ( !this.testeRoomCollision( this.getX() - 1  , this.getZ() ) ){
                    this.setX(this.getX() - 1);
                }
            }

            if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'bas' ou S
                // bas

                this.setRotateY(0);
                if ( !this.testeRoomCollision( this.getX()  , this.getZ() + 1 ) ){
                    this.setZ(this.getZ() + 1);
                }
            }

            if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'droite' ou D
                // droit

                this.setRotateY(90);
                if ( !this.testeRoomCollision( this.getX() + 1 , this.getZ() ) ){
                    this.setX(this.getX() + 1);
                }
            }
    }
    
    private Boolean testeRoomCollision( double  x , double  z) {
        
       if (  x  < this.getScale()  ||  z  <  this.getScale() 
          || x > this.room.getWidth() - this.getScale() 
          || z > this.room.getDepth() - this.getScale()   ) 
           return true;
       return false;
    }
}

