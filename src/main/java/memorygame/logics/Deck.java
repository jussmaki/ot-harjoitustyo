package memorygame.logics;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck(int size) {
        this.cards = new ArrayList<>();
        for (int i = 0; i < size * size / 2; i++) {
            this.cards.add(new Card(i));
            this.cards.add(new Card(i));            
        }
        Collections.shuffle(cards);
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    } 

    @Override
    public String toString() {
        return "Deck{" + "cards=" + this.cards + '}';
    }
    
}
