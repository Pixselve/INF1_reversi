public class ArtificialIntelligence {


  /**
   * Choose a position where to play based on the current game board and the player playing. The position is chosen randomly
   *
   * @param board    The game board
   * @param playerID The player id concerned by the AI
   * @return The position where to play chosen by the AI
   */
  public static Position ia1(Board board, int playerID) {
    Position[] whereToPlay = board.whereToPlay(playerID);
//    Return a random position where the player can play
    return whereToPlay[Utils.randomBetweenTwoInt(0, whereToPlay.length - 1)];
  }

  /**
   * Choose a position where to play based on the current game board and the player playing. The position is chosen where the greater of the other's player pieces will be flipped
   *
   * @param board    The game board
   * @param playerID The player id concerned by the AI
   * @return The position where to play chosen by the AI
   */
  public static Position ia2(Board board, int playerID) {
    Position[] whereToPlay = board.whereToPlay(playerID);
    int[] piecesFlippedByPosition = new int[whereToPlay.length];
    for (int i = 0; i < piecesFlippedByPosition.length; i++) {
//      Count the number of the other's player pieces will be flipped
      piecesFlippedByPosition[i] = board.countPiecesWillFlip(whereToPlay[i], playerID);
    }
//    Get the index of the position where the maximum number of the other's player pieces will be flipped
    int maxIndex = 0;
    for (int i = 0; i < piecesFlippedByPosition.length; i++) {
      if (piecesFlippedByPosition[maxIndex] < piecesFlippedByPosition[i]) maxIndex = i;
    }
//    Return the position
    return whereToPlay[maxIndex];
  }

  /**
   * Choose a position where to play based on the current game board and the player playing. The position is chosen using MiniMax algorithm
   *
   * @param board    The game board
   * @param playerID The player id concerned by the AI
   * @return The position where to play chosen by the AI
   */
  public static Position ia3(Board board, int playerID) {
//    Return the result of the MiniMax algorithm
    return miniMax(new Position(0, 0), playerID, playerID, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 3).pos;
  }

  /**
   * Perform a MiniMax algorithm on a board
   *
   * @param pos       The current position
   * @param playerID  The current playerID
   * @param maxPlayer The player we want to maximize
   * @param board     The current game board
   * @param alpha     The alpha limit
   * @param beta      The beta limit
   * @param depth     The current depth of the tree
   * @return The MiniMax output which can the max or min score evaluation of a current position
   */
  private static MiniMaxOutput miniMax(Position pos, int playerID, int maxPlayer, Board board, int alpha, int beta, int depth) {
    if (board.whereToPlay(playerID).length == 0 || depth == 0) {
      //    If the current player can't play OR if we reach the maximum allowed depth
      Score score = board.getScore();
//      Perform a score evaluation that depend of the current player
      int scoreEvaluation = maxPlayer == 1 ? score.getPlayer1() - score.getPlayer2() : score.getPlayer2() - score.getPlayer1();
      return new MiniMaxOutput(pos, scoreEvaluation);
    }
//    Retrieve all the positions where the current player can play
    Position[] positions = board.whereToPlay(playerID);
    MiniMaxOutput[] evals = {};

    if (maxPlayer == playerID) {
      //    If the current player is the player we want to maximise the score evaluation
      for (Position position :
          positions) {
//        Make a copy of the board
        Board newBoard = new Board(board);
//        Switch player from the last board
        newBoard.switchPlayer();
//        Place a piece at the position selected in the loop
        newBoard.placePiece(position);
//        Get the score evaluation of the new board (after the piece was placed)
        MiniMaxOutput scoreEvaluation = miniMax(position, board.otherPlayerID(playerID), maxPlayer, newBoard, alpha, beta, depth - 1);
        evals = Utils.pushToArray(evals, scoreEvaluation);
        alpha = Math.max(scoreEvaluation.eval, alpha);
//        if alpha is greater of equal than beta, we don't need to compute the other path. Break => save some computation
        if (beta <= alpha) break;
      }
// Return the MiniMaxOutput with the greater score evaluation
      return Utils.getMaxIntoArray(evals);
    } else {
      //    If the current player is not the player we want to maximise the score evaluation
      for (Position position :
          positions) {
//        Make a copy of the board
        Board newBoard = new Board(board);
//        Switch player from the last board
        newBoard.switchPlayer();
//        Place a piece at the position selected in the loop
        newBoard.placePiece(position);
//        Get the score evaluation of the new board (after the piece was placed)
        MiniMaxOutput scoreEvaluation = miniMax(position, board.otherPlayerID(playerID), maxPlayer, newBoard, alpha, beta, depth - 1);
        evals = Utils.pushToArray(evals, scoreEvaluation);
        alpha = Math.min(scoreEvaluation.eval, alpha);
//        if alpha is greater of equal than beta, we don't need to compute the other path. Break => save some computation
        if (beta <= alpha) break;
      }
      // Return the MiniMaxOutput with the lower score evaluation
      return Utils.getMinIntoArray(evals);
    }
  }
}
