package pl.edu.mimuw;

import org.junit.jupiter.api.Test;
import pl.edu.mimuw.matrix.DoubleMatrixFactory;
import pl.edu.mimuw.matrix.FullMatrix;
import pl.edu.mimuw.matrix.IDoubleMatrix;
import pl.edu.mimuw.matrix.Shape;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.edu.mimuw.TestMatrixData.assertArrayEqualsWithTestPrecision;
import static pl.edu.mimuw.matrix.Utility.randomArray;

public class MatrixOperationCompletenessTest {
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

    for (var a : all) {
      var aNormOne = a.normOne();
      var aNormOneRef = FullMatrix.fromMatrix(a).normOne();
      var aNormInfinity = a.normInfinity();
      var aNormInfinityRef = FullMatrix.fromMatrix(a).normInfinity();
      var aFrobeniusNorm = FullMatrix.fromMatrix(a).frobeniusNorm();
      var aFrobeniusNormRef = FullMatrix.fromMatrix(a).frobeniusNorm();
      assertEquals(aNormOne, aNormOneRef);
      assertEquals(aNormInfinity, aNormInfinityRef);
      assertEquals(aFrobeniusNorm, aFrobeniusNormRef);
    }
  }
}
