package pl.edu.mimuw.matrix;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

public class FullMatrix extends Matrix {
  private final double[][] values;

  public FullMatrix(double[][] values) {
    super(Shape.matrix(values.length, values.length > 0 ? values[0].length : -1));

    for (var y = 0; y < shape().rows; y++)
      assert values[y].length == shape().columns;
    this.values = values;
  }

  private static FullMatrix fromFunction(
          Shape matrixShape, BiFunction<Integer, Integer, Double> getCellValue) {
    var values = new double[matrixShape.rows][matrixShape.columns];
    for (var y = 0; y < matrixShape.rows; y++)
      for (var x = 0; x < matrixShape.columns; x++)
        values[y][x] = getCellValue.apply(x, y);
    return new FullMatrix(values);
  }

  public static FullMatrix fromMatrix(IDoubleMatrix matrix) {
    return fromFunction(matrix.shape(), (x, y) -> matrix.get(y, x));
  }

  public static FullMatrix fromMultiplication(IDoubleMatrix a, IDoubleMatrix b, Shape resultShape) {
    var depth = a.shape().columns;
    return fromFunction(resultShape,
            (x, y) -> IntStream.range(0, depth).mapToDouble(i -> a.get(y, i) * b.get(i, x)).sum());
  }

  public static FullMatrix fromAddition(IDoubleMatrix a, IDoubleMatrix b) {
    return fromFunction(a.shape(), (x, y) -> a.get(y, x) + b.get(y, x));
  }

  public static FullMatrix fromScalarOperation(
          IDoubleMatrix matrix, DoubleFunction<Double> operator) {
    return fromFunction(matrix.shape(), (x, y) -> operator.apply(matrix.get(y, x)));
  }

  @Override
  protected IDoubleMatrix doMultiplication(IDoubleMatrix other, Shape resultShape) {
    if (other instanceof ZeroMatrix that) return new ZeroMatrix(resultShape);
    if (other instanceof IdentityMatrix that) return this;
    return fromMultiplication(this, other, resultShape);
  }

  @Override
  protected IDoubleMatrix doAddition(IDoubleMatrix other) {
    if (other instanceof ZeroMatrix that) return this;
    return fromAddition(this, other);
  }

  @Override
  protected IDoubleMatrix doScalarOperation(DoubleFunction<Double> operator) {
    return fromScalarOperation(this, operator);
  }

  @Override
  public double[][] data() {
    return values;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return values[row][column];
  }
}
