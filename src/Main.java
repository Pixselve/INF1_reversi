import java.util.Scanner;

public class Main {
  static Game[] savedGames = {};

  public static void main(String[] args) {
    menu();

  }

  private static void menu() {
    int userSelection = -1;
    while (userSelection != 3) {

      userSelection = Game.menu();
      if (userSelection == 1) {
        Game game = new Game(Game.askRule(), Game.askBoardSize(), Game.askPlayersSettings());
        boolean toSave = game.play();
        if (toSave) {
          savedGames = Utils.pushToArray(savedGames, game);
          System.out.println("╔═════════════════════════════════════════════════╗\n" +
              "║ Partie sauvegardée. Retour au menu principal... ║\n" +
              "╚═════════════════════════════════════════════════╝");
        }
      } else if (userSelection == 2) {
        Game game = loadGame(savedGames);
        boolean toSave = game.play();
        if (toSave) {
          savedGames = Utils.pushToArray(savedGames, game);
          System.out.println("╔═════════════════════════════════════════════════╗\n" +
              "║ Partie sauvegardée. Retour au menu principal... ║\n" +
              "╚═════════════════════════════════════════════════╝");
        }
      }


    }

  }

  private static Game loadGame(Game[] availableSaveGame) {
    if (availableSaveGame.length >= 1) {
      System.out.println("╔══════════════════════════════════════╗\n" +
          "║ Charger une partie                   ║\n" +
          "╠══════════════════════════════════════╣\n" +
          "║ Entrez l'identifiant d'une partie... ║\n" +
          "╚══════════════════════════════════════╝");
      for (int i = 0; i < availableSaveGame.length; i++) {
        System.out.println(
            "╔═══╦═════════════════╗\n" +
                "║ " + i + " ║ Partie sans nom ║\n" +
                "╚═══╩═════════════════╝"
        );

      }
      Scanner sc = new Scanner(System.in);
      int gameID = -1;
      while (true) {
        try {
          gameID = sc.nextInt();
          if (gameID >= 0 && gameID < availableSaveGame.length) {
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
