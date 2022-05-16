package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

public class IdentityMatrix extends Matrix {

  protected IdentityMatrix(int size) {
    super(Shape.square(size));
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return row == column ? 1 : 0;
  }

  @Override
  protected Matrix multipliedBy(Matrix what) {
    return what.times(this);
  }

  @Override
  public Matrix addedTo(Matrix what) {
    return what.plus(this);
  }

  @Override
  protected Matrix times(ConstantValueMatrix that) {
    multiplicationResultShape(that);
    return that;
  }

  @Override
  protected Matrix times(IdentityMatrix that) {
    multiplicationResultShape(that);
    return that;
  }

  @Override
  protected Matrix times(DiagonalMatrix that) {
    multiplicationResultShape(that);
    return that;
  }

  @Override
  protected Matrix times(FullMatrix that) {
    multiplicationResultShape(that);
    return that;
  }

  @Override
  protected Matrix times(SparseMatrix that) {
    multiplicationResultShape(that);
    return that;
  }

  @Override
  protected Matrix times(VectorMatrix that) {
    multiplicationResultShape(that);
    return that;
  }

  @Override
  protected Matrix times(AntiDiagonalMatrix that) {
    multiplicationResultShape(that);
    return that;
  }

  @Override
  protected Matrix times(ColumnMatrix that) {
    multiplicationResultShape(that);
    return that;
  }

  @Override
  protected Matrix times(RowMatrix that) {
    multiplicationResultShape(that);
    return that;
  }

  @Override
  protected Matrix plus(IdentityMatrix that) {
    return DiagonalMatrix.fromIdentityMatrix(this).plus(that);
  }

  @Override
  protected Matrix plus(DiagonalMatrix that) {
    return DiagonalMatrix.fromIdentityMatrix(this).plus(that);
  }

  @Override
  protected IDoubleMatrix applyElementwise(DoubleFunction<Double> operator) {
    if (operator.apply(0.0) == 0.0)
      return DiagonalMatrix.fromIdentityMatrix(this).applyElementwise(operator);
    return FullMatrix.fromScalarOperation(this, operator);
  }

  @Override
  public double normOne() {
    return 1;
  }

  @Override
  public double normInfinity() {
    return 1;
  }

  @Override
  protected IntStream getExistingCellsInRow(int row) {
    return IntStream.of(row);
  }

  @Override
  protected IntStream getExistingCellsInColumn(int column) {
    return IntStream.of(column);
  }
}
