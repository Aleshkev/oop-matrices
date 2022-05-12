package pl.edu.mimuw.matrix;

public final class AntiDiagonalMatrix extends DiagonalMatrix {
  public AntiDiagonalMatrix(double... diagonalValues) {
    super(diagonalValues);
  }

  @Override
  public double get(int row, int column) {
    return super.get(shape().rows - row - 1, column);
  }
}
