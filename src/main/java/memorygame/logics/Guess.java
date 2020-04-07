
package memorygame.logics;

public class Guess {
    private Card card1;
    private Card card2;
    private int cardsSelected;

    public Guess() {
        this.card1 = null;
        this.card2 = null;
        this.cardsSelected = 0;
    }
    
    public boolean addGuess(Card card) {
        if (card.isFound() || !card.isFaceDown()) {
            return false;
        }
        if (this.cardsSelected == 0) {
            this.card1 = card;
            this.card1.setFaceDown(false);
            this.cardsSelected++;
            return true;
        } else if (this.cardsSelected == 1) {
            if (card.equals(this.card1)) {
                return false;
            }
            this.card2 = card;
            this.card2.setFaceDown(false);
            this.cardsSelected++;
            return true;
        }
        return false;
    }
    
    public boolean match() {
        if (this.cardsSelected == 2 && this.card1.getNumber() == this.card2.getNumber()) {
            this.card1.setFound(true);
            this.card2.setFound(true);
            return true;
        }
        this.card1.setFaceDown(true);
        this.card2.setFaceDown(true);
        return false;
    }

    public int getCardsSelected() {
        return cardsSelected;
    }
}
