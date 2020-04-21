package memorygame.dao;

public class Score {
    private String name;
    private int tries;
    private int time;
    private int totalPairs;

    public Score(String name, int tries, int time, int totalPairs) {
        this.name = name;
        this.tries = tries;
        this.time = time;
        this.totalPairs = totalPairs;
    }
    
}
