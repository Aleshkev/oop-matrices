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
  protected Matrix multipliedBy(Matrix that) {
    return that.times(this);
  }

  @Override
  public Matrix addedTo(Matrix that) {
    return that.plus(this);
  }

  @Override
  protected Matrix times(ConstantValueMatrix that) {
    shapeOfThisTimes(that);
    return that;
  }

  @Override
  protected Matrix times(IdentityMatrix that) {
    shapeOfThisTimes(that);
    return that;
  }

  @Override
  protected Matrix times(DiagonalMatrix that) {
    shapeOfThisTimes(that);
    return that;
  }

  @Override
  protected Matrix times(FullMatrix that) {
    shapeOfThisTimes(that);
    return that;
  }

  @Override
  protected Matrix times(SparseMatrix that) {
    shapeOfThisTimes(that);
    return that;
  }

  @Override
  protected Matrix times(VectorMatrix that) {
    shapeOfThisTimes(that);
    return that;
  }

  @Override
  protected Matrix times(AntiDiagonalMatrix that) {
    shapeOfThisTimes(that);
    return that;
  }

  @Override
  protected Matrix times(ColumnMatrix that) {
    shapeOfThisTimes(that);
    return that;
  }

  @Override
  protected Matrix times(RowMatrix that) {
    shapeOfThisTimes(that);
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
  protected Matrix mapCells(DoubleFunction<Double> operator) {
    if (operator.apply(0.0) == 0.0)
      return DiagonalMatrix.fromIdentityMatrix(this).mapCells(operator);
    return super.mapCells(operator);
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
  protected IntStream getIndicesOfExistingCellsInRow(int row) {
    return IntStream.of(row);
  }

  @Override
  protected IntStream getIndicesOfExistingCellsInColumn(int column) {
    return IntStream.of(column);
  }
}
