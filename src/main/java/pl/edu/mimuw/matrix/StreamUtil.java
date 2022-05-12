package pl.edu.mimuw.matrix;

import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;

public class StreamUtil {
  private StreamUtil() {}

  public static double mapRangeSum(int endExclusive, IntToDoubleFunction map) {
    return IntStream.range(0, endExclusive).mapToDouble(map).sum();
  }

  public static double mapRangeMax(int endExclusive, IntToDoubleFunction map) {
    return IntStream.range(0, endExclusive).mapToDouble(map).max().orElseThrow();
  }
}
