package org.foi.uzdiz.dinnovose2.dz2.podaci;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.foi.uzdiz.dinnovose2.dz2.builder.Dispecer;
import org.foi.uzdiz.dinnovose2.dz2.builder.Podrucje;
import org.foi.uzdiz.dinnovose2.dz2.builder.SpremnikPostavke;
import org.foi.uzdiz.dinnovose2.dz2.builder.Ulica;
import org.foi.uzdiz.dinnovose2.dz2.builder.Vozilo;

/**
 *
 * @author Dino
 */
public class Datoteka {

    //Podaci spremnika
    String nazivSpremnika;
    int vrsta;
    int jedanNaBrojMalih;
    int jedanNaBrojSrednjih;
    int jedanNaBrojVelikih;
    int nosivostSpremnika;

    //Podaci ulica
    String idUlice;
    String nazivUlice;
    int brojMjesta;
    int udioMali;
    int udioSrednji;
    int udioVeliki;
    int brojMalih;
    int brojSrednjih;
    int brojVelikih;

    //Podaci vozila
    String idVozila;
    String nazivVozila;
    String vozaci;
    int tipVozila;
    int vrstaVozila;
    int nosivostVozila;

    //Podaci podrucja
    String idPodrucja;
    String nazivPodrucja;
    String podrucja;

    //Podaci dispecera
    String komanda;
    String listaVozila;

    //Dohvati podatke spremnika
    public List<SpremnikPostavke> dohvatiPodatkeSpremnici(String datoteka) {
        List<SpremnikPostavke> listaSpremnika = new ArrayList<>();
        try {
            try (Scanner scanner = new Scanner(new File(datoteka))) {
                //preskoci prvi red u datoteci
                scanner.nextLine();

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] lineSplit = line.split(";");
                    if (lineSplit.length == 6) {
                        nazivSpremnika = lineSplit[0];
                        vrsta = Integer.parseInt(lineSplit[1]);
                        jedanNaBrojMalih = Integer.parseInt(lineSplit[2]);
                        jedanNaBrojSrednjih = Integer.parseInt(lineSplit[3]);
                        jedanNaBrojVelikih = Integer.parseInt(lineSplit[4]);
                        nosivostSpremnika = Integer.parseInt(lineSplit[5]);
                        SpremnikPostavke spremnik = new SpremnikPostavke.SpremniciPostavkeBuilder(nazivSpremnika, vrsta, nosivostSpremnika,
                                jedanNaBrojMalih, jedanNaBrojSrednjih, jedanNaBrojVelikih).build();
                        listaSpremnika.add(spremnik);
                    } else {
                        System.out.println("Greška u parametrima spremnika, preskačem liniju");
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
                if (lineSplit.length == 6) {
                    idUlice = lineSplit[0];
                    nazivUlice = lineSplit[1];
                    brojMjesta = Integer.parseInt(lineSplit[2]);
                    udioMali = Integer.parseInt(lineSplit[3]);
                    udioSrednji = Integer.parseInt(lineSplit[4]);
                    udioVeliki = Integer.parseInt(lineSplit[5]);
                    brojMalih = (int) Math.round(((brojMjesta * udioMali) / 100d));
                    brojSrednjih = (int) Math.round(((brojMjesta * udioSrednji) / 100d));
                    brojVelikih = (int) Math.round(((brojMjesta * udioVeliki) / 100d));
                    Ulica ulica = new Ulica.UlicaBuilder(idUlice, nazivUlice, brojMjesta, udioMali, udioSrednji, udioVeliki, brojMalih, brojSrednjih, brojVelikih).build();
                    listaUlica.add(ulica);
                } else {
                    System.out.println("Greška u parametrima ulica, preskačem liniju");
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
                if (lineSplit.length == 6) {
                    idVozila = lineSplit[0];
                    nazivVozila = lineSplit[1];
                    tipVozila = Integer.parseInt(lineSplit[2]);
                    vrstaVozila = Integer.parseInt(lineSplit[3]);
                    nosivostVozila = Integer.parseInt(lineSplit[4]);
                    vozaci = lineSplit[5];
                    Vozilo vozilo = new Vozilo.VoziloBuilder(idVozila, nazivVozila, vozaci, tipVozila, vrstaVozila, nosivostVozila).build();
                    listaVozila.add(vozilo);
                } else {
                    System.out.println("Greška u parametrima vozila, preskačem liniju");
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Datoteka Vozila ne postoji!");
        }
        return listaVozila;
    }

    public List<Podrucje> dohvatiPodatkePodrucja(String datoteka) {
        List<Podrucje> listaPodrucja = new ArrayList<>();
        String[] dijeloviPodrucja;

        try {
            Scanner scanner = new Scanner(new File(datoteka));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineSplit = line.split(";");
                if (lineSplit.length == 3) {
                    idPodrucja = lineSplit[0];
                    nazivPodrucja = lineSplit[1];
                    podrucja = lineSplit[2];
                    dijeloviPodrucja = podrucja.split(",");
                    List<String> dijelovi = new ArrayList<>();
                    for (int i = 0; i < dijeloviPodrucja.length; i++) {
                        dijelovi.add(dijeloviPodrucja[i]);
                    }
                    Podrucje pod = new Podrucje.PodrucjeBuilder(idPodrucja, nazivPodrucja, dijelovi).build();
                    listaPodrucja.add(pod);
                } else {
                    System.out.println("Greška u parametrima područja, preskačem liniju");
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Datoteka Podrucja ne postoji!");
        }
        return listaPodrucja;
    }

    public List<Dispecer> dohvatiPodatkeDispecera(String datoteka) {
        List<Dispecer> listaDispecera = new ArrayList<>();
        String[] vozila;
        
        try {
            Scanner scanner = new Scanner(new File(datoteka));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineSplit = line.split(";");
                if (lineSplit.length == 2) {
                    komanda = lineSplit[0];
                    listaVozila = lineSplit[1];
                    vozila = listaVozila.split(",");
                    List<String> listaVozila = new ArrayList<>();
                    for(int i = 0; i<vozila.length;i++){
                        listaVozila.add(vozila[i]);
                    }
                    Dispecer disp = new Dispecer.DispecerBuilder(komanda, listaVozila).build();
                    listaDispecera.add(disp);
                } else if(lineSplit.length == 1){
                    komanda = lineSplit[0];
                    Dispecer disp = new Dispecer.DispecerBuilder(komanda, null).build();
                    listaDispecera.add(disp);
                }

            }
        } catch (FileNotFoundException ex) {
            System.out.println("Datoteka Dispecer ne postoji!");
        }

        return listaDispecera;
    }
    
    
}
