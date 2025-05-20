/**
 * CS2030S Ex 0: RandomPoint.java
 * Semester 1, 2024/25
 *
 * <p>The RandomPoint class encapsulates a random point on a 2D plane.
 *
 * @author Justin Ng Jia He (Group 14B)
 */
import java.util.Random;

// TODO
class RandomPoint extends Point{
  /** The minimum value of x. */
  private double minX;

  /** The maximum value of x. */
  private double maxX;

  /** The minimum value of y. */
  private double minY;

  /** The maximum value of y. */
  private double maxY;

  /** The random number generator with default seed value of 1. */
  private static Random rng = new Random(1);

  /** 
   * Constructor for a random point. Takes in a minimum x value minX, maximum x
   * value maxX, minimum y value minY, and maximum y value maxY.
   *
   * @param minX The minimum x value of the new random point.
   * @param maxX The maximum x value of the new random point.
   * @param minY The minimum y value of the new random point.
   * @param maxY The maximum y value of the new random point.
   */  
  public RandomPoint(double minX, double maxX, double minY, double maxY) {
    // instantiates a Point of random x and y values, between the min and max x
    // values supplied
    super(rng.nextDouble() * (maxX - minX) + minX, rng.nextDouble() * 
        (maxY - minY) + minY);
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
  }
  
  /**
   * Sets the seed of the random number generator.
   * */
  public static void setSeed(long seed) {
    RandomPoint.rng.setSeed(seed);
  }
}
