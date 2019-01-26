package org.foi.uzdiz.dinnovose2.dz3.podaci;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.foi.uzdiz.dinnovose2.dz3.builder.Dispecer;
import org.foi.uzdiz.dinnovose2.dz3.builder.Podrucje;
import org.foi.uzdiz.dinnovose2.dz3.builder.SpremnikPostavke;
import org.foi.uzdiz.dinnovose2.dz3.builder.Ulica;
import org.foi.uzdiz.dinnovose2.dz3.builder.Vozilo;

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
    String prvaLista;
    String drugaLista;

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
                scanner.close();
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
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Datoteka Ulica ne postoji!");
        }
        return listaUlica;
    }

    //Dohvati podatke vozila
    public List<Vozilo> dohvatiPodatkeVozila(String datoteka) {
        List<Vozilo> listaVozila = new ArrayList<>();
        String dijeloviVozaca[];

        try {
            //preskoci prvi red u datoteci
            try (Scanner scanner = new Scanner(new File(datoteka))) {
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
                        dijeloviVozaca = vozaci.split(",");
                        List<String> vozaci = new ArrayList<>();
                        for (int i = 0; i < dijeloviVozaca.length; i++) {
                            vozaci.add(dijeloviVozaca[i]);
                        }
                        Vozilo vozilo = new Vozilo.VoziloBuilder(idVozila, nazivVozila, vozaci, tipVozila, vrstaVozila, nosivostVozila, 0).build();
                        listaVozila.add(vozilo);
                    } else {
                        System.out.println("Greška u parametrima vozila, preskačem liniju");
                    }
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
            try (Scanner scanner = new Scanner(new File(datoteka))) {
                
                //scanner podaci podrucja, poznati bug
                //u NB radi sa scanner.nextLine u CMD ne
                //scanner.nextLine();
                
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                   
                    //obilazak za bug sa scannerom
                    if(line.equals("﻿id;naziv;dijelovi")){ line = scanner.nextLine();}
                    
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
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Datoteka Podrucja ne postoji!");
        }
        return listaPodrucja;
    }

    public List<Dispecer> dohvatiPodatkeDispecera(String datoteka) {
        List<Dispecer> listaDispecera = new ArrayList<>();
        String[] prvLista;
        String[] druLista;

        try {
            try (Scanner scanner = new Scanner(new File(datoteka))) {
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] lineSplit = line.split(";");
                    switch (lineSplit.length) {
                        case 1: {
                            komanda = lineSplit[0];
                            Dispecer disp = new Dispecer.DispecerBuilder(komanda, null, null).build();
                            listaDispecera.add(disp);
                            break;
                        }
                        case 2: {
                            komanda = lineSplit[0];
                            prvaLista = lineSplit[1];
                            prvLista = prvaLista.split(",");
                            List<String> lista = new ArrayList<>();
                            for (int i = 0; i < prvLista.length; i++) {
                                lista.add(prvLista[i]);
                            }
                            Dispecer disp = new Dispecer.DispecerBuilder(komanda, lista, null).build();
                            listaDispecera.add(disp);
                            break;
                        }
                        case 3: {
                            komanda = lineSplit[0];
                            prvaLista = lineSplit[1];
                            drugaLista = lineSplit[2];
                            prvLista = prvaLista.split(",");
                            List<String> lista = new ArrayList<>();
                            for (int i = 0; i < prvLista.length; i++) {
                                lista.add(prvLista[i]);
                            }
                            druLista = drugaLista.split(",");
                            List<String> drLista = new ArrayList<>();
                            for (int i = 0; i < druLista.length; i++) {
                                drLista.add(druLista[i]);
                            }
                            Dispecer disp = new Dispecer.DispecerBuilder(komanda, lista, drLista).build();
                            listaDispecera.add(disp);
                            break;
                        }
                        default:
                            System.out.println("Komanda nije u odgovarajućem formatu!");
                            break;
                    }

                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Datoteka Dispecer ne postoji!");
        }

        return listaDispecera;
    }

    public Dispecer dohvatiKomanduSaKonzole(String komanda) {
        
        String[] prvLista;
        String[] druLista;
        String[] stringSplit = komanda.split(";");
        
        switch (stringSplit.length) {
            //ovisno gdje se pokrece, promjena case-ova
            //case 1,2,3 za NB ili case 2,3,4 za ConEmu i CMD
            case 2: {
                komanda = stringSplit[0].trim();
                if(komanda.equals("VOZA�I") || komanda.equals("VOZAÄŚI") || komanda.equals("VOZA I") || komanda.equals("VOZA?I")){
                    komanda = "VOZAČI";
                }
                Dispecer disp = new Dispecer.DispecerBuilder(komanda, null, null).build();
                return disp;
            }
            case 3: {
                komanda = stringSplit[0].trim();
                if(komanda.equals("GODI�NJI ODMOR") || komanda.equals("GODISNJI ODMOR") || komanda.equals("GODI?NJI ODMOR")){
                    komanda = "GODIŠNJI ODMOR";
                }
                prvaLista = stringSplit[1].trim();
                prvLista = prvaLista.split(",");
                List<String> lista = new ArrayList<>();
                for (int i = 0; i < prvLista.length; i++) {
                    lista.add(prvLista[i]);
                }
                Dispecer disp = new Dispecer.DispecerBuilder(komanda, lista, null).build();
                return disp;
            }
            case 4: {
                komanda = stringSplit[0].trim();
                prvaLista = stringSplit[1].trim();
                drugaLista = stringSplit[2].trim();
                prvLista = prvaLista.split(",");
                List<String> lista = new ArrayList<>();
                for (int i = 0; i < prvLista.length; i++) {
                    lista.add(prvLista[i]);
                }
                druLista = drugaLista.split(",");
                List<String> drLista = new ArrayList<>();
                for (int i = 0; i < druLista.length; i++) {
                    drLista.add(druLista[i]);
                }
                Dispecer disp = new Dispecer.DispecerBuilder(komanda, lista, drLista).build();
                return disp;
            }
            default:
                System.out.println("Komanda nije u odgovarajućem formatu!");
                Dispecer disp = new Dispecer.DispecerBuilder(null, null, null).build();
                return disp;
        }

    }

}
