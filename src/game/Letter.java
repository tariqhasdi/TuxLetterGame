
package game;

/**
 *
 * @author Tariq
 */


import env3d.advanced.EnvNode;

public class Letter extends EnvNode{
    
    private char letter;
    
    public Letter(char l, double x, double z){
        setScale(5.0);
        setX(x);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(z); // positionnement au milieu de la profondeur de la room
        if ( l == ' ' ) {
            setTexture("models/letter/cube.png"); 
        }
        else{
            setTexture("models/letter/"+l+".png");    
        }
        setModel("models/letter/cube.obj");   
    }
    
}
