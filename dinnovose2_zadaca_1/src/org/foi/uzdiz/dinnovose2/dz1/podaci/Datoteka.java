package org.foi.uzdiz.dinnovose2.dz1.podaci;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.foi.uzdiz.dinnovose2.dz1.Spremnici;
import org.foi.uzdiz.dinnovose2.dz1.Ulica;
import org.foi.uzdiz.dinnovose2.dz1.Vozilo;

/**
 *
 * @author Dino
 */
public class Datoteka {

    //Podaci spremnici
    String nazivSpremnika;
    int vrsta;
    int jedanNaBrojMalih;
    int jedanNaBrojSrednjih;
    int jedanNaBrojVelikih;
    int nosivostSpremnika;

    //Podaci ulica
    String nazivUlice;
    int brojMjesta;
    int udioMali;
    int udioSrednji;
    int udioVeliki;
    int brojMalih; 
    int brojSrednjih; 
    int brojVelikih;

    //Podaci vozila
    String nazivVozila;
    String vozaci;
    int tipVozila;
    int vrstaVozila;
    int nosivostVozila;
    
    //Dohvati podatke spremnika
    public List<Spremnici> dohvatiPodatkeSpremnici(String datoteka) {
        List<Spremnici> listaSpremnika = new ArrayList<>();
        try {
            try (Scanner scanner = new Scanner(new File(datoteka))) {
                //preskoci prvi red u datoteci
                scanner.nextLine();

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] lineSplit = line.split(";");
                    if (lineSplit.length > 6) {
                        System.out.println("Greška u parametrima datoteka: " + datoteka);
                        return null;
                    } else {
                        nazivSpremnika = lineSplit[0];
                        vrsta = Integer.parseInt(lineSplit[1]);
                        jedanNaBrojMalih = Integer.parseInt(lineSplit[2]);
                        jedanNaBrojSrednjih = Integer.parseInt(lineSplit[3]);
                        jedanNaBrojVelikih = Integer.parseInt(lineSplit[4]);
                        nosivostSpremnika = Integer.parseInt(lineSplit[5]);
                        Spremnici spremnik = new Spremnici.SpremniciBuilder(nazivSpremnika, vrsta, nosivostSpremnika,
                                jedanNaBrojMalih, jedanNaBrojSrednjih, jedanNaBrojVelikih).build();
                        listaSpremnika.add(spremnik);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Datoteka Spremnici ne postoji!");
        }
        return listaSpremnika;
    }

    //Dohvati podatke ulica
    public List<Ulica> dohvatiPodatkeUlica(String datoteka) {
        List<Ulica> listaUlica = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(datoteka));
            //preskoci prvi red u datoteci
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineSplit = line.split(";");
                if (lineSplit.length > 5) {
                    System.out.println("Greška u parametrima datoteka: " + datoteka);
                    return null;
                } else {
                    nazivUlice = lineSplit[0];
                    brojMjesta = Integer.parseInt(lineSplit[1]);
                    udioMali = Integer.parseInt(lineSplit[2]);
                    udioSrednji = Integer.parseInt(lineSplit[3]);
                    udioVeliki = Integer.parseInt(lineSplit[4]);
                    brojMalih = (int)Math.round(((brojMjesta*udioMali)/100d));
                    brojSrednjih = (int)Math.round(((brojMjesta*udioSrednji)/100d));
                    brojVelikih = (int)Math.round(((brojMjesta*udioVeliki)/100d));
                    Ulica ulica = new Ulica.UlicaBuilder(nazivUlice, brojMjesta, udioMali, udioSrednji, udioVeliki, brojMalih, brojSrednjih, brojVelikih).build();
                    listaUlica.add(ulica);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Datoteka Ulica ne postoji!");
        }
        return listaUlica;
    }

    //Dohvati podatke vozila
    public List<Vozilo> dohvatiPodatkeVozila(String datoteka) {
        List<Vozilo> listaVozila = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(datoteka));
            //preskoci prvi red u datoteci
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineSplit = line.split(";");
                if (lineSplit.length > 5) {
                    System.out.println("Greška u parametrima datoteka: " + datoteka);
                    return null;
                } else {
                     nazivVozila = lineSplit[0];
                     tipVozila = Integer.parseInt(lineSplit[1]);
                     vrstaVozila = Integer.parseInt(lineSplit[2]);
                     nosivostVozila = Integer.parseInt(lineSplit[3]);
                     vozaci = lineSplit[4];
                     Vozilo vozilo = new Vozilo.VoziloBuilder(nazivVozila, vozaci, tipVozila, vrstaVozila, nosivostVozila).build();
                     listaVozila.add(vozilo);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Datoteka Vozila ne postoji!");
        }
        return listaVozila;
    }
}
