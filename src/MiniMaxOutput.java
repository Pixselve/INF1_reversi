public class MiniMaxOutput {
  Position pos;
  int eval;

  MiniMaxOutput(Position pos, int eval) {
    this.pos = pos;
    this.eval = eval;
  }

  public MiniMaxOutput(Position pos) {
    this.pos = pos;
  }

  public MiniMaxOutput() {

  }
}
