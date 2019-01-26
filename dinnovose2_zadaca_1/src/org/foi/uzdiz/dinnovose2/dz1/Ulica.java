package org.foi.uzdiz.dinnovose2.dz1;

/**
 *
 * @author Dino
 */
public class Ulica {

    String naziv;
    int brojMjesta;
    int udioMali;
    int udioSrednji;
    int udioVeliki;
    int brojMalihKorisnika;
    int brojSrednjihKorisnika;
    int brojVelikihKorisnika;

    public String getPodaciUlica() {
        return "Naziv: " + naziv + ", brojMjesta: " + brojMjesta + ", udioMali: " + udioMali + ", "
                + "udioSrednji: " + udioSrednji + ", udioVeliki: " + udioVeliki + ", "
                + "brojMalih: "+brojMalihKorisnika+", brojSrednjih: "+brojSrednjihKorisnika+", "
                + "brojVelikih: "+brojVelikihKorisnika+"";
    }

    public String getNaziv() {
        return naziv;
    }

    public int getBrojMjesta() {
        return brojMjesta;
    }

    public int getUdioMali() {
        return udioMali;
    }

    public int getUdioSrednji() {
        return udioSrednji;
    }

    public int getUdioVeliki() {
        return udioVeliki;
    }

    public int getBrojMalihKorisnika() {
        return brojMalihKorisnika;
    }

    public int getBrojSrednjihKorisnika() {
        return brojSrednjihKorisnika;
    }

    public int getBrojVelikihKorisnika() {
        return brojVelikihKorisnika;
    }
    
    

    private Ulica(UlicaBuilder builder) {
        this.naziv = builder.naziv;
        this.brojMjesta = builder.brojMjesta;
        this.udioMali = builder.udioMali;
        this.udioSrednji = builder.udioSrednji;
        this.udioVeliki = builder.udioVeliki;
        this.brojMalihKorisnika = builder.brojMalihKorisnika;
        this.brojSrednjihKorisnika = builder.brojSrednjihKorisnika;
        this.brojVelikihKorisnika = builder.brojVelikihKorisnika;
    }

    public static class UlicaBuilder {

        private String naziv;
        private int brojMjesta;
        private int udioMali;
        private int udioSrednji;
        private int udioVeliki;

        int brojMalihKorisnika;
        int brojSrednjihKorisnika;
        int brojVelikihKorisnika;

        public UlicaBuilder(String naziv, int brojMjesta, int udioMali, int udioSrednji, int udioVeliki, 
                int brojMalih, int brojSrednjih, int brojVelikih) {
            this.naziv = naziv;
            this.brojMjesta = brojMjesta;
            this.udioMali = udioMali;
            this.udioSrednji = udioSrednji;
            this.udioVeliki = udioVeliki;
            this.brojMalihKorisnika = brojMalih;
            this.brojSrednjihKorisnika = brojSrednjih;
            this.brojVelikihKorisnika = brojVelikih;
        }

        public UlicaBuilder setNaziv(String naziv) {
            this.naziv = naziv;
            return this;
        }

        public UlicaBuilder setBrojMjesta(int brojMjesta) {
            this.brojMjesta = brojMjesta;
            return this;
        }

        public UlicaBuilder setUdioMali(int udioMali) {
            this.udioMali = udioMali;
            return this;
        }

        public UlicaBuilder setUdioSrednji(int udioSrednji) {
            this.udioSrednji = udioSrednji;
            return this;
        }

        public UlicaBuilder setUdioVeliki(int udioVeliki) {
            this.udioVeliki = udioVeliki;
            return this;
        }
        
        public Ulica build() {
            return new Ulica(this);
        }
    }
}
