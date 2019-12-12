import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WhereToPlayBoardTest {
  @Test
  void player1() {
    Board board = new Board(4, false);
    assertEquals(4, board.whereToPlay(1).length);
  }

  @Test
  void player2() {
    Board board = new Board(4, false);
    assertEquals(4, board.whereToPlay(2).length);
  }
  @Test
  void player1AfterPlayer1Round() {
    Board board = new Board(4, false);
    board.placePiece(new Position(3, 1));
    assertEquals(3, board.whereToPlay(1).length);
  }
  @Test
  void player2AfterPlayer1Round() {
    Board board = new Board(4, false);
    board.placePiece(new Position(3, 1));
    assertEquals(7, board.whereToPlay(2).length);
  }
}