package org.foi.uzdiz.dinnovose2.dz3.singleton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dino
 */
public class Parametri {

    private static Parametri instance = null;

    private Parametri() {
    }
    
    public static Parametri getInstance() {
        if(instance == null){
            instance = new Parametri();
        }
        return instance;
    }

    private Properties postavke;

    public Properties dajSveParametre() {
        return this.postavke;
    }

    public String dajParametar(String postavka) {
        return this.postavke.getProperty(postavka);
    }

    public void ucitajSveParametre(String datoteka) {
        //TODO izbaceno zbog parametara --brg --brd za konzolu
//        StringBuilder sb = new StringBuilder();
//        for (String i : datoteka) {
//            sb.append(i);
//        }
//        String dat = sb.toString();
//        String dato = dat.trim();
        postavke = preuzmiParametre(datoteka.trim());
    }

    public static Properties preuzmiParametre(String datoteka) {

        if (datoteka == null || datoteka.length() == 0) {
            try {
                System.out.println("Datoteka mora imati naziv!");
            } catch (Exception ex) {
                Logger.getLogger(Parametri.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        File datKonf = new File(datoteka);

        if (!datKonf.exists()) {
            try {
                System.out.println("Datoteka "+datoteka+" ne postoji!");
                System.exit(0);
            } catch (Exception ex) {
                Logger.getLogger(Parametri.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (datKonf.isDirectory()) {
            try {
                throw new Exception("Datoteka " + datoteka + " nije datoteka!");
            } catch (Exception ex) {
                Logger.getLogger(Parametri.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(datKonf), "UTF8"));
            Properties postavke = new Properties();
            postavke.load(br);
            br.close();
            return postavke;

        } catch (IOException ex) {
            try {
                System.out.println("Problem kod čitanja datoteke "
                        + datKonf.getAbsolutePath());
            } catch (Exception ex1) {
                Logger.getLogger(Parametri.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

}
