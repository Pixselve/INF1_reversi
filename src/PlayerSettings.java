public class PlayerSettings {
  private int playerID;
  private int controllerID;

  PlayerSettings(int playerID, int controllerID) {
    this.playerID = playerID;
    this.controllerID = controllerID;
  }

  public int getPlayerID() {
    return playerID;
  }

  public int getControllerID() {
    return controllerID;
  }
}
