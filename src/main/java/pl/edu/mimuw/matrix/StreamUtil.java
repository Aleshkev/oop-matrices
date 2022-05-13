package pl.edu.mimuw.matrix;

import java.util.function.IntToDoubleFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class StreamUtil {
  private StreamUtil() {}

  public static double mapRangeSum(int endExclusive, IntToDoubleFunction map) {
    return IntStream.range(0, endExclusive).mapToDouble(map).sum();
  }

  public static double mapRangeMax(int endExclusive, IntToDoubleFunction map) {
    return IntStream.range(0, endExclusive).mapToDouble(map).max().orElseThrow();
  }

  public static DoubleStream nCopies(int n, double value) {
    return IntStream.range(0, n).mapToDouble(x -> value);
  }

  public static String padLeft(String s, int n) {
    return String.format("%" + n + "s", s);
  }
}
