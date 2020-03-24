package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate kassa;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(0);
    }

    @Test
    public void luotuKassapaateOlemassa() {
        assertTrue(kassa!=null);      
    }
    
    @Test
    public void luotuMaksukorttiOnOlemassa() {
        assertTrue(kortti!=null);
    }
    
    @Test
    public void luodunKassapaatteenRahaamaaraOikein () {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void luodunKortinSaldoAlussaOikein() {
        assertEquals(0, kortti.saldo());
    }
    
    @Test
    public void luodunKassapaatteenMyydytEdullisetLounaatOnNolla () {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }    

    @Test
    public void luodunKassapaatteenMyydytMaukkaatLounaatOnNolla () {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiEdullisetLounaat() {
        kassa.syoEdullisesti(240);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void kateisostoToimiiMaukkaat() {
        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kassassaOlevaRahamaaraKasvaaOikeinEdulliset() {
        kassa.syoEdullisesti(300);
        assertEquals(100240, kassa.kassassaRahaa());
    }

    @Test
    public void kateisostonVaihtorahanSuurusOikeinEdulliset() {
        assertEquals(60, kassa.syoEdullisesti(300));
    }
    
    @Test
    public void kunAnnetaanLiianVahanRahaaLounastaEiMyydaEdulliset() {
        kassa.syoEdullisesti(100);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kunAnnetaanLiianVahanRahaaPalautetaanKaikkiRahatEdulliset() {
        assertEquals(100, kassa.syoEdullisesti(100));
    }

    @Test
    public void kassassaOlevaRahamaaraKasvaaOikeinMaukkaat() {
        kassa.syoMaukkaasti(500);
        assertEquals(100400, kassa.kassassaRahaa());
    }

    @Test
    public void kateisostonVaihtorahanSuurusOikeinMaukkaat() {
        assertEquals(100, kassa.syoMaukkaasti(500));
    }
    
    @Test
    public void kunAnnetaanLiianVahanRahaaLounastaEiMyydaMaukkaat() {
        kassa.syoMaukkaasti(100);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kunAnnetaanLiianVahanRahaaPalautetaanKaikkiRahatMaukkaat() {
        assertEquals(100, kassa.syoMaukkaasti(100));
    }

    public void korttiostoToimiiEdulliset() {
        kortti.lataaRahaa(1000);
        assertTrue(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void kortiltaVeloitetaanOikeaSummaEdulliset() {
        kortti.lataaRahaa(1000);
        kassa.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
    }
    
    @Test
    public void korttiostoKasvattaaMyytyjenLounaidenMaaraaEdulliset() {
        kortti.lataaRahaa(1000);
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void josKortinSaldoEiRiitaKassapaatePalauttaaFalseEdulliset() {
        kortti.lataaRahaa(100);
        assertFalse(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void josKortinSaldoEiRiitaKortinSaldoEimuutuEdulliset() {
        kortti.lataaRahaa(100);
        kassa.syoEdullisesti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void josKortinSaldoEiRiitaLounastaMyydaEdulliset() {
        kortti.lataaRahaa(100);
        kassa.syoEdullisesti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuKortillaOstettaessaEdulliset() {
        kortti.lataaRahaa(1000);
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    public void korttiostoToimiiMaukkaat() {
        kortti.lataaRahaa(1000);
        assertTrue(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void kortiltaVeloitetaanOikeaSummaMaukkaat() {
        kortti.lataaRahaa(1000);
        kassa.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
    }
    
    @Test
    public void korttiostoKasvattaaMyytyjenLounaidenMaaraaMaukkaat() {
        kortti.lataaRahaa(1000);
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void josKortinSaldoEiRiitaKassapaatePalauttaaFalseMaukkaat() {
        kortti.lataaRahaa(100);
        assertFalse(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void josKortinSaldoEiRiitaKortinSaldoEimuutuMaukkaat() {
        kortti.lataaRahaa(100);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void josKortinSaldoEiRiitaLounastaMyydaMaukkaat() {
        kortti.lataaRahaa(100);
        kassa.syoMaukkaasti(kortti);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuKortillaOstettaessaMaukkaat() {
        kortti.lataaRahaa(1000);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void kortilleRahaaLadattaessaKortinSaldoMuuttuuOikein() {
        kassa.lataaRahaaKortille(kortti, 1000);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void kortilleRahaaLadattaessaKassanRahamaaraKasvaaLadatullaSummalla() {
        kassa.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortilleEiVoiLadataNegatiivistaSummaa() {
        kassa.lataaRahaaKortille(kortti, -1000);
        assertEquals(0, kortti.saldo());
    }
        
}