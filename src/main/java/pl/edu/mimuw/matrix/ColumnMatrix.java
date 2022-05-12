package pl.edu.mimuw.matrix;

public final class ColumnMatrix extends Matrix {
  private final double[] columnValues;

  public ColumnMatrix(int columns, double[] columnValues) {
    super(Shape.matrix(columnValues.length, columns));
    this.columnValues = columnValues;
  }

  @Override
  public double getButUnchecked(int row, int column) {
    return columnValues[row];
  }
}
