package org.foi.uzdiz.dinnovose2.dz1.podaci;

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

    private static final Parametri instance = new Parametri();

    private Parametri() {
    }

    ;
    
    public static Parametri getInstance() {
        return instance;
    }

    private Properties postavke;

    public Properties dajSveParametre() {
        return this.postavke;
    }

    public String dajParametar(String postavka) {
        return this.postavke.getProperty(postavka);
    }

    public void ucitajSveParametre(String[] datoteka) {
        StringBuilder sb = new StringBuilder();
        for (String i : datoteka) {
            sb.append(i);
        }
        String dat = sb.toString();
        String dato = dat.trim();
        postavke = preuzmiParametre(dato);
    }

    public static Properties preuzmiParametre(String datoteka) {

        if (datoteka == null || datoteka.length() == 0) {
            try {
                throw new Exception("Datoteka mora imati naziv");
            } catch (Exception ex) {
                Logger.getLogger(Parametri.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        File datKonf = new File(datoteka);

        if (!datKonf.exists()) {
            try {
                throw new Exception("Datoteka " + datoteka + " ne postoji!");
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
                throw new Exception("Problem kod čitanja datoteke "
                        + datKonf.getAbsolutePath());
            } catch (Exception ex1) {
                Logger.getLogger(Parametri.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

}
