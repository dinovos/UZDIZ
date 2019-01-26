/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.dinnovose2.dz3.builder;

/**
 *
 * @author Dino
 */
public class Korisnik {

    int id;
    String ulica;
    String vrstaKorisnika;
    float stakleniOtpad; 
    float papirnatiOtpad;
    float metalniOtpad; 
    float bioOtpad; 
    float mjesaniOtpad; 
    
    public String getPodaciKorisnik(){
        return "Id: "+id+", Ulica: "+ulica+", Vrsta: "+vrstaKorisnika+", stakleniOtpad: "+stakleniOtpad+", "
                + "papirnatiOtpad: "+papirnatiOtpad+", metalniOtpad: "+metalniOtpad+", bioOtpad: "+bioOtpad+", "
                + "mjesaniOtpad: "+mjesaniOtpad+"";
    }

    public int getId() {
        return id;
    }

    public String getUlica() {
        return ulica;
    }

    public String getVrstaKorisnika() {
        return vrstaKorisnika;
    }

    public float getStakleniOtpad() {
        return stakleniOtpad;
    }

    public float getPapirnatiOtpad() {
        return papirnatiOtpad;
    }

    public float getMetalniOtpad() {
        return metalniOtpad;
    }

    public float getBioOtpad() {
        return bioOtpad;
    }

    public float getMjesaniOtpad() {
        return mjesaniOtpad;
    }

    public void setStakleniOtpad(float stakleniOtpad) {
        this.stakleniOtpad = stakleniOtpad;
    }

    public void setPapirnatiOtpad(float papirnatiOtpad) {
        this.papirnatiOtpad = papirnatiOtpad;
    }

    public void setMetalniOtpad(float metalniOtpad) {
        this.metalniOtpad = metalniOtpad;
    }

    public void setMjesaniOtpad(float mjesaniOtpad) {
        this.mjesaniOtpad = mjesaniOtpad;
    }

    public void setBioOtpad(float bioOtpad) {
        this.bioOtpad = bioOtpad;
    }
    
    
    
    private Korisnik(KorisnikBuilder builder){
        this.id = builder.id; 
        this.ulica = builder.ulica; 
        this.vrstaKorisnika = builder.vrstaKorisnika; 
        this.metalniOtpad = builder.metalniOtpad; 
        this.mjesaniOtpad = builder.mjesaniOtpad; 
        this.papirnatiOtpad = builder.papirnatiOtpad; 
        this.stakleniOtpad = builder.stakleniOtpad; 
        this.bioOtpad = builder.bioOtpad;
    }

    public static class KorisnikBuilder {

        private int id;
        private String ulica;
        private String vrstaKorisnika;
        private float metalniOtpad;
        private float mjesaniOtpad;
        private float papirnatiOtpad;
        private float stakleniOtpad;
        private float bioOtpad;
        private int idSpremnika;

        public KorisnikBuilder(int id, String ulica, String vrstaKorisnika, float metalniOtpad,
                float mjesaniOtpad, float papirnatiOtpad, float stakleniOtpad, float bioOtpad) {
            this.id = id;
            this.ulica = ulica;
            this.vrstaKorisnika = vrstaKorisnika;
            this.metalniOtpad = metalniOtpad;
            this.mjesaniOtpad = mjesaniOtpad;
            this.papirnatiOtpad = papirnatiOtpad;
            this.stakleniOtpad = stakleniOtpad;
            this.bioOtpad = bioOtpad;
        }

        public KorisnikBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public KorisnikBuilder setUlica(String ulica) {
            this.ulica = ulica;
            return this;
        }

        public KorisnikBuilder setVrstaKorisnika(String vrstaKorisnika) {
            this.vrstaKorisnika = vrstaKorisnika;
            return this;
        }

        public KorisnikBuilder setMetalniOtpad(float metalniOtpad) {
            this.metalniOtpad = metalniOtpad;
            return this;
        }

        public KorisnikBuilder setMjesaniOtpad(float mjesaniOtpad) {
            this.mjesaniOtpad = mjesaniOtpad;
            return this;
        }

        public KorisnikBuilder setPapirnatiOtpad(float papirnatiOtpad) {
            this.papirnatiOtpad = papirnatiOtpad;
            return this;
        }

        public KorisnikBuilder setStakleniOtpad(float stakleniOtpad) {
            this.stakleniOtpad = stakleniOtpad;
            return this;
        }

        public KorisnikBuilder setBioOtpad(float bioOtpad) {
            this.bioOtpad = bioOtpad;
            return this;
        }

        public void setIdSpremnika(int idSpremnika) {
            this.idSpremnika = idSpremnika;
        }

        public Korisnik build(){
            return new Korisnik(this);
        }
        
    }

}
