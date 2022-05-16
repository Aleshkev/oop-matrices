package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;

public final class ConstantValueMatrix extends Matrix {
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
  protected Matrix multipliedBy(Matrix that) {
    return that.times(this);
  }

  @Override
  public Matrix addedTo(Matrix that) {
    return that.plus(this);
  }

  @Override
  protected Matrix plus(ConstantValueMatrix that) {
    return new ConstantValueMatrix(shapeOfThisPlus(that), value + that.value);
  }

  @Override
  protected Matrix mapCells(DoubleFunction<Double> operator) {
    return new ConstantValueMatrix(shape(), operator.apply(value));
  }
}
