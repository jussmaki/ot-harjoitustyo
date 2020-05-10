# Testausdokumentti

## Sovelluksen testaamisesta

Sovellus on testattu monipuolisesti JUnit-kirjaston avulla laadituilla yksikkötesteillä ja kehitystyön aikana manuaalisesti.

### Memorygame.data

*Score*, *ScoreDao*, *SqlScoreDao*, ja *DbConnection*-luokkien testaamisesta vastaa *ScoreDaoTest* -luokka, joka testatessaan *ScoreDao*-rajapinnan toimintaa testaa samalla em. luokkien toimintaa.

### Memorygame.service

*ScoreService*:n, palvelukerroksen testaus ja yhteistoiminta *ScoreDao*:n ja muiden tietojen tallentamisesta vastaavien luokkien kanssa on testattu *ScoreServiceTest* luokassa.

### Memorygame.logics

Sovelluslogiikan testit on toteutettu kahdella eri testiluokalla: *GuessTest* ja *GameTest*. *GuessTest* keskittyy puhtaasti *Guess*-luokan testaamiseen. *GameTest* luokkaa testaa *Game* -luokan lisäksi muiden pakkauksen luokkien yhteistoimintaa *Game*-luokan kanssa.


### Järjestelmätestaus ja Memorygame.UI

Käyttöliittymästä vastaava luokka *MemorygameUI*, käyttöliittymän yhteistoiminta sovelluslogiikan kanssa ja sovellus kokonaisuudessaan on testattu kattavasti manuaalisesti pelin kehityksen aikana. Kaikkia määrittelydokumentissa määriteltyjä toiminnallisuuksia on testattu ja sovellusta on 'yritetty rikkoa' virheellisillä syötteillä.
 
## Testikattavuus

![Testikattavuus](testikattavuus.png)

Sovelluksen automatisoitujen testien rivikattavuus on 77 prosenttia ja haaraumakattavuus 70 prosenttia.

## Tiedossa olevat virheet sovelluksen toiminnassa

- Pelin jälkeen uusi tallennettu tulos ei välttämättä näy toplistassa heti.
