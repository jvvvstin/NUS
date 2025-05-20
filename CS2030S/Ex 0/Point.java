/**
 * CS2030S Ex 0: Point.java
 * Semester 1, 2024/25
 *
 * <p>The Point class encapsulates a point on a 2D plane.
 *
 * @author Justin Ng Jia He (Group 14B)
 */
class Point {
  // TODO
  /** The x-coordinate of a point. */
  private double x;

  /** The y-coordinate of a point. */
  private double y;
  
  /** 
   * Constructor for a point. Takes in a x-coordinate x and a
   * y-coordinate y.
   *
   * @param x The x-coordinate of the new point.
   * @param y The y-coordinate of the new point.
   */  
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Calculates the distance squared between two points.
   *
   * @param p The point to calculate.
   * @return distance squared between the two points.
   * */
  public double distSqrBetweenPoints(Point p) {
    double dX = this.x - p.x;
    double dY = this.y - p.y;
    return dX * dX + dY * dY;
  }
  
  /**
   * Return the string representation of this point.
   *
   * @return The string representing of this point in format
   * (x, y) e.g. (0.0, 0.0)
   * */
  public String toString() {
    return "(" + this.x + ", " + this.y + ")";
  }
}
