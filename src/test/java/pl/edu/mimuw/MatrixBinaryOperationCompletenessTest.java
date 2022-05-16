package pl.edu.mimuw;

import org.junit.jupiter.api.Test;
import pl.edu.mimuw.matrix.DoubleMatrixFactory;
import pl.edu.mimuw.matrix.FullMatrix;
import pl.edu.mimuw.matrix.IDoubleMatrix;
import pl.edu.mimuw.matrix.Shape;

import static pl.edu.mimuw.TestMatrixData.assertArrayEqualsWithTestPrecision;
import static pl.edu.mimuw.matrix.Iteration.randomArray;

public class MatrixBinaryOperationCompletenessTest {
  @Test
  public void testAllCombinations() {
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
          antiDiagonal, column, diagonal, full, identity, row, sparse, zero, constantValue
        };

    for (var a : all) {
      for (var b : all) {
        var aPlusB = a.plus(b);
        var aPlusBRef = FullMatrix.fromMatrix(a).plus(FullMatrix.fromMatrix(b));
        var bPlusA = b.plus(a);
        var aTimesB = a.times(b);
        var aTimesBRef = FullMatrix.fromMatrix(a).times(FullMatrix.fromMatrix(b));

        assertArrayEqualsWithTestPrecision(aPlusBRef.data(), aPlusB.data());
        assertArrayEqualsWithTestPrecision(aPlusBRef.data(), bPlusA.data());
        assertArrayEqualsWithTestPrecision(aTimesBRef.data(), aTimesB.data());
      }
    }
  }
}
