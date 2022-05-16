package pl.edu.mimuw.matrix;

import java.util.Random;
import java.util.stream.IntStream;

public class Iteration {
  private static final Random random = new Random();

  private Iteration() {}

  public static double[] randomArray(int n) {
    return IntStream.range(1, n + 1).mapToDouble(x -> x).toArray();
  }

  public static double[][] randomArray(int rows, int columns) {
    var array = new double[rows][columns];
    for (var y = 0; y < rows; y++)
      for (var x = 0; x < columns; x++) array[y][x] = y * columns + x + 1;
    return array;
  }

  public static String padLeft(String s, int n) {
    return String.format("%" + n + "s", s);
  }
}
