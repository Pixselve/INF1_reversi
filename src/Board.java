import java.util.Arrays;

public class Board {
  private int[][] board;
  private int player;

  /**
   * Create a new board based on a size and a rule
   *
   * @param size The size of the desired board
   * @param rule The desired rule
   */
  Board(int size, boolean rule) {
    if (size <= 0 || size % 2 != 0)
      throw new Error("A size of " + size + " is not allowed. Please specify an even and greater than 0 size");
    board = new int[size][size];
    int middle = size / 2;
    if (rule) {
      board[middle][middle] = 2;
      board[middle][middle - 1] = 1;
      board[middle - 1][middle] = 2;
      board[middle - 1][middle - 1] = 1;
    } else {
      board[middle][middle] = 1;
      board[middle][middle - 1] = 2;
      board[middle - 1][middle] = 2;
      board[middle - 1][middle - 1] = 1;
    }
    player = 1;
  }

  /**
   * Create a copy of an existing board
   *
   * @param board The board from which we want to create the new one
   */
  Board(Board board) {
//    Copy the board
    this.board = copy2DArray(board.getBoard());
//    Use the same current player as the old one
    this.player = board.getPlayer();
  }

  /**
   * Get the board size
   *
   * @return The board size
   */
  public int size() {
    return board.length;
  }

  /**
   * Check if a position is valid on the board
   *
   * @param pos The position we want to check
   * @return A boolean that represent if the position is on the board
   */
  public boolean isPositionCorrect(Position pos) {
    return pos.getX() < size() && pos.getY() < size() && pos.getX() >= 0 && pos.getY() >= 0;
  }

  /**
   * Get the piece at a given position
   *
   * @param pos The position where we want to get the piece
   * @return The piece value
   */
  public int getPieceAtPosition(Position pos) {
    return isPositionCorrect(pos) ? board[pos.getY()][pos.getX()] : -1;
  }

  /**
   * Set the piece at a given position
   *
   * @param pos   The position where we want to set the piece
   * @param value The value of the piece
   */
  public void setPieceAtPosition(Position pos, int value) {
    if (value >= 0 && value <= 2) {
      board[pos.getY()][pos.getX()] = value;
    }
  }

  /**
   * Check if a player piece is present on the board at a given position
   *
   * @param pos The position where we want to check
   * @return A boolean that represent if a players's (any) piece is at this position
   */
  public boolean containAPlayerPiece(Position pos) {
    return getPieceAtPosition(pos) != 0;
  }

  /**
   * Check if a given player piece is present on the board at a given position
   *
   * @param pos    The position where we want to check
   * @param player The player
   * @return A boolean that represent if a piece of the player passed in params is present at the position
   */
  public boolean containAPlayerPiece(Position pos, int player) {
    return getPieceAtPosition(pos) == player;
  }

  /**
   * Get all the positions where a given player have a piece
   *
   * @param player The player we are interested in
   * @return All the player's pieces positions on, the board
   */
  public Position[] getPlayerPositions(int player) {
    Position[] result = {};
    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[y].length; x++) {
//        Loop over all the positions on the board
        Position pos = new Position(x, y);
        if (containAPlayerPiece(pos) && getPieceAtPosition(pos) == player) {
          result = Utils.pushToArray(result, pos);
        }
      }
    }
    return result;
  }

  /**
   * Flip a piece on the board at a given position
   *
   * @param pos The position where to flip the piece
   * @return Itself
   */
  public Board flipPiece(Position pos) {
    if (containAPlayerPiece(pos)) {
      setPieceAtPosition(pos, otherPlayerID(getPieceAtPosition(pos)));
    }
    return this;
  }

  /**
   * Make a copy of a 2D array
   *
   * @param arr The array we want to copy
   * @return The clone of the given array
   */
  private static int[][] copy2DArray(int[][] arr) {
    int[][] result = new int[arr.length][];
    for (int i = 0; i < arr.length; i++) {
      result[i] = new int[arr[i].length];
      for (int j = 0; j < arr[i].length; j++) {
        result[i][j] = arr[i][j];
      }
    }
    return result;
  }

  /**
   * Check if a input string represent a valid position on the board
   *
   * @param input The input string
   * @return A boolean that represent if a given string input represent a valid position on the board
   */
  public boolean checkPositionStringFormat(String input) {
//    If the input do not respect the pattern 99A, return false
    if (!input.matches("^\\d{1,2}[\\w]$")) return false;
    int row = Integer.parseInt(input.substring(0, input.length() - 1));
    char letterChar = input.charAt(input.length() - 1);
    int column = letterChar <= 90 ? letterChar - 'A' : letterChar - 'A' - 6;
    return isPositionCorrect(new Position(row, column));
  }

  /**
   * Convert a string that represent a position on the board to a Position
   *
   * @param str The input string
   * @return The position converted from the string
   */
  public Position getPositionFromString(String str) {
    if (!checkPositionStringFormat(str)) throw new Error("Invalid Position");
    char letterChar = str.charAt(str.length() - 1);
    int letterToInt = letterChar <= 90 ? letterChar - 'A' : letterChar - 'A' - 6;
    int firstInt = Integer.parseInt(str.substring(0, str.length() - 1));
    return new Position(letterToInt, firstInt);
  }

  /**
   * Get the players score
   *
   * @return The players score
   */
  public Score getScore() {
    int[] occurScoreTable = new int[3];
    for (int y = 0; y < size(); y++) {
      for (int x = 0; x < size(); x++) {
        occurScoreTable[getPieceAtPosition(new Position(x, y))]++;
      }
    }
    return new Score(occurScoreTable[1], occurScoreTable[2]);
  }

  /**
   * Display the board without any highlighted positions
   */
  public void display() {
    display(new Position[]{});
  }

  /**
   * Display the board with highlighted positions
   *
   * @param highlightedPositions The positions we want to be highlighted on the board
   */
  public void display(Position[] highlightedPositions) {

    int max = String.valueOf(size()).length();
//    Generate the letters line
    StringBuilder lettersLine = new StringBuilder("").append(" ".repeat(max)).append("|");
    for (int i = 0; i < size(); i++) {
//      Append to the line |{letter}. Example |A or |B
      int space = 'A' + i <= 90 ? i : i + 6;
      lettersLine.append((char) ('A' + space)).append("|");
    }
//    Display the letters line
    System.out.println(lettersLine);


    int[][] boardWithHighlight = new int[size()][size()];

    for (int y = 0; y < size(); y++) {
      for (int x = 0; x < size(); x++) {
        boardWithHighlight[y][x] = getPieceAtPosition(new Position(x, y));
      }
    }
    for (Position highlightedPosition :
        highlightedPositions) {
      boardWithHighlight[highlightedPosition.getY()][highlightedPosition.getX()] = getPieceAtPosition(highlightedPosition) == 0 ? 3 : boardWithHighlight[highlightedPosition.getY()][highlightedPosition.getX()];
    }
//    Generate the numbers lines
    for (int y = 0; y < size(); y++) {
      StringBuilder numberLine = new StringBuilder(" ".repeat(max - String.valueOf(y).length()) + y + "|");
      for (int x = 0; x < size(); x++) {
        if (boardWithHighlight[y][x] == 1) {
          numberLine.append("X|");
        } else if (boardWithHighlight[y][x] == 2) {
          numberLine.append("O|");
        } else if (boardWithHighlight[y][x] == 3) {
          numberLine.append("#|");
        } else {
          numberLine.append(" |");
        }
      }
      System.out.println(numberLine);
    }
  }

  /**
   * Get the other player ID
   *
   * @param player The current player
   * @return The other player ID
   */
  public int otherPlayerID(int player) {
    return player == 1 ? 2 : 1;
  }

  /**
   * Check if a player can place a piece at a given position by applying the rules
   *
   * @param pos    The position we want to check
   * @param player The player responsible to the move
   * @return A boolean that represent if the piece placement is correct
   */
  public boolean isThePlacementCorrect(Position pos, int player) {
    return isPositionCorrect(pos) && !containAPlayerPiece(pos) && (
        countPiecesWillFlip(pos, player) > 0
    );
  }

  /**
   * Count the number of the other player piece that will be flip by a move at a given position
   *
   * @param pos    The current position
   * @param player The player responsible for the move
   * @return The number of the other player piece that will be flip by the move
   */
  public int countPiecesWillFlip(Position pos, int player) {
    if (isPositionCorrect(pos) && !containAPlayerPiece(pos)) {
      return north(pos, player).length + south(pos, player).length + east(pos, player).length + west(pos, player).length + northEast(pos, player).length + northWest(pos, player).length + southEast(pos, player).length + southWest(pos, player).length;
    } else {
      return 0;
    }
  }

  /**
   * Check if the current player can place a piece at a given position by applying the rules
   *
   * @param pos The position we want to check
   * @return A boolean that represent if the piece placement is correct
   */
  public boolean isThePlacementCorrect(Position pos) {
    return isThePlacementCorrect(pos, player);
  }

  /**
   * Count the pieces flipped by a move. Direction NORTH
   *
   * @param pos    The position where we want to check
   * @param player The player responsible for the move
   * @return The pieces flipped by the move
   */
  public Position[] north(Position pos, int player) {
    Position[] result = {};

    for (int y = pos.getY() - 1; y >= 0; y--) {
      Position actualPosition = new Position(pos.getX(), y);
      if (!isPositionCorrect(actualPosition)) {
        break;
      } else if (containAPlayerPiece(actualPosition, player)) {
        return result;
      } else if (!containAPlayerPiece(actualPosition)) {
        break;
      } else if (getPieceAtPosition(actualPosition) != player) {
        result = Utils.pushToArray(result, actualPosition);
      }
    }
    return new Position[]{};
  }

  /**
   * Count the pieces flipped by a move. Direction SOUTH
   *
   * @param pos    The position where we want to check
   * @param player The player responsible for the move
   * @return The pieces flipped by the move
   */
  public Position[] south(Position pos, int player) {
    Position[] result = {};

    for (int y = pos.getY() + 1; y < size(); y++) {
      Position actualPosition = new Position(pos.getX(), y);
      if (!isPositionCorrect(actualPosition)) {
        break;
      } else if (containAPlayerPiece(actualPosition, player)) {
        return result;
      } else if (!containAPlayerPiece(actualPosition)) {
        break;
      } else if (getPieceAtPosition(actualPosition) != player) {
        result = Utils.pushToArray(result, actualPosition);
      }
    }
    return new Position[]{};
  }

  /**
   * Count the pieces flipped by a move. Direction EAST
   *
   * @param pos    The position where we want to check
   * @param player The player responsible for the move
   * @return The pieces flipped by the move
   */
  public Position[] east(Position pos, int player) {
    Position[] result = {};

    for (int x = pos.getX() + 1; x < size(); x++) {
      Position actualPosition = new Position(x, pos.getY());
      if (!isPositionCorrect(actualPosition)) {
        break;
      } else if (containAPlayerPiece(actualPosition, player)) {
        return result;
      } else if (!containAPlayerPiece(actualPosition)) {
        break;
      } else if (getPieceAtPosition(actualPosition) != player) {
        result = Utils.pushToArray(result, actualPosition);
      }
    }
    return new Position[]{};
  }

  /**
   * Count the pieces flipped by a move. Direction WEST
   *
   * @param pos    The position where we want to check
   * @param player The player responsible for the move
   * @return The pieces flipped by the move
   */
  public Position[] west(Position pos, int player) {
    Position[] result = {};

    for (int x = pos.getX() - 1; x >= 0; x--) {
      Position actualPosition = new Position(x, pos.getY());
      if (!isPositionCorrect(actualPosition)) {
        break;
      } else if (containAPlayerPiece(actualPosition, player)) {
        return result;
      } else if (!containAPlayerPiece(actualPosition)) {
        break;
      } else if (getPieceAtPosition(actualPosition) != player) {
        result = Utils.pushToArray(result, actualPosition);
      }
    }
    return new Position[]{};
  }

  /**
   * Count the pieces flipped by a move. Direction NORTH EAST
   *
   * @param pos    The position where we want to check
   * @param player The player responsible for the move
   * @return The pieces flipped by the move
   */
  public Position[] northEast(Position pos, int player) {
    Position[] result = {};

    for (int i = 1; i < size(); i++) {
      Position actualPosition = new Position(pos.getX() + i, pos.getY() - i);
      if (!isPositionCorrect(actualPosition)) {
        break;
      } else if (containAPlayerPiece(actualPosition, player)) {
        return result;
      } else if (!containAPlayerPiece(actualPosition)) {
        break;
      } else if (getPieceAtPosition(actualPosition) != player) {
        result = Utils.pushToArray(result, actualPosition);
      }
    }
    return new Position[]{};
  }

  /**
   * Count the pieces flipped by a move. Direction NORTH WEST
   *
   * @param pos    The position where we want to check
   * @param player The player responsible for the move
   * @return The pieces flipped by the move
   */
  public Position[] northWest(Position pos, int player) {
    Position[] result = {};

    for (int i = 1; i < size(); i++) {
      Position actualPosition = new Position(pos.getX() - i, pos.getY() - i);
      if (!isPositionCorrect(actualPosition)) {
        break;
      } else if (containAPlayerPiece(actualPosition, player)) {
        return result;
      } else if (!containAPlayerPiece(actualPosition)) {
        break;
      } else if (getPieceAtPosition(actualPosition) != player) {
        result = Utils.pushToArray(result, actualPosition);
      }
    }
    return new Position[]{};
  }

  /**
   * Count the pieces flipped by a move. Direction SOUTH EAST
   *
   * @param pos    The position where we want to check
   * @param player The player responsible for the move
   * @return The pieces flipped by the move
   */
  public Position[] southEast(Position pos, int player) {
    Position[] result = {};

    for (int i = 1; i < size(); i++) {
      Position actualPosition = new Position(pos.getX() + i, pos.getY() + i);
      if (!isPositionCorrect(actualPosition)) {
        break;
      } else if (containAPlayerPiece(actualPosition, player)) {
        return result;
      } else if (!containAPlayerPiece(actualPosition)) {
        break;
      } else if (getPieceAtPosition(actualPosition) != player) {
        result = Utils.pushToArray(result, actualPosition);
      }
    }
    return new Position[]{};
  }

  /**
   * Count the pieces flipped by a move. Direction SOUTH WEST
   *
   * @param pos    The position where we want to check
   * @param player The player responsible for the move
   * @return The pieces flipped by the move
   */
  public Position[] southWest(Position pos, int player) {
    Position[] result = {};

    for (int i = 1; i < size(); i++) {
      Position actualPosition = new Position(pos.getX() - i, pos.getY() + i);
      if (!isPositionCorrect(actualPosition)) {
        break;
      } else if (containAPlayerPiece(actualPosition, player)) {
        return result;
      } else if (!containAPlayerPiece(actualPosition)) {
        break;
      } else if (getPieceAtPosition(actualPosition) != player) {
        result = Utils.pushToArray(result, actualPosition);
      }
    }
    return new Position[]{};
  }

  /**
   * Get the positions where the current player can play
   *
   * @return The positions where the player can play
   */
  public Position[] whereToPlay() {
    return whereToPlay(player);
  }

  /**
   * Get the positions where a given player can play
   *
   * @param player The player ID
   * @return The positions where the player can play
   */
  public Position[] whereToPlay(int player) {
    Position[] enemyPositions = getPlayerPositions(otherPlayerID(player));
    Position[] availablePosition = {};
    for (Position enemyPosition :
        enemyPositions) {

      for (int x = enemyPosition.getX() - 1; x <= enemyPosition.getX() + 1; x++) {
        Position newPositionGreaterY = new Position(x, enemyPosition.getY() + 1);
        Position newPositionSameY = new Position(x, enemyPosition.getY());
        Position newPositionLesserY = new Position(x, enemyPosition.getY() - 1);
        if (this.isThePlacementCorrect(newPositionGreaterY, player))
          availablePosition = Utils.pushToArray(availablePosition, newPositionGreaterY);
        if (this.isThePlacementCorrect(newPositionSameY, player))
          availablePosition = Utils.pushToArray(availablePosition, newPositionSameY);
        if (this.isThePlacementCorrect(newPositionLesserY, player))
          availablePosition = Utils.pushToArray(availablePosition, newPositionLesserY);
      }
    }
    return availablePosition;
  }

  /**
   * @return A boolean that represent if the both players can play
   */
  public boolean canBothPlayersPlay() {
    return whereToPlay(1).length > 0 || whereToPlay(2).length > 0;
  }

  /**
   * Switch the current player
   */
  public void switchPlayer() {
    player = otherPlayerID(player);
  }

  /**
   * Flip an array of positions
   *
   * @param positions The positions where we want to flip the pieces
   */
  public void flipPieces(Position[] positions) {
    for (Position position :
        positions) {
      flipPiece(position);
    }
  }

  /**
   * Place a piece of the current player on the board
   *
   * @param pos The position where we want to put a piece
   * @return A boolean that represent if the piece have been placed
   */
  public boolean placePiece(Position pos) {
    return placePiece(pos, player);
  }

  /**
   * Place a piece of a given player on the board
   *
   * @param pos The position where we want to put a piece
   * @return A boolean that represent if the piece have been placed
   */
  public boolean placePiece(Position pos, int player) {
    if (isThePlacementCorrect(pos)) {
      setPieceAtPosition(pos, player);
      flipPieces(north(pos, player));
      flipPieces(south(pos, player));
      flipPieces(east(pos, player));
      flipPieces(west(pos, player));
      flipPieces(northEast(pos, player));
      flipPieces(northWest(pos, player));
      flipPieces(southEast(pos, player));
      flipPieces(southWest(pos, player));
      flipPieces(southWest(pos, player));
      return true;
    } else {
      return false;
    }
  }

  /**
   * Get the current player
   *
   * @return The current player
   */
  public int getPlayer() {
    return player;
  }

  /**
   * Get the current board state
   *
   * @return The board state
   */
  public int[][] getBoard() {
    return board;
  }
}
