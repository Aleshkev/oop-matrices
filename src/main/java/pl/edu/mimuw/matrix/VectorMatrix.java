package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;

public final class VectorMatrix extends Matrix {
  private final double[] values;

  public VectorMatrix(double[] values) {
    super(Shape.vector(values.length));
    this.values = values;
  }

  public static VectorMatrix fromMatrix(IDoubleMatrix matrix) {
    assert matrix.shape().columns == 1;
    var values = new double[matrix.shape().rows];
    for (var i = 0; i < matrix.shape().rows; i++) values[i] = matrix.get(i, 0);
    return new VectorMatrix(values);
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    assert column == 0;
    return values[row];
  }

  // Too lazy to implement these optimally.

  @Override
  protected IDoubleMatrix doMultiplication(IDoubleMatrix other, Shape resultShape) {
    return VectorMatrix.fromMatrix(
        FullMatrix.fromMatrix(this).doMultiplication(other, resultShape));
  }

  @Override
  protected IDoubleMatrix doAddition(IDoubleMatrix other) {
    return VectorMatrix.fromMatrix(FullMatrix.fromMatrix(this).doAddition(other));
  }

  @Override
  protected IDoubleMatrix doScalarOperation(DoubleFunction<Double> operator) {
    return VectorMatrix.fromMatrix(FullMatrix.fromMatrix(this).doScalarOperation(operator));
  }
}
