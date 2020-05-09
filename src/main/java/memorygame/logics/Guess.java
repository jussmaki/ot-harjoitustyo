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
   /**
    * Metodi lisää käyttäjän valitseman kortin arvaukseksi
    * @param Card valittu kortti 
    * @return kortin valinta onnistui/ei onnistunut
    */
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

    /**
    * Metodi vertaa valittuja kortteja toisiinsa ja palauttaa tiedon siitä löytyikö pari
    * 
    * @return löytyikö pari vai ei
    */
    public boolean match() {
        if (this.cardsSelected == 2 && this.card1.getNumber() == this.card2.getNumber()) { //pair found
            this.card1.setFound(true);
            this.card2.setFound(true);
            this.cardsSelected = 0;
            return true;
        }
        this.card1.setFaceDown(true); //pair not found
        this.card2.setFaceDown(true);
        this.cardsSelected = 0;
        return false;
    }

    public int getCardsSelected() {
        return cardsSelected;
    }
}
