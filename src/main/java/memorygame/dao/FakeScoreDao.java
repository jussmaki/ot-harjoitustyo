package memorygame.dao;

import java.util.ArrayList;
import java.util.List;

public class FakeScoreDao implements ScoreDao {

    private ArrayList<Score> scores;

    public FakeScoreDao() {
        this.scores = new ArrayList<>();
    }
    
    @Override
    public void create(Score score) {
        this.scores.add(score);
    }

    @Override
    public List<Score> getAll() {
        return this.scores;
    }

    @Override
    public void removeByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
