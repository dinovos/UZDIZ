package org.foi.uzdiz.dinnovose2.dz3;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.foi.uzdiz.dinnovose2.dz3.builder.SpremnikPostavke;
import org.foi.uzdiz.dinnovose2.dz3.builder.Korisnik;
import org.foi.uzdiz.dinnovose2.dz3.builder.Ulica;
import org.foi.uzdiz.dinnovose2.dz3.builder.Vozilo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.dinnovose2.dz3.builder.Dispecer;
import org.foi.uzdiz.dinnovose2.dz3.builder.Podrucje;
import org.foi.uzdiz.dinnovose2.dz3.builder.Spremnik;
import org.foi.uzdiz.dinnovose2.dz3.builder.Vozac;
import org.foi.uzdiz.dinnovose2.dz3.podaci.Datoteka;
import org.foi.uzdiz.dinnovose2.dz3.podaci.Ispis;
import org.foi.uzdiz.dinnovose2.dz3.generiranje.Generator;
import org.foi.uzdiz.dinnovose2.dz3.singleton.Parametri;
import org.foi.uzdiz.dinnovose2.dz3.state.VozacState;
import org.foi.uzdiz.dinnovose2.dz3.state.VoziloState;
import org.foi.uzdiz.dinnovose2.dz3.strategy.DispecerCiklus;
import org.foi.uzdiz.dinnovose2.dz3.strategy.DispecerClean;
import org.foi.uzdiz.dinnovose2.dz3.strategy.DispecerLista;

/**
 *
 * @author Dino
 */
public class EZO {

    int ispis;
    static int sjemeGeneratora;
    static int brojDecimala;
    static int brojRadnihCiklusaZaOdvoz;
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

    static int kapacitetDizelVozila;
    static int punjenjeDizelVozila;
    static int kapacitetElektroVozila;
    static int punjenjeElektroVozila;

    static String datotekaParametara = "";
    static int brg = 0;
    static int brd = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Dohvati naziv datoteka i parametre redaka
        dohvatiDatotekuIRetke(args);

        Parametri parametri = Parametri.getInstance();
        parametri.ucitajSveParametre(datotekaParametara);

        //Dohvati kapacitet i punjenje za vozila
        dohvatiParametreVozila(parametri);

        //State
        VozacState vozacState = new VozacState();
        VoziloState voziloState = new VoziloState();

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

        //Ucitavanje vozaca
        List<Vozac> vozaciVozila = ucitajPodatkeVozaca(vozila, is);

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
        obradaPodataka(dispecer, vozila, ulice, listaSpremnika, korisnici, podrucja, vozaciVozila, vozacState, voziloState, is, brojRadnihCiklusaZaOdvoz);

        //Unos komande preko konzole
        rucniUnosKomande(dat, vozila, ulice, listaSpremnika, korisnici, podrucja, vozaciVozila, vozacState, voziloState, is);

        //Statistika prikupljenog otpada
    }

    private static void dohvatiDatotekuIRetke(String[] args) {
        String sintaksa = "^(DZ_3_parametri.txt) (--brg) (\\d+) (--brd) (\\d+)$";
        List<String> dijelovi = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String p = sb.toString().trim();
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(p);

        if (m.matches()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                dijelovi.add(m.group(i));
            }
        } else {
            System.out.println("Regex ne odgovara - krivo zadani parametri!");
        }

        if (dijelovi.size() != 6 || dijelovi.isEmpty()) {
            System.out.println("Greška u zadavanju parametara");
        } else {
            datotekaParametara = dijelovi.get(1);
            brg = Integer.parseInt(dijelovi.get(3));
            brd = Integer.parseInt(dijelovi.get(5));
        }
    }

    private static void dohvatiParametreVozila(Parametri parametri) throws NumberFormatException {
        //Punjenje i kapacitet vozila
        kapacitetDizelVozila = Integer.parseInt(parametri.dajParametar("kapacitetDizelVozila").trim());
        punjenjeDizelVozila = Integer.parseInt(parametri.dajParametar("punjenjeDizelVozila").trim());
        kapacitetElektroVozila = Integer.parseInt(parametri.dajParametar("kapacitetElektroVozila").trim());
        punjenjeElektroVozila = Integer.parseInt(parametri.dajParametar("punjenjeElektroVozila").trim());
    }

    private static void rucniUnosKomande(Datoteka dat, List<Vozilo> vozila, List<Ulica> ulice, List<Spremnik> listaSpremnika, List<Korisnik> korisnici, List<Podrucje> podrucja, List<Vozac> vozaciVozila, VozacState vozacState, VoziloState voziloState, Ispis is) {
        Scanner sc = new Scanner(System.in, "windows-1250");
        String komanda;
        String kom = "";

        while (!kom.equals("IZLAZ")) {
            System.out.println("Upisi komandu: ");
            if (sc.hasNext()) {
                try {
                    sc.useDelimiter("\n");
                    komanda = new String(sc.next().getBytes("windows-1250"), Charset.forName("UTF-8"));
                    Dispecer disp = dat.dohvatiKomanduSaKonzole(komanda);
                    if (disp.getKomanda() == null) {
                    } else {
                        kom = disp.getKomanda();
                        List<Dispecer> listDispecera = new ArrayList<>();
                        listDispecera.add(disp);
                        obradaPodataka(listDispecera, vozila, ulice, listaSpremnika, korisnici, podrucja, vozaciVozila, vozacState, voziloState, is, brojRadnihCiklusaZaOdvoz);
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(EZO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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
            float ukupnoPodrucje = 0;
            StringBuilder sb = new StringBuilder();
            for (Ulica u : ulice) {
                if (p.getDijelovi().contains(u.getIdUlice())) {

                    sb.append(System.lineSeparator());
                    sb.append("Podrucje: ").append(p.getNaziv()).append(", ");
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

                }

                if (p.getDijelovi().contains(u.getIdUlice())) {
                    ukupnoPodrucje = ukupnoPodrucje + ukupnoOtpad;
                }
            }
            sb.append("---------------------------------------------------------").append(System.lineSeparator());
            sb.append("Područje: ").append(p.getNaziv()).append(" ima kreirano: ")
                    .append(ukupnoPodrucje).append(" kg otpada").append(System.lineSeparator());
            sb.append("---------------------------------------------------------").append(System.lineSeparator());

            is.zapisiUDatoteku(sb.toString());
            statistika.add(sb.toString());

        }
        for (String s : statistika) {
            System.out.println(s);
            is.zapisiUDatoteku(s);
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

            id = kreirajMaleKorisnike(s, gen, id, listaKorisnika);

            id = kreirajSrednjeKorisnike(s, gen, id, listaKorisnika);

            kreirajVelikeKorisnike(s, gen, id, listaKorisnika);
        }

        statistikaKreiranihKorisnika(listaKorisnika, is);
        return listaKorisnika;
    }

    private static void kreirajVelikeKorisnike(Ulica s, Generator gen, int id, List<Korisnik> listaKorisnika) {
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

    private static int kreirajSrednjeKorisnike(Ulica s, Generator gen, int id, List<Korisnik> listaKorisnika) {
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
        return id;
    }

    private static int kreirajMaleKorisnike(Ulica s, Generator gen, int id, List<Korisnik> listaKorisnika) {
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
        return id;
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

    private static List<Vozac> ucitajPodatkeVozaca(List<Vozilo> vozila, Ispis is) {
        List<Vozac> vozaciVozila = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        sb.append("Ucitani vozaci").append(System.lineSeparator());
        sb.append("-------------------------------------------------------").append(System.lineSeparator());
        int idVozaca = 1;
        for (Vozilo v : vozila) {
            for (String s : v.getVozac()) {
                Vozac vozac = new Vozac.VozacBuilder(idVozaca, s.trim(), v.getIdVozila(), 0).build();
                sb.append(vozac.getPodaciVozac()).append(System.lineSeparator());
                vozaciVozila.add(vozac);
                idVozaca++;
            }
        }
        System.out.println(sb.toString());
        is.zapisiUDatoteku(sb.toString());
        return vozaciVozila;
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

        //TODO popraviti raspodjelu 0
        List<Spremnik> listaSpremnika = new ArrayList<>();
        int idSpremnika = 1;

        for (SpremnikPostavke sp : listaPostavkiSpremnika) {
            for (Ulica u : listaUlica) {
                if (u.getBrojMalihKorisnika() <= sp.getJedanNaBrojMalih()) {
                    List<Korisnik> listaMalih = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (k.getUlica().equals(u.getIdUlice()) && k.getVrstaKorisnika().equals("mali")) {
                            listaMalih.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaMalih, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                } else {
                    List<Korisnik> listaMalih = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (k.getUlica().equals(u.getIdUlice()) && k.getVrstaKorisnika().equals("mali")) {
                            listaMalih.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaMalih, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                }

                if (u.getBrojSrednjihKorisnika() <= sp.getJedanNaBrojSrednjih()) {
                    List<Korisnik> listaSrednjih = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (k.getUlica().equals(u.getIdUlice()) && k.getVrstaKorisnika().equals("srednji")) {
                            listaSrednjih.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaSrednjih, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                } else {
                    List<Korisnik> listaSrednjih = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (k.getUlica().equals(u.getIdUlice()) && k.getVrstaKorisnika().equals("srednji")) {
                            listaSrednjih.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaSrednjih, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                }

                if (u.getBrojVelikihKorisnika() <= sp.getJedanNaBrojVelikih()) {
                    List<Korisnik> listaVelikih = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (k.getUlica().equals(u.getIdUlice()) && k.getVrstaKorisnika().equals("veliki")) {
                            listaVelikih.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaVelikih, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                } else {
                    List<Korisnik> listaVelikih = new ArrayList<>();
                    for (Korisnik k : listaKorisnika) {
                        if (k.getUlica().equals(u.getIdUlice()) && k.getVrstaKorisnika().equals("veliki")) {
                            listaVelikih.add(k);
                        }
                    }
                    Spremnik s = new Spremnik.SpremnikBuilder(idSpremnika, sp.getNosivost(), sp.getNaziv(), listaVelikih, u.getIdUlice()).build();
                    idSpremnika++;
                    listaSpremnika.add(s);
                }
            }
        }

        return listaSpremnika;
    }

    private static void obradaPodataka(List<Dispecer> dispecer, List<Vozilo> ucitanaVozila,
            List<Ulica> ucitaneUlice, List<Spremnik> listaKreiranihSpremnika,
            List<Korisnik> kreiraniKorisnici, List<Podrucje> ucitanaPodrucja, List<Vozac> vozaci,
            VozacState vozacState, VoziloState voziloState, Ispis is, int brojRadnihCiklusa) {

        List<Dispecer> komandeDispecera = dispecer;
        List<Vozilo> vozila = ucitanaVozila;
        List<Spremnik> spremnici = listaKreiranihSpremnika;

        for (Dispecer d : komandeDispecera) {

            DispecerClean dc = new DispecerClean();
            DispecerCiklus dcik = new DispecerCiklus();
            DispecerLista dl = new DispecerLista();

            //KOMANDA BEZ LISTE
            if (!d.getKomanda().equals("") && d.getPrvaLista() == null) {

                String komanda;
                String brojCiklusa;
                String[] dijeloviKomande = d.getKomanda().split("\\s+");
                //KOMANDA BEZ CIKLUSA
                if (dijeloviKomande.length == 1) {
                    komanda = dijeloviKomande[0];
                    dc.ispisiKomandu(d);

                    switch (komanda) {
                        case "KRENI":
                            odvozOtpada(0, vozila, spremnici, is, vozacState, voziloState, ucitanaPodrucja, brojRadnihCiklusaZaOdvoz);
                            break;
                        case "STATUS":
                            ispisStatusVozila(vozila, is);
                            break;
                        case "VOZAČI":
                            ispisStatusVozaca(is, vozaci);
                            break;
                        case "IZLAZ":
                            System.out.println("Izlaz iz programa");
                            break;
                        default:
                            System.out.println("Nepoznata komanda");
                    }

                } else {
                    //KOMANDA S CIKLUSOM
                    komanda = dijeloviKomande[0];
                    brojCiklusa = dijeloviKomande[1];
                    dcik.ispisiKomandu(d);

                    switch (komanda) {
                        case "KRENI":
                            //pokreni izvrsavanje sa zadanim brojem ciklusa
                            odvozOtpada(Integer.parseInt(brojCiklusa), vozila, spremnici, is, vozacState, voziloState, ucitanaPodrucja, brojRadnihCiklusaZaOdvoz);
                            break;
                        default:
                            System.out.println("Nepoznata komanda");
                    }
                }
            } else if (!d.getKomanda().equals("") && d.getPrvaLista() != null) {
                //POSTOJI KOMANDA I LISTA ILI LISTE 
                String komanda = d.getKomanda();
                List<String> prvaLista = d.getPrvaLista();
                List<String> drugaLista = d.getDrugaLista();
                dl.ispisiKomandu(d);

                switch (komanda) {
                    case "PRIPREMI":
                        for (Vozilo v : vozila) {
                            for (String s : prvaLista) {
                                if (v.getIdVozila().equals(s)) {
                                    v.setPopunjenost(0);
                                    voziloState.doVoziloPripremi(v);
                                }
                            }
                        }
                        break;
                    case "KVAR":
                        for (Vozilo v : vozila) {
                            for (String s : prvaLista) {
                                if (v.getIdVozila().equals(s)) {
                                    voziloState.doVoziloKvar(v);
                                }
                            }
                        }
                        break;
                    case "ISPRAZNI":
                        try {
                            for (Vozilo v : vozila) {
                                for (String s : prvaLista) {
                                    if (v.getIdVozila().equals(s)) {
                                        voziloState.doVoziloIsprazni(v);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Vozilo za pražnjenje nije među pripremljenima!");
                        }
                        break;
                    case "KONTROLA":
                        for (Vozilo v : vozila) {
                            for (String s : prvaLista) {
                                if (v.getIdVozila().equals(s)) {
                                    voziloState.doVoziloKontrola(v);
                                }
                            }
                        }
                        break;
                    case "GODIŠNJI ODMOR":
                        for (Vozac v : vozaci) {
                            for (String s : prvaLista) {
                                if (v.getIme().equals(s.trim()) && v.getStatus() != 2) {
                                    vozacState.doVozacGodisnji(v);
                                }
                                if (v.getIme().equals(s.trim()) && v.getStatus() == 2) {
                                    System.out.println("Vozač : " + v.getIme() + " je dobio OTKAZ i ne može na GODIŠNJI");
                                }
                            }
                        }

                        break;
                    case "OTKAZ":
                        for (Vozac v : vozaci) {
                            for (String s : prvaLista) {
                                if (v.getIme().equals(s.trim())) {
                                    vozacState.doVozacOtkaz(v);
                                }
                                if (v.getIme().equals(s.trim()) && v.getStatus() == 2) {
                                    System.out.println("Vozač : " + v.getIme() + " je dobio OTKAZ");
                                }
                            }
                            for (Vozilo vo : vozila) {
                                if (vo.getVozac().contains(v.getIme())) {
                                    vo.obrisiVozaca(v.getIme());
                                }
                            }
                        }
                        break;
                    case "BOLOVANJE":
                        for (Vozac v : vozaci) {
                            for (String s : prvaLista) {
                                if (v.getIme().equals(s.trim()) && v.getStatus() != 2) {
                                    vozacState.doVozacBolovanje(v);
                                }
                                if (v.getIme().equals(s.trim()) && v.getStatus() == 2) {
                                    System.out.println("Vozač : " + v.getIme() + " je dobio OTKAZ i ne može na BOLOVANJE");
                                }
                            }
                        }
                        break;
                    case "PREUZMI":
                        for (Vozac v : vozaci) {
                            for (Vozilo vo : vozila) {
                                if (vo.getVozac().contains(v.getIme())) {
                                    vo.obrisiVozaca(v.getIme());
                                }
                            }
                            for (String s : prvaLista) {
                                if (v.getIme().equals(s)) {
                                    if (drugaLista.size() == 1) {
                                        vozacState.doVozacPreuzmi(v, drugaLista.get(0));
                                    }
                                }
                            }
                        }
                        break;
                    case "OBRADI":
                        List<Vozilo> odabranaVozila = new ArrayList<>();
                        List<Spremnik> odabraniSpremnici = new ArrayList<>();

                        //dohvati vozila iz komande
                        for (Vozilo v : vozila) {
                            if (drugaLista.contains(v.getIdVozila())) {
                                odabranaVozila.add(v);
                            }
                        }
                        //dohvati spremnike
                        for (Podrucje p : ucitanaPodrucja) {
                            if (prvaLista.contains(p.getId())) {
                                for (Ulica u : ucitaneUlice) {
                                    for (Spremnik s : spremnici) {
                                        if (s.getUlica().equals(u.getIdUlice()) && p.getDijelovi().contains(u.getIdUlice())) {
                                            odabraniSpremnici.add(s);
                                        }
                                    }
                                }
                            }
                        }

                        odvozOtpada(0, odabranaVozila, odabraniSpremnici, is, vozacState, voziloState, ucitanaPodrucja, brojRadnihCiklusaZaOdvoz);
                        break;
                    case "NOVI":
                        //Kreira nove vozace
                        int id = vozaci.size();
                        for (String s : prvaLista) {
                            id++;
                            Vozac v = new Vozac.VozacBuilder(id, s.trim(), "", 5).build();
                            vozaci.add(v);
                        }
                        break;
                    default:
                        System.out.println("Nepoznata komanda");
                }
            }
        }
    }

    private static void odvozOtpada(int brojCiklusa, List<Vozilo> vozila, List<Spremnik> spremnici, Ispis is,
            VozacState vozaciState, VoziloState vozilaState, List<Podrucje> listaPodrucja, int brojRadnihCiklusaZaOdvoz) {

        int br = 0;

        boolean gotovoStaklo = false;
        boolean gotovMetal = false;
        boolean gotovBio = false;
        boolean gotovPapir = false;
        boolean gotovoMjesano = false;

        float ukStaklo = 0;
        float ukPapir = 0;
        float ukMetal = 0;
        float ukMjesano = 0;
        float ukBio = 0;

        float brStaklo = 0;
        float brPapir = 0;
        float brMetal = 0;
        float brMjesano = 0;
        float brBio = 0;

        StringBuilder sb = new StringBuilder();

        //Sakupljanje otpada bez zadanog ciklusa!!
        if (brojCiklusa == 0) {

            for (Spremnik s : spremnici) {
                //Ako su svi spremnici prazni
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
                    sb.append("PREUZIMANJE OTPADA ZAVRŠENO!");
                } else {

                    for (Podrucje p : listaPodrucja) {

                        if (p.getDijelovi().contains(s.getUlica())) {

                            //Preuzimanje otpada
                            for (Vozilo v : vozila) {

                                if (v.getVrstaVozila() == 0 && s.getOtpad().equals("staklo")) {
                                    //skupi staklo
                                    if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                        v.setPopunjenost(s.getPopunjenost());
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                        ukStaklo = ukStaklo + v.getPopunjenost();
                                        s.setPopunjenost(0);

                                    } else {
                                        float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                        v.setPopunjenost(razlika);
                                        s.setPopunjenost(s.getPopunjenost() - razlika);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno!").append(System.lineSeparator());
                                        ukStaklo = ukStaklo + v.getPopunjenost();
                                        vozilaState.doVoziloIsprazni(v);

                                    }
                                }
                                if (v.getVrstaVozila() == 1 && s.getOtpad().equals("papir")) {
                                    //skupi papir
                                    if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                        v.setPopunjenost(s.getPopunjenost());
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                        ukPapir = ukPapir + v.getPopunjenost();
                                        s.setPopunjenost(0);

                                    } else {
                                        float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                        v.setPopunjenost(razlika);
                                        s.setPopunjenost(s.getPopunjenost() - razlika);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                        ukPapir = ukPapir + v.getPopunjenost();
                                        vozilaState.doVoziloIsprazni(v);

                                    }
                                }
                                if (v.getVrstaVozila() == 2 && s.getOtpad().equals("metal")) {
                                    //skupi metal
                                    if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                        v.setPopunjenost(s.getPopunjenost());
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                        ukMetal = ukMetal + v.getPopunjenost();
                                        s.setPopunjenost(0);

                                    } else {
                                        float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                        v.setPopunjenost(razlika);
                                        s.setPopunjenost(s.getPopunjenost() - razlika);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                        ukMetal = ukMetal + v.getPopunjenost();
                                        vozilaState.doVoziloIsprazni(v);

                                    }
                                }
                                if (v.getVrstaVozila() == 3 && s.getOtpad().equals("bio")) {
                                    //skupi bio
                                    if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                        v.setPopunjenost(s.getPopunjenost());
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                        ukBio = ukBio + v.getPopunjenost();
                                        s.setPopunjenost(0);

                                    } else {
                                        float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                        v.setPopunjenost(razlika);
                                        s.setPopunjenost(s.getPopunjenost() - razlika);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                        ukBio = ukBio + v.getPopunjenost();
                                        vozilaState.doVoziloIsprazni(v);

                                    }
                                }
                                if (v.getVrstaVozila() == 4 && s.getOtpad().equals("mješano")) {
                                    //skupi mješano
                                    if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                        v.setPopunjenost(s.getPopunjenost());
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                        ukMjesano = ukMjesano + v.getPopunjenost();
                                        s.setPopunjenost(0);

                                    } else {
                                        float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                        v.setPopunjenost(razlika);
                                        s.setPopunjenost(s.getPopunjenost() - razlika);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                        ukMjesano = ukMjesano + v.getPopunjenost();
                                        vozilaState.doVoziloIsprazni(v);

                                    }
                                }

                                if (v.getStatus() == 3) {
                                    if (v.getVrstaVozila() == 0) {
                                        v.setPopunjenost(0);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                                        vozilaState.doVoziloPripremi(v);
                                        br++;
                                    }
                                    if (v.getVrstaVozila() == 1) {
                                        v.setPopunjenost(0);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                                        vozilaState.doVoziloPripremi(v);
                                        br++;
                                    }
                                    if (v.getVrstaVozila() == 2) {
                                        v.setPopunjenost(0);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                                        vozilaState.doVoziloPripremi(v);
                                        br++;
                                    }
                                    if (v.getVrstaVozila() == 3) {
                                        v.setPopunjenost(0);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                                        vozilaState.doVoziloPripremi(v);
                                        br++;
                                    }
                                    if (v.getVrstaVozila() == 4) {
                                        v.setPopunjenost(0);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                                        vozilaState.doVoziloPripremi(v);
                                        br++;
                                    }
                                }

                            }
                        }
                    }

                }

            }

            sb.append(System.lineSeparator());
            sb.append("--------------------------------").append(System.lineSeparator());
            sb.append("Ukupno je sakupljeno: ").append(System.lineSeparator());
            sb.append("--------------------------------").append(System.lineSeparator());
            sb.append("Papira: ").append(ukPapir).append(" kg").append(System.lineSeparator());
            sb.append("Metala: ").append(ukMetal).append(" kg").append(System.lineSeparator());
            sb.append("Bio: ").append(ukBio).append(" kg").append(System.lineSeparator());
            sb.append("Stakla: ").append(ukStaklo).append(" kg").append(System.lineSeparator());
            sb.append("Mjesano: ").append(ukMjesano).append(" kg").append(System.lineSeparator());

            for (Vozilo v : vozila) {
                if (v.getStatus() == 3) {
                    vozilaState.doVoziloPripremi(v);
                }
            }

        } else {

            while (br < brojCiklusa) {

                for (Spremnik s : spremnici) {

                    for (Podrucje p : listaPodrucja) {

                        if (p.getDijelovi().contains(s.getUlica())) {

                            for (Vozilo v : vozila) {

                                if (v.getVrstaVozila() == 0 && s.getOtpad().equals("staklo")) {
                                    //skupi staklo
                                    if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                        v.setPopunjenost(s.getPopunjenost());
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                        brStaklo = brStaklo + v.getPopunjenost();
                                        s.setPopunjenost(0);
                                    } else {
                                        float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                        v.setPopunjenost(razlika);
                                        s.setPopunjenost(s.getPopunjenost() - razlika);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                        brStaklo = brStaklo + v.getPopunjenost();
                                        vozilaState.doVoziloIsprazni(v);
                                    }
                                    br++;
                                }
                                if (v.getVrstaVozila() == 1 && s.getOtpad().equals("papir")) {
                                    //skupi papir
                                    if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                        v.setPopunjenost(s.getPopunjenost());
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                        brPapir = brPapir + v.getPopunjenost();
                                        s.setPopunjenost(0);
                                    } else {
                                        float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                        v.setPopunjenost(razlika);
                                        s.setPopunjenost(s.getPopunjenost() - razlika);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                        brPapir = brPapir + v.getPopunjenost();
                                        vozilaState.doVoziloIsprazni(v);
                                    }
                                    br++;
                                }
                                if (v.getVrstaVozila() == 2 && s.getOtpad().equals("metal")) {
                                    //skupi metal
                                    if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                        v.setPopunjenost(s.getPopunjenost());
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                        brMetal = brMetal + v.getPopunjenost();
                                        s.setPopunjenost(0);
                                    } else {
                                        float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                        v.setPopunjenost(razlika);
                                        s.setPopunjenost(s.getPopunjenost() - razlika);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                        brMetal = brMetal + v.getPopunjenost();
                                        vozilaState.doVoziloIsprazni(v);
                                    }
                                    br++;
                                }
                                if (v.getVrstaVozila() == 3 && s.getOtpad().equals("bio")) {
                                    //skupi bio
                                    if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                        v.setPopunjenost(s.getPopunjenost());
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                        brBio = brBio + v.getPopunjenost();
                                        s.setPopunjenost(0);
                                    } else {
                                        float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                        v.setPopunjenost(razlika);
                                        s.setPopunjenost(s.getPopunjenost() - razlika);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                        brBio = brBio + v.getPopunjenost();
                                        vozilaState.doVoziloIsprazni(v);
                                    }
                                    br++;
                                }
                                if (v.getVrstaVozila() == 4 && s.getOtpad().equals("mješano")) {
                                    //skupi mješano
                                    if ((v.getNosivost() - v.getPopunjenost()) > s.getPopunjenost()) {
                                        v.setPopunjenost(s.getPopunjenost());
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je popunjeno ").append(v.getPopunjenost()).append("/").append(v.getNosivost()).append(System.lineSeparator());
                                        brMjesano = brMjesano + v.getPopunjenost();
                                        s.setPopunjenost(0);
                                    } else {
                                        float razlika = (v.getNosivost() - v.getPopunjenost()) - s.getPopunjenost();
                                        v.setPopunjenost(razlika);
                                        s.setPopunjenost(s.getPopunjenost() - razlika);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je skroz popunjeno ").append(System.lineSeparator());
                                        brMjesano = brMjesano + v.getPopunjenost();
                                        vozilaState.doVoziloIsprazni(v);
                                    }
                                }

                                if (v.getStatus() == 3) {
                                    if (v.getVrstaVozila() == 0) {
                                        v.setPopunjenost(0);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                                        vozilaState.doVoziloPripremi(v);
                                        br++;
                                    }
                                    if (v.getVrstaVozila() == 1) {
                                        v.setPopunjenost(0);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                                        vozilaState.doVoziloPripremi(v);
                                        br++;
                                    }
                                    if (v.getVrstaVozila() == 2) {
                                        v.setPopunjenost(0);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                                        vozilaState.doVoziloPripremi(v);
                                        br++;
                                    }
                                    if (v.getVrstaVozila() == 3) {
                                        v.setPopunjenost(0);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                                        vozilaState.doVoziloPripremi(v);
                                        br++;
                                    }
                                    if (v.getVrstaVozila() == 4) {
                                        v.setPopunjenost(0);
                                        sb.append("Vozilo ").append(v.getNaziv()).append(" je ispraznjeno").append(System.lineSeparator());
                                        vozilaState.doVoziloPripremi(v);
                                        br++;
                                    }
                                }
                            }
                        }
                    }

                }
            }

            sb.append(System.lineSeparator());
            sb.append("--------------------------------").append(System.lineSeparator());
            sb.append("Ukupno je sakupljeno: ").append(System.lineSeparator());
            sb.append("--------------------------------").append(System.lineSeparator());
            sb.append("Papira: ").append(brPapir).append(" kg").append(System.lineSeparator());
            sb.append("Metala: ").append(brMetal).append(" kg").append(System.lineSeparator());
            sb.append("Bio: ").append(brBio).append(" kg").append(System.lineSeparator());
            sb.append("Stakla: ").append(brStaklo).append(" kg").append(System.lineSeparator());
            sb.append("Mjesano: ").append(brMjesano).append(" kg").append(System.lineSeparator());

            for (Vozilo v : vozila) {
                if (v.getStatus() == 3) {
                    vozilaState.doVoziloPripremi(v);
                }
            }
        }

        System.out.println(sb.toString());
        is.zapisiUDatoteku(sb.toString());

    }

    private static void ispisStatusVozila(List<Vozilo> vozila, Ispis is) {
        StringBuilder sb = new StringBuilder();
        sb.append("Status - VOZILA: ").append(System.lineSeparator());
        sb.append("----------------------------------------------").append(System.lineSeparator());
        for (Vozilo v : vozila) {
            switch (v.getStatus()) {
                case 0:
                    sb.append(v.getPodaciVozilo()).append(", Status: PARKIRANO").append(System.lineSeparator());
                    break;
                case 1:
                    sb.append(v.getPodaciVozilo()).append(", Status: PRIPREMLJENO").append(System.lineSeparator());
                    break;
                case 2:
                    sb.append(v.getPodaciVozilo()).append(", Status: KVAR").append(System.lineSeparator());
                    break;
                case 3:
                    sb.append(v.getPodaciVozilo()).append(", Status: PRAZNJENJE").append(System.lineSeparator());
                    break;
                case 4:
                    sb.append(v.getPodaciVozilo()).append(", Status: KONTROLA").append(System.lineSeparator());
                    break;
            }
        }
        System.out.println(sb);
        is.zapisiUDatoteku(sb.toString());
    }

    private static void ispisStatusVozaca(Ispis is, List<Vozac> vozaci) {
        StringBuilder sb = new StringBuilder();
        sb.append("Status - VOZAČI: ").append(System.lineSeparator());
        sb.append("----------------------------------------------").append(System.lineSeparator());
        for (Vozac v : vozaci) {
            switch (v.getStatus()) {
                case 0:
                    sb.append("Vozač: ").append(v.getPodaciVozac()).append(", Opis: Vozi").append(System.lineSeparator());
                    break;
                case 1:
                    sb.append("Vozač: ").append(v.getPodaciVozac()).append(", Opis: Bolovanje").append(System.lineSeparator());
                    break;
                case 2:
                    sb.append("Vozač: ").append(v.getPodaciVozac()).append(", Opis: Otkaz").append(System.lineSeparator());
                    break;
                case 3:
                    sb.append("Vozač: ").append(v.getPodaciVozac()).append(", Opis: Godisnji").append(System.lineSeparator());
                    break;
                case 4:
                    sb.append("Vozač: ").append(v.getPodaciVozac()).append(", Opis: Preuzeo vozilo").append(System.lineSeparator());
                    break;
                case 5:
                    sb.append("Vozač: ").append(v.getPodaciVozac()).append(", Opis: Novi vozač").append(System.lineSeparator());
                    break;
            }
        }
        is.zapisiUDatoteku(sb.toString());
        System.out.println(sb.toString());
    }

    private static void radniCiklusiZaOdvoz(int ciklusVozilaOdvoz, Vozilo v, VoziloState vs) {
        int brojac = 0;
        if (ciklusVozilaOdvoz < brojac) {
            vs.doVoziloPripremi(v);
        } else {
            brojac++;
        }
    }

    private static void punjenjeVozila(int punjenjeDizel, int punjenjeElektro, Vozilo v, VoziloState vs) {
        //0 dizel, 1 električni
        int brojacDizel = 0;
        int brojacElektro = 0;

        if (v.getTip() == 0) {
            if (punjenjeDizel < brojacDizel) {
                vs.doVoziloPripremi(v);
            } else {
                brojacDizel++;
            }
        }
        if (v.getTip() == 1) {
            if (punjenjeElektro < brojacElektro) {
                vs.doVoziloPripremi(v);
            } else {
                brojacElektro++;
            }
        }
    }

}
