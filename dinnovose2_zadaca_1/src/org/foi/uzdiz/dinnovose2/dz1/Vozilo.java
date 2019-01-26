package org.foi.uzdiz.dinnovose2.dz1;

/**
 *
 * @author Dino
 */
public class Vozilo {
    
    String naziv; 
    String vozac;
    int tip; 
    int vrstaVozila; 
    int nosivost;
    
    public String getPodaciVozilo(){
        return "Naziv: "+naziv+", tip: "+tip+", vrsta: "+vrstaVozila+", "
                + "nosivost: "+nosivost+", vozaci: "+vozac+"";
    }
    
    public String getNaziv() {
        return naziv;
    }

    public String getVozac() {
        return vozac;
    }

    public int getTip() {
        return tip;
    }

    public int getVrstaVozila() {
        return vrstaVozila;
    }

    public int getNosivost() {
        return nosivost;
    }

    private Vozilo(VoziloBuilder builder){
        this.naziv = builder.naziv;
        this.vozac = builder.vozac;
        this.tip  = builder.tip; 
        this.vrstaVozila = builder.vrstaVozila; 
        this.nosivost = builder.nosivost;
    }

    public static class VoziloBuilder {

        private String naziv;
        private String vozac;
        private int tip;
        private int vrstaVozila;
        private int nosivost;

        public VoziloBuilder(String naziv, String vozac, int tip, int vrstaVozila, int nosivost) {
            this.naziv = naziv;
            this.vozac = vozac;
            this.tip = tip;
            this.vrstaVozila = vrstaVozila;
            this.nosivost = nosivost;
        }

        public VoziloBuilder setNaziv(String naziv){
            this.naziv = naziv;
            return this;
        }
        
        public VoziloBuilder setVozac(String vozac){
            this.vozac = vozac;
            return this;
        }
        
        public VoziloBuilder setTip(int tip){
            this.tip = tip;
            return this;
        }
        
        public VoziloBuilder setVrsta(int vrstaVozila){
            this.vrstaVozila = vrstaVozila;
            return this;
        }
        
        public VoziloBuilder setNosivost(int nosivost){
            this.nosivost = nosivost;
            return this;
        }
        
        public Vozilo build(){
            return new Vozilo(this);
        }
    }
            

}


