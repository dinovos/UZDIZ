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
public class DispecerLista implements DispecerStrategy{

    @Override
    public void ispisiKomandu(Dispecer d) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        sb.append("Pristigla komanda: ").append(d.getKomanda()).append(System.lineSeparator())
                .append("Lista 1: ").append(d.getPrvaLista()).append(System.lineSeparator()).append("Lista 2: ")
                .append(d.getDrugaLista()).append(System.lineSeparator());
        System.out.println(sb);
    }

}
