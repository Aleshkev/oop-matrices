package pl.edu.mimuw.matrix;

import java.util.*;
import java.util.function.DoubleFunction;

public final class SparseMatrix extends Matrix {
  private final MatrixCellValue[] values;

  public SparseMatrix(Shape shape, MatrixCellValue[] values) {
    super(shape);

    values = values.clone();
    Arrays.sort(values, MatrixCellValue::compareByRowThenByColumn);
    for (var i = 0; i + 1 < values.length; i++)
      assert values[i].compareByRowThenByColumn(values[i + 1]) != 0;
    for (var value : values) shape().assertInShape(value.row, value.column);
    this.values = values;
  }

  public static SparseMatrix fromDiagonalMatrix(DiagonalMatrix matrix) {
    var values = new MatrixCellValue[matrix.shape().rows];
    for (var i = 0; i < matrix.shape().rows; i++)
      values[i] = MatrixCellValue.cell(i, i, matrix.get(i, i));
    return new SparseMatrix(matrix.shape(), values);
  }

  private static SparseMatrix fromSparseMultiplication(SparseMatrix a, SparseMatrix b, Shape resultShape) {
    Set<Integer> nonEmptyRowsInA = new HashSet<>();
    for (var cell : a.values) nonEmptyRowsInA.add(cell.row);
    Set<Integer> nonEmptyColumnsInB = new HashSet<>();
    for (var cell : b.values) nonEmptyColumnsInB.add(cell.column);

    Set<Integer> possibleZ = new HashSet<>();
    for (var cell : a.values) possibleZ.add(cell.column);
    for (var cell : b.values) possibleZ.add(cell.row);

    var values = new ArrayList<MatrixCellValue>();
    for (int y : nonEmptyRowsInA) {
      for (int x : nonEmptyColumnsInB) {
        var s = possibleZ.stream().mapToDouble(z -> a.get(y, z) * b.get(z, x)).sum();
        if (Math.abs(s) != 0.0)
          values.add(new MatrixCellValue(y, x, s));
      }
    }
    return new SparseMatrix(resultShape, values.toArray(MatrixCellValue[]::new));
  }

  private static SparseMatrix fromSparseAddition(SparseMatrix a, SparseMatrix b) {
    var cellPositions = new TreeSet<>(MatrixCellValue::compareByRowThenByColumn);
    cellPositions.addAll(Arrays.asList(a.values));
    cellPositions.addAll(Arrays.asList(b.values));
    var values = new ArrayList<MatrixCellValue>();
    for (var cellPosition : cellPositions) {
      int y = cellPosition.row, x = cellPosition.column;
      values.add(new MatrixCellValue(y, x, a.get(y, x) + b.get(y, x)));
    }
    return new SparseMatrix(a.shape(), values.toArray(MatrixCellValue[]::new));
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
  protected IDoubleMatrix doMultiplication(IDoubleMatrix other, Shape resultShape) {
    if (other instanceof ZeroMatrix that) return new ZeroMatrix(resultShape);
    if (other instanceof IdentityMatrix that) return this;
    if (other instanceof DiagonalMatrix that)
      return doMultiplication(fromDiagonalMatrix(that), resultShape);
    if (other instanceof SparseMatrix that)
      return fromSparseMultiplication(this, that, resultShape);
    return FullMatrix.fromMultiplication(this, other, resultShape);
  }

  @Override
  protected IDoubleMatrix doAddition(IDoubleMatrix other) {
    if (other instanceof ZeroMatrix that) return this;
    if (other instanceof IdentityMatrix that)
      return doAddition(fromDiagonalMatrix(DiagonalMatrix.fromIdentityMatrix(that)));
    if (other instanceof DiagonalMatrix that)
      return doAddition(fromDiagonalMatrix(that));
    if (other instanceof SparseMatrix that)
      return fromSparseAddition(this, that);
    return FullMatrix.fromAddition(this, other);
  }

  @Override
  protected IDoubleMatrix doScalarOperation(DoubleFunction<Double> operator) {
    if (operator.apply(0.0) == 0.0)
      return new SparseMatrix(shape(), Arrays.stream(values).map(cell -> cell.withValue(operator.apply(cell.value))).toArray(MatrixCellValue[]::new));
    return FullMatrix.fromMatrix(this).doScalarOperation(operator);
  }
}
