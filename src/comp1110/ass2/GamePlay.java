package comp1110.ass2;

import comp1110.ass2.GameSetup;
// ^ Connects GameSetup java class with GameSetup
public class GamePlay {


    public int diceNumber; // Value rolled on the dice

    public int[] diceValue; // Array of possible dice rolls

    public int[] rugPosition; // Placement position of rug.

    public int[] rugRegion; // Possible placement areas for the rug


    public GamePlay() {
        //Constructor code
    }
    public void rotationAssam() {
        /**
         * Gives the current player the choice to rotate
         * Provides the player with the options [Left, Right, None]
         */
    }

    public int diceRoll(int diceNumber) {
        /**
         * randomly picks an integer from the list
         * diceValue[] = 1, 2, 2, 3, 3, 4
         */
        return diceNumber;
    }

    public void transformAssam(int distance) {
        /**
         * Moves Assam in the direction he is facing by movementAmount
         * Use his position in array from GameSetup (Default [4,4])
         * Found through assamPosition()
         */
    }

    public void assamReachEdge() {
        /**
         * Checks if Assam's position is facing the edge of the board
         * Takes in direction and position
         * Implements rotation method from earlier, depending on position in the board.
         * i.e, if odd on x axis and:
         * facing north --> Rotate right, moves one square, rotate right
         * facing south --> Rotate left, moves one square, rotate left
         * After rotation, runs transformAssam again, with remaining movement
         *
         * Sets new position using assamPosition();
         */
    }

//    public int[] updateAssamPosition() {
//        /**
//         * Gets Assam's new position
//         * returns new value for variable
//         */
//    }

    public boolean checkOpponentRug(){
        /**
         * Checks Assam's position with the known position of rugs
         * If on rug, checks ownership
         * If on opponent rug, returns true
         * If on no rug, or current players rug, returns false
         */
        return false;
    }

    public void payMoney() {
        /**
         * if (checkOpponentRug == True)
         * Checks surrounding rugs for total payment )calcDirhmas
         * Subtracts payment amount from current players numberOfDirhmas
         * If numberofDirhams < payment --> Player is out.
         */

    }

    public void rugPossibleRegion() {
        /**
         * The possible area the rug can be placed --> 8 surrounding squares around Assam
         * Unless he is at the edge, then there would be less possible squares
         */
    }

    public void placeRug() {
        /**
         * uses player colour, numberOfRugs
         * uses rugPossibleRegion to limit allowed placement area
         * Implements rug placing logic
         */
    }

    public void playerStringUpdates() {
        /**
         * Updates player string with new numberOfRugs, numberOfDirhmas
         */
    }

}
