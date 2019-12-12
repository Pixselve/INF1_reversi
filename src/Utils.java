public class Utils {
  /**
   * @param arr le tableau dans lequel on va push
   * @param num l'entier à push
   * @return le nouveau tableau avec le nouvel élement à sa fin
   */
  public static int[] pushToArray(int[] arr, int num) {
    int[] result = new int[arr.length + 1];
    for (int i = 0; i < arr.length; i++) {
      result[i] = arr[i];
    }
    result[arr.length] = num;
    return result;
  }


  public static Position[] pushToArray(Position[] arr, Position el) {
    Position[] result = new Position[arr.length + 1];
    for (int i = 0; i < arr.length; i++) {
      result[i] = arr[i];
    }
    result[arr.length] = el;
    return result;
  }

  public static Game[] pushToArray(Game[] arr, Game el) {
    Game[] result = new Game[arr.length + 1];
    for (int i = 0; i < arr.length; i++) {
      result[i] = arr[i];
    }
    result[arr.length] = el;
    return result;
  }

  public static Minimax[] pushToArray(Minimax[] arr, Minimax el) {
    Minimax[] result = new Minimax[arr.length + 1];
    for (int i = 0; i < arr.length; i++) {
      result[i] = arr[i];
    }
    result[arr.length] = el;
    return result;
  }

  public static int randomBetweenTwoInt(int min, int max) {
    return (int) (Math.random() * ((max - min) + 1)) + min;
  }

  public static Minimax getMaxIntoArray(Minimax[] arr) {
    if (arr.length == 0) return new Minimax();
    Minimax max = arr[0];
    for (Minimax minimax : arr) {
      if (minimax.eval > max.eval) max = minimax;
    }
    return max;
  }
  public static Minimax getMinIntoArray(Minimax[] arr) {
    if (arr.length == 0) return new Minimax();
    Minimax max = arr[0];
    for (Minimax minimax : arr) {
      if (minimax.eval < max.eval) max = minimax;
    }
    return max;
  }
}
