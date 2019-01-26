/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.dinnovose2.dz3.mvc;

/**
 *
 * @author Dino
 */
public class Model {
    
    public static final String ANSI_ESC = "\033[";

    public void postavi(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }

    public void prikazi(int x, int y, int boja, String tekst) {
        postavi(x, y);
        System.out.print(ANSI_ESC + boja + "m");
        System.out.print(tekst);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
        }
    }
    
    public void brisiCijeliZaslon(){
        System.out.println(ANSI_ESC + "2J");
    }
    
    public void brisiDole(){
        System.out.println(ANSI_ESC + "J");
    }
    
    public void brisiGore(){
        System.out.println(ANSI_ESC + "1J");
    }
    
    public void brisiUnazad(){
        System.out.println(ANSI_ESC +  "1K");
    }
    
    public void brisiUnaprijed(){
        System.out.println(ANSI_ESC + "K");
    }
    
    public void scrollCijeliZaslon(){
        System.out.println(ANSI_ESC + "r");
    }
    
    public void scrollDole(){
        System.out.println("\033" + "D");
    }
    
    public void scrollGore(){
        System.out.println("\033" + "M");
    }
    
    public void scrollOdDo(int i, int y){
        System.out.println(ANSI_ESC + i + ";" + y + "r");
    }
    
}
