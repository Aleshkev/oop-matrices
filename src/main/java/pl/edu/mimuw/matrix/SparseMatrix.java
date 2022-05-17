package pl.edu.mimuw.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

public final class SparseMatrix extends Matrix {
  private final MatrixCellValue[] values;
  private final int[] existingRows;
  private final int[] existingColumns;

  public SparseMatrix(Shape shape, MatrixCellValue[] values) {
    super(shape);

    values = values.clone();
    Arrays.sort(values, MatrixCellValue::compareByRowThenByColumn);
    for (var i = 0; i + 1 < values.length; i++)
      assert values[i].compareByRowThenByColumn(values[i + 1]) != 0;
    for (var value : values) shape().assertInShape(value.row, value.column);
    this.values = values;

    this.existingRows =
        Arrays.stream(values).mapToInt(cell -> cell.row).sorted().distinct().toArray();
    this.existingColumns =
        Arrays.stream(values).mapToInt(cell -> cell.column).sorted().distinct().toArray();
  }

  /** Convert the matrix to a sparse matrix. Skips blank cells if possible. */
  public static SparseMatrix fromMatrix(Matrix matrix) {
    var values = new ArrayList<MatrixCellValue>();

    matrix
        .getExistingRows()
        .forEach(
            y ->
                matrix
                    .getExistingCellsInRow(y)
                    .forEach(
                        x -> {
                          var value = matrix.get(y, x);
                          if (Math.abs(value) != 0.0) values.add(MatrixCellValue.cell(y, x, value));
                        }));
    return new SparseMatrix(matrix.shape(), values.toArray(MatrixCellValue[]::new));
  }

  /** Multiply two sparse matrices. */
  private static SparseMatrix fromSparseMultiplication(SparseMatrix a, SparseMatrix b) {
    a.shapeOfThisTimes(b);
    var possibleZ =
        IntStream.concat(Arrays.stream(a.existingColumns), Arrays.stream(b.existingRows))
            .distinct()
            .toArray();

    var values = new ArrayList<MatrixCellValue>();
    for (int y : a.existingRows) {
      for (int x : b.existingColumns) {
        var s = Arrays.stream(possibleZ).mapToDouble(z -> a.get(y, z) * b.get(z, x)).sum();
        if (Math.abs(s) != 0.0) values.add(new MatrixCellValue(y, x, s));
      }
    }
    return new SparseMatrix(a.shapeOfThisTimes(b), values.toArray(MatrixCellValue[]::new));
  }

  /** Add two sparse matrices. */
  private static SparseMatrix fromSparseAddition(SparseMatrix a, SparseMatrix b) {
    a.shapeOfThisPlus(b);
    var cellPositions = new TreeSet<>(MatrixCellValue::compareByRowThenByColumn);
    cellPositions.addAll(Arrays.asList(a.values));
    cellPositions.addAll(Arrays.asList(b.values));
    var values = new ArrayList<MatrixCellValue>();
    for (var cellPosition : cellPositions) {
      int y = cellPosition.row, x = cellPosition.column;
      values.add(new MatrixCellValue(y, x, a.get(y, x) + b.get(y, x)));
    }
    return new SparseMatrix(a.shapeOfThisPlus(b), values.toArray(MatrixCellValue[]::new));
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    var index =
        Arrays.binarySearch(
            values, new MatrixCellValue(row, column, 0), MatrixCellValue::compareByRowThenByColumn);
    if (index < 0) return 0;
    return values[index].value;
  }

  @Override
  protected Matrix multipliedBy(Matrix that) {
    return that.times(this);
  }

  @Override
  public Matrix addedTo(Matrix that) {
    return that.plus(this);
  }

  @Override
  protected Matrix times(DiagonalMatrix that) {
    return times(fromMatrix(that));
  }

  @Override
  protected Matrix times(SparseMatrix that) {
    return fromSparseMultiplication(this, that);
  }

  @Override
  protected Matrix plus(IdentityMatrix that) {
    return plus(fromMatrix(that));
  }

  @Override
  protected Matrix plus(DiagonalMatrix that) {
    return plus(fromMatrix(that));
  }

  @Override
  protected Matrix plus(SparseMatrix that) {
    return fromSparseAddition(this, that);
  }

  @Override
  protected Matrix mapCells(DoubleFunction<Double> operator) {
    if (operator.apply(0.0) == 0.0)
      return new SparseMatrix(
          shape(),
          Arrays.stream(values)
              .map(cell -> cell.withValue(operator.apply(cell.value)))
              .toArray(MatrixCellValue[]::new));
    return super.mapCells(operator);
  }

  // These methods actually return too many cells as non-empty, which could be easily fixed with
  // more memory or more time complexity. But this is good enough; operations will at most use the
  // same amount of time as if the sparse matrix was a full matrix with no completely blank rows or
  // columns.

  @Override
  protected IntStream getExistingRows() {
    return Arrays.stream(existingRows);
  }

  @Override
  protected IntStream getExistingCellsInRow(int row) {
    return Arrays.stream(existingColumns);
  }

  @Override
  protected IntStream getExistingColumns() {
    return Arrays.stream(existingColumns);
  }

  @Override
  protected IntStream getExistingCellsInColumn(int column) {
    return Arrays.stream(existingRows);
  }
}
