package pl.edu.mimuw.matrix;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

public final class AntiDiagonalMatrix extends Matrix {
  private final int size;
  private final double[] antiDiagonalValues;

  public AntiDiagonalMatrix(double... antiDiagonalValues) {
    super(Shape.square(antiDiagonalValues.length));
    this.antiDiagonalValues = antiDiagonalValues;
    this.size = antiDiagonalValues.length;
  }

  /**
   * @return The matrix created by applying the binary function {@code f} to every pair of a value
   *     from the first and a value from the second matrix.
   */
  private static AntiDiagonalMatrix fromElementwiseMerge(
      AntiDiagonalMatrix a, AntiDiagonalMatrix b, BiFunction<Double, Double, Double> f) {
    var values = new double[a.size];
    Arrays.setAll(values, i -> f.apply(a.antiDiagonalValues[i], b.antiDiagonalValues[i]));
    return new AntiDiagonalMatrix(values);
  }

  @Override
  public double getButUnchecked(int row, int column) {
    if (row == shape().edge() - column - 1) return antiDiagonalValues[row];
    return 0;
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
  protected Matrix plus(AntiDiagonalMatrix that) {
    return fromElementwiseMerge(this, that, Double::sum);
  }

  @Override
  protected Matrix mapCells(DoubleFunction<Double> operator) {
    return fromElementwiseMerge(this, this, (a, b) -> operator.apply(a));
  }

  protected IntStream getExistingCellsInRow(int row) {
    return IntStream.of(size - row - 1);
  }

  protected IntStream getExistingCellsInColumn(int column) {
    return IntStream.of(size - column - 1);
  }
}
