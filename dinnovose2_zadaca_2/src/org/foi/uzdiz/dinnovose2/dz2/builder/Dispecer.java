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
public class Dispecer {
    
    String komanda; 
    List<String> listaVozila;
    
    public String getPodaciDispecer(){
        return "Komanda: "+komanda+", lista vozila: "+listaVozila+"";
    }

    public String getKomanda() {
        return komanda;
    }

    public List<String> getListaVozila() {
        return listaVozila;
    }
    
    private Dispecer(DispecerBuilder builder){
        this.komanda = builder.komanda;
        this.listaVozila = builder.listaVozila;
    }
    
    public static class DispecerBuilder{
        private String komanda; 
        private List<String> listaVozila;
        
        public DispecerBuilder(String komanda, List<String> listaVozila){
            this.komanda = komanda;
            this.listaVozila = listaVozila;
        }

        public DispecerBuilder setKomanda(String komanda) {
            this.komanda = komanda;
            return this;
        }

        public DispecerBuilder setListaVozila(List<String> listaVozila) {
            this.listaVozila = listaVozila;
            return this;
        }
        
        public Dispecer build(){
            return new Dispecer(this);
        }
        
        
    }
}
