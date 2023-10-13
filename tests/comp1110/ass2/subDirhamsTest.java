package comp1110.ass2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class subDirhamsTest {
    @Test
    public void subDirhamsTest(){
        Player player = new Player("Pc00912i");

        int result = player.subDirhams(1);

        Assertions.assertEquals(8, result, "One dirham was subtracted from nine. Expected 8 but got " + result);
    }

    @Test
    public void negativeValue() {
        Player player = new Player("Pc00912i");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {player.subDirhams(-3); },
                "Expected Illegal Argument exception - Cannot subtract negative dirhams");
    }

    @Test
    public void tooManyDirhams() {
        Player player = new Player("Pc00912i");

        int result = player.subDirhams(1000);

        Assertions.assertFalse(player.isInGame());
        Assertions.assertEquals(0, result);
    }
}
