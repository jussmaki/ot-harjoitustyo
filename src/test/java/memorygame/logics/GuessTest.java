package memorygame.logics;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GuessTest {
    
    private Guess guess;
    
    public GuessTest() {
    }
    
    @Before
    public void setUp() {
        guess = new Guess();
    }
    
    @Test
    public void addingCardToGuessReturnsTrue() {
        Card c = new Card(1, 2);
        c.setFound(false);
        c.setFaceDown(true);
        assertTrue(guess.addGuess(c));
    }

    @Test
    public void addingFoundCardToGuessReturnsFalse() {
        Card c = new Card(1, 2);
        c.setFound(true);
        assertFalse(guess.addGuess(c));
    }
    
    @Test
    public void addingFaceUpCardToGuessReturnsFalse() {
        Card c = new Card(1, 2);
        c.setFaceDown(false);
        assertFalse(guess.addGuess(c));
    }
    
    @Test
    public void addingSameCardTwiceToGuessReturnsFalse() {
        Card c = new Card(1, 2);
        guess.addGuess(c);
        assertFalse(guess.addGuess(c));
    }
    
    @Test
    public void matchingPairReturnsTrue() {
        Card c1 = new Card (1, 3);
        Card c2 = new Card (2, 3);
        guess.addGuess(c1);
        guess.addGuess(c2);
        assertTrue(guess.match());
    }
    
    @Test
    public void matchingNonPairReturnsFalse() {
        Card c1 = new Card (1, 3);
        Card c2 = new Card (2, 4);
        guess.addGuess(c1);
        guess.addGuess(c2);
        assertFalse(guess.match());
    }

    @Test
    public void selectedCardsCounterIsZeroAtStart() {
        assertEquals(0, guess.getCardsSelected());
    }
         
    @Test
    public void selectedCardsCounterIsOneAfterFirstCardSelected() {
        guess.addGuess(new Card (1, 2));
        assertEquals(1, guess.getCardsSelected());
    }    @Test
   
    public void selectedCardsCounterIsTwoAfterSecondCardSelected() {
        guess.addGuess(new Card (1, 2));
        guess.addGuess(new Card(3, 4));
        assertEquals(2, guess.getCardsSelected());
    }
   
    public void selectedCardsCounterIsZeroAfterNoMatch() {
        guess.addGuess(new Card (1, 2));
        guess.addGuess(new Card(3, 4));
        guess.match();
        assertEquals(0, guess.getCardsSelected());
    }
    
    public void selectedCardsCounterIsZeroAfterMatch() {
        guess.addGuess(new Card (1, 3));
        guess.addGuess(new Card(2, 3));
        guess.match();
        assertEquals(0, guess.getCardsSelected());
    }
}
