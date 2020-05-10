package memorygame.service;

import java.util.List;
import memorygame.data.Score;
import memorygame.data.ScoreDao;

/**
 * Luokka pelitulosten tallentamiseen, hakemiseen ja poistamiseen.
 */
public class ScoreService {
    
    private ScoreDao scoreDao;
    
    /**
     * Konstruktorissa valitaan käytettävä ScoreDao-rajapinnan toteutus.
     * @param scoreDao Käytettävä ScoreDao
     */
    public ScoreService(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }
    
   /**
    * Metodi tallentaa pelituloksen.
    * @param name pelaajan nimi
    * @param tries käännettyjä kortteja löytämättä paria
    * @param time käytetty aika
    * @param totalPairs parien määrä
    */
    public void addNewScore(String name, int tries, int time, int totalPairs) {
        scoreDao.create(new Score(name, tries, time, totalPairs));
    }
    
    /**
    * Metodi palauttaa kaikki pelitulokset listana.
    * 
    * @return Pelitulokset listana Score-olioita
    */
    public List<Score> getAll() {
        return scoreDao.getAll();
    }
    
    /**
     * Metodi palauttaa kymmenen parasta pelitulosta annetulla parien määrällä ajan mukaan nousevassa järjestyksessä.
     * @param totalPairs parien määrä
     * @return Kymmenen parasta peritulosta annetulla parien määrällä ajan mukaan nousevassa järjestyksessä
     */
    public List<Score> getTopTenByTime(int totalPairs) {
        return scoreDao.getScoresByTotalPairsOrderByTime(totalPairs, 10);
    }

    /**
     * Metodi palauttaa kymmenen parasta pelitulosta annetulla parien määrällä yritysten mukaan nousevassa järjestyksessä.
     * @param totalPairs parien määrä
     * @return Kymmenen parasta peritulosta annetulla parien määrällä yritysten mukaan nousevassa järjestyksessä
     */    
    public List<Score> getTopTenByTries(int totalPairs) {
        return scoreDao.getScoresByTotalPairsOrderByTries(totalPairs, 10);
    }

    /**
     * Metodi poistaa kaikki tallennetut tulokset.
     */
    public void emptyToplists() {
        scoreDao.removeAllScores();
    }
}
