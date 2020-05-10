package memorygame.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    
    private Connection connection = null;
    private String propertiesFile = "dbconfig.properties";
    private String driverName;
    private String dbFile;    

    public DbConnection() {
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
            this.driverName = props.getProperty("db.driver", "org.sqlite.JDBC");
            this.dbFile = props.getProperty("db.file", "scores.db");
            props.setProperty("db.driver", driverName);
            props.setProperty("db.file", dbFile);
            props.store(new FileOutputStream(propertiesFile), null);
        } catch (IOException ex) { 
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    DbConnection(String driverName, String dbFile) {
        this.driverName = driverName;
        this.dbFile = dbFile;
    }
    
    private void connect() {
        try {
            Class.forName(driverName);
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            createDatabaseIfNotExists();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        if (this.connection == null) {
            this.connect();
        }
        return this.connection;
    }
    
    private void createDatabaseIfNotExists() {
        try {
            this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS Score (\n" +
                "    id INTEGER PRIMARY KEY,\n" +
                "    player TEXT NOT NULL,\n" +
                "    tries INTEGER NOT NULL,\n" +
                "    total_time INTEGER NOT NULL,\n" +
                "    total_pairs INTEGER NOT NULL\n" +
                ");").executeUpdate();
            this.connection.prepareStatement("CREATE INDEX IF NOT EXISTS index_id ON Score(id);").executeUpdate();
            this.connection.prepareStatement("CREATE INDEX IF NOT EXISTS index_player ON Score(player);").executeUpdate();
            this.connection.prepareStatement("CREATE INDEX IF NOT EXISTS index_total_pairs ON Score(total_pairs);").executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SqlDbScoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}