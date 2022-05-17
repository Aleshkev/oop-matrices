package pl.edu.mimuw.matrix;

public interface IDoubleMatrix {

  IDoubleMatrix times(IDoubleMatrix other);

  IDoubleMatrix times(double scalar);

  IDoubleMatrix plus(IDoubleMatrix other);

  IDoubleMatrix plus(double scalar);

  IDoubleMatrix minus(IDoubleMatrix other);

  IDoubleMatrix minus(double scalar);

  double get(int row, int column);

  double[][] data();

  /**
   * @return ||A||_1 = max_j sum_i |a_ij|
   */
  double normOne();

  /**
   * @return ||A||_oo = max_i sum_j |a_ij|
   */
  double normInfinity();

  /**
   * @return ||A||_F = sqrt( sum_i sum_j |a_ij|^2 )
   */
  double frobeniusNorm();

  String toString();

  Shape shape();
}
