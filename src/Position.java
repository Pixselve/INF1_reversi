public class Position {
  private int x;
  private int y;

  Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @return The X axis of the position
   */
  public int getX() {
    return x;
  }

  /**
   * @return The Y axis of the position
   */
  public int getY() {
    return y;
  }

  /**
   * Check if the position is equal to another
   *
   * @param pos The position we want to verify
   * @return If the two positions are equals
   */
  public boolean equals(Position pos) {
    return pos.getY() == getY() && pos.getX() == getX();
  }

  /**
   * Convert a position to a string
   *
   * @return The position converted to string
   */
  public String toString() {
    return "P(" + getX() + ", " + getY() + ")";
  }
}
