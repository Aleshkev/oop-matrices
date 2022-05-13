package pl.edu.mimuw.matrix;

import java.util.stream.IntStream;

public final class ZeroMatrix extends Matrix {
  public ZeroMatrix(Shape theShape) {
    super(theShape);
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return 0;
  }

  @Override
  public IDoubleMatrix timesButUnchecked(IDoubleMatrix other) {
    return this;
  }

  @Override
  public IDoubleMatrix times(double scalar) {
    return this;
  }

  @Override
  protected IDoubleMatrix plusButUnchecked(IDoubleMatrix other) {
    return other;
  }

  @Override
  protected IntStream existingRows() {
    return IntStream.empty();
  }

  @Override
  protected IntStream existingCellsInRow(int row) {
    return IntStream.empty();
  }

  @Override
  protected IntStream existingColumns() {
    return IntStream.empty();
  }

  @Override
  protected IntStream existingCellsInColumn(int column) {
    return IntStream.empty();
  }
}
