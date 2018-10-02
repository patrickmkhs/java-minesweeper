import java.lang.reflect.Array;

public class MSModel {
        //  1       2       3a          3b
        //x_pos, y_pos, [cell_state,num_bombs_to_display]
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
                col[i][1] = 0;
            }
        }
    }

}