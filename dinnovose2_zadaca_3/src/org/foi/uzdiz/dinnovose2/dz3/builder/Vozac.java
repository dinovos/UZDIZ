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
public class Vozac {
    
    int id; 
    String ime; 
    String vozilo;
    int status; 
    
    public String getPodaciVozac(){
        return "Id: "+id+", Ime: "+ime+", Vozilo: "+vozilo+", Status: "+status;
    }

    public int getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    public int getStatus() {
        return status;
    }

    public String getVozilo() {
        return vozilo;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setVozilo(String vozilo) {
        this.vozilo = vozilo;
    }
    
    
    
    private Vozac(VozacBuilder vozacBuilder){
        this.id = vozacBuilder.id; 
        this.ime = vozacBuilder.ime; 
        this.vozilo = vozacBuilder.vozilo;
        this.status = vozacBuilder.status; 
    }
    
    public static class VozacBuilder{
        
        private int id; 
        private String ime;
        private String vozilo;
        private int status;
        
        public VozacBuilder(int id, String ime, String vozilo, int status){
            this.id = id; 
            this.ime = ime; 
            this.vozilo = vozilo;
            this.status = status;
        }

        public VozacBuilder setId(int id) {
            this.id = id;
            return this; 
        }

        public VozacBuilder setIme(String ime) {
            this.ime = ime;
            return this; 
        }

        public VozacBuilder setStatus(int status) {
            this.status = status;
            return this; 
        }

        public VozacBuilder setVozilo(String vozilo) {
            this.vozilo = vozilo;
            return this;
        }
        
        public Vozac build(){
            return new Vozac(this);
        } 
    }
    
}
