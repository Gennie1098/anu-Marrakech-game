package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class SubPlayerRugTest {
    @Test
    /**
     * Tests if the player is in game
     * Tests if the amountOfRugs is subtracted properly
     */
    public void testPlayerInGame() {
        Player player = new Player("Pc00913i");

        int result = player.subPlayerRug(1);

        Assertions.assertTrue(player.isInGame());
        Assertions.assertEquals(12, result);
    }

    @Test
    /**
     * Tests if the player is out of the game
     * Tests if the amountOfRugs is not subtracted from the players rugs
     */
    public void testPlayerOutOfGame() {
        Player player = new Player("Pc00013o");

        int result = player.subPlayerRug(1);

        Assertions.assertFalse(player.isInGame(), "Player is expected to be out of the game");
        Assertions.assertEquals(13, result, "If a player is declared out of the game, their rugs should not change");

    }

    @Test
    /**
     * Tests if the player has no rugs left
     * Tests if the player is correctly set to out of the game if they don't have any rugs.
     */
    public void testNoRugsLeft() {
        Player player = new Player("Pc00900i");

        int result = player.subPlayerRug(1);

        Assertions.assertFalse(player.isInGame(), "After a player is out of rugs, they should be out of the game");
        Assertions.assertTrue(result == 0, "After a player is declared out of the game, their rugs should be 0");
    }

    @Test
    /**
     * Tests if an incorrect input into subPlayerRug throws an exception
     */
    public void testNegativeAmount() {
        Player player = new Player("Pc00913i");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {player.subPlayerRug(-2);}, "Expected Illegal Argument Exception");
    }
}
