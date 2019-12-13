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

  /**
   * @param arr The array in which we want to pish
   * @param el  The element to push
   * @return arr with the element at the end of it
   */
  public static Position[] pushToArray(Position[] arr, Position el) {
    Position[] result = new Position[arr.length + 1];
    for (int i = 0; i < arr.length; i++) {
      result[i] = arr[i];
    }
    result[arr.length] = el;
    return result;
  }

  /**
   * @param arr The array in which we want to pish
   * @param el  The element to push
   * @return arr with the element at the end of it
   */
  public static Game[] pushToArray(Game[] arr, Game el) {
    Game[] result = new Game[arr.length + 1];
    for (int i = 0; i < arr.length; i++) {
      result[i] = arr[i];
    }
    result[arr.length] = el;
    return result;
  }

  /**
   * @param arr The array in which we want to pish
   * @param el  The element to push
   * @return arr with the element at the end of it
   */
  public static MiniMaxOutput[] pushToArray(MiniMaxOutput[] arr, MiniMaxOutput el) {
    MiniMaxOutput[] result = new MiniMaxOutput[arr.length + 1];
    for (int i = 0; i < arr.length; i++) {
      result[i] = arr[i];
    }
    result[arr.length] = el;
    return result;
  }

  /**
   * Generate a random number between two boundaries
   *
   * @param min The min bound
   * @param max the max bound
   * @return A random number between min and max
   */
  public static int randomBetweenTwoInt(int min, int max) {
    return (int) (Math.random() * ((max - min) + 1)) + min;
  }

  /**
   * Get the max element from a MiniMaxOutput array
   *
   * @param arr The array in which we want to find the max
   * @return The maximum element
   */
  public static MiniMaxOutput getMaxIntoArray(MiniMaxOutput[] arr) {
    if (arr.length == 0) return new MiniMaxOutput();
    MiniMaxOutput max = arr[0];
    for (MiniMaxOutput miniMaxOutput : arr) {
      if (miniMaxOutput.eval > max.eval) max = miniMaxOutput;
    }
    return max;
  }

  /**
   * Get the min element from a MiniMaxOutput array
   *
   * @param arr The array in which we want to find the min
   * @return The minimum element
   */
  public static MiniMaxOutput getMinIntoArray(MiniMaxOutput[] arr) {
    if (arr.length == 0) return new MiniMaxOutput();
    MiniMaxOutput max = arr[0];
    for (MiniMaxOutput miniMaxOutput : arr) {
      if (miniMaxOutput.eval < max.eval) max = miniMaxOutput;
    }
    return max;
  }
}
