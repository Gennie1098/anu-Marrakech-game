package comp1110.ass2;

import java.util.Arrays;
import java.util.List;

public class Player {
    //4 players max

    /**
     * player string, must be something like "Pr00803i"
     */
    //player string information
    final int DEFAULT_DIRHAMS = 30;
    final int MAX_RUGS = 15;
    private char color; // 'c', 'y', 'r', or 'p'
    private int numberOfDirhams; // a number 0-30, represented with 3 digits
    private int numberOfRugs; // a number 0-15, represented with 2 digits
    private boolean inGame; // 'i' or 'o'

    public Player(){
        //empty
    }

    public Player(char color, int numberOfDirhams, int numberOfRugs, boolean inGame) {
        this.color = color;
        this.numberOfDirhams = numberOfDirhams;
        this.numberOfRugs = numberOfRugs;
        this.inGame = inGame; //'i' , map when print state
    }

    public Player (String playerString) {
        if (playerString.length() != 8) {
            throw new IllegalArgumentException();
        }
        this.color = playerString.charAt(1);
        this.numberOfDirhams = Integer.parseInt(playerString.substring(2, 5));
        this.numberOfRugs = Integer.parseInt(playerString.substring(5, 7));
        this.inGame = playerString.charAt(7) == 'i';

        List<Character> colorList = Arrays.asList('c', 'r', 'p', 'y');

        if (!colorList.contains(color)) {
            throw new IllegalArgumentException();
        }
    }


    public String getPlayerState() {
        return "P" + color + String.format("%03d", numberOfDirhams)
                + String.format("%02d", numberOfRugs) + (this.inGame ? 'i' : 'o');
    }


    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public int getNumberOfDirhams() {
        return numberOfDirhams;
    }

    //+ addDirhams(int amountOfDirhams): int
    public int addDirhams(int amountOfDirhams){
        if (amountOfDirhams >= 1000 - getNumberOfDirhams()) { //exceeds the 3 digit threshold
            throw new IllegalArgumentException("Too many dirhams added");
        }
        if (amountOfDirhams < 0) {
            throw new IllegalArgumentException("Can't add a negative amount of Dirhams");
        }
        this.numberOfDirhams += amountOfDirhams;
        return numberOfDirhams;
    }

    //+ subDirhams(int amountOfDirhams): int
    public int subDirhams(int amountOfDirhams){
        if (amountOfDirhams < 0) {
            throw new IllegalArgumentException("Can't add a negative amount of Dirhams");
        }
        if (numberOfDirhams < amountOfDirhams){
            numberOfDirhams = 0;
            inGame = false; //if numberOfDirhams = 0, player is out of game
        } else {
            this.numberOfDirhams -= amountOfDirhams;
        }
        return numberOfDirhams;
    }

    public void setNumberOfDirhams(int numberOfDirhams) {
        this.numberOfDirhams = numberOfDirhams;
    }

    public int getNumberOfRugs() {
        return numberOfRugs;
    }

    //addPlayerRug(int amountOfRugs): int
    public int subPlayerRug(int amountOfRugs) {
        if (amountOfRugs < 0) {
            throw new IllegalArgumentException("Amount of rugs can't be negative");
        }
        if (!inGame) { //inGame == false
            return numberOfRugs;
        }
        this.numberOfRugs -= amountOfRugs;
        if (numberOfRugs <= 0) {
            inGame = false; //if numberOfRugs = 0 (player put all his rug on the board), player is out of game
            numberOfRugs = 0;
        }
        return numberOfRugs;
    }

    public void setNumberOfRugs(int numberOfRugs) {
        this.numberOfRugs = numberOfRugs;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

//    public static void main(String[] args) {
//        Player player1 = new Player();
//        player1.subDirhams(40);
//        System.out.println(player1.getPlayerState());
//    }

}





