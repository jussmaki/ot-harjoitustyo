package memorygame.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import memorygame.dao.SqlDbScoreDao;
import memorygame.ui.MemorygameUI;

public class Main {
    public static void main(String[] args) {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection dbConn = DriverManager.getConnection("jdbc:sqlite:scores.db");
            PreparedStatement stmt = dbConn.prepareStatement("CREATE TABLE IF NOT EXISTS Score (\n" +
                "    id INTEGER PRIMARY KEY,\n" +
                "    player TEXT NOT NULL,\n" +
                "    tries INTEGER NOT NULL,\n" +
                "    total_time INTEGER NOT NULL,\n" +
                "    total_pairs INTEGER NOT NULL\n" +
                ");");
            stmt.executeUpdate();
            stmt.close();
            //dbConn.prepareStatement("CREATE INDEX IF NOT EXISTS index_id ON Score(id);").executeUpdate();
            //dbConn.prepareStatement("CREATE INDEX IF NOT EXISTS index_player ON Score(player);").executeUpdate();
            //dbConn.prepareStatement("CREATE INDEX IF NOT EXISTS index_total_pairs ON Score(total_pairs);").executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {       
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        MemorygameUI.main(args);
    }
}