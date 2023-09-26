package comp1110.ass2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testSetTileValid() {
        Board board = new Board();

        String rugState = "c01";
        int row = 3;
        int col = 4;

        board.setTile(rugState, row, col);
        assertEquals(rugState, board.getTile(row, col), "The rug state at the specified row and col should match the set value");
    }

    @Test
    public void testSetTileBoundaryBegin() {
        Board board = new Board();

        String rugState = "y02";
        int row = 0;
        int col = 0;

        board.setTile(rugState, row, col);
        assertEquals(rugState, board.getTile(row, col), "The rug state at the beginning of the board should match the set value");
    }

    @Test
    public void testSetTileBoundaryEnd() {
        Board board = new Board();

        String rugState = "r03";
        int row = 6;  // Assuming 7x7 board, so index goes from 0 to 6
        int col = 6;

        board.setTile(rugState, row, col);
        assertEquals(rugState, board.getTile(row, col), "The rug state at the end of the board should match the set value");
    }

    @Test
    public void testSetTileOverwrite() {
        Board board = new Board();

        String initialRugState = "p04";
        String newRugState = "y05";
        int row = 2;
        int col = 5;

        board.setTile(initialRugState, row, col);
        board.setTile(newRugState, row, col);

        assertEquals(newRugState, board.getTile(row, col), "The rug state should be overwritten with the new value");
    }

    @Test
    public void testSetTileInvalidPosition() {
        Board board = new Board();

        String rugState = "c06";
        int row = 8;  // Outside of the valid board indices
        int col = 10; // Outside of the valid board indices

        assertThrows(IndexOutOfBoundsException.class, () -> board.setTile(rugState, row, col), "Setting tile outside of board boundaries should throw exception");
    }

}

