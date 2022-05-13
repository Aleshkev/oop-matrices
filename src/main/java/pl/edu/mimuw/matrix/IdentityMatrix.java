package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;

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
    if(other instanceof ZeroMatrix that) return this;
    if(other instanceof IdentityMatrix that) return DiagonalMatrix.fromIdentityMatrix(this).plus(that);
    if(other instanceof DiagonalMatrix that) return that.plus(this);
    return FullMatrix.fromAddition(this, other);
  }

  @Override
  protected IDoubleMatrix doScalarOperation(DoubleFunction<Double> operator) {
    return FullMatrix.fromScalarOperation(this, operator);
  }
}
