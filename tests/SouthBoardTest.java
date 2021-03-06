import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SouthBoardTest {
  @Test
  void topRightCornerWithoutOwnPieceToHit() {
    Board board = new Board(4, false);
    board.setPieceAtPosition(new Position(0, 0), 2);
    board.setPieceAtPosition(new Position(1, 0), 2);
    board.setPieceAtPosition(new Position(3, 2), 2);
    board.setPieceAtPosition(new Position(3, 3), 2);

    assertArrayEquals(new Position[]{}, board.south(new Position(3, 0), 1));
  }

  @Test
  void topRightCornerWithOwnPieceToHit() {
    Board board = new Board(4, false);
    board.setPieceAtPosition(new Position(0, 0), 2);
    board.setPieceAtPosition(new Position(1, 0), 2);
    board.setPieceAtPosition(new Position(3, 1), 2);
    board.setPieceAtPosition(new Position(3, 2), 2);

    board.setPieceAtPosition(new Position(3, 3), 1);

    Position[] computed = board.south(new Position(3, 0), 1);
    Position[] expected = new Position[]{new Position(3, 1), new Position(3, 2)};
    assertEquals(expected.length, computed.length);
    assertArrayEquals(new int[]{computed[0].getX(), computed[1].getX(), computed[0].getY(), computed[1].getY()}, new int[]{expected[0].getX(), expected[1].getX(), expected[0].getY(), expected[1].getY()});
  }
  @Test
  void middleWithOwnPieceToHitNotBottom() {
    Board board = new Board(4, false);
    board.setPieceAtPosition(new Position(0, 0), 2);
    board.setPieceAtPosition(new Position(1, 0), 2);
    board.setPieceAtPosition(new Position(3, 1), 2);
    board.setPieceAtPosition(new Position(3, 2), 2);

    board.setPieceAtPosition(new Position(3, 3), 1);

    Position[] computed = board.south(new Position(2, 0), 1);
    Position[] expected = new Position[]{new Position(2, 1)};
    assertEquals(expected.length, computed.length);
    assertArrayEquals(new int[]{computed[0].getX(), computed[0].getY()}, new int[]{expected[0].getX(), expected[0].getY()});
  }
}