package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

public abstract class Matrix implements IDoubleMatrix {
  private final Shape shape;

  protected Matrix(Shape shape) {
    this.shape = shape;
  }

  // Multiplication.

  protected final Shape shapeOfThisTimes(IDoubleMatrix that) {
    assert shape().columns == that.shape().rows;
    return Shape.matrix(shape().rows, that.shape().columns);
  }

  protected Matrix times(ZeroMatrix that) {
    return new ZeroMatrix(shapeOfThisTimes(that));
  }

  protected Matrix times(ConstantValueMatrix that) {
    return FullMatrix.fromMultiplication(this, that);
  }

  protected Matrix times(IdentityMatrix that) {
    shapeOfThisTimes(that);
    return this;
  }

  protected Matrix times(DiagonalMatrix that) {
    return FullMatrix.fromMultiplication(this, that);
  }

  protected Matrix times(FullMatrix that) {
    return FullMatrix.fromMultiplication(this, that);
  }

  protected Matrix times(SparseMatrix that) {
    return FullMatrix.fromMultiplication(this, that);
  }

  protected Matrix times(VectorMatrix that) {
    return FullMatrix.fromMultiplication(this, that);
  }

  protected Matrix times(AntiDiagonalMatrix that) {
    return FullMatrix.fromMultiplication(this, that);
  }

  protected Matrix times(ColumnMatrix that) {
    return FullMatrix.fromMultiplication(this, that);
  }

  protected Matrix times(RowMatrix that) {
    return FullMatrix.fromMultiplication(this, that);
  }

  protected abstract Matrix multipliedBy(Matrix that);

  @Override
  public final Matrix times(IDoubleMatrix other) {
    if (other instanceof Matrix) return ((Matrix) other).multipliedBy(this);
    return times(FullMatrix.fromMatrix(other));
  }

  // Addition.

  protected final Shape shapeOfThisPlus(Matrix that) {
    assert shape().equals(that.shape());
    return shape();
  }

  protected Matrix plus(ZeroMatrix that) {
    shapeOfThisPlus(that);
    return this;
  }

  protected Matrix plus(ConstantValueMatrix that) {
    return FullMatrix.fromAddition(this, that);
  }

  protected Matrix plus(IdentityMatrix that) {
    return FullMatrix.fromAddition(this, that);
  }

  protected Matrix plus(DiagonalMatrix that) {
    return FullMatrix.fromAddition(this, that);
  }

  protected Matrix plus(FullMatrix that) {
    return FullMatrix.fromAddition(this, that);
  }

  protected Matrix plus(SparseMatrix that) {
    return FullMatrix.fromAddition(this, that);
  }

  protected Matrix plus(VectorMatrix that) {
    return FullMatrix.fromAddition(this, that);
  }

  protected Matrix plus(AntiDiagonalMatrix that) {
    return FullMatrix.fromAddition(this, that);
  }

  protected Matrix plus(ColumnMatrix that) {
    return FullMatrix.fromAddition(this, that);
  }

  protected Matrix plus(RowMatrix that) {
    return FullMatrix.fromAddition(this, that);
  }

  public abstract Matrix addedTo(Matrix that);

  @Override
  public final Matrix plus(IDoubleMatrix other) {
    if (other instanceof Matrix) return ((Matrix) other).addedTo(this);
    return plus(FullMatrix.fromMatrix(other));
  }

  @Override
  public final Matrix minus(IDoubleMatrix other) {
    return plus(other.times(-1));
  }

  // Scalar multiplication and addition.

  protected Matrix mapCells(DoubleFunction<Double> operator) {
    return FullMatrix.fromMatrix(this).mapCells(operator);
  }

  @Override
  public final IDoubleMatrix times(double scalar) {
    if (scalar == 1) return this;
    return mapCells(x -> scalar * x);
  }

  @Override
  public final IDoubleMatrix plus(double scalar) {
    if (Math.abs(scalar) == 0) return this;
    return mapCells(x -> x + scalar);
  }

  @Override
  public final IDoubleMatrix minus(double scalar) {
    return plus(-scalar);
  }

  // Norm functions.

  @Override
  public double normOne() {
    return getExistingColumns()
        .mapToDouble(j -> getExistingCellsInColumn(j).mapToDouble(i -> Math.abs(get(i, j))).sum())
        .max()
        .orElse(0.0);
  }

  @Override
  public double normInfinity() {
    return getExistingRows()
        .mapToDouble(i -> getExistingCellsInRow(i).mapToDouble(j -> Math.abs(get(i, j))).sum())
        .max()
        .orElse(0.0);
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
    return shape;
  }

  // String representation.

  @Override
  public String toString() {
    var s = new StringBuilder();
    s.append(getClass().getName()).append(" of ").append(shape()).append("\n");

    var width = 7;
    s.append(" { { ");
    for (var y = 0; y < shape().rows; y++) {
      if (y > 0) s.append("   { ");
      for (var x = 0; x < shape().columns; x++) {
        if (x > 0) s.append(", ");
        var zeroesUntil = x;
        while (zeroesUntil < shape().columns && Math.abs(get(y, zeroesUntil)) == 0.0) ++zeroesUntil;

        if (zeroesUntil - x < 3) {
          s.append(Utility.padLeft(Double.toString(get(y, x)), width - 2));
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
