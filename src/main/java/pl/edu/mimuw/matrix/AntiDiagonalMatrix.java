package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;

public final class AntiDiagonalMatrix extends Matrix {
  private final double[] antiDiagonalValues;

  public AntiDiagonalMatrix(double... antiDiagonalValues) {
    super(Shape.square(antiDiagonalValues.length));
    this.antiDiagonalValues = antiDiagonalValues;
  }

  @Override
  public double getButUnchecked(int row, int column) {
    if (row == shape().edge() - column - 1) return antiDiagonalValues[row];
    return 0;
  }

  // Too lazy to implement these optimally.

  @Override
  protected IDoubleMatrix doMultiplication(IDoubleMatrix other, Shape resultShape) {
    return FullMatrix.fromMatrix(this).doMultiplication(other, resultShape);
  }

  @Override
  protected IDoubleMatrix doAddition(IDoubleMatrix other) {
    return FullMatrix.fromMatrix(this).doAddition(other);
  }

  @Override
  protected IDoubleMatrix doScalarOperation(DoubleFunction<Double> operator) {
    return FullMatrix.fromMatrix(this).doScalarOperation(operator);
  }
}
