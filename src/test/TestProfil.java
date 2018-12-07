/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import game.Profil;

/**
 *
 * @author Tariq
 */
public class TestProfil {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Profil profil = new Profil("hasdi.xml");
        System.out.println(profil.toString());
    }
    
}
