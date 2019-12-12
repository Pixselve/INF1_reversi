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

  public boolean equals(Position pos) {
    return pos.getY() == getY() && pos.getX() == getX();
  }

  public String toString() {
    return "P(" + getX() + ", " + getY() + ")";
  }
}
