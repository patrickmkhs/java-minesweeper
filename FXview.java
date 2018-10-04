import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

public class FXview extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT= 700;
    private static final int BOX_WIDTH = 30;
    private static final int BOX_HEIGHT= 30;
    private static final int BOX_ROWS = 10;
    private static final int BOX_COLS= 10;
    private static final int BOX_MARGIN= 2;
    private static final int BOX_X_OFFSET= (WIDTH/2) - ((BOX_WIDTH + BOX_MARGIN) * ((BOX_ROWS+BOX_MARGIN)/2));
    private static final int BOX_Y_OFFSET= (HEIGHT/2) - ((BOX_HEIGHT + BOX_MARGIN) * ((BOX_COLS+BOX_MARGIN)/2));

    @Override
    public void start(Stage stage) {
        //creating a new game grid object
        MSModel gameData = new MSModel(BOX_ROWS,BOX_COLS);

        //create image objects
        Image flagImage = new Image("flag.png");
        Image coveredImage = new Image("covered.png");
        Image emptyImage = new Image("empty.png");
        Image bombImage = new Image("bomb.png");

        //creating array of rectangle objects
        ImageView[][] gridImages = new ImageView[BOX_ROWS][BOX_COLS];
        for (int a = 0; a < gameData.grid.length; a++){
            for (int b = 0; b < gameData.grid[a].length; b++){
                ImageView iv = new ImageView();
                iv.setImage(coveredImage);
                iv.setX((BOX_WIDTH + BOX_MARGIN) * (a+1) + BOX_X_OFFSET);
                iv.setY((BOX_HEIGHT + BOX_MARGIN)* (b+1) + BOX_Y_OFFSET);
                gridImages[a][b] = iv;
                System.out.println(gridImages[a][b]);
            }
        }

        //VIEW = Creating a Group object
        Group root = new Group();

        Button start = new Button("start");
        start.setTranslateX(50);
        Button win = new Button("win");
        win.setTranslateX(150);
        win.setVisible(false);

        Button lose = new Button("lose");
        lose.setTranslateX(250);
        lose.setVisible(false);

        Button again = new Button("Play again?");
        again.setTranslateY(100);
        again.setTranslateX(100);
        again.setVisible(false);


        //CONTROLLER = Setting up eventListeners
        start.setOnAction(event -> {
            start.setVisible(false);
            win.setVisible(true);
            lose.setVisible(true);
            gameData.gameState = 1;
            gameData.placeBombs();
        });
        win.setOnAction(event -> {
            again.setVisible(true);
            lose.setDisable(true);
            gameData.gameState = 2;
        });
        lose.setOnAction(event -> {
            again.setVisible(true);
            win.setDisable(true);
            gameData.gameState = 2;
        });
        again.setOnAction(event -> {
            //reset buttons
            win.setDisable(false);
            win.setVisible(false);
            lose.setDisable(false);
            lose.setVisible(false);
            start.setVisible(true);
            again.setVisible(false);
            //reset game object
            gameData.reset();
        });

        //add UI elements

        root.getChildren().addAll(start,win,lose,again);

        //Setup GridBox Events and add grid to group
        for (int a = 0; a < gameData.grid.length; a++){
            for (int b = 0; b < gameData.grid[a].length; b++){
                final int aa = a;
                final int bb = b;
                gridImages[a][b].setOnMouseClicked(event -> {
                    //If game is running ( == 1)
                    if (gameData.gameState == 1) {
                        if (event.getButton().toString().equals("PRIMARY")) {
                            gridImages[aa][bb].setImage(emptyImage);
                            gameData.grid[aa][bb][0] = 1;
                        }
                        if(event.getButton().toString().equals("SECONDARY")) {
                            gridImages[aa][bb].setImage(flagImage);
                            gameData.grid[aa][bb][0] = 2;
                        }
                    }
                });
                root.getChildren().add(gridImages[a][b]);
            }
        }

        //Creating a scene object
        Scene scene = new Scene(root,FXview.WIDTH, FXview.HEIGHT);
        //Setting title to the Stage
        stage.setTitle("MineSweeper");
        //Adding scene to the stage
        stage.setScene(scene);
        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){
        launch(args);

    }
}      