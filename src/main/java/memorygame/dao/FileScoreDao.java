package memorygame.dao;

import java.io.FileWriter;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileScoreDao implements ScoreDao {

    String pointsFile = "points.csv";
    Reader reader;

    public FileScoreDao() throws IOException {
        File file = new File(pointsFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileScoreDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.reader = Files.newBufferedReader(Paths.get(pointsFile));
    }
    
    
    @Override
    public void create(Score score) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(this.pointsFile))) {
            writer.writeNext(new String[]{score.getName(), String.valueOf(score.getTries()), String.valueOf(score.getTime()), String.valueOf(score.getTotalPairs())});
            writer.close();
        } catch (Exception e) {
            
        }
    }

    @Override
    public List<Score> getAll() {
        ArrayList<Score> scores = new ArrayList<>();
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> points;
        try {
            points = csvReader.readAll();
            for (String[] point : points) {
                scores.add(new Score(point[0], Integer.valueOf(point[1]), Integer.valueOf(point[2]), Integer.valueOf(point[3])));   
            }
        } catch (Exception e) {
            Logger.getLogger(FileScoreDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return scores;
    }
    
    public void removeByName(String name) {
        ArrayList<String[]> scores = new ArrayList<>();
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> points;
        try {
            points = csvReader.readAll();
            for (String[] point : points) {
                if (!point[0].equals(name)) {
                    scores.add(point);
                }
            }
            FileWriter fileWriter = new FileWriter(this.pointsFile);
            fileWriter.write("");
            try (CSVWriter writer = new CSVWriter(fileWriter)) {
                writer.writeAll(scores);
            }
        } catch (Exception e) {
            Logger.getLogger(FileScoreDao.class.getName()).log(Level.SEVERE, null, e);
        }        
    }
}
