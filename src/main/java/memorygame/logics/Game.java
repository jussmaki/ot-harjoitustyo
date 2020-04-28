package memorygame.logics;

public class Game {
    private Deck deck;
    private Card[][] grid;
    private int gridSizeX;
    private int gridSizeY;
    private int tries;
    private int pairsFound;
    private int pairsTotal;
    private long startTime;
    private long stopTime;
    private boolean firstCardClicked;
    private Guess guess;

    public Game(int gridSizeX, int gridSizeY) {

        //this.gridSize = size;
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        //initializing counters
        this.tries = 0;
        this.pairsFound = 0;
        this.pairsTotal = gridSizeX * gridSizeY / 2;
        this.startTime = System.nanoTime();
        //creating and shuffling new deck
        this.deck = new Deck(this.pairsTotal);
        //placing cards in board
        this.grid = new Card[gridSizeX][gridSizeY];
        for (int x = 0; x < gridSizeX; x++) {
            for (int y = 0; y < gridSizeY; y++) {
                this.grid[x][y] = this.deck.getCards().get(0);
                this.deck.getCards().remove(0);
                //System.out.println(this.grid[x][y].getNumber());
            }
        }
        this.guess = new Guess();
    }
    /**
    * Metodi hoitaa kaikkien pelitapahtumien logiikan käsittelyn
    * 
    * @return 1-2 = valittujen korttien lukumäärä, 3 = löytyi pari.
    */    
    public int handleAction(int x, int y) {
        //printGrid();
        System.out.println("player clicked, x: " + x + " y: " + y + " card: " + this.grid[x][y].toString());
        System.out.println("tries: " + this.tries);
        if (!firstCardClicked) {
            firstCardClicked = true;
            this.startTime = System.nanoTime();            
        }
        System.out.println("cards selected: " + this.guess.getCardsSelected());
        if (this.guess.getCardsSelected() < 1) { //selecting first card
            this.guess.addGuess(this.grid[x][y]);
            return this.guess.getCardsSelected();
        } else if (!this.guess.addGuess(this.grid[x][y])) { //if selecting second card ok, then check if selected cards are pair, otherwise return
            return 1;
        }
        boolean foundPair = false;
        if (this.guess.match()) {
            this.pairsFound++;
            foundPair = true;
        }
        this.tries++;
        if (!gameInProgress()) {
            this.stopTime = System.nanoTime();
        }
        if (foundPair) {
            return 3;
        }
        return 2;
    }
    /**
    * Metodi palauttaa tiedon siitä onko peli käynnissä
    * 
    * @return peli käynnissä
    */
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

    public int getGridSizeX() {
        return gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
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

    public Guess getGuess() {
        return guess;
    }
        
    public void printGrid() {
        for (int y = 0; y < this.gridSizeY; y++) {
            for (int x = 0; x < this.gridSizeX; x++) {
                System.out.print(this.grid[x][y].getNumber() + " ");
            }
            System.out.println("");
        }
    }
}
