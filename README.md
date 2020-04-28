# Ohjelmistotekniikka, harjoitustyö

## Sovellus

Harjoitustyön aiheeksi olen valinnut muistipelin

## Komentorivitoiminnot

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

[Viikko 6](https://github.com/jussmaki/ot-harjoitustyo/releases/tag/Week6)

[Viikko 5](https://github.com/jussmaki/ot-harjoitustyo/releases/tag/Week5)

## Dokumentaatio

[Arkkitehtuuri](dokumentointi/arkkitehtuuri.md)

[Vaatimustenmäärittely](dokumentointi/vaatimusmaarittely.md)

[Työaikakirjanpito](dokumentointi/tuntikirjanpito.md)
