package pl.edu.mimuw.matrix;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

public class DiagonalMatrix extends Matrix {
  private final int size;
  private final double[] diagonalValues;

  protected DiagonalMatrix(double[] diagonalValues) {
    super(Shape.square(diagonalValues.length));
    this.size = diagonalValues.length;
    this.diagonalValues = diagonalValues;
  }

  public static DiagonalMatrix fromIdentityMatrix(IdentityMatrix matrix) {
    var values = new double[matrix.shape().rows];
    Arrays.fill(values, 1);
    return new DiagonalMatrix(values);
  }

  private static IDoubleMatrix fromElementwiseMerge(DiagonalMatrix a, IDoubleMatrix b, BiFunction<Double, Double, Double> f) {
    var values = new double[a.size];
    Arrays.setAll(values, i -> f.apply(a.get(i, i), b.get(i, i)));
    return new DiagonalMatrix(values);
  }


  @Override
  protected double getButUnchecked(int row, int column) {
    return row == column ? diagonalValues[column] : 0;
  }

  @Override
  protected IDoubleMatrix doMultiplication(IDoubleMatrix other, Shape resultShape) {
    if (other instanceof ZeroMatrix that) return that;
    if (other instanceof IdentityMatrix that) return this;
    if (other instanceof DiagonalMatrix that)
      return fromElementwiseMerge(this, that, (a, b) -> a * b);
    return FullMatrix.fromMultiplication(this, other, resultShape);
  }

  @Override
  protected IDoubleMatrix doAddition(IDoubleMatrix other) {
    if (other instanceof ZeroMatrix that) return this;
    if (other instanceof IdentityMatrix that)
      return fromElementwiseMerge(this, that, Double::sum);
    if (other instanceof DiagonalMatrix that)
      return fromElementwiseMerge(this, that, Double::sum);
    return FullMatrix.fromAddition(this, other);
  }

  @Override
  protected IDoubleMatrix doScalarOperation(DoubleFunction<Double> operator) {
    if (operator.apply(0.0) == 0.0)
      return fromElementwiseMerge(this, this, (a, b) -> operator.apply(a));
    return FullMatrix.fromScalarOperation(this, operator);
  }

  protected IntStream getExistingCellsInRow(int row) {
    return IntStream.of(row);
  }

  protected IntStream getExistingCellsInColumn(int column) {
    return IntStream.of(column);
  }
}
