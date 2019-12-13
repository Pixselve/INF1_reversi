public class PlayerSettings {
  private int playerID;
  private int controllerID;

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
}
