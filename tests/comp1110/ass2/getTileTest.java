package comp1110.ass2;

import comp1110.ass2.Board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class getTileTest {
    @Test
    public void getTileTest() {
        Board board = new Board("n00n00y08n00n00n00n00r97n00n00n00n00y44n00r01n00n00r78r22n00n00n00c03y86c04r12n00n00n00n00n00n00y66r08c76r24n00n00n00y22y00r20n00n00n00y82r74n00n00");

        String tileFound = board.getTile(1,0);

        Assertions.assertEquals("r97", tileFound);
    }

    @Test
    public void outOfBoundsTile() {
        Board board = new Board("n00n00y08n00n00n00n00r97n00n00n00n00y44n00r01n00n00r78r22n00n00n00c03y86c04r12n00n00n00n00n00n00y66r08c76r24n00n00n00y22y00r20n00n00n00y82r74n00n00");

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            board.getTile(8, 8);
        }, "Expected Index out of Bounds Exception- Tile is out of bounds");
    }
}
