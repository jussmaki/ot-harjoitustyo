package memorygame.logics;
public class Card {
    private int id;
    private int number;
    private boolean faceDown;
    private boolean found;
    
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

    public boolean isFaceDown() {
        return faceDown;
    }

    public void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

}
