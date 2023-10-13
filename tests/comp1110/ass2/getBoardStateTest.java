package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class getBoardStateTest {
    @Test
    public void getBoardStateTest() {

        Board board = new Board("Bn00n00y08n00n00n00n00r97n00n00n00n00y44n00r01n00n00r78r22n00n00n00c03y86c04r12n00n00n00n00n00n00y66r08c76r24n00n00n00y22y00r20n00n00n00y82r74n00y34");

        String test = board.getBoardState();

        Assertions.assertEquals("Bn00n00y08n00n00n00n00r97n00n00n00n00y44n00r01n00n00r78r22n00n00n00c03y86c04r12n00n00n00n00n00n00y66r08c76r24n00n00n00y22y00r20n00n00n00y82r74n00y34", test);
    }

    @Test
    public void incorrectLength() {
        Assertions.assertThrows(StringIndexOutOfBoundsException.class, () -> {
            new Board("Bn00n00y08n00n00n00n00r97n00n00n00n00y44n00r01n00n00r78r22n00n00n00c03y86c04r12n00n00n00n00n00n00y66r08c76r24n00n00n00y22y00r20n00n00n00y82r74");
        }, "Expected string index out of bounds exception - Board string is of incorrect length");
    }

    @Test
    public void incorrectBoardValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> { new Board("Bn00n00y08n00n00n00t99r97n00n00n00n00y44n00r01n00n00r78r22n00n00n00c03y86c04r12n00n00n00n00n00n00y66r08c76r24n00n00n00y22y00r20n00n00n00y82r74n00n00");
        }, "Expected illegal argument exception - There is an incorrect abb Board string withing");
    }
}
