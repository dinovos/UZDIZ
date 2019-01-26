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
public interface Podrucje {
    
    public void add(Podrucje podrucje);
    public void remove(Podrucje podrucje);
    public Podrucje getChild(int i);
    public String getName();
    public String getId();
    public List<Podrucje> getDijelovi();
    public void print();
    
}
