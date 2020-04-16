package memorygame.ui;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import memorygame.dao.FileScoreDao;
import memorygame.domain.ScoreService;
import memorygame.logics.Card;
import memorygame.logics.Game;

public class MemorygameUI extends Application {

    private ScoreService scoreService;
    private Game game;
    private Card[][] gameGrid;
    
    @Override
    public void init() {
        FileScoreDao scoreDao = new FileScoreDao();
        scoreService = new ScoreService(scoreDao);
        
        game = new Game(4);
        gameGrid = game.getGrid();
    }
    
    @Override
    public void start(Stage stage) throws Exception {

        //Game game = new Game(4);
        //Card[][] gameGrid = game.getGrid();
        
        //homescreen
        BorderPane homescreen = new BorderPane();
        Button newGameButton = new Button("New game!");
        Button quitButton = new Button("Quit");
        homescreen.setTop(newGameButton);
        homescreen.setBottom(quitButton);
        Scene start = new Scene(homescreen);
        
        //game view
        
        Label statistics = new Label("Tries: " + game.getTries() + " Pairs found: " + game.getPairsFound() + "/" + game.getPairsTotal());
        statistics.setFont(Font.font("Monospaced", 10));
        //Text playTime = new Text();
        //playTime.textProperty().bind(game.getPlayTimeAsString());
        Label time = new Label(" Time: " + game.getPlayTime());
        //time.
        time.setFont(Font.font("Monospaced", 10));        
        Timer timeTimer = new Timer();
        timeTimer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
              Platform.runLater(() -> time.setText((" Time: " + game.getPlayTime()))); 
            }
        }, 1000, 1000);
        HBox hud = new HBox(statistics, time);
        BorderPane layout = new BorderPane();
        layout.setTop(hud);
        
        //drawing board
        GridPane board = new GridPane();
        board.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        board.setHgap(30);
        board.setVgap(30);
        board.setPadding(new Insets(10, 10, 10, 10));
        Font font = Font.font("Monospaced", 50);
        
        Button[][] cardsOnBoard = new Button[game.getGridSize()][game.getGridSize()];
        for (int x = 0; x < game.getGridSize(); x++) {
            for (int y = 0; y < game.getGridSize(); y++) {
                Button button = new Button("");
                button.setFont(font);
                button.setBackground(new Background(new BackgroundFill(Color.DARKCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
                button.setMinHeight(100);
                button.setMaxHeight(100);
                button.setMinWidth(100);
                button.setMaxWidth(100);
                board.add(button, x, y);
                cardsOnBoard[x][y] = button;
                int rx = x;
                int ry = y;
                button.setOnMouseClicked ((javafx.scene.input.MouseEvent e) -> {
                    for (int a = 0; a < game.getGridSize(); a++) {
                        for (int b = 0; b < game.getGridSize(); b++) {
                            if (!game.getGrid()[a][b].isFaceDown()) {
                                cardsOnBoard[a][b].setText("" + gameGrid[a][b].getNumber());
                            } else {
                                cardsOnBoard[a][b].setText("");
                            }
                        }
                    }
                    game.handleAction(rx, ry);
                    cardsOnBoard[rx][ry].setText("" + gameGrid[rx][ry].getNumber());              
                    statistics.setText("Tries: " + game.getTries() + " Pairs found: " + game.getPairsFound() + "/" + game.getPairsTotal());   
                    //checking if all pairs are found
                    if (!game.gameInProgress()) {
                        //draw window with statistics
                        System.out.println("game end");
                        Alert information = new Alert(AlertType.INFORMATION);
                        information.setHeaderText("game ended with " + game.getTries() + " tries.");
                        information.setContentText(game.getPlayTime() + " seconds");
                        information.setTitle("The end.");
                        information.setOnCloseRequest((javafx.scene.control.DialogEvent eh) -> {
                            timeTimer.cancel();
                            stage.close();
                        });
                        information.show();
                    }
                });  
                
            }
        }

        layout.setCenter(board);
        Scene gameview = new Scene(layout);
        
        //homescreen button actions
        quitButton.setOnAction((event) -> {
            timeTimer.cancel();
            stage.close();
        });
        
        newGameButton.setOnAction((event) -> {
            stage.setScene(gameview);
        });
        
        //stopping timer
        stage.setOnCloseRequest((event) -> {
            timeTimer.cancel();
        });        
        
        //show homescreen first
        stage.setScene(start);
        stage.show();
       
    }
    
    @Override
    public void stop() {
      System.out.println("application is shutting down");
    }    
    
    public static void main(String[] args) {
       launch(MemorygameUI.class);
   }   
    
}
