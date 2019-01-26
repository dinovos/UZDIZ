package org.foi.uzdiz.dinnovose2.dz1;

/**
 *
 * @author Dino
 */
public class Spremnici {
    
    String naziv; 
    int vrsta;
    int jedanNaBrojMalih;
    int jedanNaBrojSrednjih; 
    int jedanNaBrojVelikih; 
    int nosivost;

    public String getPodaciSpremnika(){
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
    
    private Spremnici(SpremniciBuilder builder){
        this.naziv = builder.naziv; 
        this.vrsta = builder.vrsta;
        this.nosivost = builder.nosivost;
        this.jedanNaBrojMalih = builder.jedanNaBrojMalih;
        this.jedanNaBrojSrednjih = builder.jedanNaBrojSrednjih; 
        this.jedanNaBrojVelikih = builder.jedanNaBrojVelikih; 
    }

    public static class SpremniciBuilder {

        private String naziv;
        private int vrsta;
        private int nosivost;
        private int jedanNaBrojMalih;
        private int jedanNaBrojSrednjih;
        private int jedanNaBrojVelikih;

        public SpremniciBuilder(String naziv, int vrsta, int nosivost, int jedanNaBrojMalih, int jedanNaBrojSrednjih, int jedanNaBrojVelikih) {
            this.naziv = naziv;
            this.vrsta = vrsta;
            this.nosivost = nosivost;
            this.jedanNaBrojMalih = jedanNaBrojMalih;
            this.jedanNaBrojSrednjih = jedanNaBrojSrednjih;
            this.jedanNaBrojVelikih = jedanNaBrojVelikih;
        }

        public SpremniciBuilder setNaziv(String naziv) {
            this.naziv = naziv;
            return this;
        }

        public SpremniciBuilder setVrsta(int vrsta) {
            this.vrsta = vrsta;
            return this;
        }

        public SpremniciBuilder setNosivost(int nosivost) {
            this.nosivost = nosivost;
            return this;
        }

        public SpremniciBuilder setJedanNaBrojMalih(int jedanNaBrojMalih) {
            this.jedanNaBrojMalih = jedanNaBrojMalih;
            return this; 
        }

        public SpremniciBuilder setJedanNaBrojSrednjih(int jedanNaBrojSrednjih) {
            this.jedanNaBrojSrednjih = jedanNaBrojSrednjih;
            return this; 
        }

        public SpremniciBuilder setJedanNaBrojVelikih(int jedanNaBrojVelikih) {
            this.jedanNaBrojVelikih = jedanNaBrojVelikih;
            return this; 
        }

        public Spremnici build(){
            return new Spremnici(this);
        }
        
    }
}
