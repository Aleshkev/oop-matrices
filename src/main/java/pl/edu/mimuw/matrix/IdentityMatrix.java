package pl.edu.mimuw.matrix;

public class IdentityMatrix extends Matrix {
  protected IdentityMatrix(int size) {
    super(Shape.matrix(size, size));
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return row == column ? 1 : 0;
  }
}
