import java.util.Scanner;

public class Main {
  static Game[] savedGames = {};

  public static void main(String[] args) {
    menu();
  }

  /**
   * Display the game menu and allow the user to make a selection
   */
  private static void menu() {
    int userSelection = -1;
    while (userSelection != 3) {

      userSelection = Game.menu();
//      If the user choose to create a new Game
      if (userSelection == 1) {
//        Game creation
        Game game = new Game(Game.askRule(), Game.askBoardSize(), Game.askPlayersSettings());
//        Game playing
        boolean toSave = game.play();
//        If the game have to be saved
        if (toSave) {
//          Saving the game
          savedGames = Utils.pushToArray(savedGames, game);
          System.out.println("╔═════════════════════════════════════════════════╗\n" +
              "║ Partie sauvegardée. Retour au menu principal... ║\n" +
              "╚═════════════════════════════════════════════════╝");
//          Exiting after the save is completed
        }
//        If the user choose to load a Game
      } else if (userSelection == 2) {
        //        Game loading
        Game game = loadGame(savedGames);
        //        Game playing (resume)
        boolean toSave = game.play();
        //        If the game have to be saved
        if (toSave) {
//          Saving the game
          savedGames = Utils.pushToArray(savedGames, game);
          System.out.println("╔═════════════════════════════════════════════════╗\n" +
              "║ Partie sauvegardée. Retour au menu principal... ║\n" +
              "╚═════════════════════════════════════════════════╝");
//          Exiting after the save is completed
        }
      }


    }

  }

  /**
   * Ask the user to select a game to load and return it
   *
   * @param availableSaveGame All the available saved games
   * @return The selected game to load by the user
   */
  private static Game loadGame(Game[] availableSaveGame) {
    if (availableSaveGame.length >= 1) {
//      If there is saved games to load
      System.out.println("╔══════════════════════════════════════╗\n" +
          "║ Charger une partie                   ║\n" +
          "╠══════════════════════════════════════╣\n" +
          "║ Entrez l'identifiant d'une partie... ║\n" +
          "╚══════════════════════════════════════╝");
//      Print all the games with there ID
      for (int i = 0; i < availableSaveGame.length; i++) {
        System.out.println(
            "╔═══╦═════════════════╗\n" +
                "║ " + i + " ║ Partie sans nom ║\n" +
                "╚═══╩═════════════════╝"
        );

      }
//      Ask the player to select a game
      Scanner sc = new Scanner(System.in);
      int gameID = -1;
      while (true) {
        try {
          gameID = sc.nextInt();
          if (gameID >= 0 && gameID < availableSaveGame.length) {
//            Return the selected game to load by the user
            return availableSaveGame[gameID];
          } else {
            throw new Exception("Cet partie n'existe pas");
          }
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    } else {
      System.out.println("╔═════════════════════════════════════════════════════════════════════════════════╗\n" +
          "║ Vous n'avez pas encore de partie enregistrée. Création d'une partie en cours... ║\n" +
          "╚═════════════════════════════════════════════════════════════════════════════════╝");
      return new Game(Game.askRule(), Game.askBoardSize(), Game.askPlayersSettings());
    }

  }
}
