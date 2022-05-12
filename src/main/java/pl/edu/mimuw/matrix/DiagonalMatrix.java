package pl.edu.mimuw.matrix;

public class DiagonalMatrix extends Matrix {
  private final double[] diagonalValues;

  protected DiagonalMatrix(double... diagonalValues) {
    super(Shape.matrix(diagonalValues.length, diagonalValues.length));
    this.diagonalValues = diagonalValues;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return row == column ? diagonalValues[column] : 0;
  }
}
