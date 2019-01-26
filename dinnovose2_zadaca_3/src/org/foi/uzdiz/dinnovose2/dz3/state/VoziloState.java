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
public class VoziloState implements StateVozilo {

    /*
    0 - vozilo PARKIRANO
    1 - vozilo PRIPREMLJENO
    2 - vozilo KVAR
    3 - vozilo PRAZNJENJE
    4 - vozilo KONTROLA
    5 - vozilo NA PUNJENJE
     */
    
    
    @Override
    public void doVoziloParkiraj(Vozilo v) {
        v.setStatus(0);
    }
    
    @Override
    public void doVoziloPripremi(Vozilo v) {
        v.setStatus(1);
    }

    @Override
    public void doVoziloKvar(Vozilo v) {
        v.setStatus(2);
    }

    @Override
    public void doVoziloIsprazni(Vozilo v) {
        v.setStatus(3);
    }

    @Override
    public void doVoziloKontrola(Vozilo v) {
        v.setStatus(4);
    }

    @Override
    public void doVoziloPunjenje(Vozilo v) {
        v.setStatus(5);
    }
    
    

    @Override
    public void print() {
        System.out.println("Promjena statusa vozila!");
    }

}
