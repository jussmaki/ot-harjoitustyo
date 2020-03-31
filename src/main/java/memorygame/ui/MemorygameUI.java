package memorygame.ui;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import memorygame.logics.Card;
import memorygame.logics.Game;

public class MemorygameUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Game game = new Game(4);
        Card[][] gameGrid = game.getGrid();
        
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
        BorderPane layout = new BorderPane();
        layout.setTop(statistics);

        //drawing board
        GridPane board = new GridPane();
        board.setHgap(30);
        board.setVgap(30);
        board.setPadding(new Insets(10, 10, 10, 10));
        Font font = Font.font("Monospaced", 50);
        
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Button button = new Button("");
                button.setFont(font);
                board.add(button, x, y);
                /*int rx=x;
                int ry=y;
                button.setOnAction((event) -> {
                    if (game.getFirstCardSelected() == null) {
                        game.setFirstCardSelected(gameGrid[rx][ry]);
                        game.setFirstCardSelectedPosX(rx);
                        game.setFirstCardSelectedPosY(ry);
                        button.setText("" + gameGrid[rx][ry].getNumber());
                    } else if (game.getFirstCardSelectedPosX() != rx && game.getFirstCardSelectedPosY() != ry) {
                        if (game.checkIfPair(game.getFirstCardSelected(), gameGrid[rx][ry])) {
                            button.setText("" + gameGrid[rx][ry].getNumber());
                        } else {
                            game.setFirstCardSelected(null);
                            game.setFirstCardSelectedPosX(-1);
                            game.setFirstCardSelectedPosY(-1);
                            board.getChildren().forEach((children) -> {
                                if (children.getLayoutX() == game.getFirstCardSelectedPosX() && children.getLayoutY() == game.getFirstCardSelectedPosY()) {
                                    children = new Button("");
                                }
                            });
                        }
                        System.out.println(game.getFirstCardSelected());
                     
                        statistics.setText("Tries: " + game.getTries() + " Pairs found: " + game.getPairsFound() + "/" + game.getPairsTotal());   
                    }
                    
                });*/
                
                //checking if all pairs are found
                if (!game.gameInProgress()) {
                    //draw window with statistics and go to homescreeen
                    
                }
            }
        }

        layout.setCenter(board);
        Scene gameview = new Scene(layout);        
        
        //homescreen button actions
        quitButton.setOnAction((event) -> {
            stage.close();
        });
        
        newGameButton.setOnAction((event) -> {
            stage.setScene(gameview);
        });
        
        //show homescreen first
        stage.setScene(start);
        stage.show();
    }
    
    public static void main(String[] args) {
       launch(MemorygameUI.class);
   }   
    
}
