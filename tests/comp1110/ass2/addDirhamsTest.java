package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class addDirhamsTest {

    @Test
    public void addDirhamsTest() {
        Player player = new Player("Pc00911i");

        int result = player.addDirhams(1);

        Assertions.assertEquals(10, result);
    }

    @Test
    public void negativeDirhams() {
        Player player = new Player("Pc00911i");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {player.addDirhams(-3); },
        "Expected Illegal Argument Exception - You cannot add negative dirhams");
    }

    @Test
    public void tooManyDirhams() {
        Player player = new Player("Pc00911i");

        int tooManyDirhams = 1000 - player.getNumberOfDirhams();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {player.addDirhams(tooManyDirhams); },
                "Expected Illegal Argument Exception - Too many dirhams added");
    }
}
