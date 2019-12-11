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
}
