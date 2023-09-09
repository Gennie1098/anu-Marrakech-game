package comp1110.ass2;

public class Player {
    //4 players max

    /**
     * player string, must be something like "Pr00803i"
     */
    //player string information
    final int DEFAULT_DIRHAMS = 30;
    final int MAX_RUGS = 15;
    private char color; // 'c', 'y', 'r', or 'p'
    private int numberOfDirhams; // a number 0-999, represented with 3 digits
    private int numberOfRugs; // a number 0-15, represented with 2 digits
    private boolean inGame; // 'i' or 'o'

    public Player(char color) {
        this.color = color;
        this.numberOfDirhams = DEFAULT_DIRHAMS;
        this.numberOfRugs = 0;
        this.inGame = true; //'i' , map when print state
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
        this.numberOfDirhams += amountOfDirhams;
        return numberOfDirhams;
    }

    //+ subDirhams(int amountOfDirhams): int
    public int subDirhams(int amountOfDirhams){
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
    public int addPlayerRug(int amountOfRugs) {
        if (inGame == false) {
            return numberOfRugs;
        }
        this.numberOfRugs += amountOfRugs;
        if (numberOfRugs == MAX_RUGS) {
            inGame = false; //if numberOfRugs = 15 (player put all his rug on the board), player is out of game
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

    public static void main(String[] args) {
        Player player1 = new Player('p');
        player1.subDirhams(40);
        System.out.println(player1.getPlayerState());
    }

}





