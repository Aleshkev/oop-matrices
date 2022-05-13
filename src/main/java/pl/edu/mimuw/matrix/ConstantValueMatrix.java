package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;

public class ConstantValueMatrix extends Matrix {
  private final double value;

  public ConstantValueMatrix(Shape shape, double value) {
    super(shape);
    this.value = value;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return value;
  }

  @Override
  protected IDoubleMatrix doMultiplication(IDoubleMatrix other, Shape resultShape) {
    return FullMatrix.fromMultiplication(this, other, resultShape);
  }

  @Override
  protected IDoubleMatrix doAddition(IDoubleMatrix other) {
    if(other instanceof ZeroMatrix that) return this;
    return other.plus(value);
  }

  @Override
  protected IDoubleMatrix doScalarOperation(DoubleFunction<Double> operator) {
    return new ConstantValueMatrix(shape(), operator.apply(value));
  }
}
