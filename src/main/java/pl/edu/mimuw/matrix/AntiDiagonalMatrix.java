package pl.edu.mimuw.matrix;

public final class AntiDiagonalMatrix extends Matrix {
  private final double[] antiDiagonalValues;

  public AntiDiagonalMatrix(double... antiDiagonalValues) {
    super(Shape.square(antiDiagonalValues.length));
    this.antiDiagonalValues = antiDiagonalValues;
  }

  @Override
  public double getButUnchecked(int row, int column) {
    if (row == shape().edge() - column - 1) return antiDiagonalValues[row];
    return 0;
  }

  @Override
  protected Matrix multipliedBy(Matrix that) {
    return that.times(this);
  }

  @Override
  public Matrix addedTo(Matrix that) {
    return that.plus(this);
  }

  // This class isn't optimized. See other classes to enjoy some efficient code.
}
