import java.util.Scanner;

public class Game {
  Board board;
  PlayerSettings[] settings;

  boolean rule;

  Game(boolean rule, int boardSize, PlayerSettings[] settings) {
    this.rule = rule;
    this.board = new Board(boardSize, rule);
    this.settings = settings;
  }

  /**
   * Play or resume the current game
   *
   * @return A boolean that represent if we have to save or not the game
   */
  public boolean play() {
    System.out.println(rule);
    System.out.println(settings[0].isHaveSwitched());
    System.out.println(settings[1].isHaveSwitched());
    while (rule ? board.canBothPlayersPlay() && !(settings[0].isHaveSwitched() && settings[1].isHaveSwitched()) : board.canBothPlayersPlay()) {
      System.out.println(rule);
      System.out.println(settings[0].isHaveSwitched());
      System.out.println(settings[1].isHaveSwitched());
      String roundResult = round();
      if (roundResult.equals("SaveTheGame")) {
        return true;
      }
      if (roundResult.equals("DoNotSave")) {
        return false;
      }

    }
    endGame();
    return false;
  }


  /**
   * Play a round
   *
   * @return A string that indicate if we have to save or not the game
   */
  private String round() {
    int controllerID = settings[board.getPlayer() - 1].getControllerID();
    if (board.whereToPlay().length > 0) {
      displayInformation();
      if (controllerID == 1) {
//      Real player
        InputAndSelection menuSelection = inGameMenu(board.getPlayer());
        if (menuSelection.getSelection() == 1) {
//          Save the game
          return "SaveTheGame";
        } else if (menuSelection.getSelection() == 4) {
//          Pass the round
          settings[board.getPlayer() - 1].setHaveSwitched(true);
        } else if (menuSelection.getSelection() == 5) {
//          Play
          settings[board.getPlayer() - 1].setHaveSwitched(false);
          board.placePiece(board.getPositionFromString(menuSelection.getInput()));

        } else {
//          Exit
          return "DoNotSave";
        }
      } else if (controllerID == 2) {
//      AI 1
        board.placePiece(ArtificialIntelligence.ia1(board, board.getPlayer()));
      } else if (controllerID == 3) {
//      AI 2
        board.placePiece(ArtificialIntelligence.ia2(board, board.getPlayer()));
      } else {
//      AI 3
        board.placePiece(ArtificialIntelligence.ia3(board, board.getPlayer()));

      }

    } else {
      System.out.println("Vous ne pouvez pas jouer, passage au joueur suivant...");
    }


    board.switchPlayer();
    return "";
  }

  /**
   * Display the end of the game information
   */
  private void endGame() {
    Score score = board.getScore();
    if (rule && Math.abs(score.getPlayer1() - score.getPlayer2()) < 5 || !rule && score.getPlayer2() == score.getPlayer1()) {
      System.out.println("╔════════════════════════════╗\n" +
          "║ Fin de la partie !         ║\n" +
          "╠════════════════════════════╣\n" +
          "║ Il n'y a pas de gagnant ;( ║\n" +
          "╚════════════════════════════╝");
    } else {
      int winner = Math.max(score.getPlayer1(), score.getPlayer2()) == score.getPlayer1() ? 1 : 2;
      System.out.println(
          "╔══════════════════════════════════════════════════╗\n" +
              "║ Fin de la partie !                               ║\n" +
              "╠══════════════════════════════════════════════════╣\n" +
              "║ Félicitations au joueur " + winner + " qui remporte la partie ║\n" +
              "╚══════════════════════════════════════════════════╝"
      );
    }

  }

  /**
   * Display the score and the current board
   */
  private void displayInformation() {
    displayScore();
    board.display();
  }

  /**
   * Ask the player for a board size
   *
   * @return The board size
   */
  public static int askBoardSize() {
    System.out.println(
        "╔═══════════════════════════════════════╗\n" +
            "║ Veuillez saisir une taille de plateau ║\n" +
            "╚═══════════════════════════════════════╝"
    );
    Scanner sc = new Scanner(System.in);
    int boardSize = sc.nextInt();
    while (boardSize < 4 || boardSize % 2 != 0 || boardSize > 52) {
      System.out.println("Veuillez saisir une taille paire et supérieure à 4 et inférieure à 52...");
      boardSize = sc.nextInt();
    }
    return boardSize;
  }

  public static boolean askRule() {
    System.out.println(
        "╔════════════════════════════════╗\n" +
            "║ Comment souhaitez-vous jouer ? ║\n" +
            "╠═════════╦══════════════════════╣\n" +
            "║ 1       ║ Avec la règle 1      ║\n" +
            "╠═════════╬══════════════════════╣\n" +
            "║ 2       ║ Avec la règle 2      ║\n" +
            "╚═════════╩══════════════════════╝"
    );
    Scanner sc = new Scanner(System.in);
    int rule = sc.nextInt();
    while (rule < 1 || rule > 2) {
      System.out.println("Veuillez saisir une option valide...");
      rule = sc.nextInt();
    }
    return rule != 1;
  }

  public static PlayerSettings[] askPlayersSettings() {
    System.out.println(
        "╔═══════════════════════════════════════════╗\n" +
            "║ Configuration de la partie                ║\n" +
            "╠═══════════════════════╦═══════════════════╣\n" +
            "║ JOUEUR 1              ║ JOUEUR 2          ║\n" +
            "╠════════╦══════════════╬════╦══════════════╣\n" +
            "║ A1     ║ Humain       ║ B1 ║ Humain       ║\n" +
            "╠════════╬══════════════╬════╬══════════════╣\n" +
            "║ A2     ║ Ordinateur 1 ║ B2 ║ Ordinateur 1 ║\n" +
            "╠════════╬══════════════╬════╬══════════════╣\n" +
            "║ A3     ║ Ordinateur 2 ║ B3 ║ Ordinateur 2 ║\n" +
            "╠════════╬══════════════╬════╬══════════════╣\n" +
            "║ A4     ║ Ordinateur 3 ║ B4 ║ Ordinateur 3 ║\n" +
            "╠════════╬══════════════╩════╩══════════════╣\n" +
            "║ finish ║ Terminer                         ║\n" +
            "╚════════╩══════════════════════════════════╝"
    );
    String command = "";

    boolean completed = false;
    PlayerSettings[] settings = new PlayerSettings[2];
    Scanner sc = new Scanner(System.in);
    while (true) {
      try {
        command = sc.nextLine();
        if (command.equals("finish")) {
          if (completed) {
            return settings;
          } else {
            throw new Exception("Veuillez finir la configuration avant de terminer");
          }
        } else {
          if (command.length() != 2) throw new Exception("Taille incorrecte");
          if (!command.matches("^[A-Z]\\d$")) throw new Exception("Format incorrect");
          if (command.charAt(0) == 'A' || command.charAt(0) == 'B') {
            int commandID = Integer.parseInt(command.charAt(1) + "");
            if (commandID > 0 && commandID <= 4) {
              int playerID = command.charAt(0) - 'A' + 1;
              settings[playerID - 1] = new PlayerSettings(playerID, commandID);
              if (settings[0] != null && settings[1] != null) completed = true;
              System.out.println("Le joueur " + (playerID) + " est prêt pour la partie !");
            } else {
              throw new Exception("Cet identifiant de commande n'éxiste pas");
            }
          } else {
            throw new Exception("Cet identifiant de joueur n'éxiste pas");
          }
        }

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }

    }


  }

  public static int menu() {
    System.out.println("" +
        "╔═════════════════════════════════════════╗\n" +
        "║ Bienvenue sur Reversi !                 ║\n" +
        "╠═════════════════════════════════════════╣\n" +
        "║ Pour commencer, choisissez une option : ║\n" +
        "╠══════╦══════════════════════════════════╣\n" +
        "║ 1    ║ Commencer une nouvelle partie    ║\n" +
        "╠══════╬══════════════════════════════════╣\n" +
        "║ 2    ║ Charger une partie               ║\n" +
        "╠══════╬══════════════════════════════════╣\n" +
        "║ 3    ║ Quitter                          ║\n" +
        "╚══════╩══════════════════════════════════╝"
    );
    Scanner sc = new Scanner(System.in);
    int menuSelection = sc.nextInt();
    while (menuSelection <= 0 || menuSelection > 3) {
      System.out.println("Veuillez saisir une option correcte...");
      menuSelection = sc.nextInt();
    }
    return menuSelection;
  }

  private void displayScore() {
    Score score = board.getScore();
    System.out.println("+===============+===============+");
    System.out.println("|  🧑 JOUEUR 1 |  🧑 JOUEUR 2  |");
    System.out.println("+===============+===============+");
    System.out.println("| " + score.getPlayer1() + "             | " + score.getPlayer2() + "             |");
    System.out.println("+===============+===============+");

  }


  /**
   * Display a in-game menu and ask the player to make a selection
   *
   * @param player The current player
   * @return The selection ID and input in a class
   */
  private InputAndSelection inGameMenu(int player) {
    while (true) {
      System.out.println(
          "╔══════════════════════════════════════════╗\n" +
              "║ A vous de jouer JOUEUR " + player + "                 ║\n" +
              "╠══════════════════╦═══════════════════════╣\n" +
              "║ {Nombre}{Lettre} ║ Jouer un coup         ║\n" +
              "╠══════════════════╬═══════════════════════╣\n" +
              "║ save             ║ Sauvegarder la partie ║\n" +
              "╠══════════════════╬═══════════════════════╣\n" +
              "║ help             ║ Obtenir de l'aide     ║\n" +
              "╠══════════════════╬═══════════════════════╣\n" +
              "║ pass             ║ Passer son tour       ║\n" +
              "╠══════════════════╬═══════════════════════╣\n" +
              "║ exit             ║ Quitter               ║\n" +
              "╚══════════════════╩═══════════════════════╝"
      );
      Scanner sc = new Scanner(System.in);
      String input = sc.nextLine();
      switch (input) {
        case "save":
          return new InputAndSelection(1, input);
        case "exit":
          return new InputAndSelection(2, input);
        case "help":
          if (rule) {
            help(0, board.getPlayer());
          } else {
            System.out.println("⚠ Vous ne pouvez pas passer votre tour...");
          }

          break;
        case "pass":
          return new InputAndSelection(4, input);
        default:
          if (board.checkPositionStringFormat(input)) {
            Position posWhereWantToPlay;
            posWhereWantToPlay = board.getPositionFromString(input);

            if (!board.containAPlayerPiece(posWhereWantToPlay)) {
              if (board.isThePlacementCorrect(posWhereWantToPlay)) {
                return new InputAndSelection(5, input);
              } else {
                System.out.println("⚠ Vous ne pouvez pas jouer ici. Utilisez l'aide pour voir où jouer");
              }
            } else {
              System.out.println("⚠ Veuillez choisir une case libre");
            }
          } else {
            System.out.println("⚠ Veuillez respecter le format ChiffreLettre (0A)");
          }
          break;
      }
    }
  }

  /**
   * Help the player by displaying where he can play
   *
   * @param version The version of the help (which AI will be used)
   * @param player  The current player
   */
  private void help(int version, int player) {
    if (version == 1) {
      board.display(new Position[]{ArtificialIntelligence.ia1(board, player)});
    } else if (version == 2) {
      board.display(new Position[]{ArtificialIntelligence.ia2(board, player)});
    } else if (version == 3) {
      board.display(new Position[]{ArtificialIntelligence.ia3(board, player)});
    } else {
      board.display(board.whereToPlay(player));
    }
  }


}
