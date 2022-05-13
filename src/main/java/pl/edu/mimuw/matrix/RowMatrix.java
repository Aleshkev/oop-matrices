package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;

public final class RowMatrix extends Matrix {
  private final double[] columnValues;

  public RowMatrix(int nRows, double[] columnValues) {
    super(Shape.matrix(nRows, columnValues.length));
    this.columnValues = columnValues;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return columnValues[row];
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
