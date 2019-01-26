/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.dinnovose2.dz3.state;

import org.foi.uzdiz.dinnovose2.dz3.builder.Vozilo;

/**
 *
 * @author Dino
 */
public interface StateVozilo {
    
    //state vozila
    public void doVoziloPripremi(Vozilo v);
    public void doVoziloKvar(Vozilo v);
    public void doVoziloIsprazni(Vozilo v);
    public void doVoziloKontrola(Vozilo v);
    public void doVoziloPunjenje(Vozilo v);
    public void doVoziloParkiraj(Vozilo v);
    
    public void print();
}
