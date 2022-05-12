package pl.edu.mimuw.matrix;

public final class RowMatrix extends Matrix {
  private final double[] rowValues;

  public RowMatrix(int rows, double[] rowValues) {
    super(Shape.matrix(rows, rowValues.length));
    this.rowValues = rowValues;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return rowValues[column];
  }
}
