package memorygame.logics;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck(int size) {
        this.cards = new ArrayList<>();
        int id = 0;
        for (int i = 0; i < size * size / 2; i++) {
            this.cards.add(new Card(id, i));
            id++;
            this.cards.add(new Card(id, i));
            id++;
        }
        Collections.shuffle(cards);
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    } 
    
}
