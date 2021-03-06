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
        Image flagImage = new Image("images/flag.png");
        Image coveredImage = new Image("images/covered.png");
        Image emptyImage = new Image("images/empty.png");
        Image bombImage = new Image("images/bomb.png");
        Image oneImage = new Image("images/1.png");
        Image twoImage = new Image("images/2.png");
        Image threeImage = new Image("images/3.png");
        Image fourImage = new Image("images/4.png");
        Image fiveImage = new Image("images/5.png");
        Image sixImage = new Image("images/6.png");
        Image sevenImage = new Image("images/7.png");
        Image eightImage = new Image("images/8.png");

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
                            //if state is covered -> reveal
                            //if bomb then DIE else update board and continue
                            int bombs = gameData.checkBlocks(aa,bb);
                            switch (bombs){
                                case 0: gridImages[aa][bb].setImage(emptyImage);
                                        break;
                                case 1: gridImages[aa][bb].setImage(oneImage);
                                        break;
                                case 2: gridImages[aa][bb].setImage(twoImage);
                                        break;
                                case 3: gridImages[aa][bb].setImage(threeImage);
                                        break;
                                case 4: gridImages[aa][bb].setImage(fourImage);
                                        break;
                                case 5: gridImages[aa][bb].setImage(fiveImage);
                                        break;
                                case 6: gridImages[aa][bb].setImage(sixImage);
                                        break;
                                case 7: gridImages[aa][bb].setImage(sevenImage);
                                        break;
                                case 8: gridImages[aa][bb].setImage(eightImage);
                                        break;
                                case 9: gridImages[aa][bb].setImage(bombImage);
                                        break;
                            }
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