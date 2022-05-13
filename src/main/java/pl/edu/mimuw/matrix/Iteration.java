package pl.edu.mimuw.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Iteration {
  private Iteration() {}

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

  public static <T> T[] mapArray(T[] array, Function<T, T> f) {
    //noinspection unchecked
    return (T[]) Arrays.stream(array).map(f).toArray();
  }

  public static <T> List<T> sortAndRemoveDuplicates(List<T> list) {
    return list.parallelStream().distinct().sorted().collect(Collectors.toList());
  }
}
