package comp1110.ass2;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Arrays;
import comp1110.ass2.Player;
import comp1110.ass2.Board;
import comp1110.ass2.Rugs;
import comp1110.ass2.Assam;


import java.util.Random;

public class Marrakech {
    private Assam assam;
    private Board board;
    public static Player[] players;
    private Rugs[] rugs;
    private static final int MAX_PLAYERS = 4;

    char [] colorGame = {'c', 'y', 'r', 'p'};
    int numberOfPlayers;

    public Marrakech(int numPlayers) {
        this.numberOfPlayers = numPlayers;
        this.assam = new Assam();
        this.board = new Board();
        this.players = new Player[4];
        for (int i = 0; i < 4; i++) {
            if (i < numberOfPlayers) {
                this.players[i] = new Player(colorGame[i], 30, 15, true);
            } else {
                this.players[i] = new Player(colorGame[i], 0, 0, false);
            }
        }
    }




    public Marrakech() {
    }

    public Marrakech(String gameString) {
        this.setGameInfo(gameString);
    }

    // get String current game state
    public String getGameState(){
        String gameString = "";
        for(int i = 0; i < 4; i++) {
            gameString += players[i].getPlayerState();
        }
        return gameString += assam.getAssamState() + board.getBoardState();
    }

    public void setGameInfo(String gameString) {
        final int PLAYER_STRING_LENGTH = 8;
        final int ASSAM_STRING_LENGTH = 4;
        final int BOARD_STRING_LENGTH = 49 * 3;
        int numberOfPlayers = 4;

        numberOfPlayers = gameString.indexOf("A")/PLAYER_STRING_LENGTH;
        players = new Player[numberOfPlayers];


        if (gameString.length() != PLAYER_STRING_LENGTH * numberOfPlayers + ASSAM_STRING_LENGTH + BOARD_STRING_LENGTH + 1) {
            System.err.println("Invalid game string");

        }


        //create players from game string
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] =  new Player(gameString.substring(i * PLAYER_STRING_LENGTH, (i + 1) * PLAYER_STRING_LENGTH));
        }

        //create assam from game string
        int assamStart = numberOfPlayers * PLAYER_STRING_LENGTH;
        assam = new Assam(gameString.substring(assamStart, assamStart + ASSAM_STRING_LENGTH));

        //create board from game string
        int boardStart = assamStart + 5;
        board = new Board(gameString.substring(boardStart));

    }






    /**
     * Determine whether a rug String is valid.
     * For this method, you need to determine whether the rug String is valid, but do not need to determine whether it
     * can be placed on the board (you will determine that in Task 10 ). A rug is valid if and only if all the following
     * conditions apply:
     *  - The String is 7 characters long
     *  - The first character in the String corresponds to the colour character of a player present in the game
     *  - The next two characters represent a 2-digit ID number
     *  - The next 4 characters represent coordinates that are on the board
     *  - The combination of that ID number and colour is unique
     * To clarify this last point, if a rug has the same ID as a rug on the board, but a different colour to that rug,
     * then it may still be valid. Obviously multiple rugs are allowed to have the same colour as well so long as they
     * do not share an ID. So, if we already have the rug c013343 on the board, then we can have the following rugs
     *  - c023343 (Shares the colour but not the ID)
     *  - y013343 (Shares the ID but not the colour)
     * But you cannot have c014445, because this has the same colour and ID as a rug on the board already.
     * @param gameString A String representing the current state of the game as per the README
     * @param rug A String representing the rug you are checking
     * @return true if the rug is valid, and false otherwise.
     */
    public static boolean isRugValid(String gameString, String rug) {
        //FIXME: Task 4
//       tong yuan xiong

        // Check if the rug string length is 7
        if (rug.length() != 7) {
            return false;
        }

        // Get carpet color, ID, and coordinate information
        char color = rug.charAt(0);
        String idStr = rug.substring(1, 3);
        int xCoordinate1 = Character.getNumericValue(rug.charAt(3));
        int yCoordinate1 = Character.getNumericValue(rug.charAt(4));
        int xCoordinate2 = Character.getNumericValue(rug.charAt(5));
        int yCoordinate2 = Character.getNumericValue(rug.charAt(6));

        // Verify that the color is valid (C, Y, R, P)
        if (color != 'c' && color != 'y' && color != 'r' && color != 'p') {
            return false;
        }

        try {
            // Verify that the ID is a valid 2-digit number
            // And to get the id number
            int id = Integer.parseInt(idStr);
            if (id < 0 || id > 99) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        // Verify that the coordinates are within the legal range (1-7)
        if (xCoordinate1 < 0 || xCoordinate1 > 6) {
            return false;
        }
        if (yCoordinate1 < 0 || yCoordinate1 > 6){
            return false;
        }
        if (xCoordinate2 < 0 || xCoordinate2 > 6 ) {
            return false;
        }
        if (yCoordinate2 < 0 || yCoordinate2 > 6){
            return false;
        }
        String boardString = gameString.substring(32, gameString.length());
        if (boardString.contains(color + idStr)) {
            return false;
        }

        return true;
    }

    /**
     * Roll the special Marrakech die and return the result.
     * Note that the die in Marrakech is not a regular 6-sided die, since there
     * are no faces that show 5 or 6, and instead 2 faces that show 2 and 3. That
     * is, of the 6 faces
     *  - One shows 1
     *  - Two show 2
     *  - Two show 3
     *  - One shows 4
     * As such, in order to get full marks for this task, you will need to implement
     * a die where the distribution of results from 1 to 4 is not even, with a 2 or 3
     * being twice as likely to be returned as a 1 or 4.
     * @return The result of the roll of the die meeting the criteria above
     */
    public static int rollDie() {
        //FIXME: Task 6


        Random random = new Random();
        int randomResult = random.nextInt(6); // Generate a random number between 1 and 6

        // Map the random result to the die's faces
        if (randomResult == 1) {
            return 1;
        } else if (randomResult <= 3 && randomResult >=2) {
            return 2;
        } else if (randomResult <= 5 && randomResult >=4) {
            return 3;
        } else {
            return 4;
        }
    }

    /**
     * Determine whether a game of Marrakech is over
     * Recall from the README that a game of Marrakech is over if a Player is about to enter the rotation phase of their
     * turn, but no longer has any rugs. Note that we do not encode in the game state String whose turn it is, so you
     * will have to think about how to use the information we do encode to determine whether a game is over or not.
     * @param currentGame A String representation of the current state of the game.
     * @return true if the game is over, or false otherwise.
     */
    public static boolean isGameOver(String currentGame) {
        //FIXME: Task 8
        char player1 = currentGame.charAt(7);
        char player2 = currentGame.charAt(15);
        char player3 = currentGame.charAt(23);
        char player4 = currentGame.charAt(31);
        char[] playerstate = new char[4];
        playerstate[0]= player1;
        playerstate[1]= player2;
        playerstate[2]= player3;
        playerstate[3]= player4;
        for ( int i = 0; i < playerstate.length; i++){
            if ( playerstate[i] == 'i' ){
                if (Integer.parseInt(currentGame.substring(5+i*8, 7+i*8))>=1) {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board), then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree increments.
     * If the requested rotation is illegal, you should return Assam's current state unchanged.
     * @param currentAssam A String representing Assam's current state
     * @param rotation The requested rotation, in degrees. This degree reading is relative to the direction Assam
     *                 is currently facing, so a value of 0 for this argument will keep Assam facing in his
     *                 current orientation, 90 would be turning him to the right, etc.
     * @return A String representing Assam's state after the rotation, or the input currentAssam if the requested
     * rotation is illegal.
     */
    public static String rotateAssam(String currentAssam, int rotation) {
        //FIXME: Task 9

        // Verify that the input is valid
        if (currentAssam.length() != 4 || rotation % 90 != 0 ) {
            return currentAssam; // get back the currentAssam
        }

        // get the current Assam direction
        char currentDirection = currentAssam.charAt(3);

        // def the new direction
        char newDirection;

        // Calculate the new orientation based on the rotation angle
        if (rotation == 90) {
            switch (currentDirection) {
                case 'N':
                    newDirection = 'E'; // From North to East
                    break;
                case 'E':
                    newDirection = 'S'; // From East to South
                    break;
                case 'S':
                    newDirection = 'W'; // From South to West
                    break;
                case 'W':
                    newDirection = 'N'; // From West to North
                    break;
                default:
                    return currentAssam;
            }
        } else if (rotation == 270) {
            // Counterclockwise rotation is equal to three clockwise rotations.
            for (int i = 0; i < 3; i++) {
                currentAssam = rotateAssam(currentAssam, 90);
            }
            return currentAssam;
        } else {
            return currentAssam; // Unsupported rotation angle, return to original state
        }

        // Building new Assam strings
        return currentAssam.substring(0,3)+newDirection;
    }

    /**
     * Determine whether a potential new placement is valid (i.e that it describes a legal way to place a rug).
     * There are a number of rules which apply to potential new placements, which are detailed in the README but to
     * reiterate here:
     *   1. A new rug must have one edge adjacent to Assam (not counting diagonals)
     *   2. A new rug must not completely cover another rug. It is legal to partially cover an already placed rug, but
     *      the new rug must not cover the entirety of another rug that's already on the board.
     * @param gameState A game string representing the current state of the game
     * @param rug A rug string representing the candidate rug which you must check the validity of.
     * @return true if the placement is valid, and false otherwise.
     */
    public static boolean isPlacementValid(String gameState, String rug) {

        // Parse Rugs Get information about carpets to be placed
        int startx1 = Character.getNumericValue(rug.charAt(3));
        int starty1 = Character.getNumericValue(rug.charAt(4));
        int startx2 = Character.getNumericValue(rug.charAt(5));
        int starty2 = Character.getNumericValue(rug.charAt(6));

        // Check that the starting coordinates of the carpet are adjacent to the square where Assam is located
        // Analyze Assam's coordinates based on the game state
        int assamX = Character.getNumericValue(gameState.charAt(33));
        int assamY = Character.getNumericValue(gameState.charAt(34)); // get the String of Assam
        boolean isAsssamNeighbor = false;
        // Check that the starting coordinates of the carpet are adjacent to Assam
        int d1 = Math.abs(startx1 - assamX) + Math.abs(starty1 - assamY);
        int d2 = Math.abs(startx2 - assamX) + Math.abs(starty2 - assamY);

        if (d1*d1+d2*d2<=5&&d1*d1+d2*d2>=3){
                isAsssamNeighbor = true;
            }


        if (!isAsssamNeighbor)
            return false;


        String rugString1 = gameState.substring(37+3*(7*startx1+starty1),37+3*(7*startx1+starty1)+3);

        String rugString2 = gameState.substring(37+3*(7*startx2+starty2), 37+3*(7*startx2+starty2)+3);//Get checkerboard string
        if(!rugString1.equals("n00") && !rugString2.equals("n00")){
            if(rugString1.equals(rugString2)){
                    return false;
            }
        }

        return true; // rug placement valid if all conditions are met
    }

    /**
     * Determine the amount of payment required should another player land on a square.
     * For this method, you may assume that Assam has just landed on the square he is currently placed on, and that
     * the player who last moved Assam is not the player who owns the rug landed on (if there is a rug on his current
     * square). Recall that the payment owed to the owner of the rug is equal to the number of connected squares showing
     * on the board that are of that colour. Similarly to the placement rules, two squares are only connected if they
     * share an entire edge -- diagonals do not count.
     * @param gameString A String representation of the current state of the game.
     * @return The amount of payment due, as an integer.
     */
    public static int getPaymentAmount(String gameString) {
        //FIXME: Task 11
        String boardString = gameString.substring(37, 184);
        // // Parse the current game state to get player and board information

        // Parse the position of Assam and the color of the current square
        int assamX = Character.getNumericValue(gameString.charAt(33));
        int assamY = Character.getNumericValue(gameString.charAt(34)); // get the String of Assam
        char assamColor = boardString.charAt(3*(assamX*7 + assamY));
        if (assamColor == 'n'){
            return 0;
        }
        // Use depth-first search (DFS) to find the number of connected blocks
        boolean[][] visited = new boolean[7][7];
        int paymentAmount = dfs(boardString, assamX, assamY, assamColor, visited);
        return paymentAmount;
    }

    private static int dfs(String boardString, int x, int y, char targetColor, boolean[][] visited) {
        // Check that the current grid is legitimate and has not been accessed
        if (x < 0 || x >=7 || y < 0 || y >=7 || visited[x][y]) {
            return 0;
        }

        // Mark the current grid as visited
        visited[x][y] = true;

        // Gets the color of the current square
        char currentColor = boardString.charAt((x*7 + y)*3);

        // If the current square color is the same as the target color, the search for adjacent squares continues
        if (currentColor == targetColor) {
            int paymentAmount = 1; // The current square counts towards the payment amount
            // Keep searching in four directions: N, W, S and E
            paymentAmount += dfs(boardString, x - 1, y, targetColor, visited);
            paymentAmount += dfs(boardString, x + 1, y, targetColor, visited);
            paymentAmount += dfs(boardString, x, y - 1, targetColor, visited);
            paymentAmount += dfs(boardString, x, y + 1, targetColor, visited);
            return paymentAmount;
        }

        return 0; // The current square color does not match, the payment amount is 0
    }


    /**
     * Determine the winner of a game of Marrakech.
     * For this task, you will be provided with a game state string and have to return a char representing the colour
     * of the winner of the game. So for example if the cyan player is the winner, then you return 'c', if the red
     * player is the winner return 'r', etc...
     * If the game is not yet over, then you should return 'n'.
     * If the game is over, but is a tie, then you should return 't'.
     * Recall that a player's total score is the sum of their number of dirhams and the number of squares showing on the
     * board that are of their colour, and that a player who is out of the game cannot win. If multiple players have the
     * same total score, the player with the largest number of dirhams wins. If multiple players have the same total
     * score and number of dirhams, then the game is a tie.
     * @param gameState A String representation of the current state of the game
     * @return A char representing the winner of the game as described above.
     */
    public static char getWinner(String gameState) {
        //FIXME: Task 12

        // Parse the game state to get player and board information
        String player1 = gameState.substring(0,8);
        String player2 = gameState.substring(8,16);
        String player3 = gameState.substring(16,24);
        String player4 = gameState.substring(24,32);
        String[] players = new String[4];
        players[0]= player1;
        players[1]= player2;
        players[2]= player3;
        players[3]= player4;
        String boardString = gameState.substring(32, 184);

        // Initialize variables to keep track of player scores
        int cyanScore = 0;
        int yellowScore = 0;
        int redScore = 0;
        int purpleScore = 0;

        // Calculate player scores based on their dirhams
        // Player strings are in the format: P<color><dirhams><remaining rugs>i
        for (int i = 0; i < 4; i++) {
            String playerString = players[i];
            char color = playerString.charAt(1);
            int dirhams = Integer.parseInt(playerString.substring(2, 5));

            switch (color) {
                case 'c':
                    cyanScore = dirhams;
                    break;
                case 'y':
                    yellowScore = dirhams;
                    break;
                case 'r':
                    redScore = dirhams;
                    break;
                case 'p':
                    purpleScore = dirhams;
                    break;
            }
        }

        // Calculate player scores based on the board information
        // Board string is in the format: "n00<board_data>"
        // Each character in <board_data> represents a square on the board
        for (char square : boardString.toCharArray()) {
            switch (square) {
                case 'c':
                    cyanScore++;
                    break;
                case 'y':
                    yellowScore++;
                    break;
                case 'r':
                    redScore++;
                    break;
                case 'p':
                    purpleScore++;
                    break;
            }
        }

        // Find the player with the highest score
        char winner = 'n'; // Default: No winner
        int maxScore = Math.max(Math.max(cyanScore, yellowScore), Math.max(redScore, purpleScore));

        if (cyanScore == maxScore) {
            winner = 'c';
        } else if (yellowScore == maxScore) {
            winner = 'y';
        } else if (redScore == maxScore) {
            winner = 'r';
        } else if (purpleScore == maxScore) {
            winner = 'p';
        } else if (maxScore==cyanScore && maxScore==yellowScore && redScore == maxScore && purpleScore == maxScore ) {
            winner = 't';
        }

        return winner;
    }


    /**
     * Implement Assam's movement.
     * Assam moves a number of squares equal to the die result, provided to you by the argument dieResult. Assam moves
     * in the direction he is currently facing. If part of Assam's movement results in him leaving the board, he moves
     * according to the tracks diagrammed in the assignment README, which should be studied carefully before attempting
     * this task. For this task, you are not required to do any checking that the die result is sensible, nor whether
     * the current Assam string is sensible either -- you may assume that both of these are valid.
     * @param currentAssam A string representation of Assam's current state.
     * @param dieResult The result of the die, which determines the number of squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */
    public static String moveAssam(String currentAssam, int dieResult){
        //FIXME: Task 13

        // Parse the status of the current Assam
        char direction = currentAssam.charAt(3); // Get current orientation
        int x = Character.getNumericValue(currentAssam.charAt(1)); // Gets the current x coordinate
        int y = Character.getNumericValue(currentAssam.charAt(2)); // Gets the current y coordinate

        // New coordinates are calculated based on the current orientation and dice result
        int newX = x;
        int newY = y;

        for(int i = 1; i <= dieResult; i++){// Update coordinates based on current orientation and dice result
            switch (direction) {
                case 'N':
                    newY -= 1;
                    break;
                case 'E':
                    newX += 1;
                    break;
                case 'S':
                    newY += 1;
                    break;
                case 'W':
                    newX -= 1;
                    break;
            }
            // Check whether the checkerboard boundary has been stepped out,
            // and if so, calculate the new coordinates according to the Mosaic orbit rules
            if (newY < 0 && newX%2==0 && newX!=6) {
                newX = newX+1;
                direction ='S';
                newY= 0;
            }
            if (newY < 0 && newX == 6 ) {
                direction ='W';
                newY= 0;
            }
            if(newY < 0 && newX % 2== 1 ){
                newX = newX - 1;
                direction ='S';
                newY= 0;
            }
            if(newX < 0 && newY%2 == 0 && newY != 6){
                newY = newY + 1;
                direction ='E';
                newX= 0;
            }
            if (newX < 0 && newY == 6) {
                direction ='N';
                newX= 0;
            }
            if(newX < 0 && newY % 2 == 1 ){
                newY = newY - 1;
                direction ='E';
                newX= 0;
            }

            if (newY > 6 && newX%2==0 && newX!=0) {
                newX = newX - 1;
                direction ='N';
                newY = 6;

            }
            if (newY > 6 && newX == 0 ) {
                direction ='E';
                newY = 6;
            }
            if(newY > 6 && newX % 2 == 1 ){
                newX = newX + 1;
                direction ='N';
                newY = 6;
            }

            if (newX > 6 && newY%2==0 && newY!=0) {
                newY = newY - 1;
                direction ='W';
                newX = 6;
            }
            if (newX > 6 && newY == 0 ) {
                direction ='S';
                newX = 6;
            }
            if(newX > 6 && newY % 2 == 1 ){
                newY = newY + 1;
                direction ='W';
                newX = 6;
            }

        }




        // Builds a new Assam string based on the new coordinates and current orientation
        String newAssam = ("A" + newX + newY + direction);

        return newAssam;
    }

    /**
     * Place a rug on the board
     * This method can be assumed to be called after Assam has been rotated and moved, i.e in the placement phase of
     * a turn. A rug may only be placed if it meets the conditions listed in the isPlacementValid task. If the rug
     * placement is valid, then you should return a new game string representing the board after the placement has
     * been completed. If the placement is invalid, then you should return the existing game unchanged.
     * @param currentGame A String representation of the current state of the game.
     * @param rug A String representation of the rug that is to be placed.
     * @return A new game string representing the game following the successful placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */
    public static String makePlacement(String currentGame, String rug) {
        //FIXME: Task 14
        char player1 = currentGame.charAt(1);
        char player2 = currentGame.charAt(9);
        char player3 = currentGame.charAt(17);
        char player4 = currentGame.charAt(25);
        char[] playersSting = new char[4];
        playersSting[0]= player1;
        playersSting[1]= player2;
        playersSting[2]= player3;
        playersSting[3]= player4;
        // Parse the current game state
        String players = currentGame.substring(0,32);
        String assamString = currentGame.substring(32,36);
        String boardString = currentGame.substring(36, 184);

        // // Verify that the rug is legal
        if (!isPlacementValid(currentGame, rug)) {
            return currentGame; // If not, return directly to the original game state
        }
        if(!isRugValid(currentGame,rug)){
            return currentGame;
        }
        // Parsing rug strings
        char rugColor = rug.charAt(0);
        String rugID = rug.substring(1, 3);
        int rugx1 = Character.getNumericValue(rug.charAt(3));
        int rugy1 = Character.getNumericValue(rug.charAt(4));
        int rugx2 = Character.getNumericValue(rug.charAt(5));
        int rugy2 = Character.getNumericValue(rug.charAt(6));

        // Update checkerboard string
        StringBuilder newBoardString = new StringBuilder(boardString);
        newBoardString.replace((rugx1*7 + rugy1)*3 + 1, 3*(rugx1*7 + rugy1)+4, rugColor+rugID);
        newBoardString.replace((rugx2*7 + rugy2)*3  + 1, 3*(rugx2*7 + rugy2)+4, rugColor+rugID);
        // Create a player string
        StringBuilder newPlayerString = new StringBuilder(players);
        // Deduct the amount of carpet left by the player who placed the carpet
        for(int i = 0 ; i < playersSting.length ; i++){
            //   Check which player the rug is from
            if (String.valueOf(playersSting[i]).contains(String.valueOf(rugColor))){
                // get the number of rug
                String numberofRugs = players.substring(5 + i*8, 7 + i*8);
                int num = Integer.parseInt(numberofRugs);
                String newnum  = Integer.toString(num-1);
                if(num>=11){
                    newPlayerString.replace(5 + i*8, 7 + i*8 , newnum);
                }
                if(num<11){
                    newPlayerString.replace(5 + i*8, 7 + i*8 , "0"+newnum);
                }

            }
        }

        // Update the player string
        // Build a new game status string
        String newGameString = newPlayerString + assamString + newBoardString;
        return newGameString;
    }

    private static int rotationState = 0;
    private static boolean rotateRugLeftPressed = false;
    private static boolean rotateRugRightPressed = false;

    public static void resetRotationState() {
        rotationState = 0;
    }

    public static String rotateRug(String rugString, boolean rotateLeft) {

        Rugs rugs = new Rugs(rugString);

        int halfOneX = rugs.getX2();
        int halfOneY = rugs.getY2();
        int halfTwoX = rugs.getX1();
        int halfTwoY = rugs.getY1();

        if ((halfOneX == 0 && halfTwoX == 0) || (halfOneX == 6 && halfTwoX == 6) || (halfOneY == 0 && halfTwoY == 0) || (halfOneY == 6 && halfTwoY == 6)) {
            String formattedId = String.format("%02d", rugs.getId());
            return rugs.getColor() + formattedId + String.valueOf(halfTwoX) + String.valueOf(halfTwoY) + String.valueOf(halfOneX) + String.valueOf(halfOneY);
        } else {
            rotationState = (rotationState + 1) % 4;
            switch (rotationState) {
                case 1:
                    if (rotateLeft) {
                        halfOneX = rugs.getX2() - 1;
                        halfOneY = rugs.getY2() + 1;
                        halfTwoX = rugs.getX1();
                        halfTwoY = rugs.getY1();
                    } else {
                        halfOneX = rugs.getX2() + 1;
                        halfOneY = rugs.getY2() + 1;
                        halfTwoX = rugs.getX1();
                        halfTwoY = rugs.getY1();
                    }
                    break;
                case 2:
                    if (rotateLeft) {
                        if (rotateRugRightPressed) {
                            halfOneX = rugs.getX2() - 1;
                            halfOneY = rugs.getY2() - 1;
                            halfTwoX = rugs.getX1();
                            halfTwoY = rugs.getY1();
                            rotationState = 0;
                        }
                        else {
                            halfOneX = rugs.getX2() + 1;
                            halfOneY = rugs.getY2() + 1;
                            halfTwoX = rugs.getX1();
                            halfTwoY = rugs.getY1();
                        }
                    }
                    else {
                        if (rotateRugLeftPressed) {
                            halfOneX = rugs.getX2() + 1;
                            halfOneY = rugs.getY2() - 1;
                            halfTwoX = rugs.getX1();
                            halfTwoY = rugs.getY1();
                            rotationState = 0;
                        }
                        else {
                            halfOneX = rugs.getX2() - 1;
                            halfOneY = rugs.getY2() + 1;
                            halfTwoX = rugs.getX1();
                            halfTwoY = rugs.getY1();
                        }
                    }
                    break;
                case 3:
                    if (rotateLeft) {
                        halfOneX = rugs.getX2() + 1;
                        halfOneY = rugs.getY2() - 1;
                        halfTwoX = rugs.getX1();
                        halfTwoY = rugs.getY1();
                    } else {
                        halfOneX = rugs.getX2() - 1;
                        halfOneY = rugs.getY2() - 1;
                        halfTwoX = rugs.getX1();
                        halfTwoY = rugs.getY1();
                    }
                    break;
                default:
                    if (rotateLeft) {
                        if (rotateRugRightPressed) {
                            halfOneX = rugs.getX2() + 1;
                            halfOneY = rugs.getY2() + 1;
                            halfTwoX = rugs.getX1();
                            halfTwoY = rugs.getY1();
                            rotationState = 2;
                        }
                        else {
                            halfOneX = rugs.getX2() - 1;
                            halfOneY = rugs.getY2() - 1;
                            halfTwoX = rugs.getX1();
                            halfTwoY = rugs.getY1();
                        }
                    }
                    else {
                        if (rotateRugLeftPressed) {
                            halfOneX = rugs.getX2() - 1;
                            halfOneY = rugs.getY2() + 1;
                            halfTwoX = rugs.getX1();
                            halfTwoY = rugs.getY1();
                            rotationState = 2;
                        }
                        else {
                            halfOneX = rugs.getX2() + 1;
                            halfOneY = rugs.getY2() - 1;
                            halfTwoX = rugs.getX1();
                            halfTwoY = rugs.getY1();
                        }
                    }
                    break;
            }
        }
        if (rotateLeft) {
            rotateRugLeftPressed = true;
            rotateRugRightPressed = false;
        } else {
            rotateRugLeftPressed = false;
            rotateRugRightPressed = true;
        }

        String formattedId = String.format("%02d", rugs.getId());
        return rugs.getColor() + formattedId + String.valueOf(halfTwoX) + String.valueOf(halfTwoY) + String.valueOf(halfOneX) + String.valueOf(halfOneY);
    }

    public static String moveRug(String rugString, String direction) {
        Rugs rugs = new Rugs(rugString);

        int halfOneX = rugs.getX2();
        int halfTwoX = rugs.getX1();
        int halfOneY = rugs.getY2();
        int halfTwoY = rugs.getY1();

        switch (direction) {
            case "up":
                if (halfOneY > 0 && halfTwoY > 0) {
                    halfOneY -= 1;
                    halfTwoY -= 1;
                }
                break;
            case "down":
                if (halfOneY < 6 && halfTwoY < 6) {
                    halfOneY += 1;
                    halfTwoY += 1;
                }
                break;
            case "left":
                if (halfOneX > 0 && halfTwoX > 0) {
                    halfOneX -= 1;
                    halfTwoX -= 1;
                }
                break;
            case "right":
                if (halfOneX < 6 && halfTwoX < 6) {
                    halfOneX += 1;
                    halfTwoX += 1;
                }
                break;
            default:
                // Handle invalid direction (optional)
                break;
        }

        String formattedId = String.format("%02d", rugs.getId());
        return rugs.getColor() + formattedId + String.valueOf(halfTwoX) + String.valueOf(halfTwoY) + String.valueOf(halfOneX) + String.valueOf(halfOneY);
    }

    public static void main(String[] args) {

    }

}
