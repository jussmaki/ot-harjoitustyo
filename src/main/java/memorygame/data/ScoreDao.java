package memorygame.data;

import java.util.List;

/**
 * Rajapinta pelitulosten käsittelyyn.
 */
public interface ScoreDao {

    /**
     * Pelituloksen tallennus.
     * @param score tallennettava pelitulos Score-oliona
     */
    void create(Score score);

    /**
     * Hakee kaikki tallennetut tulokset.
     * @return Lista Score-olioina kaikista pelituloksista
     */
    List<Score> getAll();

    /**
     * Hakee annetun määrän tallennettuja pelituloksia.
     * @param limit kuinka monta pelitulosta maksimissaan haetaan 
     * @return Lista Score-olioita pelituloksista nousevassa järjestyksessä
     */
    List<Score> getAll(int limit);

    /**
     * Hakee annetun määrän tallennettuja pelituloksia annetulla parien määrällä käytetyn ajan mukaisessa nousevassa järjestyksessä.
     * @param totalPairs parien määrä
     * @param limit kuinka monta pelitulosta maksimissaan haetaan
     * @return Lista Score-olioita pelituloksista peliin käytetyn ajan mukaan nousevassa järjestyksessä
     */
    List<Score> getScoresByTotalPairsOrderByTime(int totalPairs, int limit);

    /**
     * Hakee annetun määrän tallennettuja pelituloksia annetulla parien määrällä yritysten määrän mukaisessa nousevassa järjestyksessä.
     * @param totalPairs parien määrä
     * @param limit kuinka monta pelitulosta maksimissaan haetaan
     * @return Lista Score-olioita pelituloksista yritysten määrän mukaaan nousevassa järjestyksessä
     */
    List<Score> getScoresByTotalPairsOrderByTries(int totalPairs, int limit);

    /**
     * Poistaa pelaajan kaikki tallennetut tulokset.
     * @param name pelaajan nimi
     */
    void removeByName(String name);

    /**
     * Poistaa kaikki tallennetut tulokset.
     */
    void removeAllScores();
}
