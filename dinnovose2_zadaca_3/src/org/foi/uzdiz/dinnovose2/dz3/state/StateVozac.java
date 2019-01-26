/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.dinnovose2.dz3.state;

import org.foi.uzdiz.dinnovose2.dz3.builder.Vozac;

/**
 *
 * @author Dino
 */
public interface StateVozac {
    
    //state vozaca
    public void doVozacNovi(Vozac v);
    public void doVozacBolovanje(Vozac v);
    public void doVozacOtkaz(Vozac v);
    public void doVozacGodisnji(Vozac v);
    public void doVozacVozi(Vozac v);
    public void doVozacPreuzmi(Vozac v, String idVozila);
              
    public void print();
    
}
