package pl.edu.mimuw.matrix;

public final class VectorMatrix extends Matrix {
  private final double[] values;

  public VectorMatrix(double[] values) {
    super(Shape.vector(values.length));
    this.values = values;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    assert column == 0;
    return values[row];
  }
}
