package memorygame.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ScoreDao-rajapinnan toteuttava tietokantaa hyödyntävä luokka.
 */
public class SqlDbScoreDao implements ScoreDao {

    private DbConnection dbConnection;
    private Connection connection;
    
    /**
     * Tietokantayhteyden avaus tiedostoon tallennettujen asetusten avulla.
     */
    public SqlDbScoreDao() {
        this.dbConnection = new DbConnection();
        this.connection = dbConnection.getConnection();
    }
    
    /**
     * Tietokantayhteyden avaus parametreina annetuilla tiedoilla.
     * @param driverName käytettävä JDBC ohjain 
     * @param dbFile tietokannan nimi tai sijainti
     */
    public SqlDbScoreDao(String driverName, String dbFile) {
        this.dbConnection = new DbConnection(driverName, dbFile);
        this.connection = dbConnection.getConnection();
    }
    
    @Override
    public void create(Score score) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Score (player, tries, total_time, total_pairs) VALUES (?, ?, ?, ?);");
            stmt.setString(1, score.getName());
            stmt.setInt(2, score.getTries());
            stmt.setInt(3, score.getTime());
            stmt.setInt(4, score.getTotalPairs());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Score> getAll() {
        List<Score> scores = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Score;");
            while (rs.next()) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                scores.add(score);
            }
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }
    
    @Override
    public List<Score> getAll(int limit) {
        List<Score> scores = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Score ORDER BY tries ASC LIMIT ?;");
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                scores.add(score);
            }
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;    
    }
    
    @Override
    public List<Score> getScoresByTotalPairsOrderByTime(int totalPairs, int limit) {
        List<Score> scores = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Score WHERE total_pairs=? ORDER BY total_time ASC LIMIT ?;");
            stmt.setInt(1, totalPairs);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                scores.add(score);
            }
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }

    @Override
    public List<Score> getScoresByTotalPairsOrderByTries(int totalPairs, int limit) {
        List<Score> scores = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Score WHERE total_pairs=? ORDER BY tries ASC LIMIT ?;");
            stmt.setInt(1, totalPairs);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                scores.add(score);
            }
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }

    @Override
    public void removeByName(String name) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Score WHERE player=?;");
            stmt.setString(1, name);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    public void removeAllScores() {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Score;");
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}