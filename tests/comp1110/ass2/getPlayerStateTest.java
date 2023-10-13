package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class getPlayerStateTest {
    @Test
    public void getPlayerStateTest() {
        Player player = new Player("Pc00913i");

        String playerState = player.getPlayerState();

        Assertions.assertTrue(player.isInGame());
        Assertions.assertEquals("Pc00913i", playerState);
    }

    @Test
    public void incorrectValues() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> { new Player("Pt00912i"); },
                "Expected Illegal Argument Exception - t is not a valid colour");
    }

    @Test
    public void incorrectLength() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {new Player("Pc0912i"); },
                "Expected Illegal Argument Exception - Player string is not of valid length");
    }
/**
    @Test
    public void incorrectInGame() {
        Player player = new Player("Pc00912t");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {new Player("Pc00912t"); },
                "Expected Illegal Argument Exception - Player's in game status is incorrect");
    }*/
}
