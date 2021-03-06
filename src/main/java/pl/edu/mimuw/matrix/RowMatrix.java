package pl.edu.mimuw.matrix;

public final class RowMatrix extends Matrix {
  private final double[] columnValues;

  public RowMatrix(int nRows, double[] columnValues) {
    super(Shape.matrix(nRows, columnValues.length));
    this.columnValues = columnValues;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return columnValues[row];
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
