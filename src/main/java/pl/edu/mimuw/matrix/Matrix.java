package pl.edu.mimuw.matrix;

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

  @Override
  public IDoubleMatrix negative() {
    return times(-1);
  }

  @Override
  public IDoubleMatrix minus(IDoubleMatrix other) {
    return plus(other.negative());
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

  // This could be less generic and implemented in subclasses, but the output size requires at least
  // O(n^2) time anyway.
  @Override
  public String toString() {
    var s = new StringBuilder();
    s.append(super.toString()).append(" ").append(shape()).append("\n");

    var width = 7;
    s.append(" { { ");
    for (var y = 0; y < shape().rows; y++) {
      if (y > 0) s.append("   { ");
      for (var x = 0; x < shape().columns; x++) {
        if (x > 0) s.append(", ");
        var zeroesUntil = x;
        while (zeroesUntil < shape().columns && Math.abs(get(y, zeroesUntil)) == 0.0) ++zeroesUntil;

        if (zeroesUntil - x < 3) {
          s.append(StreamUtil.padLeft(Double.toString(get(y, x)), width - 2));
          continue;
        }
        var spaces = " ".repeat(width * (zeroesUntil - x) - "..., ".length());
        if (x == 0 && zeroesUntil < shape().columns) s.append(spaces).append("...");
        else s.append("...").append(spaces);
        x = zeroesUntil - 1;
      }
      if (y + 1 < shape().rows) s.append(" }, \n");
    }
    s.append(" } }\n");

    return s.toString();
  }

  public Shape shape() {
    return theShape;
  }

  protected IntStream existingRows() {
    return IntStream.range(0, shape().rows);
  }

  protected IntStream existingCellsInRow(int row) {
    return IntStream.range(0, shape().columns);
  }

  protected IntStream existingColumns() {
    return IntStream.range(0, shape().columns);
  }

  protected IntStream existingCellsInColumn(int column) {
    return IntStream.range(0, shape().rows);
  }
}
