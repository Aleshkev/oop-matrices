package pl.edu.mimuw.matrix;

public class DoubleMatrixFactory {

  private DoubleMatrixFactory() {}

  public static IDoubleMatrix sparse(Shape shape, MatrixCellValue... values) {
    return new SparseMatrix(shape, values);
  }

  public static IDoubleMatrix full(double[][] values) {
    assert values != null;
    return new FullMatrix(values);
  }

  public static IDoubleMatrix identity(int size) {
    return new IdentityMatrix(size);
  }

  public static IDoubleMatrix diagonal(double... diagonalValues) {
    return new DiagonalMatrix(diagonalValues);
  }

  public static IDoubleMatrix antiDiagonal(double... antiDiagonalValues) {
    return new AntiDiagonalMatrix(antiDiagonalValues);
  }

  public static IDoubleMatrix vector(double... values) {
    return new VectorMatrix(values);
  }

  public static IDoubleMatrix zero(Shape shape) {
    return new ZeroMatrix(shape);
  }

  public static IDoubleMatrix column(int columns, double... columnValues) {
    return new ColumnMatrix(columns, columnValues);
  }
  public static IDoubleMatrix row(int rows, double... rowValues) {
    return new RowMatrix(rows, rowValues);
  }
}
