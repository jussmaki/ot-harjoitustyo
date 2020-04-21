
package memorygame.logics;

import memorygame.dao.ScoreDao;

public class ScoreService {
    
    private ScoreDao scoreDao;
    //private otherDao otherDao;
    
    public ScoreService(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
        //this.otherDao = otherDao;
    }
    
    public boolean addNewScore(String name, int tries, int time, int gridSize) {
        return false;
    }    
}
