package comp1110.ass2;

import java.util.Arrays;

public class Board {
    //7x7 grid
    //2D array or a list of lists

    // The size of the board (left to right)
    public final static int BOARD_SIZE = 7;

    /**
     * board string, must be something like "n00n01n02...p77"
     */
    private String[][] boardMatrix = new String[BOARD_SIZE][BOARD_SIZE];

    //constructor initialize from a string representation


    public Board() {
        for (int i = 0; i < BOARD_SIZE; i++){
            Arrays.fill(boardMatrix[i], "n00");
        }
    }

    //method getBoardState(), return String to print Board State at the time "n00n00n00n00...n00"
    public String getBoardState() {
        String boardState = "";
        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                boardState += boardMatrix[i][j];
            }
        }
        return boardState;
    }

    //setTile(int x, int y, String abbreviatedRugState): void
    //change String value at tile(x,y) by Abbreviated Rug String
    public void setTile(String abbreviatedRugState, int x, int y){
        boardMatrix[x][y] = abbreviatedRugState; //String
    }

    //getTile(int x, int y): String
    //return String represent Tile state, "n00" for empty, "p01" (example) for occupied
    public String getTile (int x, int y){
        return boardMatrix[x][y];
    }

    // TODO: (Nice to have maybe) how to know index of the 15th tile

    public static void main(String[] args) {
        Board newBoard = new Board();
        newBoard.setTile("p01", 0, 1);
        newBoard.setTile("p01", 2, 0);
        System.out.println(newBoard.getBoardState());

    }
}



