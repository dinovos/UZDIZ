/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.dinnovose2.dz1.racunanje;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 *
 * @author Dino
 */
public class Generator {

    Random random = new Random();

    public Generator(int sjeme) {
        random.setSeed(sjeme);
    }

    public int dajSlucajniInt(int gornja, int donja) {
        int slucajniInt = random.nextInt((gornja - donja) + 1) + donja;
        return slucajniInt;
    }

    public float dajSlucajniFloat(int gornja, int donja, int brDecimala) {
        float slucajniFloat = random.nextFloat() * (gornja - donja) + donja;
        String fl = new BigDecimal(String.valueOf(slucajniFloat)).setScale(brDecimala, RoundingMode.HALF_UP).toString();
        return Float.parseFloat(fl);
    }

    public long dajSlucajniLong(int gornja, int donja) {
        long slucajniLong = donja + ((long) (random.nextDouble() * (gornja - donja)));
        return slucajniLong;
    }

}
