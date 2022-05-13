package pl.edu.mimuw.matrix;

public final class MatrixCellValue {
  public final int row;
  public final int column;
  public final double value;

  public MatrixCellValue(int row, int column, double value) {
    this.column = column;
    this.row = row;
    this.value = value;
  }

  public static MatrixCellValue cell(int row, int column, double value) {
    return new MatrixCellValue(row, column, value);
  }

  public MatrixCellValue withValue(double value) {
    return new MatrixCellValue(row, column, value);
  }

  @Override
  public String toString() {
    return "{" + value + " @[" + row + ", " + column + "]}";
  }

  public int compareByRowThenByColumn(MatrixCellValue other) {
    return row != other.row
        ? Integer.compare(row, other.row)
        : Integer.compare(column, other.column);
  }

  public int compareByColumnThenByRow(MatrixCellValue other) {
    return column != other.column
        ? Integer.compare(column, other.column)
        : Integer.compare(row, other.row);
  }
}
