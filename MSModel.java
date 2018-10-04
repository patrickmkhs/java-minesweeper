import com.sun.jdi.event.StepEvent;

import java.util.Random;

public class MSModel {
        //  1       2       3a          3b
        //x_pos, y_pos, [cell_state,num_bombs_to_display]
        //cell states: 0=empty, 1=bomb
        //num_bombs_to_display: 0=draw nothing, 1,2,3,4,5,6,7,8, 9=flag, 10=bomb

    public int[][][] grid;
    public int gameState;

    public MSModel(int x, int y){
        grid = new int[x][y][2];

        gameState = 0;
    }

    public void reset(){
        this.gameState = 0;
        for (int[][] col : grid) {
            for (int i = 0; i < col.length; i++) {
                col[i][0] = 0;
                col[i][1] = 0;
            }
        }
    }

    public void placeBombs(){
        int bombsRemaining = 40;
        Random rand = new Random();
        for (int[][] col : grid) {
            for (int i = 0; i < col.length; i++) {
                if (bombsRemaining > 0) {
                    col[i][0] = rand.nextInt(2);
                    if (col[i][0] == 1){bombsRemaining--;}
                }

                //testing
                if (i<col.length-1)
                    System.out.print(col[i][0] + " ");
                else
                    System.out.println(col[i][0]);

            }
        }
        System.out.println(bombsRemaining);
    }

}