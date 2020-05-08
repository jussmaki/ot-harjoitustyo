package memorygame.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlDbScoreDao implements ScoreDao {

    private String driverName;
    private String dbFile;
    private String propertiesFile = "dbconfig.properties";
    
    public SqlDbScoreDao() {
        Properties props = new Properties();
        File file = new File(propertiesFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            props.load(new FileInputStream((propertiesFile)));      
            driverName = props.getProperty("db.driver", "org.sqlite.JDBC");
            dbFile = props.getProperty("db.file", "scores.db");
            props.setProperty("db.driver", driverName);
            props.setProperty("db.file", dbFile);
            props.store(new FileOutputStream(propertiesFile), null);
        } catch (IOException ex) { 
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        createDatabaseIfNotExists();
    }

    public SqlDbScoreDao(String driverName, String dbFile) {
        this.driverName = driverName;
        this.dbFile = dbFile;
        createDatabaseIfNotExists();
    }
    
    

    @Override
    public void create(Score score) {
        try {
            Connection dbConn;
            Class.forName(driverName);
            dbConn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            PreparedStatement stmt = dbConn.prepareStatement("INSERT INTO Score (player, tries, total_time, total_pairs) VALUES (?, ?, ?, ?);");
            stmt.setString(1, score.getName());
            stmt.setInt(2, score.getTries());
            stmt.setInt(3, score.getTime());
            stmt.setInt(4, score.getTotalPairs());
            stmt.executeUpdate();
            stmt.close();
            dbConn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Score> getAll() {
        List<Score> scores = new ArrayList<>();
        try {
            Connection dbConn;
            Class.forName(driverName);
            dbConn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Score;");
            while (rs.next()) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                scores.add(score);
            }
            stmt.close();
            rs.close();
            dbConn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }
    
    @Override
    public List<Score> getAll(int limit) {
        List<Score> scores = new ArrayList<>();
        try {
            Connection dbConn;
            Class.forName(driverName);
            dbConn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            PreparedStatement stmt = dbConn.prepareStatement("SELECT * FROM Score ORDER BY tries ASC LIMIT ?;");
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                scores.add(score);
            }
            stmt.close();
            rs.close();
            dbConn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;    
    }
    
    @Override
    public List<Score> getScoresByTotalPairsOrderByTime(int totalPairs, int limit) {
        List<Score> scores = new ArrayList<>();
        try {
            Connection dbConn;
            Class.forName(driverName);
            dbConn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            PreparedStatement stmt = dbConn.prepareStatement("SELECT * FROM Score WHERE total_pairs=? ORDER BY total_time ASC LIMIT ?;");
            stmt.setInt(1, totalPairs);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                scores.add(score);
            }
            stmt.close();
            rs.close();
            dbConn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }

    @Override
    public List<Score> getScoresByTotalPairsOrderByTries(int totalPairs, int limit) {
        List<Score> scores = new ArrayList<>();
        try {
            Connection dbConn;
            Class.forName(driverName);
            dbConn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            PreparedStatement stmt = dbConn.prepareStatement("SELECT * FROM Score WHERE total_pairs=? ORDER BY tries ASC LIMIT ?;");
            stmt.setInt(1, totalPairs);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                System.out.println(score);
                scores.add(score);
            }
            stmt.close();
            rs.close();
            dbConn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }

    @Override
    public void removeByName(String name) {
        try {
            Connection dbConn;
            Class.forName(driverName);
            dbConn = DriverManager.getConnection("jdbc:sqlite: " + dbFile);
            PreparedStatement stmt = dbConn.prepareStatement("DELETE FROM Score WHERE player=?;");
            stmt.setString(1, name);
            stmt.executeUpdate();
            stmt.close();
            dbConn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    public void removeAllScores() {
        try {
            Connection dbConn;
            Class.forName(driverName);
            dbConn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            PreparedStatement stmt = dbConn.prepareStatement("DELETE FROM Score;");
            stmt.executeUpdate();
            stmt.close();
            dbConn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private void createDatabaseIfNotExists() {
        try {
            Connection dbConn;
            Class.forName(driverName);
            dbConn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);           
            dbConn.prepareStatement("CREATE TABLE IF NOT EXISTS Score (\n" +
                "    id INTEGER PRIMARY KEY,\n" +
                "    player TEXT NOT NULL,\n" +
                "    tries INTEGER NOT NULL,\n" +
                "    total_time INTEGER NOT NULL,\n" +
                "    total_pairs INTEGER NOT NULL\n" +
                ");").executeUpdate();
            dbConn.prepareStatement("CREATE INDEX IF NOT EXISTS index_id ON Score(id);").executeUpdate();
            dbConn.prepareStatement("CREATE INDEX IF NOT EXISTS index_player ON Score(player);").executeUpdate();
            dbConn.prepareStatement("CREATE INDEX IF NOT EXISTS index_total_pairs ON Score(total_pairs);").executeUpdate();
            dbConn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
