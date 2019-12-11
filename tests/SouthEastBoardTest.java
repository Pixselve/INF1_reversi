import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SouthEastBoardTest {
  @Test
  void lineStoppedByOwnPieceEnd() {
    Board board = new Board(4, false);
    board.setPieceAtPosition(new Position(0, 3), 1);
    board.setPieceAtPosition(new Position(1, 3), 1);
    board.setPieceAtPosition(new Position(2, 3), 1);
    board.setPieceAtPosition(new Position(3, 3), 1);

    board.setPieceAtPosition(new Position(1, 2), 2);
    board.setPieceAtPosition(new Position(2, 2), 2);


    board.setPieceAtPosition(new Position(1, 1), 2);

    Position[] computed = board.southEast(new Position(0, 0), 1);
    Position[] expected = new Position[]{new Position(1, 1), new Position(2, 2)};
    assertEquals(expected.length, computed.length);
    assertArrayEquals(new int[]{computed[0].getX(), computed[0].getY(), expected[1].getX(), expected[1].getY()}, new int[]{computed[0].getX(), computed[0].getY(), expected[1].getX(), expected[1].getY()});
  }

  @Test
  void lineStoppedByOwnPieceMiddle() {
    Board board = new Board(4, false);
    board.setPieceAtPosition(new Position(0, 3), 1);
    board.setPieceAtPosition(new Position(1, 3), 1);
    board.setPieceAtPosition(new Position(2, 3), 1);
    board.setPieceAtPosition(new Position(3, 3), 1);

    board.setPieceAtPosition(new Position(1, 2), 2);
    board.setPieceAtPosition(new Position(2, 2), 2);


    board.setPieceAtPosition(new Position(1, 1), 2);

    Position[] computed = board.southEast(new Position(0, 1), 1);
    Position[] expected = new Position[]{new Position(1, 2)};
    assertEquals(expected.length, computed.length);
    assertArrayEquals(new int[]{computed[0].getX(), computed[0].getY()}, new int[]{expected[0].getX(), expected[0].getY()});
  }
  @Test
  void lineNoEnemyPiece() {
    Board board = new Board(4, false);
    board.setPieceAtPosition(new Position(0, 3), 1);
    board.setPieceAtPosition(new Position(1, 3), 1);
    board.setPieceAtPosition(new Position(2, 3), 1);
    board.setPieceAtPosition(new Position(3, 3), 1);

    board.setPieceAtPosition(new Position(1, 2), 2);
    board.setPieceAtPosition(new Position(2, 2), 2);


    board.setPieceAtPosition(new Position(1, 1), 2);

    Position[] computed = board.southEast(new Position(0, 2), 1);
    Position[] expected = new Position[]{};
    assertEquals(expected.length, computed.length);
  }
  @Test
  void emptyLine() {
    Board board = new Board(4, false);
    board.setPieceAtPosition(new Position(0, 3), 1);
    board.setPieceAtPosition(new Position(1, 3), 1);
    board.setPieceAtPosition(new Position(2, 3), 1);
    board.setPieceAtPosition(new Position(3, 3), 1);

    board.setPieceAtPosition(new Position(1, 2), 2);
    board.setPieceAtPosition(new Position(2, 2), 2);


    board.setPieceAtPosition(new Position(1, 1), 2);


    Position[] computed = board.southEast(new Position(2, 0), 1);
    Position[] expected = new Position[]{};
    assertEquals(expected.length, computed.length);
  }
}