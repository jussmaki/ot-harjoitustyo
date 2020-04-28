
package memorygame.logics;

import java.util.List;
import memorygame.dao.Score;
import memorygame.dao.ScoreDao;

public class ScoreService {
    
    private ScoreDao scoreDao;
    //private otherDao otherDao;
    
    public ScoreService(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
        //this.otherDao = otherDao;
    }
    
    public void addNewScore(String name, int tries, int time, int gridSize) {
        scoreDao.create(new Score(name, tries, time, gridSize));
    }
    
    public List<Score> getAll() {
        return scoreDao.getAll();
    }
}
