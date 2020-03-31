import memorygame.logics.Card;
import memorygame.logics.Game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class GameTest {

    @Test
    public void totalPairIsCountedCorrectlyAtStart() {
        Game game = new Game(4);
        assertEquals(game.getPairsTotal(), 4*4/2);
    }
    
    @Test
    public void triesCounterIsZeroAtStart() {
        Game game = new Game(4);
        assertEquals(game.getTries(), 0);
    }
    
    @Test
    public void checkingPairIncreasesTriesCounter() {
        Game game = new Game(4);
        game.checkIfPair(new Card(1), new Card(2));
        assertEquals(game.getTries(), 1);
    }
    
    
    @Test
    public void pairsFoundCounterIsZeroAtStart() {
        Game game = new Game(4);
        assertEquals(game.getPairsFound(), 0);
    }
    
    @Test
    public void foundingPairIncreasesPairFoundCounter() {
        Game game = new Game(4);
        game.checkIfPair(new Card(1), new Card(1));
        assertEquals(game.getPairsFound(), 1);
    }
    
    @Test
    public void notFoundingPairDoesNotIncreasePairFoundCounter() {
        Game game = new Game(4);
        game.checkIfPair(new Card(1), new Card(2));
        assertEquals(game.getPairsFound(), 0);
    }
}
