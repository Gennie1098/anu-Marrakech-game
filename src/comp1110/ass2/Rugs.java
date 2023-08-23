package comp1110.ass2;

public class Rugs {
    public int[] rugPosition; // Placement position of rug.

    public int rugRegion; // Possible placement areas for the rug

    public void placeRug() {
        /**
         * uses player colour, numberOfRugs
         * uses rugPossibleRegion to limit allowed placement area
         * Implements rug placing logic
         */
    }
    public void rugPossibleRegion() {
        /**
         * The possible area the rug can be placed --> 8 surrounding squares around Assam
         * Unless he is at the edge, then there would be less possible squares
         */
    }
    public int rugConnected() {
        /** check if rugs connected/sharing an edge
         * then count the amount of squares that connected
         */
        return rugRegion;
    }

    public boolean rugwithinBoard() {
        /** check if rug are inside board
         * user rugPosition and gameBoardMatrix
         * return true if rugs inside board
         */
        return true;
    }

    public boolean rugsOverlap() {
        /** check if cover both squares of another rug (must be all visible / 2 square)
         * that is already on the board
         */
        return true;
         }

}
