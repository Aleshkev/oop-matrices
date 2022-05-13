package pl.edu.mimuw.matrix;

public class DiagonalMatrix extends Matrix {
  private final int size;
  private final double[] diagonalValues;

  protected DiagonalMatrix(double[] diagonalValues) {
    super(Shape.square(diagonalValues.length));
    this.size = diagonalValues.length;
    this.diagonalValues = diagonalValues;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return row == column ? diagonalValues[column] : 0;
  }


}
