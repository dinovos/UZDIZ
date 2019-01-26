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
public class Podrucje {
    
    String id; 
    String naziv; 
    List<String> dijelovi;
    
    public String getPodaciPodrucje(){
        return "Id: "+id+", naziv: "+naziv+", dijelovi: "+dijelovi+"";
    }

    public String getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public List<String> getDijelovi() {
        return dijelovi;
    }
    
    private Podrucje(PodrucjeBuilder builder){
        this.id = builder.id;
        this.naziv = builder.naziv;
        this.dijelovi = builder.dijelovi;
    }
    
    public static class PodrucjeBuilder{
        private String id; 
        private String naziv; 
        private List<String> dijelovi;

        public PodrucjeBuilder(String id, String naziv, List<String> dijelovi){
            this.id = id; 
            this.naziv = naziv; 
            this.dijelovi = dijelovi;
        }

        public PodrucjeBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public PodrucjeBuilder setNaziv(String naziv) {
            this.naziv = naziv;
            return this;
        }

        public PodrucjeBuilder setDijelovi(List<String> dijelovi) {
            this.dijelovi = dijelovi;
            return this;
        }
        
        public Podrucje build(){
            return new Podrucje(this);
        }
        
        
        
    }
}
