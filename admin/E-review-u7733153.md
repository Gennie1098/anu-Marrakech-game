## Code Review

Reviewed by: tong yuan xiong, u7733153

Reviewing code written by: Gennie Nguyen, u7616055

Component: <Player class>
```java
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


    public Player(char color, int numberOfDirhams, int numberOfRugs, boolean inGame) {
        this.color = color;
        this.numberOfDirhams = numberOfDirhams;
        this.numberOfRugs = numberOfRugs;
        this.inGame = inGame; //'i' , map when print state
    }

    public Player (String playerString) {
        this.color = playerString.charAt(1);
        this.numberOfDirhams = Integer.parseInt(playerString.substring(2, 5));
        this.numberOfRugs = Integer.parseInt(playerString.substring(5, 7));
        this.inGame = playerString.charAt(7) == 'i';
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

```

### Comments 
**Functionality:
provided a Java class called Player that represents a player in a game. This class has attributes such as color, the number of dirhams, the number of rugs, and whether the player is in the game

- The`public Player` Constructor method  to initialize a player with the specified attributes
- The `public Player(String playerString)`that takes a string in the format "Pr00803i" and extracts the player's attributes from it.

**Readability:
The `play` class includes comments describing the purpose of the class and what each method does. Good documentation is crucial for understanding the code's purpose and usage

**Encapsulation:
The class uses private instance variables (color, numberOfDirhams, numberOfRugs, and inGame) and provides getter and setter methods to access and modify these variables. This encapsulation ensures data integrity and follows the principle of information hiding

**Error Handling:
The code includes error handling for invalid input. For example, it checks for negative values when subtracting rugs and throws an IllegalArgumentException if necessary. This helps prevent invalid states and aids in debugging

**What are the best features of this code?**
The code uses conditional statements to handle various game-related scenarios, such as subtracting dirhams and rugs and updating the player's in-game status. 
This clear logic enhances maintainability

**Is the code well-documented?**
The code has many great commented

**Does it follow Java code conventions, and is the style consistent throughout?**
The code largely follows Java code conventions, and the style is generally consistent throughout
While the code largely follows Java code conventions and maintains a consistent style, there is room for minor improvements, such as adhering to the constant naming convention and providing more detailed class-level documentation

**Suggestions for Improvement:**
- Ensure consistent code style, including naming conventions and formatting, across all parts of the code
- Provide more comprehensive and detailed documentation at the class level
- remove comment out code and increase the readability