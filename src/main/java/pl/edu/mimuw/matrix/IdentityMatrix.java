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
  protected IDoubleMatrix doMultiplication(IDoubleMatrix other, Shape resultShape) {
    return other;
  }

  @Override
  protected IDoubleMatrix doAddition(IDoubleMatrix other) {
    if (other instanceof ZeroMatrix that) return this;
    if (other instanceof IdentityMatrix that)
      return DiagonalMatrix.fromIdentityMatrix(this).plus(that);
    if (other instanceof DiagonalMatrix that) return that.plus(this);
    return FullMatrix.fromAddition(this, other);
  }

  @Override
  protected IDoubleMatrix doScalarOperation(DoubleFunction<Double> operator) {
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
