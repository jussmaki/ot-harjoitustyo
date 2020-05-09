package memorygame.logics;

import java.util.List;
import memorygame.dao.Score;
import memorygame.dao.ScoreDao;

public class ScoreService {
    
    private ScoreDao scoreDao;
    
    public ScoreService(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }
    
   /**
    * Metodi tallentaa pelituloksen
    * @param name nimi
    * @param tries, käännettyjä kortteja löytämättä paria
    * @param time aika
    * @param gridSize pelilaudan koko
    */
    public void addNewScore(String name, int tries, int time, int gridSize) {
        scoreDao.create(new Score(name, tries, time, gridSize));
    }
    
    /**
    * Metodi palauttaa kaikki pelitulokset listana
    * 
    * @return pelitulokset
    */
    public List<Score> getAll() {
        return scoreDao.getAll();
    }
    
    public List<Score> getTopTenByTime(int totalPairs) {
        return scoreDao.getScoresByTotalPairsOrderByTime(totalPairs, 10);
    }
    
    public List<Score> getTopTenByTries(int totalPairs) {
        return scoreDao.getScoresByTotalPairsOrderByTries(totalPairs, 10);
    }    
}
