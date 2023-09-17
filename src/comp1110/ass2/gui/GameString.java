import java.util.Random;

public class GameString {
    private static final String[] COLORS = {"c", "y", "r", "p"};
    private static final String[] ORIENTATIONS = {"N", "E", "S", "W"};

    public static String generateRandomGameString(int numPlayers) {
        if (numPlayers < 2 || numPlayers > 4) {
            throw new IllegalArgumentException("Number of players must be between 2 and 4");
        }

        StringBuilder gameString = new StringBuilder();

        // Generate the specified number of players with random colors
        for (int i = 0; i < numPlayers; i++) {
            String color = COLORS[i]; // Use a color from the predefined array
            gameString.append(generatePlayerString(color));
        }

        // Generate Assam string
        gameString.append(generateAssamString());

        // Generate board string
        gameString.append(generateBoardString());

        return gameString.toString();
    }

    private static String generatePlayerString(String color) {
        Random random = new Random();
        int dirhams = random.nextInt(16); // 0 to 15 dirhams
        int rugsRemaining = random.nextInt(16); // 0 to 15 rugs remaining
        char status = random.nextBoolean() ? 'i' : 'o'; // In or Out of the game
        return "P" + color + String.format("%03d", dirhams) + String.format("%02d", rugsRemaining) + status;
    }

    private static String generateAssamString() {
        Random random = new Random();
        int xCoordinate = random.nextInt(7); // 0 to 6
        int yCoordinate = random.nextInt(7); // 0 to 6
        String orientation = ORIENTATIONS[random.nextInt(ORIENTATIONS.length)];
        return "A" + xCoordinate + yCoordinate + orientation;
    }

    private static String generateBoardString() {
        Random random = new Random();
        StringBuilder boardString = new StringBuilder("B");
        for (int i = 0; i < 49; i++) {
            String abbreviatedRugString;
            if (random.nextBoolean()) {
                abbreviatedRugString = generateAbbreviatedRugString();
            } else {
                abbreviatedRugString = "n00"; // No rug
            }
            boardString.append(abbreviatedRugString);
        }
        return boardString.toString();
    }

    private static String generateAbbreviatedRugString() {
        Random random = new Random();
        int rugId = random.nextInt(100); // 0 to 99 rug IDs
        return "c" + String.format("%02d", rugId); // You can change the color if needed
    }

    public static void main(String[] args) {
        int numPlayers = new Random().nextInt(3) + 2; // Random number of players between 2 and 4
        String randomGameString = generateRandomGameString(numPlayers);
        System.out.println(randomGameString);
    }
}
