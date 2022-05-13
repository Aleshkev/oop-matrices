package pl.edu.mimuw.matrix;

public class IdentityMatrix extends Matrix {
  private final int size;

  protected IdentityMatrix(int size) {
    super(Shape.square(size));
    this.size = size;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return row == column ? 1 : 0;
  }

  @Override
  public IDoubleMatrix times(IDoubleMatrix other) {
    return other;
  }

  @Override
  public IDoubleMatrix times(double scalar) {
    return new DiagonalMatrix(StreamUtil.nCopies(size, scalar).toArray());
  }
}
