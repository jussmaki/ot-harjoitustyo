
package memorygame.dao;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScoreDaoTest {
    
    private ScoreDao scoreDao;
    
    @Before
    public void setUp() throws IOException {
        //scoreDao = new FileScoreDao();
    }
    
    /*@Test
    public void scoreCanBeAdded() {
        Score s = new Score ("ddtesti", 2, 1, 2);
        scoreDao.create(s);
        assertTrue(scoreDao.getAll().contains(s));
    }*/
    
    /*@Test
    public void scoreCanBeRemoved() {
        Score s = new Score ("ddtesti", 2, 1, 2);
        assertTrue(scoreDao.getAll().contains(s));
        scoreDao.removeByName(s.getName());
        assertFalse(scoreDao.getAll().contains(s));
    }*/    
}
