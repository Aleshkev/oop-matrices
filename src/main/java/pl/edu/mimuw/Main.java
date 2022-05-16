package pl.edu.mimuw;

import pl.edu.mimuw.matrix.DoubleMatrixFactory;
import pl.edu.mimuw.matrix.IDoubleMatrix;
import pl.edu.mimuw.matrix.Shape;

import static pl.edu.mimuw.matrix.Utility.randomArray;

// Used IDE: IntelliJ.
// GitHub repo: https://github.com/Aleshkev/oop-matrices (private until submission deadline).

public class Main {

  public static void main(String[] args) {
    // Example matrices.

    var antiDiagonal = DoubleMatrixFactory.antiDiagonal(randomArray(10));
    var column = DoubleMatrixFactory.column(10, randomArray(10));
    var diagonal = DoubleMatrixFactory.diagonal(randomArray(10));
    var full = DoubleMatrixFactory.full(randomArray(10, 10));
    var identity = DoubleMatrixFactory.identity(10);
    var row = DoubleMatrixFactory.row(10, randomArray(10));
    var sparse = DoubleMatrixFactory.sparse(Shape.matrix(10, 10));
    var vector = DoubleMatrixFactory.vector(randomArray(10));
    var zero = DoubleMatrixFactory.zero(Shape.matrix(10, 10));
    var constantValue = DoubleMatrixFactory.constantValue(Shape.matrix(10, 10), 5);

    var all =
        new IDoubleMatrix[] {
          antiDiagonal, column, diagonal, full, identity, row, sparse, vector, zero, constantValue
        };

    for (var matrix : all) {
      System.out.print(matrix);
      System.out.println(
          "normOne = "
              + matrix.normOne()
              + ", normInfinity = "
              + matrix.normInfinity()
              + ", frobeniusNorm = "
              + matrix.frobeniusNorm());
      System.out.println();
    }
  }
}
