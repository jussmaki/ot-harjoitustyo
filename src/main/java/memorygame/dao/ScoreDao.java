package memorygame.dao;

import java.util.List;

public interface ScoreDao {
    void create(Score score);
    List<Score> getAll();
}
