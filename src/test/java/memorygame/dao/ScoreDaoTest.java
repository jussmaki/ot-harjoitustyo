package memorygame.dao;

import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScoreDaoTest {
    
    private ScoreDao scoreDao;
    private String dbDriver;
    private String dbFile;
    
    @Before
    public void setUp() {
        dbDriver = "org.sqlite.JDBC";
        dbFile = ":memory:"; //using in-memory database for testing
        scoreDao = new SqlDbScoreDao(dbDriver, dbFile);
    }
    
    @Test
    public void atStartThereAreNoScoresInDb() {
        assertTrue (scoreDao.getAll().isEmpty());
    }
    
    @Test
    public void scoreCanBeAdded() {
        scoreDao.removeAllScores();
        Score s = new Score ("ddtesti", 2, 1, 2);
        scoreDao.create(s);
        assertTrue(scoreDao.getAll().contains(s));
    }
    
    @Test
    public void scoreCanBeRemoved() {
        scoreDao.removeAllScores();        
        Score s = new Score ("testi123", 2, 1, 2);
        scoreDao.create(s);
        assertTrue(scoreDao.getAll().contains(s));
        scoreDao.removeByName(s.getName());
        assertFalse(scoreDao.getAll().contains(s));
    }

    @Test
    public void correctCountOfScoresAreReturnedWhenThereAreGivenLimitTest1() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i, i, i));
        }
        assertEquals(scoreDao.getAll(10).size(), 10);
    }
    @Test
    public void correctCountOfScoresAreReturnedWhenThereAreGivenLimitTest2() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i, i, i));
        }
        assertEquals(scoreDao.getAll(20).size(), 20);
    }
    
    @Test
    public void getScoresByTotalPairsOrderByTimeLimitWorksTest() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i, i, 64));
        }
        List<Score> scores = scoreDao.getScoresByTotalPairsOrderByTime(64, 10);
        assertEquals(scores.size(), 10);
    }
    
    @Test
    public void getScoresByTotalPairsOrderByTimeCorrectOrderTest() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i, i*2, 16));
        }
        List<Score> scores = scoreDao.getScoresByTotalPairsOrderByTime(16, 10);
        for (int i=0; i<10; i++) {
            assertEquals(scores.get(i).getTime(), i*2);
            assertEquals(scores.get(i).getName(), "player"+i);
        }
    }
    
    @Test
    public void getScoresByTotalPairsOrderByTriesLimitWorksTest() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i, i, 32));
        }
        List<Score> scores = scoreDao.getScoresByTotalPairsOrderByTries(32, 10);
        assertEquals(scores.size(), 10);
    }
    
    @Test
    public void getScoresByTotalPairsOrderByTriesCorrectOrderTest() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i^i, i, 16));
        }
        List<Score> scores = scoreDao.getScoresByTotalPairsOrderByTries(16, 10);
        for (int i=0; i<10; i++) {
            assertEquals(scores.get(i).getTries(), i^i);
            assertEquals(scores.get(i).getName(), "player"+i);
        }
    }    
}
