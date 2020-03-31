package memorygame.logics;

public class Game {
    private Deck deck;
    private Card[][] grid;
    private int tries;
    private int pairsFound;
    private int pairsTotal;
    private long startTime;
    private long stopTime;
    private Card firstCardSelected;
    private int firstCardSelectedPosX;
    private int firstCardSelectedPosY;
    
    public Game(int size) {

        //initializing counters
        this.tries = 0;
        this.pairsFound = 0;
        this.pairsTotal = size * size / 2;
        this.startTime = System.nanoTime();
        //creating and shuffling new deck
        this.deck = new Deck(size);
        //placing cards in board
        this.grid = new Card[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.grid[x][y] = this.deck.getCards().get(0);
                this.deck.getCards().remove(0);
                //System.out.println(this.grid[x][y]);
            }
        }
    }

    public int getFirstCardSelectedPosX() {
        return firstCardSelectedPosX;
    }

    public void setFirstCardSelectedPosX(int firstCardSelectedPosX) {
        this.firstCardSelectedPosX = firstCardSelectedPosX;
    }

    public int getFirstCardSelectedPosY() {
        return firstCardSelectedPosY;
    }

    public void setFirstCardSelectedPosY(int firstCardSelectedPosY) {
        this.firstCardSelectedPosY = firstCardSelectedPosY;
    }

    public Card getFirstCardSelected() {
        return firstCardSelected;
    }

    public void setFirstCardSelected(Card firstCardSelected) {
        this.firstCardSelected = firstCardSelected;
    }

    public int getTries() {
        return tries;
    }

    public int getPairsFound() {
        return pairsFound;
    }

    public int getPairsTotal() {
        return pairsTotal;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Card[][] getGrid() {
        return grid;
    }

    public void setGrid(Card[][] grid) {
        this.grid = grid;
    }
    
    public boolean checkIfPair(Card c1, Card c2) {
        this.tries++;
        if (c1.getNumber() == c2.getNumber()) { //pair is found
            this.pairsFound++;
            c1.setFaceDown(false);
            c2.setFaceDown(false);
            return true;
        }
        //pair not found
        return false;
    }
    
    public void printGrid() {
        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid.length; y++) {
                System.out.print(this.grid[x][y] + " ");
            }
            System.out.println("");
        }
    }
    
    public boolean gameInProgress() {
        if (pairsFound < pairsTotal) {
            return true;
        }
        return false;
    }
}
