package pl.edu.mimuw.matrix;

import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

/**
 * The implementation of the {@code IDoubleMatrix} interface.
 *
 * <p>Together with the subclasses, it uses double dispatch to dispatch the implementations of
 * binary operations. The methods used internally to do that are defined here, so as not to share
 * them in the public interface. This results in a single check and a cast to see if the object on
 * the right side of a binary operation is native to this module, and must be converted to a
 * compatible type if not.
 */
public abstract class Matrix implements IDoubleMatrix {
  private final Shape shape;

  protected Matrix(Shape shape) {
    this.shape = shape;
  }

  // Multiplication.

  /**
   * @return The shape of the matrix that is the result of this x that. Raises AssertionError if the
   *     shapes don't allow multiplication.
   */
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

  /** Dispatch the multiplication to the appropriate method. */
  protected abstract Matrix multipliedBy(Matrix that);

  // This is final because we want the subclasses to implement each narrow overload separately.
  @Override
  public final Matrix times(IDoubleMatrix other) {
    if (other instanceof Matrix) return ((Matrix) other).multipliedBy(this);
    return times(FullMatrix.fromMatrix(other));
  }

  // Addition.

  /**
   * @return The shape of the matrix that is the result of this + that. Raises AssertionError if the
   *     shapes don't allow addition.
   */
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

  /** Dispatch the addition to the appropriate method. */
  public abstract Matrix addedTo(Matrix that);

  // This is final because we want the subclasses to implement each narrow overload separately.
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

  /**
   * @return A matrix created by applying {@code operator} to every value of a cell in the matrix.
   */
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
  //
  // They use the helper getExistingCells* methods not to traverse empty cells, so they often don't
  // need to be overridden to be optimally efficient.

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

  /**
   * Assumes the coordinates are correct.
   *
   * @return The value of the cell at the given position.
   */
  protected abstract double getButUnchecked(int row, int column);

  public double get(int row, int column) {
    shape().assertInShape(row, column);
    return getButUnchecked(row, column);
  }

  // Metadata used by other methods.

  /**
   * @return Stream with the y coordinates of all rows which contain non-zero values.
   */
  protected IntStream getExistingRows() {
    return IntStream.range(0, shape().rows);
  }

  /**
   * @return Stream with the x coordinates of all non-zero cells in the row.
   */
  protected IntStream getExistingCellsInRow(int row) {
    return IntStream.range(0, shape().columns);
  }

  /**
   * @return Stream with the x coordinates of all columns which contain non-zero values.
   */
  protected IntStream getExistingColumns() {
    return IntStream.range(0, shape().columns);
  }

  /**
   * @return Stream with the y coordinates of all non-zero cells in the column.
   */
  protected IntStream getExistingCellsInColumn(int column) {
    return IntStream.range(0, shape().rows);
  }

  public Shape shape() {
    return shape;
  }

  // String representation.

  /**
   * @return A string representation of the matrix; contains the class name, dimensions, and all the
   *     data inside, in a tabular form. Converts long spans of zeroes to ellipses.
   */
  @Override
  public String toString() {
    var s = new StringBuilder();
    s.append(getClass().getName()).append(" of ").append(shape()).append("\n");

    var width = 7; // Width of a cell in the string representation.
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
