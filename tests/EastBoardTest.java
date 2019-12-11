import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EastBoardTest {
  @Test
  void emptyLine() {
    Board board = new Board(4, false);
    board.setPieceAtPosition(new Position(0, 0), 2);
    board.setPieceAtPosition(new Position(1, 0), 2);
    board.setPieceAtPosition(new Position(3, 2), 2);
    board.setPieceAtPosition(new Position(3, 3), 2);

    assertArrayEquals(new Position[]{}, board.east(new Position(1, 0), 1));
  }
  @Test
  void hitMiddle() {
    Board board = new Board(4, false);
    board.setPieceAtPosition(new Position(0, 0), 2);
    board.setPieceAtPosition(new Position(0, 1), 2);
    board.setPieceAtPosition(new Position(3, 2), 2);
    board.setPieceAtPosition(new Position(3, 1), 2);

    Position[] computed = board.east(new Position(0, 2), 1);
    Position[] expected = new Position[]{new Position(1, 2)};
    assertEquals(expected.length, computed.length);
    assertArrayEquals(new int[]{computed[0].getX(), computed[0].getY()}, new int[]{expected[0].getX(), expected[0].getY()});
  }

}