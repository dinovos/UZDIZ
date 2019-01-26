/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.dinnovose2.dz3.prikaz;

/**
 *
 * @author Dino
 */
public class PrikazEkrana {
    
    public static final String ANSI_ESC = "\033[";

    static void postavi(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }

    static void prikazi(int x, int y, int boja, String tekst) {
        postavi(x, y);
        System.out.print(ANSI_ESC + boja + "m");
        System.out.print(tekst);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
        }
    }
    
    static void brisiCijeliZaslon(){
        System.out.println(ANSI_ESC + "2J");
    }
    
    static void brisiDole(){
        System.out.println(ANSI_ESC + "J");
    }
    
    static void brisiGore(){
        System.out.println(ANSI_ESC + "1J");
    }
    
    static void brisiUnazad(){
        System.out.println(ANSI_ESC +  "1K");
    }
    
    static void brisiUnaprijed(){
        System.out.println(ANSI_ESC + "K");
    }
    
    static void scrollCijeliZaslon(){
        System.out.println(ANSI_ESC + "r");
    }
    
    static void scrollDole(){
        System.out.println("\033" + "D");
    }
    
    static void scrollGore(){
        System.out.println("\033" + "M");
    }
    
    static void scrollOdDo(int i, int y){
        System.out.println(ANSI_ESC + i + ";" + y + "r");
    }
    
}
