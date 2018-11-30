/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import game.Partie;

/**
 *
 * @author Tariq
 */
public class testPartie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Partie partie = new Partie("24/11/2018 à 12:27", "maman" , 1);
        partie.setTrouve(3);
        partie.setTemps(10);
        System.out.println(partie.toString());
    }
    
}
