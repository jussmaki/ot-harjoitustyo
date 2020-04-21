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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import memorygame.dao.FileScoreDao;
import memorygame.logics.ScoreService;
import memorygame.logics.Card;
import memorygame.logics.Game;

public class MemorygameUI extends Application {

    private ScoreService scoreService;
    private Game game;
    private Card[][] gameGrid;
    private Scene start;
    private Timer timeTimer;
    private boolean clickedTwoCardsNoMatch = false;
    
    @Override
    public void init() {
        FileScoreDao scoreDao = new FileScoreDao();
        scoreService = new ScoreService(scoreDao);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        //setting title
        stage.setTitle("Memorygame");
        //menus
        Menu menu = new Menu("File");
        MenuBar menubar = new MenuBar();
        MenuItem newGameItem = new MenuItem("New game");
        menu.getItems().add(newGameItem);
        menubar.getMenus().add(menu);

        //homescreen
        BorderPane homescreen = new BorderPane();
        homescreen.setMinSize(256, 256);
        Label optionsText = new Label("Select board size:");
        ComboBox comboBoxX = new ComboBox();
        comboBoxX.getItems().addAll(
            2,
            4,
            6,
            8,
            10,
            12,
            14,
            16
        );
        comboBoxX.setValue(4);
        ComboBox comboBoxY = new ComboBox();
        comboBoxY.getItems().addAll(
            2,
            4,
            6,
            8,
            10,
            12,
            14,
            16
        );
        comboBoxY.setValue(4);
        HBox options = new HBox(comboBoxX, new Label("X"), comboBoxY);
        Button newGameButton = new Button("New game!");
        Button quitButton = new Button("Quit");
        Label pairsInGameText = new Label ("Pairs in game: " + (int) comboBoxX.getValue() * (int) comboBoxY.getValue() / 2);
        VBox hsButtons = new VBox(optionsText, options, pairsInGameText, newGameButton, quitButton);
        homescreen.setTop(hsButtons);
        VBox hsLayout = new VBox(menubar, homescreen);
        start = new Scene(hsLayout);

        comboBoxX.setOnAction((event) -> {
            pairsInGameText.setText("Pairs in game: " + (int) comboBoxX.getValue() * (int) comboBoxY.getValue() / 2);
        });
        
        comboBoxY.setOnAction((event) -> {
            pairsInGameText.setText("Pairs in game: " + (int) comboBoxX.getValue() * (int) comboBoxY.getValue() / 2);
        });
        
        //action handler for new game from menu
        newGameItem.setOnAction((event) -> {
            newGame(stage, (int) comboBoxX.getValue(), (int) comboBoxY.getValue());
        });        
        
        //homescreen button actions
        quitButton.setOnAction((event) -> {
            stage.close();
        });
        
        newGameButton.setOnAction((event) -> {
            newGame(stage, (int) comboBoxX.getValue(), (int) comboBoxY.getValue());
        });
        
        //stopping timer
        /*stage.setOnCloseRequest((event) -> {
            //timeTimer.cancel();
        });*/        
        
        //show homescreen first
        stage.setScene(start);
        stage.show();
       
    }
    
    private void newGame(Stage stage, int gridSizeX, int gridSizeY) {
        game = new Game(gridSizeX, gridSizeY);
        gameGrid = game.getGrid();

        //game view
        Label statistics = new Label("Tries: " + game.getTries() + " Pairs found: " + game.getPairsFound() + "/" + game.getPairsTotal());
        statistics.setFont(Font.font("Monospaced", 10));
        Label time = new Label(" Time: " + game.getPlayTime());
        time.setFont(Font.font("Monospaced", 10));        
        timeTimer = new Timer();
        timeTimer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                Platform.runLater(() -> time.setText((" Time: " + game.getPlayTime()))); 
            }
        }, 1000, 1000);
        HBox hud = new HBox(statistics, time);
        BorderPane layout = new BorderPane();
        Label gameinfoText = new Label("Select first card to flip.");
        VBox gameinfo = new VBox(gameinfoText);
        layout.setTop(hud);
        layout.setBottom(gameinfo);

        //drawing board
        GridPane board = new GridPane();
        board.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        board.setHgap(30);
        board.setVgap(30);
        board.setPadding(new Insets(10, 10, 10, 10));
        Font font = Font.font("Monospaced", 30);

        Button[][] cardsOnBoard = new Button[game.getGridSizeX()][game.getGridSizeY()];
        for (int x = 0; x < game.getGridSizeX(); x++) {
            for (int y = 0; y < game.getGridSizeY(); y++) {
                Button button = new Button("");
                button.setFont(font);
                button.setBackground(new Background(new BackgroundFill(Color.DARKCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
                button.setMinHeight(100);
                //button.setMaxHeight(100);
                button.setMinWidth(100);
                //button.setMaxWidth(100);
                board.add(button, x, y);
                cardsOnBoard[x][y] = button;
                int rx = x;
                int ry = y;
                button.setOnMouseClicked ((javafx.scene.input.MouseEvent e) -> {
                    if (clickedTwoCardsNoMatch) { //two cards with no match face up, just draw board again, turn cards face down and return
                        drawBoard(cardsOnBoard);
                        gameinfoText.setText("Select first card to flip.");
                        clickedTwoCardsNoMatch = false;
                        return;
                    }
                    drawBoard(cardsOnBoard);
                    int cardsClicked = game.handleAction(rx, ry);
                    cardsOnBoard[rx][ry].setText("" + gameGrid[rx][ry].getNumber());
                    statistics.setText("Tries: " + game.getTries() + " Pairs found: " + game.getPairsFound() + "/" + game.getPairsTotal());   
                    //checking if all pairs are found
                    if (!game.gameInProgress()) {
                        showGameEndWindow(stage);
                    }
                    if (cardsClicked == 1) {
                        gameinfoText.setText("Select second card to flip.");
                    }   else if (cardsClicked == 2) {
                        gameinfoText.setText("No match.");
                        clickedTwoCardsNoMatch = true;
                    } else if (cardsClicked == 3) {
                        gameinfoText.setText("Found pair.");
                    }
                });  

            }
        }

        layout.setCenter(board);
        Scene gameview = new Scene(layout);

        stage.setScene(gameview);        
    }
    
    private void drawBoard(Button[][] cardsOnBoard) {
        for (int a = 0; a < game.getGridSizeX(); a++) {
            for (int b = 0; b < game.getGridSizeY(); b++) {
                if (!gameGrid[a][b].isFaceDown() || gameGrid[a][b].isFound()) {
                    cardsOnBoard[a][b].setText("" + gameGrid[a][b].getNumber());
                } else {
                    cardsOnBoard[a][b].setText("");
                }
            }
        }        
    }
     //draw window with statistics
    private void showGameEndWindow(Stage stage) {
        System.out.println("game end");
        Alert information = new Alert(AlertType.INFORMATION);
        information.setHeaderText("game ended with " + game.getTries() + " tries.");
        information.setContentText(game.getPlayTime() + " seconds");
        information.setTitle("The end.");
        information.setOnCloseRequest((javafx.scene.control.DialogEvent eh) -> {
            timeTimer.cancel();
            //stage.close();
            stage.setScene(start);
        });
        information.show();
    }
    
    @Override
    public void stop() {
      if (timeTimer != null) {
          timeTimer.cancel();
      }
      System.out.println("application is shutting down");
    }    
    
    public static void main(String[] args) {
       launch(MemorygameUI.class);
   }   
    
}
