package memorygame.logics;

import java.io.File;
import java.io.IOException;
import java.util.List;
import memorygame.dao.Score;
import memorygame.dao.ScoreDao;
import memorygame.dao.SqlDbScoreDao;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ScoreServiceTest {
    private ScoreDao scoreDao;
    private ScoreService scoreService;
    
    @Before
    public void setUp() throws IOException {
        scoreDao = new SqlDbScoreDao("org.sqlite.JDBC", "test.db");
        scoreDao.removeAllScores();
        scoreService = new ScoreService(scoreDao);
    }
    
    @After
    public void removeTestDbFile() {
        File testDb = new File("test.db");
        testDb.delete();
    }    
    
    @Test
    public void atStartThereAreNoScoresInDb() {
        assertTrue (scoreDao.getAll().isEmpty());
    }
    
    @Test
    public void scoreCanBeAdded() {
        scoreDao.removeAllScores();
        Score s = new Score ("testi", 2, 2, 4);
        scoreService.addNewScore(s.getName(), s.getTries(), s.getTime(), s.getTotalPairs());
        assertTrue(scoreDao.getAll().contains(s));
    }
    
    @Test
    public void getAllWorks() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i*2, i^i, 32));
        }
        assertEquals(scoreService.getAll().size(), 100);
    }
    
    @Test
    public void getTopTenByTimeLimitWorks() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i*2, i^i, 32));
        }
        assertEquals(scoreService.getTopTenByTime(32).size(), 10);  
    }
    
    @Test
    public void getTopTenByTimeListIsInCorrectOrder() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i*2, i^i, 32));
        }
        List<Score> scores = scoreService.getTopTenByTime(32);
        for (int i=0; i<10; i++) {
            assertEquals(scores.get(i).getName(), "player"+i);
            assertEquals(scores.get(i).getTime(), i^i);
        }        
    }
    
    @Test
    public void getTopTenByTriesLimitWorks() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i*2, i^i, 32));
        }
        assertEquals(scoreService.getTopTenByTries(32).size(), 10);          
    }

    @Test
    public void getTopTenByTriesListIsInCorrectOrder() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i*2, i^i, 32));
        }
        List<Score> scores = scoreService.getTopTenByTries(32);
        for (int i=0; i<10; i++) {
            assertEquals(scores.get(i).getName(), "player"+i);
                assertEquals(scores.get(i).getTries(), i*2);
        }        
    }    
}
