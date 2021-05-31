package spw4.game2048;

public class Tile {
  private int value;
  private boolean mergedInThisMove;

  public Tile() {
    this(0);
  }

  public Tile(int value) {
    this.value = value;
    this.mergedInThisMove = false;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Integer) {
      return this.value == (Integer) obj;
    }
    return false;
  }

  @Override
  public String toString() {
    return ((Integer)value).toString();
  }

  public int getValue() { return value; }
  public void setValue(int value) { this.value = value; }

  public boolean isMergedInThisMove() { return mergedInThisMove; }
  public void setMergedInThisMove(boolean mergedInThisMove) {
    this.mergedInThisMove = mergedInThisMove;
  }
}
