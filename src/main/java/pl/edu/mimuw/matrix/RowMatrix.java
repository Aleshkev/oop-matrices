package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;

public final class RowMatrix extends Matrix {
  private final double[] rowValues;

  public RowMatrix(int nRows, double[] rowValues) {
    super(Shape.matrix(nRows, rowValues.length));
    this.rowValues = rowValues;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return rowValues[column];
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
