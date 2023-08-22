package comp1110.ass2;

import java.util.Scanner;
public class GameSetup {

    public int numberOfPlayers;

    public int numberOfRugs;

    public int numberOfDirhmas;

    public String[] playerStrings; // stores player string in an array


    public GameSetup() {
        // Constructor code
        /**
         *numberOfPlayers = countNumberOfPlayers();
         *initialisePlayers(); --> Will loop for each player
         *initialiseBoardMatrix();
         *initialiseAssamPosition();
         */
    }
    private int countNumberOfPlayers() {
        /**
         * Asks user for how many players are playing
         * Limited to 2-4 plauers
         * Can take input from scanner
         */
    }

    private void initialisePlayers() {
        /**
         * Creates the player string for each player
         * Loops through depending on how many players
         * Variables needed:
         * playerColour - [C, Y, R, P]
         * numberOfRugs - 15
         * numberOfDirhmas - 030
         * inGame = i or o
         * Creates player string by concatenating each value to make a player string
         */

    }

    private void initialiseBoardMatrix() {
        /**
         * Sets up a 7x7 board - array/matrix
         */
    }

    private void initialiseAssamPosition() {
        /**
         * Places Assam in the middle of the board, at (4, 4)
         * Facing north (?) by default.
         */
    }
}
