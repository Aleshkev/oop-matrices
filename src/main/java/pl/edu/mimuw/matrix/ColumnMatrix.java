package pl.edu.mimuw.matrix;

public final class ColumnMatrix extends Matrix {
  private final double[] rowValues;

  public ColumnMatrix(int nColumns, double[] rowValues) {
    super(Shape.matrix(rowValues.length, nColumns));
    this.rowValues = rowValues;
  }

  @Override
  public double getButUnchecked(int row, int column) {
    return rowValues[column];
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
