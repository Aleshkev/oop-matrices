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
  protected Matrix multipliedBy(Matrix what) {
    return what.times(this);
  }

  @Override
  public Matrix addedTo(Matrix what) {
    return what.plus(this);
  }

  @Override
  protected Matrix plus(ConstantValueMatrix that) {
    return new ConstantValueMatrix(additionResultShape(that), value + that.value);
  }

  @Override
  protected IDoubleMatrix applyElementwise(DoubleFunction<Double> operator) {
    return new ConstantValueMatrix(shape(), operator.apply(value));
  }
}
