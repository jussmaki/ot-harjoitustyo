package memorygame.dao;

public class Score {
    private String name;
    private int tries;
    private int time;
    private int gridSize;

    public Score(String name, int tries, int time, int gridSize) {
        this.name = name;
        this.tries = tries;
        this.time = time;
        this.gridSize = gridSize;
    }
    
}
