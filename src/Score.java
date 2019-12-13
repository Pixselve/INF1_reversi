public class Score {
  private int player1 = 0;
  private int player2 = 0;

  Score(int player1, int player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  Score() {
  }

  /**
   * Get the score of the player 1
   *
   * @return The score of the player 1
   */
  public int getPlayer1() {
    return player1;
  }

  /**
   * Get the score of the player 2
   *
   * @return The score of the player 2
   */
  public int getPlayer2() {
    return player2;
  }

  /**
   * Set the score of the player 1
   *
   * @param player1 The new score of the player 1
   */
  public void setPlayer1(int player1) {
    this.player1 = player1;
  }

  /**
   * Set the score of the player 2
   *
   * @param player2 The new score of the player 2
   */
  public void setPlayer2(int player2) {
    this.player2 = player2;
  }
}
