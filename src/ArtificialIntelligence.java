public class ArtificialIntelligence {


  public static Position ia1(Board board, int playerID) {
    Position[] whereToPlay = board.whereToPlay(playerID);
    return whereToPlay[Utils.randomBetweenTwoInt(0, whereToPlay.length - 1)];
  }

  public static Position ia2(Board board, int playerID) {
    Position[] whereToPlay = board.whereToPlay(playerID);
    int[] piecesFlippedByPosition = new int[whereToPlay.length];
    for (int i = 0; i < piecesFlippedByPosition.length; i++) {
      piecesFlippedByPosition[i] = board.countPiecesWillFlip(whereToPlay[i], playerID);
    }
    int maxIndex = 0;
    for (int i = 0; i < piecesFlippedByPosition.length; i++) {
      if (piecesFlippedByPosition[maxIndex] < piecesFlippedByPosition[i]) maxIndex = i;
    }
    return whereToPlay[maxIndex];
  }

  public static Position ia3(Board board, int playerID) {
    return minimax(new Position(0, 0), playerID, playerID, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 3).pos;
  }

  private static Minimax minimax(Position pos, int playerID, int maxPlayer, Board board, int alpha, int beta, int depth) {
    if (board.whereToPlay(playerID).length == 0 || depth == 0) {
      Score score = board.getScore();
      int staticEvalutation = maxPlayer == 1 ? score.getPlayer1() - score.getPlayer2() : score.getPlayer2() - score.getPlayer1();
      return new Minimax(pos, staticEvalutation);
    }
    Position[] positions = board.whereToPlay(playerID);
    Minimax[] evals = {};
    if (maxPlayer == playerID) {
      for (Position position :
          positions) {
        Board newBoard = new Board(board);
        newBoard.switchPlayer();
        newBoard.placePiece(position);
        Minimax currentEvalutation = minimax(position, board.otherPlayerID(playerID), maxPlayer, newBoard, alpha, beta, depth - 1);
        evals = Utils.pushToArray(evals, currentEvalutation);
        alpha = Math.max(currentEvalutation.eval, alpha);
        if (beta <= alpha) break;
      }

      return Utils.getMaxIntoArray(evals);
    } else {
      for (Position position :
          positions) {
        Board newBoard = new Board(board);
        newBoard.switchPlayer();
        newBoard.placePiece(position);
        Minimax currentEvalutation = minimax(position, board.otherPlayerID(playerID), maxPlayer, newBoard, alpha, beta, depth - 1);
        evals = Utils.pushToArray(evals, currentEvalutation);
        alpha = Math.min(currentEvalutation.eval, alpha);
        if (beta <= alpha) break;
      }
      return Utils.getMinIntoArray(evals);
    }
  }
}
