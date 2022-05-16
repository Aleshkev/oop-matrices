package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

public final class ZeroMatrix extends Matrix {
  public ZeroMatrix(Shape theShape) {
    super(theShape);
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return 0;
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
    return new ZeroMatrix(shapeOfThisTimes(that));
  }

  @Override
  protected Matrix times(DiagonalMatrix that) {
    return new ZeroMatrix(shapeOfThisTimes(that));
  }

  @Override
  protected Matrix times(FullMatrix that) {
    return new ZeroMatrix(shapeOfThisTimes(that));
  }

  @Override
  protected Matrix times(SparseMatrix that) {
    return new ZeroMatrix(shapeOfThisTimes(that));
  }

  @Override
  protected Matrix times(VectorMatrix that) {
    return new ZeroMatrix(shapeOfThisTimes(that));
  }

  @Override
  protected Matrix times(AntiDiagonalMatrix that) {
    return new ZeroMatrix(shapeOfThisTimes(that));
  }

  @Override
  protected Matrix times(ColumnMatrix that) {
    return new ZeroMatrix(shapeOfThisTimes(that));
  }

  @Override
  protected Matrix times(RowMatrix that) {
    return new ZeroMatrix(shapeOfThisTimes(that));
  }

  @Override
  protected Matrix plus(ConstantValueMatrix that) {
    shapeOfThisPlus(that);
    return that;
  }

  @Override
  protected Matrix plus(IdentityMatrix that) {
    shapeOfThisPlus(that);
    return that;
  }

  @Override
  protected Matrix plus(DiagonalMatrix that) {
    shapeOfThisPlus(that);
    return that;
  }

  @Override
  protected Matrix plus(FullMatrix that) {
    shapeOfThisPlus(that);
    return that;
  }

  @Override
  protected Matrix plus(SparseMatrix that) {
    shapeOfThisPlus(that);
    return that;
  }

  @Override
  protected Matrix plus(VectorMatrix that) {
    shapeOfThisPlus(that);
    return that;
  }

  @Override
  protected Matrix plus(AntiDiagonalMatrix that) {
    shapeOfThisPlus(that);
    return that;
  }

  @Override
  protected Matrix plus(ColumnMatrix that) {
    shapeOfThisPlus(that);
    return that;
  }

  @Override
  protected Matrix plus(RowMatrix that) {
    shapeOfThisPlus(that);
    return that;
  }

  @Override
  protected Matrix mapCells(DoubleFunction<Double> operator) {
    if (operator.apply(0.0) == 0.0) return this;
    return new ConstantValueMatrix(shape(), operator.apply(0.0));
  }

  @Override
  public double normOne() {
    return 0;
  }

  @Override
  public double normInfinity() {
    return 0;
  }

  @Override
  public double frobeniusNorm() {
    return 0;
  }

  @Override
  protected IntStream getExistingRows() {
    return IntStream.empty();
  }

  @Override
  protected IntStream getExistingCellsInRow(int row) {
    return IntStream.empty();
  }

  @Override
  protected IntStream getExistingColumns() {
    return IntStream.empty();
  }

  @Override
  protected IntStream getExistingCellsInColumn(int column) {
    return IntStream.empty();
  }
}
