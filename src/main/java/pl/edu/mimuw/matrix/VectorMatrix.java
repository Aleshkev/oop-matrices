package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;

public final class VectorMatrix extends Matrix {
  private final double[] values;

  public VectorMatrix(double[] values) {
    super(Shape.vector(values.length));
    this.values = values;
  }

  private static VectorMatrix fromMatrix(Matrix matrix) {
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

  @Override
  protected Matrix multipliedBy(Matrix that) {
    return that.times(this);
  }

  @Override
  public Matrix addedTo(Matrix that) {
    return that.plus(this);
  }

  @Override
  protected Matrix mapCells(DoubleFunction<Double> operator) {
    return VectorMatrix.fromMatrix(FullMatrix.fromMatrix(this).mapCells(operator));
  }

  // This class isn't optimized. See other classes to enjoy some efficient code.
}
