package pl.edu.mimuw.matrix;

import java.util.Arrays;

public final class SparseMatrix extends Matrix {
  private final MatrixCellValue[] values;

  public SparseMatrix(Shape shape, MatrixCellValue[] values) {
    super(shape);

    Arrays.sort(values, new MatrixCellValue.PositionComparator());
    for (var i = 0; i + 1 < values.length; i++)
      assert values[i].comparePosition(values[i + 1]) != 0;
    for (var value : values) shape().assertInShape(value.row, value.column);
    this.values = values;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    var index =
        Arrays.binarySearch(
            values, new MatrixCellValue(row, column, 0), new MatrixCellValue.PositionComparator());
    if (index < 0) return 0;
    return values[index].value;
  }
}
