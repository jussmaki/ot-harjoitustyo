package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikeinSaldoMetodi() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void kortinSaldoAlussaOikeinToString() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(1000);
        assertEquals("saldo: 20.0", kortti.toString());
    }

    @Test
    public void saldoVaheneeOikeinJosRahaaOnTarpeeksi() {
        kortti.otaRahaa(100);
        assertEquals("saldo: 9.0", kortti.toString());
    }
 
    @Test
    public void saldoEiMuutuJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(11000);
        assertEquals("saldo: 10.0", kortti.toString());
    }    

    @Test
    public void otaRahaaPalauttaaTrueJosRahatRiittivat() {
        assertTrue(kortti.otaRahaa(100));
    }    

    @Test
    public void otaRahaaPalauttaaFalseJosRahatEivatRiittaneet() {
        assertFalse(kortti.otaRahaa(1100));
    }   
}
