package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;

public final class ColumnMatrix extends Matrix {
  private final double[] rowValues;

  public ColumnMatrix(int nColumns, double[] rowValues) {
    super(Shape.matrix(rowValues.length, nColumns));
    this.rowValues = rowValues;
  }

  @Override
  public double getButUnchecked(int row, int column) {
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
