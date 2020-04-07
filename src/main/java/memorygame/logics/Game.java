package memorygame.logics;

public class Game {
    private Deck deck;
    private Card[][] grid;
    private int gridSize;
    private int tries;
    private int pairsFound;
    private int pairsTotal;
    private long startTime;
    private long stopTime;
    private boolean firstCardClicked;
    private Guess guess;

    public Game(int size) {

        this.gridSize = size;
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
    
    public void handleAction(int x, int y) {
        System.out.println("player clicked, x: " + x + " y: " + y);
        System.out.println("tries: " + this.tries);
        if (!firstCardClicked) {
            firstCardClicked = true;
            this.startTime = System.nanoTime();            
        }
        if (this.guess == null) { //no selected card
            this.guess = new Guess();
        }
        System.out.println("cards selected: " + this.guess.getCardsSelected());
        if (this.guess.getCardsSelected() < 1) { //selecting first card
            this.guess.addGuess(this.grid[x][y]);
            return;
        }
        if (!this.guess.addGuess(this.grid[x][y])) { //if selecting second card ok, then check if selected cards are pair
            return;
        }
        if (this.guess.match()) {
            this.pairsFound++;
        }
        this.tries++;
        this.guess = null;
        if (!gameInProgress()) {
            this.stopTime = System.nanoTime();
        }
    }
    
    public boolean gameInProgress() {
        if (this.pairsFound < this.pairsTotal) {
            return true;
        }
        return false;
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

    public Card[][] getGrid() {
        return grid;
    }

    public void setGrid(Card[][] grid) {
        this.grid = grid;
    }

    public int getGridSize() {
        return gridSize;
    }
        
    public int getPlayTime() {
        if (!this.firstCardClicked) {
            return 0;
        }
        if (!gameInProgress()) {
            return (int) ((this.stopTime - this.startTime) / 1e9);
        }
        return (int) ((System.nanoTime() - this.startTime) / 1e9);
    }
    
    public void printGrid() {
        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid.length; y++) {
                System.out.print(this.grid[x][y] + " ");
            }
            System.out.println("");
        }
    }
}
