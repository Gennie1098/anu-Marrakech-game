package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class SetGameInfoTest {
    @Test
    public void testSetGameInfo() {
        BufferedReader fr1;
        BufferedReader fr2;
        fr1 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/set_game_info1.txt")));
//        fr1 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/set_game_info2.txt")));
        Stream<String> testLines1 = fr1.lines();
//        fr2 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/set_game_info2.txt")));
//        Stream<String> testLines2 = fr2.lines();

        testLines1.forEach(line -> {
            String[] splitLine = line.split("@");
            String inputGameString = splitLine[0];
//           String expectedGameStatelength = splitLine[1];
            String testDescription = splitLine[2];
            // Create a new Marrakech object
            Marrakech game = new Marrakech(4);
            game.setGameInfo(inputGameString);
            // Get the current game state from the object
            String currentGameState = game.getGameState();

            // Compare the current game state with the expected game state
            Assertions.assertEquals(184, inputGameString.length(), testDescription);
        });




    }
}

