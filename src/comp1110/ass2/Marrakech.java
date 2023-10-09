package comp1110.ass2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Marrakech {
    private Assam assam;
    private Board board;
    private Player[] players;
    private Rugs[] rugs;
    private static final int MAX_PLAYERS = 4;

    char [] colorGame = {'r', 'p', 'y', 'c'};
    int numberOfPlayers;

    public Marrakech(int numPlayers) {
        this.numberOfPlayers = numPlayers;
        this.assam = new Assam();
        this.board = new Board();
        this.players = new Player[numberOfPlayers];
//        for(int i = 0; i < numberOfPlayers; i++){
//            this.players[i] = new Player();
//        }
    }

    public Marrakech(String gameString) {
        this.setGameInfo(gameString);
    }

    // get String current game state
    public String getGameState(){
        String gameString = "";
        for(int i = 0; i < numberOfPlayers; i++) {
            gameString += players[i].getPlayerState();
        }
        return gameString += assam.getAssamState() + board.getBoardState();
    }

    public void setGameInfo(String gameString) {
        final int PLAYER_STRING_LENGTH = 8;
        final int ASSAM_STRING_LENGTH = 4;
        final int BOARD_STRING_LENGTH = 49 * 3;
        int numberOfPlayers = 0;

        numberOfPlayers = gameString.indexOf("A")/PLAYER_STRING_LENGTH;
        players = new Player[numberOfPlayers];


        if (gameString.length() != PLAYER_STRING_LENGTH * numberOfPlayers + ASSAM_STRING_LENGTH + BOARD_STRING_LENGTH) {
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
        int boardStart = assamStart + 4;
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
        String boardString = gameString.substring(32, 183);
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

        // 解析 rug 获取待放置地毯的信息
        char color = rug.charAt(0); // 地毯颜色
        int startx1 = Character.getNumericValue(rug.charAt(3));
        int starty1 = Character.getNumericValue(rug.charAt(4));
        int startx2 = Character.getNumericValue(rug.charAt(5));
        int starty2 = Character.getNumericValue(rug.charAt(6));

        // 检查地毯的起始坐标是否与阿萨姆所在的方格相邻
        // 根据游戏状态解析出阿萨姆的坐标
        int assamX = Character.getNumericValue(gameState.charAt(33));
        int assamY = Character.getNumericValue(gameState.charAt(34)); // 假设阿萨姆的Y坐标是3
        boolean isAsssamNeighbor = false;
        // 检查地毯的起始坐标是否与阿萨姆相邻
        if (Math.abs(startx1 - assamX) + Math.abs(starty1 - assamY) == 1
                || Math.abs(startx2 - assamX) + Math.abs(starty2 - assamY) == 1) {
            isAsssamNeighbor = true; // 地毯不与阿萨姆相邻，无效
        }
        if (!isAsssamNeighbor)
            return false;
        String rugString1 = gameState.substring(37+3*(7*startx1+starty1),37+3*(7*startx1+starty1)+3);

        String rugString2 = gameState.substring(37+3*(7*startx2+starty2), 37+3*(7*startx2+starty2)+3);// 获取棋盘字符串

        if(!rugString1.equals("n00") && !rugString2.equals("n00")){
            if(rugString1.equals(rugString2)){
                    return false;
            }
        }

        return true; // 符合所有条件，地毯放置有效
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
        return -1;
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
        return '\0';
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
        return "";
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
        return "";
    }


    public static void main(String[] args) {
        String game = "Pc03015iPy03015iPp03015iPr03015iA03WBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
        String rug = "c000203";
        isPlacementValid(game, rug);
    }

}
