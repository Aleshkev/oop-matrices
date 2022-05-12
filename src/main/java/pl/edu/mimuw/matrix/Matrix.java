package pl.edu.mimuw.matrix;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public abstract class Matrix implements IDoubleMatrix {
  private final Shape theShape;

  protected Matrix(Shape theShape) {
    this.theShape = theShape;
  }

  private IDoubleMatrix createFullMatrix(
      Shape matrixShape, BiFunction<Integer, Integer, Double> getCellValue) {
    var values = new double[matrixShape.rows][matrixShape.columns];
    for (var y = 0; y < matrixShape.rows; y++)
      for (var x = 0; x < matrixShape.columns; x++) values[y][x] = getCellValue.apply(x, y);
    return new FullMatrix(values);
  }

  public IDoubleMatrix timesButUnchecked(IDoubleMatrix other) {
    var depth = shape().columns;
    return createFullMatrix(
        Shape.matrix(shape().rows, other.shape().columns),
        (x, y) -> IntStream.range(0, depth).mapToDouble(i -> get(y, i) * other.get(i, x)).sum());
  }

  @Override
  public IDoubleMatrix times(IDoubleMatrix other) {
    assert shape().columns == other.shape().rows;
    return timesButUnchecked(other);
  }

  @Override
  public IDoubleMatrix times(double scalar) {
    return createFullMatrix(shape(), (x, y) -> get(y, x) * scalar);
  }

  @Override
  public IDoubleMatrix plus(double scalar) {
    return createFullMatrix(shape(), (x, y) -> get(y, x) + scalar);
  }

  @Override
  public IDoubleMatrix minus(double scalar) {
    return plus(scalar != 0.0 ? -scalar : scalar);
  }

  protected IDoubleMatrix plusButUnchecked(IDoubleMatrix other) {
    return createFullMatrix(shape(), (x, y) -> get(y, x) + other.get(y, x));
  }

  @Override
  public IDoubleMatrix plus(IDoubleMatrix other) {
    assert shape().equals(other.shape());
    return plusButUnchecked(other);
  }

  protected IDoubleMatrix minusButUnchecked(IDoubleMatrix other) {
    return createFullMatrix(shape(), (x, y) -> get(y, x) - other.get(y, x));
  }

  @Override
  public IDoubleMatrix minus(IDoubleMatrix other) {
    assert shape().equals(other.shape());
    return minusButUnchecked(other);
  }

  @Override
  public double normOne() {
    return StreamUtil.mapRangeMax(
        shape().columns, j -> StreamUtil.mapRangeSum(shape().rows, i -> Math.abs(get(i, j))));
  }

  @Override
  public double normInfinity() {
    return StreamUtil.mapRangeMax(
        shape().rows, i -> StreamUtil.mapRangeSum(shape().columns, j -> Math.abs(get(i, j))));
  }

  @Override
  public double frobeniusNorm() {
    return Math.sqrt(
        StreamUtil.mapRangeSum(
            shape().rows,
            i -> StreamUtil.mapRangeSum(shape().columns, j -> Math.pow(get(i, j), 2))));
  }

  public double[][] data() {
    return createFullMatrix(shape(), (x, y) -> get(y, x)).data();
  }

  protected abstract double getButUnchecked(int row, int column);

  public double get(int row, int column) {
    shape().assertInShape(row, column);
    return getButUnchecked(row, column);
  }

  @Override
  public String toString() {
    return super.toString() + " " + shape() + " " + Arrays.deepToString(data());
  }

  public Shape shape() {
    return theShape;
  }
}
