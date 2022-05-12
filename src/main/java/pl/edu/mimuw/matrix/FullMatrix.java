package pl.edu.mimuw.matrix;

public class FullMatrix extends Matrix {
  private final double[][] values;

  public FullMatrix(double[][] values) {
    super(Shape.matrix(values.length, values.length > 0 ? values[0].length : -1));

    for (var y = 0; y < shape().rows; y++) assert values[y].length == shape().columns;
    this.values = values;
  }

  @Override
  public double[][] data() {
    return values;
  }

  @Override
  protected double getButUnchecked(int row, int column) {
    return values[row][column];
  }
}
