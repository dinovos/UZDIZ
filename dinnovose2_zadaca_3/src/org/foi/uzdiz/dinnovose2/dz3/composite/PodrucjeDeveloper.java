/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.dinnovose2.dz3.composite;

import java.util.List;

/**
 *
 * @author Dino
 */
public class PodrucjeDeveloper implements Podrucje{

    private String id; 
    private String naziv; 
    private List<Podrucje> dijelovi; 

    public PodrucjeDeveloper(String id, String naziv, List<Podrucje> dijelovi) {
        this.id = id;
        this.naziv = naziv;
        this.dijelovi = dijelovi;
    }

    @Override
    public void add(Podrucje podrucje) {
        
    }

    @Override
    public void remove(Podrucje podrucje) {
        
    }

    @Override
    public Podrucje getChild(int i) {
        return null;
    }

    @Override
    public String getName() {
        return naziv;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<Podrucje> getDijelovi() {
        return dijelovi;
    }

    @Override
    public void print() {
        System.out.println("Id: " + getId());
        System.out.println("Naziv: " + getName());
        System.out.println("Dijelovi: " + getDijelovi());
    }
    
}
