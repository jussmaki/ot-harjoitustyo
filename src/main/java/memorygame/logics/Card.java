package memorygame.logics;
public class Card {
    private Integer number;
    private boolean faceDown;

    public Card(Integer number) {
        this.number = number;
        this.faceDown = true;
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Card{" + "number=" + number + '}';
    }
    
}
