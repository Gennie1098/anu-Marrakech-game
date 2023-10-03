## Code Review

Reviewed by: Gennie Nguyen, u7616055

Reviewing code written by: Morris Benjamin, u7649318

Component: Viewer class, GUI

```java
private static final int VIEWER_WIDTH = 1200;
private static final int VIEWER_HEIGHT = 700;
private final Group root = new Group();
private final Group controls = new Group();
private final Group gameLayout = new Group();
private TextField boardTextField;

    //Checks the length of the game string to find out how many players are in the game
    public static int checkLength(String state) {
        int length = state.length();
        if (length == 168) {
            return 2;
        }
        else if (length == 176) {
            return 3;
        }
        else if (length == 184) {
            return 4;
        }
        else {
            return 0;
        }
    }

    //Creates the board, populates it with the required colours for each rug.
    public static GridPane createBoard(List<Character> colorList) {

        GridPane boardPane = new GridPane();
        int numRows = Board.BOARD_SIZE;
        int numCols = Board.BOARD_SIZE;
        double cellSize = 64;

        //pane.setPadding(new Insets(50));

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                char color = colorList.get(0);
                colorList.remove(0);
                Rectangle r = new Rectangle(cellSize, cellSize);

                switch (color) {
                    case 'r' -> r.setFill(Color.ORANGERED);
                    case 'p' -> r.setFill(Color.MEDIUMPURPLE);
                    case 'c' -> r.setFill(Color.CYAN);
                    case 'y' -> r.setFill(Color.YELLOW);
                    default -> r.setFill(Color.TRANSPARENT);
                }
                //r.setStroke(Color.BLACK);
                boardPane.add(r, j, i);
            }
        }

        boardPane.setAlignment(Pos.CENTER);

        return boardPane;
    }
```
### Comments 

**Functionality:
Does it do what is intended?**

- The `checkLength` method determines the number of players based on the length of a given string. This seems a bit fragile, as using a string length to infer game state can be prone to errors.
- The `createBoard` method populates a board with colored rectangles based on a color list.

**Edge cases/bugs:**

- In the `checkLength` method, if the length of the state string is anything other than the specified values, the method will return 0, which might be misleading. There's no validation for invalid lengths.
- In the `createBoard` method, there is an assumption that colorList will have enough elements. There's no check for this, which can cause an IndexOutOfBoundsException.

**Good Names:**

Constants `VIEWER_WIDTH` and `VIEWER_HEIGHT` clearly indicate their purpose.
The method names are clear, although checkLength might be renamed to something more indicative of its function, like determineNumberOfPlayers

**Comments:**
- The comments present explain the methods' purpose but lack detail on some decisions or potential pitfalls.
- The code lacks JavaDoc-style comments, especially for public methods

**What are the best features of this code?**

The code is straightforward and easy to understand.
Usage of the enhanced switch (switch expression) for setting colors makes the code cleaner.

**Is the code well-documented?**

The code could benefit from more detailed comments, especially in terms of why certain decisions were made.

**Is the program decomposition (class and method structure) appropriate?**

Yes, the decomposition seems appropriate.

**Does it follow Java code conventions, and is the style consistent throughout?**

Mostly, but there are some minor issues as pointed out above.
If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.

As mentioned, the `checkLength` method can return misleading results for unexpected lengths.
The createBoard method can throw an IndexOutOfBoundsException if colorList doesn't have enough colors.

**Suggestions for Improvement:**

- Add more robust validation and error handling.
- Enhance the documentation.
- Remove commented-out code

