/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.dinnovose2.dz3.strategy;

import org.foi.uzdiz.dinnovose2.dz3.builder.Dispecer;

/**
 *
 * @author Dino
 */
public class DispecerCiklus implements DispecerStrategy {

    private String komanda;
    private String brojCiklusa;
    private String[] dijeloviKomande;

    @Override
    public void ispisiKomandu(Dispecer d) {
        dijeloviKomande = d.getKomanda().split("\\s+");
        komanda = dijeloviKomande[0];
        brojCiklusa = dijeloviKomande[1];
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        sb.append("Pristigla komanda: ").append(komanda).append(", broj ciklusa: ")
                .append(brojCiklusa).append(System.lineSeparator());
        System.out.println(sb);
    }

}
