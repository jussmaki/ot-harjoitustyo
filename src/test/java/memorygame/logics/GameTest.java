package memorygame.logics;

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
    public void pairsFoundCounterIsZeroAtStart() {
        Game game = new Game(4);
        assertEquals(game.getPairsFound(), 0);
    }
    
    @Test
    public void guessingPairIncreasesTriesCounterIfPairNotFound() {
        Game game = new Game(2);
        Card card1 = new Card(0,1); //pair of card2
        Card card2 = new Card(1,1); //pair of card1
        Card card3 = new Card(2,2); //pair of card3
        Card card4 = new Card(3,2); //pair of card4       
        game.setGrid(new Card[][] { {card1, card2},
                                    {card3, card4}} );
        game.handleAction(0, 0); //selecting card1
        game.handleAction(1, 1); //selecting card4
        assertEquals(game.getTries(), 1);
    }
    
    @Test
    public void guessingPairIncreasesTriesCounterIfPairFound() {
        Game game = new Game(2);
        Card card1 = new Card(0,1); //pair of card2
        Card card2 = new Card(1,1); //pair of card1
        Card card3 = new Card(2,2); //pair of card3
        Card card4 = new Card(3,2); //pair of card4       
        game.setGrid(new Card[][] { {card1, card2},
                                    {card3, card4}} );
        game.handleAction(1, 0); //selecting card3
        game.handleAction(1, 1); //selecting card4
        assertEquals(game.getTries(), 1);
    }    
        
    
    @Test
    public void foundingPairIncreasesPairFoundCounter() {
        Game game = new Game(2);
        Card card1 = new Card(0,1); //pair of card2
        Card card2 = new Card(1,1); //pair of card1
        Card card3 = new Card(2,2); //pair of card3
        Card card4 = new Card(3,2); //pair of card4       
        game.setGrid(new Card[][] { {card1, card2},
                                    {card3, card4}} );
        game.handleAction(0, 0); //selecting card1
        game.handleAction(0, 1); //selecting card2
        assertEquals(game.getPairsFound(), 1);
    }
    
    @Test
    public void notFoundingPairDoesNotIncreasePairFoundCounter() {
        Game game = new Game(2);
        Card card1 = new Card(0,1); //pair of card2
        Card card2 = new Card(1,1); //pair of card1
        Card card3 = new Card(2,2); //pair of card3
        Card card4 = new Card(3,2); //pair of card4       
        game.setGrid(new Card[][] { {card1, card2},
                                    {card3, card4}} );
        game.handleAction(1, 0); //selecting card3
        game.handleAction(0, 1); //selecting card2
        assertEquals(game.getPairsFound(), 0);
    }
    
    @Test
    public void getGridSizeReturnsCorrectGridSize() {
        Game game = new Game(4);
        assertEquals(game.getGridSize(), 4);
    }
        
    @Test
    public void getPairsTotalReturnsCorrectCount() {
        Game game = new Game(4);
        assertEquals(game.getPairsTotal(), 8);
    }
    
    @Test
    public void gameInProgressReturnsTrueWhenThereAreNoPairsFound() {
        Game game = new Game(2);
        Card card1 = new Card(0,1); //pair of card2
        Card card2 = new Card(1,1); //pair of card1
        Card card3 = new Card(2,2); //pair of card3
        Card card4 = new Card(3,2); //pair of card4       
        game.setGrid(new Card[][] { {card1, card2},
                                    {card3, card4}} );
        assertTrue(game.gameInProgress());
    }
    
    @Test
    public void gameInProgressReturnsTrueWhenThereAreFewPairsFound() {
        Game game = new Game(2);
        Card card1 = new Card(0,1); //pair of card2
        Card card2 = new Card(1,1); //pair of card1
        Card card3 = new Card(2,2); //pair of card3
        Card card4 = new Card(3,2); //pair of card4       
        game.setGrid(new Card[][] { {card1, card2},
                                    {card3, card4}} );
        game.handleAction(1, 0); //selecting card3
        game.handleAction(1, 1); //selecting card4
        assertTrue(game.gameInProgress());
    }
    
    @Test
    public void gameInProgressReturnsFalseWhenAllPairsAreFound() {
        Game game = new Game(2);
        Card card1 = new Card(0,1); //pair of card2
        Card card2 = new Card(1,1); //pair of card1
        Card card3 = new Card(2,2); //pair of card3
        Card card4 = new Card(3,2); //pair of card4       
        game.setGrid(new Card[][] { {card1, card2},
                                    {card3, card4}} );
        game.handleAction(1, 0); //selecting card3
        game.handleAction(1, 1); //selecting card4
        game.handleAction(0, 0); //selecting card1
        game.handleAction(0, 1); //selecting card2
        assertFalse(game.gameInProgress());        
    }
    
    @Test
    public void timerForPlayTimeIsZeroAtStart() {
        Game game = new Game(4);
        assertEquals(game.getPlayTime(), 0);
    }
    
}
