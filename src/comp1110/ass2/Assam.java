package comp1110.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assam {
    //The game's main piece Assam
    // moves on the board based on dice rolls and determines where carpets are placed

    /**Assam string, must be something like "A04N"
     *
     */
    //Assam string information
    private int x; // x-coordinate
    private int y; // y-coordinate
    private char orientation; // N, E, S, or W



    //set default Assam's position is A33N
    public Assam() {
        this.x = 3;
        this.y = 3;
        this.orientation = 'N';
    }

    public Assam (String assamString) {
        this.x = Character.getNumericValue(assamString.charAt(1));
        this.y = Character.getNumericValue(assamString.charAt(2));
        this.orientation = assamString.charAt(3);

        if (x >= 7) {
        throw new IndexOutOfBoundsException();
        }
        if (y >= 7) {
            throw new IndexOutOfBoundsException();
        }
        List<Character> orientationList = Arrays.asList('N', 'S', 'E', 'W');

        if (!orientationList.contains(orientation)) {
            throw new IllegalArgumentException();
        }
    }

    public String getAssamState() { //"A04N"
        return "A" + x + y + orientation;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
            this.orientation = orientation;

    }

}
