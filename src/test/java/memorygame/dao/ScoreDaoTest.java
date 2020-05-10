package memorygame.dao;

import memorygame.data.SqlDbScoreDao;
import memorygame.data.Score;
import memorygame.data.ScoreDao;
import java.util.List;
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
    public void allScoresCanBeRemoved() {
        scoreDao.removeAllScores();
        assertEquals(0, scoreDao.getAll().size());
        for (int i=0; i<1000; i++) {
            scoreDao.create(new Score("player"+i, i, i, i));
        }
        assertEquals(1000, scoreDao.getAll().size());
        scoreDao.removeAllScores();
        assertEquals(0, scoreDao.getAll().size());
    }
    
    @Test
    public void oneScoreCanBeRemoved() {
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
        assertEquals(10, scoreDao.getAll(10).size());
    }
    @Test
    public void correctCountOfScoresAreReturnedWhenThereAreGivenLimitTest2() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i, i, i));
        }
        assertEquals(20, scoreDao.getAll(20).size());
    }
    
    @Test
    public void getScoresByTotalPairsOrderByTimeLimitWorksTest() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i, i, 64));
        }
        List<Score> scores = scoreDao.getScoresByTotalPairsOrderByTime(64, 10);
        assertEquals(10, scores.size());
    }
    
    @Test
    public void getScoresByTotalPairsOrderByTimeCorrectOrderTest() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i, i*2, 16));
        }
        List<Score> scores = scoreDao.getScoresByTotalPairsOrderByTime(16, 10);
        for (int i=0; i<10; i++) {
            assertEquals(i*2, scores.get(i).getTime());
            assertEquals("player"+i, scores.get(i).getName());
        }
    }
    
    @Test
    public void getScoresByTotalPairsOrderByTriesLimitWorksTest() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i, i, 32));
        }
        List<Score> scores = scoreDao.getScoresByTotalPairsOrderByTries(32, 10);
        assertEquals(10, scores.size());
    }
    
    @Test
    public void getScoresByTotalPairsOrderByTriesCorrectOrderTest() {
        scoreDao.removeAllScores();
        for (int i=0; i<100; i++) {
            scoreDao.create(new Score("player"+i, i^i, i, 16));
        }
        List<Score> scores = scoreDao.getScoresByTotalPairsOrderByTries(16, 10);
        for (int i=0; i<10; i++) {
            assertEquals(i^i, scores.get(i).getTries());
            assertEquals("player"+i, scores.get(i).getName());
        }
    }    
}
