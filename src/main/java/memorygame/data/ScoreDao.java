package memorygame.data;

import java.util.List;

public interface ScoreDao {
    void create(Score score);
    List<Score> getAll();
    List<Score> getAll(int limit);    
    List<Score> getScoresByTotalPairsOrderByTime(int totalPairs, int limit);
    List<Score> getScoresByTotalPairsOrderByTries(int totalPairs, int limit);
    void removeByName(String name);
    void removeAllScores();
}
