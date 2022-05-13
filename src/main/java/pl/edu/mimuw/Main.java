package pl.edu.mimuw;

import pl.edu.mimuw.matrix.DoubleMatrixFactory;
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
      for(var x = 0; x < columns; x++)
        array[y][x] = y * columns + x + 1;
    return array;
  }

  public static void main(String[] args) {
    var antiDiagonal = DoubleMatrixFactory.antiDiagonal(randomArray(10));
    System.out.println(antiDiagonal);
    var column = DoubleMatrixFactory.column(10, randomArray(10));
    System.out.println(column);
    var diagonal = DoubleMatrixFactory.diagonal(randomArray(10));
    System.out.println(diagonal);
    var full = DoubleMatrixFactory.full(randomArray(10, 10));
    System.out.println(full);
    var identity = DoubleMatrixFactory.identity(10);
    System.out.println(identity);
    var row = DoubleMatrixFactory.row(10, randomArray(10));
    System.out.println(row);
    var sparse = DoubleMatrixFactory.sparse(Shape.matrix(10, 10));
    System.out.println(sparse);
    var vector = DoubleMatrixFactory.vector(randomArray(10));
    System.out.println(vector);
    var zero = DoubleMatrixFactory.zero(Shape.matrix(10, 10));
    System.out.println(zero);
  }
}
