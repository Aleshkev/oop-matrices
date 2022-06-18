package pl.edu.mimuw.matrix;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

public final class DiagonalMatrix extends Matrix {
  private final int size;
  private final double[] diagonalValues;

  public DiagonalMatrix(double[] diagonalValues) {
    super(Shape.square(diagonalValues.length));
    this.size = diagonalValues.length;
    this.diagonalValues = diagonalValues;
  }

  public static DiagonalMatrix fromIdentityMatrix(IdentityMatrix matrix) {
    var values = new double[matrix.shape().rows];
    Arrays.fill(values, 1);
    return new DiagonalMatrix(values);
  }

  private static DiagonalMatrix fromElementwiseMerge(
      DiagonalMatrix a, DiagonalMatrix b, BiFunction<Double, Double, Double> f) {
    var values = new double[a.size];
    Arrays.setAll(values, i -> f.apply(a.get(i, i), b.get(i, i)));
    return new DiagonalMatrix(values);
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return row == column ? diagonalValues[column] : 0;
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
  protected Matrix times(DiagonalMatrix that) {
    return fromElementwiseMerge(this, that, (a, b) -> a * b);
  }

  @Override
  protected Matrix plus(IdentityMatrix that) {
    return plus(fromIdentityMatrix(that));
  }

  @Override
  protected Matrix plus(DiagonalMatrix that) {
    return fromElementwiseMerge(this, that, Double::sum);
  }

  @Override
  protected Matrix mapCells(DoubleFunction<Double> operator) {
    if (operator.apply(0.0) == 0.0)
      return fromElementwiseMerge(this, this, (a, b) -> operator.apply(a));
    return super.mapCells(operator);
  }

  protected IntStream getIndicesOfExistingCellsInRow(int row) {
    return IntStream.of(row);
  }

  protected IntStream getIndicesOfExistingCellsInColumn(int column) {
    return IntStream.of(column);
  }
}
