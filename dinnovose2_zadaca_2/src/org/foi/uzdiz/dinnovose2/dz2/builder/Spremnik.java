/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.dinnovose2.dz2.builder;

import java.util.List;

/**
 *
 * @author Dino
 */
public class Spremnik {

    int id;
    int nosivost;
    String ulica; 
    String otpad;
    float popunjenost;
    List<Korisnik> korisnici;
    
    public int getId() {
        return id;
    }

    public int getNosivost() {
        return nosivost;
    }

    public String getOtpad() {
        return otpad;
    }

    public String getUlica() {
        return ulica;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public float getPopunjenost() {
        return popunjenost;
    }

    public void setPopunjenost(float popunjenost) {
        this.popunjenost = popunjenost;
    }
    
    
    
    private Spremnik(SpremnikBuilder builder){
        this.id = builder.id;
        this.otpad = builder.otpad;
        this.nosivost = builder.nosivost;
        this.ulica = builder.ulica;
        this.korisnici = builder.korisnici;
    }

    public static class SpremnikBuilder {

        private int id;
        private int nosivost;
        private String otpad;
        private String ulica;
        private List<Korisnik> korisnici;

        public SpremnikBuilder(int id, int nosivost, String otpad, List<Korisnik> korisnici, String ulica){
            this.id = id; 
            this.nosivost = nosivost; 
            this.otpad = otpad; 
            this.korisnici = korisnici;
            this.ulica = ulica; 
        }

        public SpremnikBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public SpremnikBuilder setNosivost(int nosivost) {
            this.nosivost = nosivost;
            return this;
        }

        public SpremnikBuilder setOtpad(String otpad) {
            this.otpad = otpad;
            return this;
        }

        public SpremnikBuilder setKorisnici(List<Korisnik> korisnici) {
            this.korisnici = korisnici;
            return this;
        }

        public SpremnikBuilder setUlica(String ulica) {
            this.ulica = ulica;
            return this;
        }
        
        public Spremnik build(){
            return new Spremnik(this);
        }
    
    }

}
