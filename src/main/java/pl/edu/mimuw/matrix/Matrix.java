package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

public abstract class Matrix implements IDoubleMatrix {
  private final Shape theShape;

  protected Matrix(Shape theShape) {
    this.theShape = theShape;
  }

  // Multiplication.

  protected abstract IDoubleMatrix doMultiplication(IDoubleMatrix other, Shape resultShape);

  @Override
  public IDoubleMatrix times(IDoubleMatrix other) {
    assert shape().columns == other.shape().rows;
    return doMultiplication(other, Shape.matrix(shape().rows, other.shape().columns));
  }

  // Addition.

  protected abstract IDoubleMatrix doAddition(IDoubleMatrix other);

  @Override
  public IDoubleMatrix plus(IDoubleMatrix other) {
    assert shape().equals(other.shape());
    return doAddition(other);
  }

  @Override
  public IDoubleMatrix minus(IDoubleMatrix other) {
    return plus(other.times(-1));
  }

  // Scalar multiplication and addition.

  protected abstract IDoubleMatrix doScalarOperation(DoubleFunction<Double> operator);

  @Override
  public IDoubleMatrix times(double scalar) {
    if (scalar == 1) return this;
    return doScalarOperation(x -> scalar * x);
  }

  @Override
  public IDoubleMatrix plus(double scalar) {
    if (Math.abs(scalar) == 0) return this;
    return doScalarOperation(x -> x + scalar);
  }

  @Override
  public IDoubleMatrix minus(double scalar) {
    return plus(-scalar);
  }

  // Norm functions.

  @Override
  public double normOne() {
    return getExistingColumns()
        .mapToDouble(j -> getExistingCellsInColumn(j).mapToDouble(i -> Math.abs(get(i, j))).sum())
        .max()
        .orElseThrow();
  }

  @Override
  public double normInfinity() {
    return getExistingRows()
        .mapToDouble(i -> getExistingCellsInRow(i).mapToDouble(j -> Math.abs(get(i, j))).sum())
        .max()
        .orElseThrow();
  }

  @Override
  public double frobeniusNorm() {
    return Math.sqrt(
        getExistingRows()
            .mapToDouble(
                i -> getExistingCellsInRow(i).mapToDouble(j -> Math.pow(get(i, j), 2)).sum())
            .sum());
  }

  // Retrieving the cells.

  public double[][] data() {
    return FullMatrix.fromMatrix(this).data();
  }

  protected abstract double getButUnchecked(int row, int column);

  public double get(int row, int column) {
    shape().assertInShape(row, column);
    return getButUnchecked(row, column);
  }

  // Metadata used by other methods.

  protected IntStream getExistingRows() {
    return IntStream.range(0, shape().rows);
  }

  protected IntStream getExistingCellsInRow(int row) {
    return IntStream.range(0, shape().columns);
  }

  protected IntStream getExistingColumns() {
    return IntStream.range(0, shape().columns);
  }

  protected IntStream getExistingCellsInColumn(int column) {
    return IntStream.range(0, shape().rows);
  }

  public Shape shape() {
    return theShape;
  }

  // String representation.

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
          s.append(Iteration.padLeft(Double.toString(get(y, x)), width - 2));
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
}
