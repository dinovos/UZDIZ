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
public class VozacState implements StateVozac {

    /*
    0 - vozac VOZI
    1 - vozac BOLOVANJE
    2 - vozac OTKAZ
    3 - vozac GODISNJI
    4 - vozac PREUZMI VOZILO
    5 - vozac NOVI
     */
    @Override
    public void doVozacVozi(Vozac v) {
        v.setStatus(0);
    }

    @Override
    public void doVozacBolovanje(Vozac v) {
        v.setStatus(1);
    }

    @Override
    public void doVozacOtkaz(Vozac v) {
        v.setStatus(2);
    }

    @Override
    public void doVozacGodisnji(Vozac v) {
        v.setStatus(3);
    }

    @Override
    public void doVozacPreuzmi(Vozac v, String idVozila) {
        v.setStatus(4);
        v.setVozilo(idVozila);
    }

    @Override
    public void doVozacNovi(Vozac v) {
        v.setStatus(5);
    }

    @Override
    public void print() {
        System.out.println("Promjena statusa vozaca!");
    }
}
