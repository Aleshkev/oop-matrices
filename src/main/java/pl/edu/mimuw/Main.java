package pl.edu.mimuw;

import pl.edu.mimuw.matrix.DoubleMatrixFactory;
import pl.edu.mimuw.matrix.IDoubleMatrix;
import pl.edu.mimuw.matrix.Shape;

import java.util.Random;
import java.util.stream.IntStream;

public class Main {

  private static final Random random = new Random();

  private static double[] randomArray(int n) {
    return IntStream.range(1, n + 1).mapToDouble(x -> x).toArray();
  }

  private static double[][] randomArray(int rows, int columns) {
    var array = new double[rows][columns];
    for (var y = 0; y < rows; y++)
      for (var x = 0; x < columns; x++)
        array[y][x] = y * columns + x + 1;
    return array;
  }

  public static void main(String[] args) {
    var antiDiagonal = DoubleMatrixFactory.antiDiagonal(randomArray(10));
    var column = DoubleMatrixFactory.column(10, randomArray(10));
    var diagonal = DoubleMatrixFactory.diagonal(randomArray(10));
    var full = DoubleMatrixFactory.full(randomArray(10, 10));
    var identity = DoubleMatrixFactory.identity(10);
    var row = DoubleMatrixFactory.row(10, randomArray(10));
    var sparse = DoubleMatrixFactory.sparse(Shape.matrix(10, 10));
    var vector = DoubleMatrixFactory.vector(randomArray(10));
    var zero = DoubleMatrixFactory.zero(Shape.matrix(10, 10));

    for (var matrix : new IDoubleMatrix[]{antiDiagonal, column, diagonal, full, identity, row, sparse, vector, zero}) {
      System.out.print(matrix);
      System.out.println("normOne = " + matrix.normOne() + ", normInfinity = " + matrix.normInfinity() + ", frobeniusNorm = " + matrix.frobeniusNorm());
      System.out.println("");
    }
  }
}
