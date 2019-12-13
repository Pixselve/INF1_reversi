public class PlayerSettings {
  private int playerID;
  private int controllerID;
  private boolean haveSwitched = false;

  PlayerSettings(int playerID, int controllerID) {
    this.playerID = playerID;
    this.controllerID = controllerID;
  }

  /**
   * Get the player ID
   *
   * @return The player ID
   */
  public int getPlayerID() {
    return playerID;
  }

  /**
   * Get the controller ID
   *
   * @return The controller ID
   */
  public int getControllerID() {
    return controllerID;
  }

  public boolean isHaveSwitched() {
    return haveSwitched;
  }

  public void setHaveSwitched(boolean haveSwitched) {
    this.haveSwitched = haveSwitched;
  }
}
