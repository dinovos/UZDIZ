/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.dinnovose2.dz2.podaci;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dino
 */
public class Ispis {

    String datoteka;
    int br = 0;

    public Ispis(String datoteka) {
        this.datoteka = datoteka;
    }

    //zapisi poruku u datoteku
    public void zapisiUDatoteku(String poruka) {
        File file = new File(datoteka);
        if(!file.isFile()){
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Ispis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            //ako je postojala stara datoteka obrisi i stvori novu
            while(br < 1){
                try {
                    file.delete();
                    file.createNewFile();
                    br++;
                } catch (IOException ex) {
                    Logger.getLogger(Ispis.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //zapisi u datoteku - poruku
        try {
            Files.write(Paths.get(datoteka), poruka.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(datoteka), System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
            
        } catch (IOException ex) {
            System.out.println("Greska kod zapisivanja u datoteku!");
        }
    }

}
