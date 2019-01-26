/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.dinnovose2.dz3.builder;

import java.util.List;

/**
 *
 * @author Dino
 */
public class Dispecer {
    
    String komanda; 
    List<String> prvaLista;
    List<String> drugaLista;
    
    public String getPodaciDispecer(){
        return "Komanda: "+komanda+", lista: "+prvaLista+", druga lista: "+drugaLista;
    }

    public String getKomanda() {
        return komanda;
    }

    public List<String> getPrvaLista() {
        return prvaLista;
    }
    
    public List<String> getDrugaLista(){
        return drugaLista;
    }
    
    
    
    private Dispecer(DispecerBuilder builder){
        this.komanda = builder.komanda;
        this.prvaLista = builder.prvaLista;
        this.drugaLista = builder.drugaLista;
    }
    
    public static class DispecerBuilder{
        private String komanda; 
        private List<String> prvaLista;
        private List<String> drugaLista;
        
        public DispecerBuilder(String komanda, List<String> prvaLista, List<String> drugaLista){
            this.komanda = komanda;
            this.prvaLista = prvaLista;
            this.drugaLista = drugaLista;
        }

        public DispecerBuilder setKomanda(String komanda) {
            this.komanda = komanda;
            return this;
        }

        public DispecerBuilder setPrvaLista(List<String> prvaLista) {
            this.prvaLista = prvaLista;
            return this;
        }
        
        public DispecerBuilder setDrugaLista(List<String> drugaLista){
            this.drugaLista = drugaLista; 
            return this; 
        }
        
        public Dispecer build(){
            return new Dispecer(this);
        }
        
        
    }
}
