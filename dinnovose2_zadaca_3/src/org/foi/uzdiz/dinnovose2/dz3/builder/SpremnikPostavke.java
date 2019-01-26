package org.foi.uzdiz.dinnovose2.dz3.builder;

/**
 *
 * @author Dino
 */
public class SpremnikPostavke {
    
    String naziv; 
    int vrsta;
    int jedanNaBrojMalih;
    int jedanNaBrojSrednjih; 
    int jedanNaBrojVelikih; 
    int nosivost;

    public String getPodaciPostavkiSpremnika(){
        return "Naziv: "+naziv+", vrsta: "+vrsta+", jedanNaMale: "+jedanNaBrojMalih+", "
                + "jedanNaSrednje: "+jedanNaBrojSrednjih+", jedanNaVelike: "+jedanNaBrojVelikih+", nosivost: "+nosivost+"";
    }
    
    public String getNaziv() {
        return naziv;
    }

    public int getVrsta() {
        return vrsta;
    }

    public int getJedanNaBrojMalih() {
        return jedanNaBrojMalih;
    }

    public int getJedanNaBrojSrednjih() {
        return jedanNaBrojSrednjih;
    }

    public int getJedanNaBrojVelikih() {
        return jedanNaBrojVelikih;
    }

    public int getNosivost() {
        return nosivost;
    }
    
    private SpremnikPostavke(SpremniciPostavkeBuilder builder){
        this.naziv = builder.naziv; 
        this.vrsta = builder.vrsta;
        this.nosivost = builder.nosivost;
        this.jedanNaBrojMalih = builder.jedanNaBrojMalih;
        this.jedanNaBrojSrednjih = builder.jedanNaBrojSrednjih; 
        this.jedanNaBrojVelikih = builder.jedanNaBrojVelikih; 
    }

    public static class SpremniciPostavkeBuilder {

        private String naziv;
        private int vrsta;
        private int nosivost;
        private int jedanNaBrojMalih;
        private int jedanNaBrojSrednjih;
        private int jedanNaBrojVelikih;

        public SpremniciPostavkeBuilder(String naziv, int vrsta, int nosivost, int jedanNaBrojMalih, int jedanNaBrojSrednjih, int jedanNaBrojVelikih) {
            this.naziv = naziv;
            this.vrsta = vrsta;
            this.nosivost = nosivost;
            this.jedanNaBrojMalih = jedanNaBrojMalih;
            this.jedanNaBrojSrednjih = jedanNaBrojSrednjih;
            this.jedanNaBrojVelikih = jedanNaBrojVelikih;
        }

        public SpremniciPostavkeBuilder setNaziv(String naziv) {
            this.naziv = naziv;
            return this;
        }

        public SpremniciPostavkeBuilder setVrsta(int vrsta) {
            this.vrsta = vrsta;
            return this;
        }

        public SpremniciPostavkeBuilder setNosivost(int nosivost) {
            this.nosivost = nosivost;
            return this;
        }

        public SpremniciPostavkeBuilder setJedanNaBrojMalih(int jedanNaBrojMalih) {
            this.jedanNaBrojMalih = jedanNaBrojMalih;
            return this; 
        }

        public SpremniciPostavkeBuilder setJedanNaBrojSrednjih(int jedanNaBrojSrednjih) {
            this.jedanNaBrojSrednjih = jedanNaBrojSrednjih;
            return this; 
        }

        public SpremniciPostavkeBuilder setJedanNaBrojVelikih(int jedanNaBrojVelikih) {
            this.jedanNaBrojVelikih = jedanNaBrojVelikih;
            return this; 
        }

        public SpremnikPostavke build(){
            return new SpremnikPostavke(this);
        }
        
    }
}
