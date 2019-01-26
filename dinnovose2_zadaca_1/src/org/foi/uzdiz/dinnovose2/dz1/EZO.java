package org.foi.uzdiz.dinnovose2.dz1;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.dinnovose2.dz1.podaci.Datoteka;
import org.foi.uzdiz.dinnovose2.dz1.podaci.Ispis;
import org.foi.uzdiz.dinnovose2.dz1.racunanje.Generator;
import org.foi.uzdiz.dinnovose2.dz1.podaci.Parametri;

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

        //Ucitaj podatke ulica, spremnika, vozila, korisnika
        Datoteka dat = new Datoteka();
        Ispis is = new Ispis(parametri.dajParametar("izlaz"));

        List<Ulica> ulice = dat.dohvatiPodatkeUlica(parametri.dajParametar("ulice"));
        is.zapisiUDatoteku("Ucitane ulice");
        is.zapisiUDatoteku("-------------------------------------------------------");
        for (Ulica u : ulice) {
            is.zapisiUDatoteku(u.getPodaciUlica());
            System.out.println(u.getPodaciUlica());
        }

        List<Spremnici> spremnici = dat.dohvatiPodatkeSpremnici(parametri.dajParametar("spremnici"));
        is.zapisiUDatoteku("-------------------------------------------------------");
        is.zapisiUDatoteku("Ucitani spremnici");
        is.zapisiUDatoteku("-------------------------------------------------------");
        for (Spremnici s : spremnici) {
            is.zapisiUDatoteku(s.getPodaciSpremnika());
            System.out.println(s.getPodaciSpremnika());
        }

        List<Vozilo> vozila = dat.dohvatiPodatkeVozila(parametri.dajParametar("vozila"));
        is.zapisiUDatoteku("-------------------------------------------------------");
        is.zapisiUDatoteku("Ucitana vozila");
        is.zapisiUDatoteku("-------------------------------------------------------");
        for (Vozilo v : vozila) {
            is.zapisiUDatoteku(v.getPodaciVozilo());
            System.out.println(v.getPodaciVozilo());
        }

        //---------------------------------------------------------------------------------------------
        dohvatiParametreKorisnika(parametri);

        List<Korisnik> listaKorisnika = new ArrayList<>();
        int id = 1;
        for (Ulica s : ulice) {

            for (int i = 0; i < s.getBrojMalihKorisnika(); i++) {
                float kolStaklo = gen.dajSlucajniFloat(maliStaklo, maliStaklo / maliMin, brojDecimala);
                float kolPapir = gen.dajSlucajniFloat(maliPapir, maliPapir / maliMin, brojDecimala);
                float kolMetal = gen.dajSlucajniFloat(maliMetal, maliMetal / maliMin, brojDecimala);
                float kolBio = gen.dajSlucajniFloat(maliBio, maliBio / maliMin, brojDecimala);
                float kolMjesano = gen.dajSlucajniFloat(maliMjesano, maliMjesano / maliMin, brojDecimala);
                Korisnik kor = new Korisnik.KorisnikBuilder(id, s.getNaziv(), "mali", kolMetal, kolMjesano, kolPapir, kolStaklo, kolBio).build();
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
                Korisnik kor = new Korisnik.KorisnikBuilder(id, s.getNaziv(), "srednji", kolMetal, kolMjesano, kolPapir, kolStaklo, kolBio).build();
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
                Korisnik kor = new Korisnik.KorisnikBuilder(id, s.getNaziv(), "veliki", kolMetal, kolMjesano, kolPapir, kolStaklo, kolBio).build();
                zbrojUkupnoGeneriranogOtpada(kolBio, kolMetal, kolMjesano, kolStaklo, kolPapir);
                listaKorisnika.add(kor);
                id++;
            }
        }

        is.zapisiUDatoteku("-------------------------------------------------------");
        is.zapisiUDatoteku("Kreirani korisnici");
        is.zapisiUDatoteku("-------------------------------------------------------");
        for (Korisnik k : listaKorisnika) {
            is.zapisiUDatoteku(k.getPodaciKorisnik());
            System.out.println(k.getPodaciKorisnik());
        }
        is.zapisiUDatoteku("-------------------------------------------------------");
        is.zapisiUDatoteku("BIO otpad: " + generiranoBioOtpada);
        is.zapisiUDatoteku("METAL otpad: " + generiranoMetalnogOtpada);
        is.zapisiUDatoteku("MJESANO otpad: " + generiranoMjesanogOtpada);
        is.zapisiUDatoteku("STAKLO otpad: " + generiranoStaklenogOtpada);
        is.zapisiUDatoteku("PAPIR otpad: " + generiranoPapirOtpada);
        is.zapisiUDatoteku("-------------------------------------------------------");
        is.zapisiUDatoteku("UKUPNO otpada: " + ukupnoGeneriranoOtpada);

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
        //Kreiraj korisnike
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

}
