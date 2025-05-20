import java.util.Scanner;

/**
 * CS2030S Ex 0: Estimating Pi with Monte Carlo
 * Semester 1, 2024/25
 *
 * <p>This program takes in two command line arguments: the 
 * number of points and a random seed.  It runs the
 * Monte Carlo simulation with the given argument and print
 * out the estimated pi value.
 *
 * @author Justin Ng Jia He (Group 14B)
 */

class Ex0 {

  // TODO 
  public static double estimatePi(int numOfPoints, int seed) {
    // set seed of Random number generator
    RandomPoint.setSeed(seed);

    // instantiate circle centred at (0.5, 0.5) with radius 0.5
    Circle c = new Circle(new Point(0.5, 0.5), 0.5);

    // counter for looping through x times where x refers to the numOfPoints
    int i = 0;

    // counter for number of points that fall within the circle
    // set to double so as to prevent integer division
    double numOfPointsWithinCircle = 0;
    
    // loops through every random point generated
    while (i < numOfPoints) {
      // checks if the random point generated falls within the circle
      if (c.contains(new RandomPoint(0, 1, 0, 1))) {
        numOfPointsWithinCircle++;
      }
      i++;
    }

    // formula to calculate the estimated value of Pi = 4n / k
    double estPi = (4 * numOfPointsWithinCircle) / numOfPoints;

    return estPi;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int numOfPoints = sc.nextInt();
    int seed = sc.nextInt();

    double pi = estimatePi(numOfPoints, seed);

    System.out.println(pi);
    sc.close();
  }
}
