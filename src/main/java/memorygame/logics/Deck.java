package memorygame.logics;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck(int size) {
        this.cards = new ArrayList<>();
        int id = 0;
        for (int i = 0; i < size; i++) {
            this.cards.add(new Card(id, i + 1));
            id++;
            this.cards.add(new Card(id, i + 1));
            id++;
        }
        Collections.shuffle(cards);
    }
    /**
    * Metodi palauttaa kaikki pakan kortit
    * 
    * @return kaikki kortit
    */
    public ArrayList<Card> getCards() {
        return this.cards;
    } 
    
}
