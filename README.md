# Ohjelmistotekniikka, harjoitustyö

## Sovellus

Ohjelmistotekniikan harjoitustyön aiheeksi valitsin muistipelin

## Komentorivitoiminnot

### Repositorion kloonaus ja siirtyminen repositorioon

```
git clone https://github.com/jussmaki/ot-harjoitustyo
cd ot-harjoitustyo
```

### Sovelluksen käynnistys

```
mvn compile exec:java -Dexec.mainClass=memorygame.ui.MemorygameUI
```

### Sovelluksen testien ajaminen

```
mvn test
```

### Testikattavuusraportin luominen

```
mvn jacoco:report
```

### Checkstyleraportin luominen

```
mvn jxr:jxr checkstyle:checkstyle
```

### Jar tiedoston luominen ja suorittaminen

```
mvn package
java -jar target/Memorygame-1.0-SNAPSHOT.jar
```

### Javadocsin generointi

```
mvn javadoc:javadoc
```

## Releaset

[Loppupalautus](https://github.com/jussmaki/ot-harjoitustyo/releases/tag/final)

[Viikko 6](https://github.com/jussmaki/ot-harjoitustyo/releases/tag/Week6)

[Viikko 5](https://github.com/jussmaki/ot-harjoitustyo/releases/tag/Week5)

## Dokumentaatio

[Arkkitehtuuri](dokumentointi/arkkitehtuuri.md)

[Käyttöohje](dokumentointi/kayttoohje.md)

[Testausdokumentti](dokumentointi/testaus.md)

[Työaikakirjanpito](dokumentointi/tuntikirjanpito.md)

[Vaatimustenmäärittely](dokumentointi/vaatimusmaarittely.md)
