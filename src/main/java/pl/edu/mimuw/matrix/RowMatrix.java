package pl.edu.mimuw.matrix;

public final class RowMatrix extends Matrix {
  private final double[] rowValues;

  public RowMatrix(int nRows, double[] rowValues) {
    super(Shape.matrix(nRows, rowValues.length));
    this.rowValues = rowValues;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return rowValues[column];
  }
}
