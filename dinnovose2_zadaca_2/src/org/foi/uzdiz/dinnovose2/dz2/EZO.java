package org.foi.uzdiz.dinnovose2.dz2;

import org.foi.uzdiz.dinnovose2.dz2.builder.SpremnikPostavke;
import org.foi.uzdiz.dinnovose2.dz2.builder.Korisnik;
import org.foi.uzdiz.dinnovose2.dz2.builder.Ulica;
import org.foi.uzdiz.dinnovose2.dz2.builder.Vozilo;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.dinnovose2.dz2.builder.Dispecer;
import org.foi.uzdiz.dinnovose2.dz2.builder.Podrucje;
import org.foi.uzdiz.dinnovose2.dz2.builder.Spremnik;
import org.foi.uzdiz.dinnovose2.dz2.podaci.Datoteka;
import org.foi.uzdiz.dinnovose2.dz2.podaci.Ispis;
import org.foi.uzdiz.dinnovose2.dz2.generiranje.Generator;
import org.foi.uzdiz.dinnovose2.dz2.singleton.Parametri;

/**
 *
 * @author Dino
 */
public class EZO {

    int ispis;
    static int sjemeGeneratora;
    static int brojDecimala;
    int brojRadnihCiklusaZaOdvoz;
    int preuzimanje;

    static int maliMin;
    static int srednjiMin;
    static int velikiMin;

    static int maliStaklo;
    static int maliPapir;
    static int maliMetal;
    static int maliBio;
    static int maliMjesano;

    static int srednjiStaklo;
    static int srednjiPapir;
    static int srednjiMetal;
    static int srednjiBio;
    static int srednjiMjesano;

    static int velikiStaklo;
    static int velikiPapir;
    static int velikiMetal;
    static int velikiBio;
    static int velikiMjesano;

    int slucajniInt;
    long slucajniLong;
    float slucajniFloat;

    static float ukupnoGeneriranoOtpada;
    static float generiranoMetalnogOtpada;
    static float generiranoBioOtpada;
    static float generiranoStaklenogOtpada;
    static float generiranoMjesanogOtpada;
    static float generiranoPapirOtpada;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Dohvacanje parametara
        Parametri parametri = Parametri.getInstance();
        parametri.ucitajSveParametre(args);

        //Random generator
        sjemeGeneratora = Integer.parseInt(parametri.dajParametar("sjemeGeneratora"));
        brojDecimala = Integer.parseInt(parametri.dajParametar("brojDecimala"));

        Generator gen = new Generator(sjemeGeneratora);

        Datoteka dat = new Datoteka();
        Ispis is = new Ispis(parametri.dajParametar("izlaz"));

        //Ucitavanje ulica
        List<Ulica> ulice = ucitajUlice(dat, parametri, is);
        //Ucitavanje postavki spremnika
        List<SpremnikPostavke> postavkeSpremnika = ucitajPostavkeSpremnika(dat, parametri, is);
        //Ucitavanje vozila
        List<Vozilo> vozila = ucitajPodatkeVozila(dat, parametri, is);
        //Ucitavanje podrucja
        List<Podrucje> podrucja = ucitajPodatkePodrucja(dat, parametri, is);
        //Ucitavanje dispecera
        List<Dispecer> dispecer = ucitajPodatkeDispecera(dat, parametri, is);
        //Kreiraj korisnike
        dohvatiParametreKorisnika(parametri);
        List<Korisnik> korisnici = kreirajKorisnike(ulice, gen, is);

        //Statistika za prostorni sustav
        statistikaProstornogSustava(is, ulice, korisnici, podrucja);

        //Statistika ukupno kreiranog otpada
        statistikaKreiranogOtpada(is);

        //dodijeli spremnike korisnicima
        List<Spremnik> listaSpremnika = dodjeljivanjeSpremnikaKorisnicima(korisnici, postavkeSpremnika, ulice);

        //izracunajPopunjenostSpremnika
        izracunajPopunjenostSpremnika(listaSpremnika);

        //Statistika kreiranih spremnika
        statistikaKreiranihSpremnika(is, listaSpremnika);

        //Obrada podataka
        obradaPodataka(dispecer, vozila, ulice, listaSpremnika, korisnici, podrucja, is);

    }

    private static void izracunajPopunjenostSpremnika(List<Spremnik> listaSpremnika) {
        for (Spremnik s : listaSpremnika) {
            float ukupno = 0;
            for (Korisnik k : s.getKorisnici()) {
                if (s.getOtpad().equals("staklo")) {
                    ukupno = ukupno + k.getStakleniOtpad();
                }
                if (s.getOtpad().equals("papir")) {
                    ukupno = ukupno + k.getPapirnatiOtpad();
                }
                if (s.getOtpad().equals("metal")) {
                    ukupno = ukupno + k.getMetalniOtpad();
                }
                if (s.getOtpad().equals("bio")) {
                    ukupno = ukupno + k.getBioOtpad();
                }
                if (s.getOtpad().equals("mješano")) {
                    ukupno = ukupno + k.getMjesaniOtpad();
                }
            }
            s.setPopunjenost(ukupno);
        }
    }

    private static void statistikaKreiranihSpremnika(Ispis is, List<Spremnik> listaSpremnika) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator()).append("Kreirani spremnici").append(System.lineSeparator());
        sb.append("------------------------------------------------------------------").append(System.lineSeparator());
        for (Spremnik s : listaSpremnika) {
            sb.append("Spremnik: ").append(s.getId()).append(", otpad: ").append(s.getOtpad()).append(", Popunjenost: ").append(s.getPopunjenost()).append(", korisnici: ").append(System.lineSeparator());
            sb.append("------------------------------------------------------------------").append(System.lineSeparator());
            System.out.println("\n Spremnik: " + s.getId() + ", otpad: " + s.getOtpad() + ", popunjenost: " + s.getPopunjenost() + ", korisnici: ");
            System.out.println("------------------------------------------------------------------");
            for (Korisnik k : s.getKorisnici()) {
                sb.append(k.getPodaciKorisnik()).append(System.lineSeparator());
                System.out.println(k.getPodaciKorisnik());
            }
        }
        is.zapisiUDatoteku(sb.toString());
    }

    private static void statistikaProstornogSustava(Ispis is, List<Ulica> ulice, List<Korisnik> korisnici,
            List<Podrucje> podrucja) {
        is.zapisiUDatoteku("-------------------------------------------------------");
        is.zapisiUDatoteku("Statistika prostornog sustava");
        is.zapisiUDatoteku("-------------------------------------------------------");
        List<String> statistika = new ArrayList<>();
        for (Podrucje p : podrucja) {
            float bioOtpad = 0;
            float metalOtpad = 0;
            float mjesanoOtpad = 0;
            float stakloOtpad = 0;
            float papirOtpad = 0;
            float ukupnoOtpad = 0;
            for (Ulica u : ulice) {
                if (p.getDijelovi().contains(u.getIdUlice())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(System.lineSeparator());
                    sb.append("Podrucje: " + p.getNaziv() + ", ");
                    for (Korisnik k : korisnici) {
                        if (k.getUlica().equals(u.getIdUlice())) {
                            bioOtpad = bioOtpad + k.getBioOtpad();
                            metalOtpad = metalOtpad + k.getMetalniOtpad();
                            mjesanoOtpad = mjesanoOtpad + k.getMjesaniOtpad();
                            stakloOtpad = stakloOtpad + k.getStakleniOtpad();
                            papirOtpad = papirOtpad + k.getPapirnatiOtpad();
                            ukupnoOtpad = bioOtpad + metalOtpad + mjesanoOtpad + stakloOtpad + papirOtpad;
                        }
                    }
                    sb.append("Ulica: ").append(u.getIdUlice()).append(System.lineSeparator());
                    sb.append("Ukupno BIO: ").append(String.format("%.2f", bioOtpad)).append(" kg").append(System.lineSeparator());
                    sb.append("Ukupno METAL: ").append(String.format("%.2f", metalOtpad)).append(" kg").append(System.lineSeparator());
                    sb.append("Ukupno MJESANO: ").append(String.format("%.2f", mjesanoOtpad)).append(" kg").append(System.lineSeparator());
                    sb.append("Ukupno STAKLO: ").append(String.format("%.2f", stakloOtpad)).append(" kg").append(System.lineSeparator());
                    sb.append("Ukupno PAPIR: ").append(String.format("%.2f", papirOtpad)).append(" kg").append(System.lineSeparator());
                    sb.append("-----------------------------").append(System.lineSeparator());
                    sb.append("Ukupno UKUPNO: ").append(String.format("%.2f", ukupnoOtpad)).append(" kg").append(System.lineSeparator());
                    is.zapisiUDatoteku(sb.toString());
                    statistika.add(sb.toString());
                }
            }
        }
        for (String s : statistika) {
            System.out.println(s);
        }
    }

    private static void statistikaKreiranogOtpada(Ispis is) {

        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        sb.append("Kreirani otpad").append(System.lineSeparator());
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        sb.append("BIO otpad: ").append(String.format("%.2f", generiranoBioOtpada)).append(" kg").append(System.lineSeparator());
        sb.append("METAL otpad: ").append(String.format("%.2f", generiranoMetalnogOtpada)).append(" kg").append(System.lineSeparator());
        sb.append("MJESANO otpad: ").append(String.format("%.2f", generiranoMjesanogOtpada)).append(" kg").append(System.lineSeparator());
        sb.append("STAKLO otpad: ").append(String.format("%.2f", generiranoStaklenogOtpada)).append(" kg").append(System.lineSeparator());
        sb.append("PAPIR otpad: ").append(String.format("%.2f", generiranoPapirOtpada)).append(" kg").append(System.lineSeparator());
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        sb.append("UKUPNO otpada: ").append(String.format("%.2f", ukupnoGeneriranoOtpada)).append(" kg").append(System.lineSeparator());

        System.out.println(sb.toString());
        is.zapisiUDatoteku(sb.toString());
    }

    private static List<Korisnik> kreirajKorisnike(List<Ulica> ulice, Generator gen, Ispis is) {
        List<Korisnik> listaKorisnika = new ArrayList<>();
        int id = 1;
        //Kreiraj korisnike po ulicama, prvo male, srednje pa velike
        for (Ulica s : ulice) {

            for (int i = 0; i < s.getBrojMalihKorisnika(); i++) {
                float kolStaklo = gen.dajSlucajniFloat(maliStaklo, maliStaklo / maliMin, brojDecimala);
                float kolPapir = gen.dajSlucajniFloat(maliPapir, maliPapir / maliMin, brojDecimala);
                float kolMetal = gen.dajSlucajniFloat(maliMetal, maliMetal / maliMin, brojDecimala);
                float kolBio = gen.dajSlucajniFloat(maliBio, maliBio / maliMin, brojDecimala);
                float kolMjesano = gen.dajSlucajniFloat(maliMjesano, maliMjesano / maliMin, brojDecimala);
                Korisnik kor = new Korisnik.KorisnikBuilder(id, s.getIdUlice(), "mali", kolMetal, kolMjesano, kolPapir, kolStaklo, kolBio).build();
                zbrojUkupnoGeneriranogOtpada(kolBio, kolMetal, kolMjesano, kolStaklo, kolPapir);
                listaKorisnika.add(kor);
                id++;
            }

            for (int i = 0; i < s.getBrojSrednjihKorisnika(); i++) {
                float kolStaklo = gen.dajSlucajniFloat(srednjiStaklo, srednjiStaklo / srednjiMin, brojDecimala);
                float kolPapir = gen.dajSlucajniFloat(srednjiPapir, srednjiPapir / srednjiMin, brojDecimala);
                float kolMetal = gen.dajSlucajniFloat(srednjiMetal, srednjiMetal / srednjiMin, brojDecimala);
                float kolBio = gen.dajSlucajniFloat(srednjiBio, srednjiBio / srednjiMin, brojDecimala);
                float kolMjesano = gen.dajSlucajniFloat(srednjiMjesano, srednjiMjesano / srednjiMin, brojDecimala);
                Korisnik kor = new Korisnik.KorisnikBuilder(id, s.getIdUlice(), "srednji", kolMetal, kolMjesano, kolPapir, kolStaklo, kolBio).build();
                zbrojUkupnoGeneriranogOtpada(kolBio, kolMetal, kolMjesano, kolStaklo, kolPapir);
                listaKorisnika.add(kor);
                id++;
            }

            for (int i = 0; i < s.getBrojVelikihKorisnika(); i++) {
                float kolStaklo = gen.dajSlucajniFloat(velikiStaklo, velikiStaklo / velikiMin, brojDecimala);
                float kolPapir = gen.dajSlucajniFloat(velikiPapir, velikiPapir / velikiMin, brojDecimala);
                float kolMetal = gen.dajSlucajniFloat(velikiMetal, velikiMetal / velikiMin, brojDecimala);
                float kolBio = gen.dajSlucajniFloat(velikiBio, velikiBio / velikiMin, brojDecimala);
                float kolMjesano = gen.dajSlucajniFloat(velikiMjesano, velikiMjesano / velikiMin, brojDecimala);
                Korisnik kor = new Korisnik.KorisnikBuilder(id, s.getIdUlice(), "veliki", kolMetal, kolMjesano, kolPapir, kolStaklo, kolBio).build();
                zbrojUkupnoGeneriranogOtpada(kolBio, kolMetal, kolMjesano, kolStaklo, kolPapir);
                listaKorisnika.add(kor);
                id++;
            }
        }

        statistikaKreiranihKorisnika(listaKorisnika, is);
        return listaKorisnika;
    }

    private static void statistikaKreiranihKorisnika(List<Korisnik> listaKorisnika, Ispis is) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator()).append("-------------------------------------------------------").append(System.lineSeparator());
        sb.append("Kreirani korisnici").append(System.lineSeparator());
        sb.append("-------------------------------------------------------").append(System.lineSeparator());

        for (Korisnik k : listaKorisnika) {
            sb.append(k.getPodaciKorisnik()).append(System.lineSeparator());
        }

        System.out.println(sb.toString());
        is.zapisiUDatoteku(sb.toString());
    }

    private static List<Dispecer> ucitajPodatkeDispecera(Datoteka dat, Parametri parametri, Ispis is) {
        List<Dispecer> dispecer = dat.dohvatiPodatkeDispecera(parametri.dajParametar("dispečer"));
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        sb.append("Ucitane komande dispecera").append(System.lineSeparator());
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        for (Dispecer d : dispecer) {
            sb.append(d.getPodaciDispecer()).append(System.lineSeparator());
        }
        System.out.println(sb.toString());
        is.zapisiUDatoteku(sb.toString());
        return dispecer;
    }

    private static List<Podrucje> ucitajPodatkePodrucja(Datoteka dat, Parametri parametri, Ispis is) {
        List<Podrucje> podrucja = dat.dohvatiPodatkePodrucja(parametri.dajParametar("područja"));
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        sb.append("Ucitana podrucja").append(System.lineSeparator());
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        for (Podrucje p : podrucja) {
            sb.append(p.getPodaciPodrucje()).append(System.lineSeparator());
        }
        System.out.println(sb.toString());
        is.zapisiUDatoteku(sb.toString());
        return podrucja;
    }

    private static List<Vozilo> ucitajPodatkeVozila(Datoteka dat, Parametri parametri, Ispis is) {
        List<Vozilo> vozila = dat.dohvatiPodatkeVozila(parametri.dajParametar("vozila"));
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        sb.append("Ucitana vozila").append(System.lineSeparator());
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        for (Vozilo v : vozila) {
            sb.append(v.getPodaciVozilo()).append(System.lineSeparator());
        }
        System.out.println(sb.toString());
        is.zapisiUDatoteku(sb.toString());
        return vozila;
    }

    private static List<SpremnikPostavke> ucitajPostavkeSpremnika(Datoteka dat, Parametri parametri, Ispis is) {
        List<SpremnikPostavke> postavkeSpremnika = dat.dohvatiPodatkeSpremnici(parametri.dajParametar("spremnici"));
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        sb.append("Ucitane postavke spremnika").append(System.lineSeparator());
        sb.append("-------------------------------------------------------").append(System.lineSeparator());

        for (SpremnikPostavke s : postavkeSpremnika) {
            sb.append(s.getPodaciPostavkiSpremnika()).append(System.lineSeparator());
        }
        System.out.println(sb.toString());
        is.zapisiUDatoteku(sb.toString());
        return postavkeSpremnika;
    }

    private static List<Ulica> ucitajUlice(Datoteka dat, Parametri parametri, Ispis is) {
        List<Ulica> ulice = dat.dohvatiPodatkeUlica(parametri.dajParametar("ulice"));
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        sb.append("Ucitane ulice").append(System.lineSeparator());
        sb.append("-------------------------------------------------------").append(System.lineSeparator());

        for (Ulica u : ulice) {
            sb.append(u.getPodaciUlica()).append(System.lineSeparator());
        }
        System.out.println(sb.toString());
        is.zapisiUDatoteku(sb.toString());
        return ulice;
    }

    private static void zbrojUkupnoGeneriranogOtpada(float kolBio, float kolMetal, float kolMjesano, float kolStaklo, float kolPapir) {
        generiranoBioOtpada = generiranoBioOtpada + kolBio;
        generiranoMetalnogOtpada = generiranoMetalnogOtpada + kolMetal;
        generiranoMjesanogOtpada = generiranoMjesanogOtpada + kolMjesano;
        generiranoStaklenogOtpada = generiranoStaklenogOtpada + kolStaklo;
        generiranoPapirOtpada = generiranoPapirOtpada + kolPapir;
        ukupnoGeneriranoOtpada = generiranoBioOtpada + generiranoMetalnogOtpada + generiranoMjesanogOtpada + generiranoPapirOtpada + generiranoStaklenogOtpada;
    }

    private static void dohvatiParametreKorisnika(Parametri parametri) throws NumberFormatException {

        maliMin = Integer.parseInt(parametri.dajParametar("maliMin"));
        srednjiMin = Integer.parseInt(parametri.dajParametar("srednjiMin"));
        velikiMin = Integer.parseInt(parametri.dajParametar("velikiMin"));

        maliBio = Integer.parseInt(parametri.dajParametar("maliBio"));
        maliMetal = Integer.parseInt(parametri.dajParametar("maliMetal"));
        maliMjesano = Integer.parseInt(parametri.dajParametar("maliMješano"));
        maliPapir = Integer.parseInt(parametri.dajParametar("maliPapir"));
        maliStaklo = Integer.parseInt(parametri.dajParametar("maliStaklo"));

        srednjiBio = Integer.parseInt(parametri.dajParametar("srednjiBio"));
        srednjiMetal = Integer.parseInt(parametri.dajParametar("srednjiMetal"));
        srednjiMjesano = Integer.parseInt(parametri.dajParametar("srednjiMješano"));
        srednjiPapir = Integer.parseInt(parametri.dajParametar("srednjiPapir"));
        srednjiStaklo = Integer.parseInt(parametri.dajParametar("srednjiStaklo"));

        velikiBio = Integer.parseInt(parametri.dajParametar("velikiBio"));
        velikiMetal = Integer.parseInt(parametri.dajParametar("velikiMetal"));
        velikiMjesano = Integer.parseInt(parametri.dajParametar("velikiMješano"));
        velikiPapir = Integer.parseInt(parametri.dajParametar("velikiPapir"));
        velikiStaklo = Integer.parseInt(parametri.dajParametar("velikiStaklo"));
    }

    private static List<Spremnik> dodjeljivanjeSpremnikaKorisnicima(List<Korisnik> listaKorisnika,
            List<SpremnikPostavke> listaPostavkiSpremnika, List<Ulica> listaUlica) {

        //TODO popraviti raspodjelu ako ima vise korisnika nego sto ih moze koristiti jedan spremnik
        List<Spremnik> listaSpremnika = new ArrayList<>();
        int idSpremnika = 1;

        for (SpremnikPostavke sp : listaPostavkiSpremnika) {
            for (Ulica u : listaUlica) {
                if (u.getBrojMalihKorisnika() <= sp.getJedanNaBrojMalih()) {
                    List<Korisnik> listaKorisnikaMali = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (u.getIdUlice().equals(k.getUlica()) && k.getVrstaKorisnika().equals("mali")) {
                            listaKorisnikaMali.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaKorisnikaMali, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                } else {
                    List<Korisnik> listaKorisnikaMali = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (u.getIdUlice().equals(k.getUlica()) && k.getVrstaKorisnika().equals("mali")) {
                            listaKorisnikaMali.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaKorisnikaMali, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                }

                if (u.getBrojSrednjihKorisnika() <= sp.getJedanNaBrojSrednjih()) {
                    List<Korisnik> listaKorisnikaSrednji = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (u.getIdUlice().equals(k.getUlica()) && k.getVrstaKorisnika().equals("srednji")) {
                            listaKorisnikaSrednji.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaKorisnikaSrednji, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                } else {
                    List<Korisnik> listaKorisnikaSrednji = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (u.getIdUlice().equals(k.getUlica()) && k.getVrstaKorisnika().equals("srednji")) {
                            listaKorisnikaSrednji.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaKorisnikaSrednji, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                }

                if (u.getBrojVelikihKorisnika() <= sp.getJedanNaBrojVelikih()) {
                    List<Korisnik> listaKorisnikaVeliki = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (u.getIdUlice().equals(k.getUlica()) && k.getVrstaKorisnika().equals("veliki")) {
                            listaKorisnikaVeliki.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaKorisnikaVeliki, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                } else {
                    List<Korisnik> listaKorisnikaVeliki = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (u.getIdUlice().equals(k.getUlica()) && k.getVrstaKorisnika().equals("veliki")) {
                            listaKorisnikaVeliki.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaKorisnikaVeliki, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                }
            }
        }

        return listaSpremnika;
    }

    private static void obradaPodataka(List<Dispecer> dispecer, List<Vozilo> ucitanaVozila,
            List<Ulica> ucitaneUlice, List<Spremnik> listaKreiranihSpremnika,
            List<Korisnik> kreiraniKorisnici, List<Podrucje> ucitanaPodrucja, Ispis is) {

        List<Dispecer> komandeDispecera = dispecer;
        List<Ulica> ulice = ucitaneUlice;
        List<Vozilo> vozila = ucitanaVozila;
        List<Spremnik> spremnici = listaKreiranihSpremnika;
        List<Korisnik> korisnici = kreiraniKorisnici;
        List<Podrucje> podrucja = ucitanaPodrucja;

        List<Vozilo> vozilaNaParkiralistu = vozila;
        List<Vozilo> vozilaPripremljenaZaPreuzimanje = new ArrayList<>();
        List<Vozilo> vozilaUKvaru = new ArrayList<>();
        List<Vozilo> vozilaUKontroli = new ArrayList<>();
        List<Vozilo> vozilaZaPraznjenje = new ArrayList<>();

        float metal = 0;
        float bio = 0;
        float staklo = 0;
        float mjesano = 0;
        float papir = 0;

        for (Dispecer d : komandeDispecera) {

            //ako je pročitana samo komanda bez liste vozila
            if (!d.getKomanda().equals("") && d.getListaVozila() == null) {
                String komanda;
                String brojCiklusa;
                String[] dijeloviKomande = d.getKomanda().split("\\s+");
                //ako je samo komanda bez ciklusa
                if (dijeloviKomande.length == 1) {
                    komanda = dijeloviKomande[0];
                    System.out.println("Komanda: " + komanda);

                    switch (komanda) {
                        case "KRENI":
                            //pokreni izvrsavanje
                            odvozOtpada(0, vozilaPripremljenaZaPreuzimanje, vozilaZaPraznjenje, spremnici, is, metal, bio, staklo, papir, mjesano);
                            break;
                        case "STATUS":
                            ispisStatusVozila(is, vozilaPripremljenaZaPreuzimanje, vozilaUKontroli, vozilaZaPraznjenje);
                            break;
                        default:
                            System.out.println("Nepoznata komanda");
                    }

                } else {
                    //komanda s ciklusima
                    komanda = dijeloviKomande[0];
                    brojCiklusa = dijeloviKomande[1];
                    System.out.println("Komanda: " + komanda + ", broj ciklusa: " + brojCiklusa);

                    switch (komanda) {
                        case "KRENI":
                            //pokreni izvrsavanje sa zadanim brojem ciklusa
                            odvozOtpada(Integer.parseInt(brojCiklusa), vozilaPripremljenaZaPreuzimanje, vozilaZaPraznjenje, spremnici, is, metal, bio, staklo, papir, mjesano);
                            break;
                        default:
                            System.out.println("Nepoznata komanda");
                    }
                }
            } else if (!d.getKomanda().equals("") && d.getListaVozila() != null) {
                //ako postoji komanda i zadana lista vozila
                String komanda = d.getKomanda();
                List<String> vozilaKomanda = d.getListaVozila();
                System.out.println("Komanda: " + d.getKomanda() + ", lista vozila: " + d.getListaVozila());

                switch (komanda) {
                    case "PRIPREMI":
                        for (Vozilo v : vozilaNaParkiralistu) {
                            for (String s : vozilaKomanda) {
                                if (v.getIdVozila().equals(s)) {
                                    v.setPopunjenost(0);
                                    vozilaPripremljenaZaPreuzimanje.add(v);
                                }
                            }
                        }
                        break;
                    case "KVAR":
                        try {
                            for (Vozilo v : vozilaPripremljenaZaPreuzimanje) {
                                for (String s : vozilaKomanda) {
                                    if (v.getIdVozila().equals(s)) {
                                        vozilaUKvaru.add(v);
                                        vozilaPripremljenaZaPreuzimanje.remove(v);
                                    }
                                }
                            }
                            for (Vozilo v : vozilaZaPraznjenje) {
                                for (String s : vozilaKomanda) {
                                    if (v.getIdVozila().equals(s)) {
                                        vozilaUKvaru.add(v);
                                        vozilaZaPraznjenje.remove(v);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Vozilo u kvaru nije među pripremljenima ili za praznjenje!");
                        }

                        break;
                    case "ISPRAZNI":
                        try {
                            for (Vozilo v : vozilaPripremljenaZaPreuzimanje) {
                                for (String s : vozilaKomanda) {
                                    if (v.getIdVozila().equals(s)) {
                                        vozilaZaPraznjenje.add(v);
                                        vozilaPripremljenaZaPreuzimanje.remove(v);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Vozilo za pražnjenje nije među pripremljenima!");
                        }
                        break;
                    case "KONTROLA":
                        try {
                            for (Vozilo v : vozilaNaParkiralistu) {
                                for (String s : vozilaKomanda) {
                                    if (v.getIdVozila().equals(s)) {
                                        vozilaUKontroli.add(v);
                                        vozilaNaParkiralistu.remove(v);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Vozilo za kontrolu nije na parkiralištu!");
                        }
                        try {
                            for (Vozilo v : vozilaPripremljenaZaPreuzimanje) {
                                for (String s : vozilaKomanda) {
                                    if (v.getIdVozila().equals(s)) {
                                        vozilaUKontroli.add(v);
                                        vozilaPripremljenaZaPreuzimanje.remove(v);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Vozilo za kontrolu nije među pripremljenima!");
                        }
                        break;
                    default:
                        System.out.println("Nepoznata komanda");
                }
            }

        }

        stanjeNaKrajuIzvrsavanja(vozilaPripremljenaZaPreuzimanje, vozilaUKvaru, vozilaUKontroli, vozilaZaPraznjenje, is);

    }

    private static void odvozOtpada(int brojCiklusa, List<Vozilo> vozilaPripremljenaZaPreuzimanje,
            List<Vozilo> vozilaPraznjenje, List<Spremnik> spremnici, Ispis is,
            float metal, float bio, float staklo, float papir, float mjesano) {

        int br = 0;
        boolean gotovoStaklo = false;
        boolean gotovMetal = false;
        boolean gotovBio = false;
        boolean gotovPapir = false;
        boolean gotovoMjesano = false;

        StringBuilder sb = new StringBuilder();

        if (brojCiklusa == 0) {
            for (Spremnik s : spremnici) {

                if (s.getPopunjenost() == 0 && s.getOtpad().equals("staklo")) {
                    gotovoStaklo = true;
                }
                if (s.getPopunjenost() == 0 && s.getOtpad().equals("papir")) {
                    gotovPapir = true;
                }
                if (s.getPopunjenost() == 0 && s.getOtpad().equals("metal")) {
                    gotovMetal = true;
                }
                if (s.getPopunjenost() == 0 && s.getOtpad().equals("bio")) {
                    gotovBio = true;
                }
                if (s.getPopunjenost() == 0 && s.getOtpad().equals("mjesano")) {
                    gotovoMjesano = true;
                }

                if (gotovoStaklo == true && gotovPapir == true && gotovMetal == true && gotovBio == true && gotovoMjesano == true) {
                    sb.append("Gotovo preuzimanje otpada!");
                } else {

                    for (Vozilo v : vozilaPripremljenaZaPreuzimanje) {

                        if (v.getVrstaVozila() == 0 && s.getOtpad().equals("staklo")) {
                            //skupi staklo
                            if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                v.setPopunjenost(s.getPopunjenost());
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                s.setPopunjenost(0);
                                br++;
                            } else {
                                float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                v.setPopunjenost(razlika);
                                s.setPopunjenost(s.getPopunjenost() - razlika);
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                vozilaPraznjenje.add(v);
                                vozilaPripremljenaZaPreuzimanje.remove(v);
                                br++;
                            }
                        }
                        if (v.getVrstaVozila() == 1 && s.getOtpad().equals("papir")) {
                            //skupi papir
                            if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                v.setPopunjenost(s.getPopunjenost());
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                s.setPopunjenost(0);
                                br++;
                            } else {
                                float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                v.setPopunjenost(razlika);
                                s.setPopunjenost(s.getPopunjenost() - razlika);
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                vozilaPraznjenje.add(v);
                                vozilaPripremljenaZaPreuzimanje.remove(v);
                                br++;
                            }
                        }
                        if (v.getVrstaVozila() == 2 && s.getOtpad().equals("metal")) {
                            //skupi metal
                            if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                v.setPopunjenost(s.getPopunjenost());
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                s.setPopunjenost(0);
                                br++;
                            } else {
                                float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                v.setPopunjenost(razlika);
                                s.setPopunjenost(s.getPopunjenost() - razlika);
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                vozilaPraznjenje.add(v);
                                vozilaPripremljenaZaPreuzimanje.remove(v);
                                br++;
                            }
                        }
                        if (v.getVrstaVozila() == 3 && s.getOtpad().equals("bio")) {
                            //skupi bio
                            if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                v.setPopunjenost(s.getPopunjenost());
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                s.setPopunjenost(0);
                                br++;
                            } else {
                                float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                v.setPopunjenost(razlika);
                                s.setPopunjenost(s.getPopunjenost() - razlika);
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                vozilaPraznjenje.add(v);
                                vozilaPripremljenaZaPreuzimanje.remove(v);
                                br++;
                            }
                        }
                        if (v.getVrstaVozila() == 4 && s.getOtpad().equals("mješano")) {
                            //skupi mješano
                            if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                v.setPopunjenost(s.getPopunjenost());
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                s.setPopunjenost(0);
                                br++;
                            } else {
                                float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                v.setPopunjenost(razlika);
                                s.setPopunjenost(s.getPopunjenost() - razlika);
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                vozilaPraznjenje.add(v);
                                vozilaPripremljenaZaPreuzimanje.remove(v);
                                br++;
                            }
                        }
                    }
                    
                }

            }
        } else {

            while (br < brojCiklusa) {
                for (Spremnik s : spremnici) {
                    for (Vozilo v : vozilaPripremljenaZaPreuzimanje) {

                        if (v.getVrstaVozila() == 0 && s.getOtpad().equals("staklo")) {
                            //skupi staklo
                            if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                v.setPopunjenost(s.getPopunjenost());
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                s.setPopunjenost(0);
                                br++;
                            } else {
                                float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                v.setPopunjenost(razlika);
                                s.setPopunjenost(s.getPopunjenost() - razlika);
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                vozilaPraznjenje.add(v);
                                vozilaPripremljenaZaPreuzimanje.remove(v);
                                br++;
                            }
                        }
                        if (v.getVrstaVozila() == 1 && s.getOtpad().equals("papir")) {
                            //skupi papir
                            if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                v.setPopunjenost(s.getPopunjenost());
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                s.setPopunjenost(0);
                                br++;
                            } else {
                                float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                v.setPopunjenost(razlika);
                                s.setPopunjenost(s.getPopunjenost() - razlika);
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                vozilaPraznjenje.add(v);
                                vozilaPripremljenaZaPreuzimanje.remove(v);
                                br++;
                            }
                        }
                        if (v.getVrstaVozila() == 2 && s.getOtpad().equals("metal")) {
                            //skupi metal
                            if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                v.setPopunjenost(s.getPopunjenost());
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                s.setPopunjenost(0);
                                br++;
                            } else {
                                float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                v.setPopunjenost(razlika);
                                s.setPopunjenost(s.getPopunjenost() - razlika);
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                vozilaPraznjenje.add(v);
                                vozilaPripremljenaZaPreuzimanje.remove(v);
                                br++;
                            }
                        }
                        if (v.getVrstaVozila() == 3 && s.getOtpad().equals("bio")) {
                            //skupi bio
                            if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                v.setPopunjenost(s.getPopunjenost());
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                s.setPopunjenost(0);
                                br++;
                            } else {
                                float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                v.setPopunjenost(razlika);
                                s.setPopunjenost(s.getPopunjenost() - razlika);
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                vozilaPraznjenje.add(v);
                                vozilaPripremljenaZaPreuzimanje.remove(v);
                                br++;
                            }
                        }
                        if (v.getVrstaVozila() == 4 && s.getOtpad().equals("mješano")) {
                            //skupi mješano
                            if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                v.setPopunjenost(s.getPopunjenost());
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                s.setPopunjenost(0);
                                br++;
                            } else {
                                float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                v.setPopunjenost(razlika);
                                s.setPopunjenost(s.getPopunjenost() - razlika);
                                sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                vozilaPraznjenje.add(v);
                                vozilaPripremljenaZaPreuzimanje.remove(v);
                                br++;
                            }
                        }
                    }

                    for (Vozilo v : vozilaPraznjenje) {
                        if (v.getVrstaVozila() == 0) {
                            staklo = staklo + v.getPopunjenost();
                            v.setPopunjenost(0);
                            sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                            vozilaPripremljenaZaPreuzimanje.add(v);
                            vozilaPraznjenje.remove(v);
                            br++;
                        }
                        if (v.getVrstaVozila() == 1) {
                            papir = papir + v.getPopunjenost();
                            v.setPopunjenost(0);
                            sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                            vozilaPripremljenaZaPreuzimanje.add(v);
                            vozilaPraznjenje.remove(v);
                            br++;
                        }
                        if (v.getVrstaVozila() == 2) {
                            metal = metal + v.getPopunjenost();
                            v.setPopunjenost(0);
                            sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                            vozilaPripremljenaZaPreuzimanje.add(v);
                            vozilaPraznjenje.remove(v);
                            br++;
                        }
                        if (v.getVrstaVozila() == 3) {
                            bio = bio + v.getPopunjenost();
                            v.setPopunjenost(0);
                            sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                            vozilaPripremljenaZaPreuzimanje.add(v);
                            vozilaPraznjenje.remove(v);
                            br++;
                        }
                        if (v.getVrstaVozila() == 4) {
                            mjesano = mjesano + v.getPopunjenost();
                            v.setPopunjenost(0);
                            sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                            vozilaPripremljenaZaPreuzimanje.add(v);
                            vozilaPraznjenje.remove(v);
                            br++;
                        }
                    }

                }
            }
        }


        System.out.println(sb.toString());
        is.zapisiUDatoteku(sb.toString());
    }

    private static void stanjeNaKrajuIzvrsavanja(List<Vozilo> vozilaPripremljenaZaPreuzimanje, List<Vozilo> vozilaUKvaru,
            List<Vozilo> vozilaUKontroli, List<Vozilo> vozilaZaPraznjenje, Ispis is) {
        StringBuilder sb = new StringBuilder();
        sb.append("Stanje na kraju izvršavanja: ").append(System.lineSeparator());

        sb.append("Pripremljena: ").append(System.lineSeparator());
        for (Vozilo v : vozilaPripremljenaZaPreuzimanje) {
            sb.append(v.getPodaciVozilo()).append(System.lineSeparator());
        }
        sb.append("Kvar: ").append(System.lineSeparator());
        for (Vozilo v : vozilaUKvaru) {
            sb.append(v.getPodaciVozilo()).append(System.lineSeparator());
        }
        sb.append("Kontrola:").append(System.lineSeparator());
        for (Vozilo v : vozilaUKontroli) {
            sb.append(v.getPodaciVozilo()).append(System.lineSeparator());
        }
        sb.append("Pražnjenje: ").append(System.lineSeparator());
        for (Vozilo v : vozilaZaPraznjenje) {
            sb.append(v.getPodaciVozilo()).append(System.lineSeparator());
        }
        System.out.println(sb);
        is.zapisiUDatoteku(sb.toString());
    }

    private static void ispisStatusVozila(Ispis is, List<Vozilo> vozilaPripremljenaZaPreuzimanje,
            List<Vozilo> vozilaUKontroli, List<Vozilo> vozilaZaPraznjenje) {
        StringBuilder sb = new StringBuilder();

        sb.append("Ispis statusa vozila: ").append(System.lineSeparator());
        sb.append("----------------------------------------------").append(System.lineSeparator());
        sb.append("Pripremljena: ").append(System.lineSeparator());
        for (Vozilo v : vozilaPripremljenaZaPreuzimanje) {
            sb.append(v.getPodaciVozilo()).append(System.lineSeparator());
        }

        sb.append("Kontrola:").append(System.lineSeparator());
        for (Vozilo v : vozilaUKontroli) {
            sb.append(v.getPodaciVozilo()).append(System.lineSeparator());
        }
        sb.append("Pražnjenje: ").append(System.lineSeparator());
        for (Vozilo v : vozilaZaPraznjenje) {
            sb.append(v.getPodaciVozilo()).append(System.lineSeparator());
        }

        System.out.println(sb);
        is.zapisiUDatoteku(sb.toString());
    }

}
