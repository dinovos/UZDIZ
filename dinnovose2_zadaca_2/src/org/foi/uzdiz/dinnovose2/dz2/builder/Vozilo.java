package org.foi.uzdiz.dinnovose2.dz2.builder;

/**
 *
 * @author Dino
 */
public class Vozilo {
    
    String idVozila;
    String naziv; 
    String vozac;
    int tip; 
    int vrstaVozila; 
    float nosivost;
    float popunjenost;
    
    
    public String getPodaciVozilo(){
        return "Id: "+idVozila+", naziv: "+naziv+", tip: "+tip+", vrsta: "+vrstaVozila+", "
                + "nosivost: "+nosivost+",popunjenost: "+popunjenost+", vozaci: "+vozac+"";
    }

    public String getIdVozila() {
        return idVozila;
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

    public float getNosivost() {
        return nosivost;
    }

    public float getPopunjenost() {
        return popunjenost;
    }

    public void setPopunjenost(float popunjenost) {
        this.popunjenost = popunjenost;
    }
    
    

    private Vozilo(VoziloBuilder builder){
        this.idVozila = builder.idVozila;
        this.naziv = builder.naziv;
        this.vozac = builder.vozac;
        this.tip  = builder.tip; 
        this.vrstaVozila = builder.vrstaVozila; 
        this.nosivost = builder.nosivost;
    }

    public static class VoziloBuilder {

        private String idVozila;
        private String naziv;
        private String vozac;
        private int tip;
        private int vrstaVozila;
        private float nosivost;
        float popunjenost;

        public VoziloBuilder(String idVozila, String naziv, String vozac, int tip, int vrstaVozila, float nosivost) {
            this.idVozila = idVozila;
            this.naziv = naziv;
            this.vozac = vozac;
            this.tip = tip;
            this.vrstaVozila = vrstaVozila;
            this.nosivost = nosivost;
        }

        public VoziloBuilder setIdVozila(String idVozila) {
            this.idVozila = idVozila;
            return this;
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
        
        public VoziloBuilder setNosivost(float nosivost){
            this.nosivost = nosivost;
            return this;
        }
        
        public VoziloBuilder setPopunjenost(float popunjenost){
            this.popunjenost = popunjenost;
            return this;
        }
        
        public Vozilo build(){
            return new Vozilo(this);
        }
    }
            

}


