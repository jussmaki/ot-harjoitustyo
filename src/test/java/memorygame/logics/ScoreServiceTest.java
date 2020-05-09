package memorygame.logics;

import memorygame.service.ScoreService;
import java.util.List;
import memorygame.dao.Score;
import memorygame.dao.ScoreDao;
import memorygame.dao.SqlDbScoreDao;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.sqlite.SQLiteConfig;

public class ScoreServiceTest {
    private ScoreDao scoreDao;
    private String dbDriver;
    private String dbFile;
    private ScoreService scoreService;
    
    @Before
    public void setUp() {
        dbDriver = "org.sqlite.JDBC";
        dbFile = ":memory:"; //using in-memory database for testing
        scoreDao = new SqlDbScoreDao(dbDriver, dbFile);
        scoreService = new ScoreService(scoreDao);
    }
    
    @Test
    public void atStartThereAreNoScoresInDb() {
        assertTrue(scoreDao.getAll().isEmpty());
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
        assertEquals(100, scoreService.getAll().size());
    }
    
    @Test
    public void getTopTenByTimeLimitWorks() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i*2, i^i, 32));
        }
        assertEquals(10, scoreService.getTopTenByTime(32).size());  
    }
    
    @Test
    public void getTopTenByTimeListIsInCorrectOrder() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i*2, i^i, 32));
        }
        List<Score> scores = scoreService.getTopTenByTime(32);
        for (int i=0; i<10; i++) {
            assertEquals("player"+i, scores.get(i).getName());
            assertEquals(i^i, scores.get(i).getTime());
        }        
    }
    
    @Test
    public void getTopTenByTriesLimitWorks() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i*2, i^i, 32));
        }
        assertEquals(10, scoreService.getTopTenByTries(32).size());          
    }

    @Test
    public void getTopTenByTriesListIsInCorrectOrder() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i*2, i^i, 32));
        }
        List<Score> scores = scoreService.getTopTenByTries(32);
        for (int i=0; i<10; i++) {
            assertEquals("player"+i, scores.get(i).getName());
            assertEquals(i*2, scores.get(i).getTries());
        }        
    }
    
    @Test
    public void emptyToplistsWorks() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i*2, i^i, i*i));
        }
        scoreService.emptyToplists();
        assertEquals(0, scoreService.getAll().size());          
    }    
}
