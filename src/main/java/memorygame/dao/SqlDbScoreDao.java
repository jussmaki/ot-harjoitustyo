package memorygame.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private String dbConnection;
    private String dbFile;
    private String propertiesFile = "dbconfig.properties";
    
    public SqlDbScoreDao() throws FileNotFoundException, IOException {
        Properties props = new Properties();
        File file = new File(propertiesFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        props.load(new FileInputStream((propertiesFile)));
        driverName = props.getProperty("db.driver", "org.sqlite.JDBC");
        dbConnection = props.getProperty("db.connection", "jdbc:sqlite");
        dbFile = props.getProperty("db.file", "points.db");
        props.setProperty("db.driver", driverName);
        props.setProperty("db.connection", dbConnection);
        props.setProperty("db.file", dbFile);
        props.store(new FileOutputStream(propertiesFile), null);
        
        try {
           Connection dbConn;
           Class.forName(driverName);
           dbConn = DriverManager.getConnection(dbConnection+":"+dbFile);
           
           //dbConn.prepareStatement("DROP TABLE IF EXISTS Score").executeUpdate();
           
            dbConn.prepareStatement("CREATE TABLE IF NOT EXISTS Score (\n" +
                "  id INTEGER PRIMARY KEY,\n" +
                "  player TEXT NOT NULL,\n" +
                "  tries INTEGER NOT NULL,\n" +
                "  total_time INTEGER NOT NULL,\n" +
                "  total_pairs INTEGER\n" +
                ");\n" +
                "CREATE INDEX index_id ON Score(id);\n" +
                "CREATE INDEX index_player ON Score(player);\n" +
                "CREATE INDEX index_total_pairs ON Score(total_pairs);").executeUpdate();
            dbConn.close();
        } catch ( Exception e ) {
           System.out.println(e.getMessage());
        }
        //System.out.println("Opened database connection to " + dbConnection + ":" + dbFile + " successfully");
    }

    @Override
    public void create(Score score) {
        try {
           Connection dbConn;
           Class.forName(driverName);
           dbConn = DriverManager.getConnection(dbConnection+":"+dbFile);
           PreparedStatement stmt = dbConn.prepareStatement("INSERT INTO Score (player, tries, total_time, total_pairs) VALUES (?, ?, ?, ?);");
           stmt.setString(1, score.getName());
           stmt.setInt(2, score.getTries());
           stmt.setInt(3, score.getTime());
           stmt.setInt(4, score.getTotalPairs());
           stmt.executeUpdate();
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
           dbConn = DriverManager.getConnection(dbConnection+":"+dbFile);
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Score;");
            while ( rs.next() ) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                System.out.println(score);
                scores.add(score);
            }
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
           dbConn = DriverManager.getConnection(dbConnection+":"+dbFile);
           PreparedStatement stmt = dbConn.prepareStatement("SELECT * FROM Score ORDER BY tries ASC LIMIT ?;");
           stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while ( rs.next() ) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                System.out.println(score);
                scores.add(score);
            }
            dbConn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;    
    }
    
    @Override
    public List<Score> getScoresByTotalPairsOrderByTime (int totalPairs, int limit) {
        List<Score> scores = new ArrayList<>();
        try {
           Connection dbConn;
           Class.forName(driverName);
           dbConn = DriverManager.getConnection(dbConnection+":"+dbFile);
           PreparedStatement stmt = dbConn.prepareStatement("SELECT * FROM Score WHERE total_pairs=? ORDER BY total_time ASC LIMIT ?;");
           stmt.setInt(1, totalPairs);
           stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while ( rs.next() ) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                System.out.println(score);
                scores.add(score);
            }
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
           dbConn = DriverManager.getConnection(dbConnection+":"+dbFile);
           PreparedStatement stmt = dbConn.prepareStatement("SELECT * FROM Score WHERE total_pairs=? ORDER BY tries ASC LIMIT ?;");
           stmt.setInt(1, totalPairs);
           stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while ( rs.next() ) {
                Score score = new Score(rs.getString("player"), rs.getInt("tries"), rs.getInt("total_time"), rs.getInt("total_pairs"));
                System.out.println(score);
                scores.add(score);
            }
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
           dbConn = DriverManager.getConnection(dbConnection+":"+dbFile);
            PreparedStatement stmt = dbConn.prepareStatement("DELETE FROM Score WHERE player=?;");
            stmt.setString(1, name);
            stmt.executeUpdate();
            dbConn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
