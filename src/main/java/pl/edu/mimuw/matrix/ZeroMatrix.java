package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

public final class ZeroMatrix extends Matrix {
  public ZeroMatrix(Shape theShape) {
    super(theShape);
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return 0;
  }

  @Override
  protected IDoubleMatrix doMultiplication(IDoubleMatrix other, Shape resultShape) {
    return new ZeroMatrix(resultShape);
  }

  @Override
  protected IDoubleMatrix doAddition(IDoubleMatrix other) {
    return other;
  }

  @Override
  protected IDoubleMatrix doScalarOperation(DoubleFunction<Double> operator) {
    return new ConstantValueMatrix(shape(), operator.apply(0.0));
  }

  @Override
  public double normOne() {
    return 0;
  }

  @Override
  public double normInfinity() {
    return 0;
  }

  @Override
  public double frobeniusNorm() {
    return 0;
  }

  @Override
  protected IntStream getExistingRows() {
    return IntStream.empty();
  }

  @Override
  protected IntStream getExistingCellsInRow(int row) {
    return IntStream.empty();
  }

  @Override
  protected IntStream getExistingColumns() {
    return IntStream.empty();
  }

  @Override
  protected IntStream getExistingCellsInColumn(int column) {
    return IntStream.empty();
  }
}
