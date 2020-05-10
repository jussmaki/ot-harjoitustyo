package memorygame.logics;

/**
 * Pakan yksittäistä korttia kuvaava luokka.
 */
public class Card {
    private int id;
    private int number;
    private boolean faceDown;
    private boolean found;
    
    /**
     * Uuden kortin luominen.
     * @param id kortin id
     * @param number kortin numero, on sama kuin parin numero
     */
    public Card(int id, int number) {
        this.id = id;
        this.number = number;
        this.faceDown = true;
        this.found = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public int getNumber() {
        return number;
    }

    /**
    * Metodi palauttaa tiedon siitä onko kortti kuvapuoli alaspäin.
    * 
    * @return Kuvapuoli alaspäin true/false
    */
    public boolean isFaceDown() {
        return faceDown;
    }

    public void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
    }
    
    /**
    * Metodi palauttaa tiedon siitä onko kortille löytynyt pari.
    * 
    * @return Onko kortille löytynyt pari true/false
    */
    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }
}
