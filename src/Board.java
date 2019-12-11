import java.util.Arrays;

public class Board {
  private int[][] board;
  private int player;

  Board(int size, boolean rule) {
    if (size <= 0 || size % 2 != 0)
      throw new Error("A size of " + size + " is not allowed. Please specify an even and greater than 0 size");
    board = new int[size][size];
    int middle = size / 2;
    if (rule) {
      board[middle][middle] = 1;
      board[middle][middle - 1] = 2;
      board[middle - 1][middle] = 1;
      board[middle - 1][middle - 1] = 2;
    } else {
      board[middle][middle] = 1;
      board[middle][middle - 1] = 2;
      board[middle - 1][middle] = 2;
      board[middle - 1][middle - 1] = 1;
    }
    player = 1;
  }

  /**
   * @return The board size
   */
  public int size() {
    return board.length;
  }

  /**
   * @param pos The position we want to check
   * @return A boolean that represent if the position is on the board
   */
  public boolean isPositionCorrect(Position pos) {
    return pos.getX() < size() && pos.getY() < size() && pos.getX() >= 0 && pos.getY() >= 0;
  }

  /**
   * @param pos The position where we want to get the piece
   * @return The piece value
   */
  public int getPieceAtPosition(Position pos) {
    return isPositionCorrect(pos) ? board[pos.getY()][pos.getX()] : -1;
  }

  /**
   * @param pos   The position where we want to set the piece
   * @param value The value of the piece
   */
  public void setPieceAtPosition(Position pos, int value) {
    if (value >= 0 && value <= 2) {
      board[pos.getY()][pos.getX()] = value;
    }
  }

  /**
   * @param pos The position where we want to check
   * @return A boolean that represent if a players's (any) piece is at this position
   */
  public boolean containAPlayerPiece(Position pos) {
    return getPieceAtPosition(pos) != 0;
  }

  /**
   * @param pos    The position where we want to check
   * @param player The player
   * @return A boolean that represent if a piece of the player passed in params is present at the position
   */
  public boolean containAPlayerPiece(Position pos, int player) {
    return getPieceAtPosition(pos) == player;
  }

  /**
   * @param player The player we are interested in
   * @return All the player's pieces positions on, the board
   */
  public Position[] getPlayerPositions(int player) {
    Position[] result = {};
    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[y].length; x++) {
        Position pos = new Position(x, y);

        if (containAPlayerPiece(pos) && getPieceAtPosition(pos) != player) {
          result = Utils.pushToArray(result, pos);
        }
      }
    }
    return result;
  }

  /**
   * @param pos The position where to flip the piece
   * @return Itself
   */
  public Board flipPiece(Position pos) {
    if (containAPlayerPiece(pos)) {
      setPieceAtPosition(pos, otherPlayerID(getPieceAtPosition(pos)));
    }
    return this;
  }

  public boolean checkPositionStringFormat(String input) {
//    If the input do not respect the pattern 99A, return false
    if (!input.matches("^\\d{1,2}[\\w]$")) return false;
    int row = Integer.parseInt(input.substring(0, input.length() - 1));
    int column = input.charAt(input.length() - 1) - 'A';
    return isPositionCorrect(new Position(row, column));
  }

  public Position getPositionFromString(String str) {
    if (!checkPositionStringFormat(str)) throw new Error("Invalid Position");
    char letterChar = str.charAt(str.length() - 1);
    int letterToInt = letterChar <= 90 ? letterChar - 'A' : letterChar - 'A' - 6;
    int firstInt = Integer.parseInt(str.substring(0, str.length() - 1));
    return new Position(letterToInt, firstInt);
  }

  /**
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

  public void display() {
    display(new Position[]{});
  }

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

  public int otherPlayerID(int player) {
    return player == 1 ? 2 : 1;
  }

  public boolean isThePlacementCorrect(Position pos, int player) {
    return isPositionCorrect(pos) && !containAPlayerPiece(pos) && (
        north(pos, player).length > 0 ||
            south(pos, player).length > 0 ||
            east(pos, player).length > 0 ||
            west(pos, player).length > 0 ||
            northEast(pos, player).length > 0 ||
            northWest(pos, player).length > 0 ||
            southEast(pos, player).length > 0 ||
            southWest(pos, player).length > 0
    );
  }

  public boolean isThePlacementCorrect(Position pos) {
    return isThePlacementCorrect(pos, player);
  }

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

  public Position[] whereToPlay() {
    return whereToPlay(player);
  }

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

  public boolean canBothPlayersPlay() {
    return whereToPlay(1).length > 0 || whereToPlay(2).length > 0;
  }

  public void switchPlayer() {
    player = otherPlayerID(player);
  }

  public void flipPieces(Position[] positions) {
    for (Position position :
        positions) {
      flipPiece(position);
    }
  }

  public boolean placePiece(Position pos) {
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

  public int getPlayer() {
    return player;
  }
}
