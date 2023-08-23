package comp1110.ass2;

public class Assam {
    public int[] assamPosition; // Assam's position as {x, y, r}

    public int gameBoardWidth = 7;

    public int gameBoardLength = 7;

    public int[][] gameBoardMatrix;

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

    public int[] updateAssamPosition() {
        /**
         * Gets Assam's new position
         * returns new value for variable
         */
        return assamPosition;
    }

    public boolean checkOpponentRug(){
        /**
         * Checks Assam's position with the known position of rugs
         * If on rug, checks ownership
         * If on opponent rug, returns true
         * If on no rug, or current players rug, returns false
         */
        return true;
    }



}
