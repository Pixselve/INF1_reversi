import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

  @Test
  void size() {
    assertEquals(8, new Board(8, false).size());
    assertEquals(4, new Board(4, true).size());
//    Test with an invalid size
    assertThrows(Error.class, () -> new Board(5, false));
  }

  @Test
  void isPositionCorrect() {
    assertTrue(new Board(8, false).isPositionCorrect(new Position(0, 0)));
    assertTrue(new Board(8, false).isPositionCorrect(new Position(7, 7)));
    assertFalse(new Board(8, false).isPositionCorrect(new Position(8, 8)));

    assertFalse(new Board(8, false).isPositionCorrect(new Position(0, 12)));
    assertFalse(new Board(8, false).isPositionCorrect(new Position(12, 0)));

  }

  @Test
  void getPieceAtPosition() {
    assertEquals(2, new Board(4, false).getPieceAtPosition(new Position(2, 1)));
    assertEquals(1, new Board(4, false).getPieceAtPosition(new Position(2, 2)));

    assertEquals(0, new Board(4, false).getPieceAtPosition(new Position(0, 0)));

    assertEquals(1, new Board(4, true).getPieceAtPosition(new Position(2, 1)));
    assertEquals(1, new Board(4, true).getPieceAtPosition(new Position(2, 2)));
  }

  @Test
  void containAPlayerPiece() {
    assertTrue(new Board(4, false).containAPlayerPiece(new Position(2, 1)));
    assertTrue(new Board(4, false).containAPlayerPiece(new Position(2, 2)));

    assertFalse(new Board(4, false).containAPlayerPiece(new Position(0, 0)));

    assertTrue(new Board(4, true).containAPlayerPiece(new Position(2, 1)));
    assertTrue(new Board(4, true).containAPlayerPiece(new Position(2, 2)));
  }

  @Test
  void testContainAPlayerPiece() {
    assertTrue(new Board(4, false).containAPlayerPiece(new Position(2, 1), 2));
    assertTrue(new Board(4, false).containAPlayerPiece(new Position(2, 2), 1));

    assertFalse(new Board(4, false).containAPlayerPiece(new Position(0, 0), 1));

    assertTrue(new Board(4, true).containAPlayerPiece(new Position(2, 1), 1));
    assertTrue(new Board(4, true).containAPlayerPiece(new Position(2, 2), 1));

    assertFalse(new Board(4, true).containAPlayerPiece(new Position(2, 1), 2));
    assertFalse(new Board(4, true).containAPlayerPiece(new Position(2, 2), 2));
  }

  @Test
  void flipPiece() {
    assertEquals(1, new Board(4, false).flipPiece(new Position(2, 1)).getPieceAtPosition(new Position(2, 1)));
    assertEquals(2, new Board(4, false).flipPiece(new Position(2, 2)).getPieceAtPosition(new Position(2, 2)));

    assertEquals(0, new Board(4, false).flipPiece(new Position(0, 0)).getPieceAtPosition(new Position(0, 0)));

    assertEquals(2, new Board(4, true).flipPiece(new Position(2, 1)).getPieceAtPosition(new Position(2, 1)));
    assertEquals(2, new Board(4, true).flipPiece(new Position(2, 2)).getPieceAtPosition(new Position(2, 2)));
  }

}