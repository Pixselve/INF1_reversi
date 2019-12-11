import java.util.Scanner;

public class Game {
  Board board;
  Scanner sc = new Scanner(System.in);

  Game() {

  }

  private void play() {
    board = new Board(askBoardSize(), askRule());
    while (board.canBothPlayersPlay()) {
      round();
      System.out.println(board.canBothPlayersPlay());
    }
    System.out.println("END OF THE GAME");

  }


  private void round() {
    displayInformation();
    if (board.whereToPlay().length > 0) {
      board.placePiece(getPlacementFromPlayer(board.getPlayer()));
    } else {
      System.out.println("Vous ne pouvez pas jouer, passage au joueur suivant...");
    }
    board.switchPlayer();

  }

  private void displayInformation() {
    for (int i = 0; i < 50; i++) {
      System.out.println();
    }
    displayScore();
    board.display();
  }

  private int askBoardSize() {
    System.out.println(
        "╔═══════════════════════════════════════╗\n" +
            "║ Veuillez saisir une taille de plateau ║\n" +
            "╚═══════════════════════════════════════╝"
    );
    int boardSize = sc.nextInt();
    while (boardSize < 4 || boardSize % 2 != 0) {
      System.out.println("Veuillez saisir une taille paire et supérieure à 4...");
      boardSize = sc.nextInt();
    }
    return boardSize;
  }

  private boolean askRule() {
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
      System.out.println("Veuillez saisir une taille paire et supérieure à 4...");
      rule = sc.nextInt();
    }
    return rule != 1;
  }

  public void menu() {
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
    int menuSelection = sc.nextInt();
    while (menuSelection <= 0 || menuSelection > 3) {
      System.out.println("Veuillez saisir une option correcte...");
      menuSelection = sc.nextInt();
    }
    switch (menuSelection) {
      case 1:
        play();
        break;
      case 2:
        break;
      default:
    }
  }

  private void displayScore() {
    Score score = board.getScore();
    System.out.println("+===============+===============+");
    System.out.println("|  🧑 JOUEUR 1 |  🧑 JOUEUR 2  |");
    System.out.println("+===============+===============+");
    System.out.println("| " + score.getPlayer1() + "             | " + score.getPlayer2() + "             |");
    System.out.println("+===============+===============+");

  }

  private Position getPlacementFromPlayer(int player) {
    Position posWhereWantToPlay;
    while (true) {
      System.out.println("" +
          "╔══════════════════════════════════════╗\n" +
          "║ À vous de jouer JOUEUR " + player + " !           ║\n" +
          "╠══════════════╦═══════════════════════╣\n" +
          "║ save         ║ Enregistrer la partie ║\n" +
          "╠══════════════╬═══════════════════════╣\n" +
          "║ exit         ║ Quitter la partie     ║\n" +
          "╠══════════════╬═══════════════════════╣\n" +
          "║ help         ║ Savoir où jouer       ║\n" +
          "╠══════════════╬═══════════════════════╣\n" +
          "║ NombreLetter ║ Jouer un coup         ║\n" +
          "╚══════════════╩═══════════════════════╝");
      String input = sc.nextLine();
      if (input.equals("save")) {

      } else if (input.equals("exit")) {
        menu();
      } else if (input.equals("help")) {
        board.display(board.whereToPlay());
      } else {
        if (board.checkPositionStringFormat(input)) {
          posWhereWantToPlay = board.getPositionFromString(input);

          if (!board.containAPlayerPiece(posWhereWantToPlay)) {
            if (board.isThePlacementCorrect(posWhereWantToPlay)) {
              return posWhereWantToPlay;
            } else {
              System.out.println("⚠ Vous ne pouvez pas jouer ici. Utilisez l'aide pour voir où jouer");
            }
          } else {
            System.out.println("⚠ Veuillez choisir une case libre");
          }
        } else {
          System.out.println("⚠ Veuillez respecter le format ChiffreLettre (0A)");
        }
      }


    }
  }
}
