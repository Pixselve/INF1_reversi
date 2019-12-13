public class InputAndSelection {
  private final int selection;
  private final String input;

  public InputAndSelection(int selection, String input) {
    this.selection = selection;
    this.input = input;
  }

  public int getSelection() {
    return selection;
  }

  public String getInput() {
    return input;
  }
}
